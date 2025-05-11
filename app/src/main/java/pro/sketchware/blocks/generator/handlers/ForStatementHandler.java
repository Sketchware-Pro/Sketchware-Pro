package pro.sketchware.blocks.generator.handlers;

import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.CastExpr;
import com.github.javaparser.ast.expr.EnclosedExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.UnaryExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.ForEachStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.besome.sketch.beans.BlockBean;

import pro.sketchware.blocks.generator.EventBlocksGenerator;
import pro.sketchware.blocks.generator.builders.ExpressionBlockBuilder;
import pro.sketchware.blocks.generator.interfaces.StatementHandler;
import pro.sketchware.blocks.generator.records.HandlerContext;
import pro.sketchware.blocks.generator.records.RequiredBlockType;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class ForStatementHandler implements StatementHandler {

    private final EventBlocksGenerator parent;
    private final ExpressionBlockBuilder expressionBlockBuilder;

    public ForStatementHandler(EventBlocksGenerator parent, ExpressionBlockBuilder expressionBlockBuilder) {
        this.parent = parent;
        this.expressionBlockBuilder = expressionBlockBuilder;
    }

    @Override
    public boolean canHandle(Statement stmt) {
        return stmt instanceof ForStmt || stmt instanceof ForEachStmt;
    }

    @Override
    public void handle(Statement stmt, int id, HandlerContext context) {
        String opCode;
        String spec;
        ArrayList<String> parameters = new ArrayList<>();
        Statement bodyStmt;

        if (stmt instanceof ForStmt fs) {
            bodyStmt = fs.getBody();

            if (fs.getInitialization().size() == 1 && fs.getInitialization().get(0) instanceof VariableDeclarationExpr vde) {
                var varDeclaration = vde.getVariable(0);
                if (varDeclaration.getInitializer().isPresent()) {
                    String varName = varDeclaration.getNameAsString();

                    if (fs.getCompare().isPresent() && fs.getCompare().get() instanceof BinaryExpr be) {
                        Expression rawRight = be.getRight();
                        Expression right = getEnclosedInner(rawRight);

                        if (fs.getUpdate().size() == 1 && fs.getUpdate().get(0) instanceof UnaryExpr ue && ue.getExpression().isNameExpr() && ue.getExpression().asNameExpr().getNameAsString().equals(varName)) {

                            if (ue.getOperator() == UnaryExpr.Operator.POSTFIX_INCREMENT && be.getOperator() == BinaryExpr.Operator.LESS && varDeclaration.getInitializer().get().toString().equals("0")) {
                                if (varName.matches("^_repeat\\d+$")) {
                                    buildBlock(id, context, "repeat", "repeat %s", parameters, right, bodyStmt);
                                } else {
                                    parameters.add(varName);
                                    buildBlock(id, context, "repeatKnownNum", "repeat %d: %s.inputOnly ++", parameters, right, bodyStmt);
                                }
                                return;

                            } else if (ue.getOperator() == UnaryExpr.Operator.POSTFIX_DECREMENT && be.getOperator() == BinaryExpr.Operator.GREATER) {

                                Expression initExpr = varDeclaration.getInitializer().get();
                                Expression simplified = getEnclosedInner(initExpr);

                                if (simplified instanceof BinaryExpr binExpr &&
                                        binExpr.getOperator() == BinaryExpr.Operator.MINUS &&
                                        binExpr.getRight() instanceof IntegerLiteralExpr literal &&
                                        literal.getValue().equals("1")) {

                                    parameters.add(varName);
                                    right = getEnclosedInner(binExpr.getLeft());
                                    buildBlock(id, context, "RepeatKnownNumDescending", "repeat %d: %s.inputOnly --", parameters, right, bodyStmt);
                                    return;
                                }
                            }
                        }
                    }
                }
            }
            opCode = "ASDForLoop";
            spec = "for %s.inputOnly";
            bodyStmt = fs.getBody();
            parameters.add(getForStmtHeader(fs));
        } else {
            opCode = "ASDForLoop";
            spec = "for %s.inputOnly";
            bodyStmt = ((ForEachStmt) stmt).getBody();
            var fes = (ForEachStmt) stmt;
            String header = fes.getVariable() + " : " + fes.getIterable();
            parameters.add(header);
        }
        buildBlock(id, context, opCode, spec, parameters, null, bodyStmt);
    }

    private void buildBlock(int id, HandlerContext context, String opCode, String spec, ArrayList<String> parameters, Expression expression, Statement bodyStmt) {

        BlockBean forBlock = new BlockBean(String.valueOf(id), spec, "c", "", opCode);
        if (expression != null) {
            if (expression instanceof IntegerLiteralExpr expr) {
                parameters.add(0, expr.getValue());
            } else {
                ArrayList<BlockBean> exprBlocks = expressionBlockBuilder.build(expression, context.idCounter().getAndIncrement(), new RequiredBlockType("d", null), "");
                BlockBean bean = exprBlocks.get(exprBlocks.size() - 1);
                parameters.add(0, "@" + bean.id);
                context.blockBeans().addAll(exprBlocks);
            }
        }
        forBlock.parameters = parameters;
        context.blockBeans().add(forBlock);

        if (bodyStmt.isBlockStmt()) {
            var statements = bodyStmt.asBlockStmt().getStatements();
            forBlock.subStack1 = statements.isEmpty() ? -1 : context.idCounter().get();
            for (int i = 0; i < statements.size(); i++) {
                if (i == statements.size() - 1)
                    context.noNextBlocks().add(String.valueOf(context.idCounter().get()));
                parent.processStatement(statements.get(i));
            }
        } else forBlock.subStack1 = -1;

        forBlock.nextBlock = context.idCounter().get();
    }

    private Expression getEnclosedInner(Expression expr) {
        while (true) {
            if (expr instanceof EnclosedExpr ee) {
                expr = ee.getInner();
            } else if (expr instanceof CastExpr ce) {
                expr = ce.getExpression();
            } else break;
        }
        return expr;
    }

    private String getForStmtHeader(ForStmt fs) {
        String init = fs.getInitialization().stream()
                .map(Object::toString)
                .collect(Collectors.joining(", "));

        String compare = fs.getCompare().map(Object::toString).orElse("");

        String update = fs.getUpdate().stream()
                .map(Object::toString)
                .collect(Collectors.joining(", "));

        return init + "; " + compare + "; " + update;
    }

}
