package pro.sketchware.blocks.generator.components.handlers;

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

import pro.sketchware.blocks.generator.components.interfaces.StatementHandler;
import pro.sketchware.blocks.generator.components.BlockGeneratorCoordinator;
import pro.sketchware.blocks.generator.components.records.RequiredBlockType;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class ForStatementHandler implements StatementHandler {

    private final BlockGeneratorCoordinator blockGeneratorCoordinator;

    public ForStatementHandler(BlockGeneratorCoordinator blockGeneratorCoordinator) {
        this.blockGeneratorCoordinator = blockGeneratorCoordinator;
    }

    @Override
    public boolean canHandle(Statement stmt) {
        return stmt instanceof ForStmt || stmt instanceof ForEachStmt;
    }

    @Override
    public void handle(Statement stmt) {
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
                                    buildBlock("repeat", "repeat %s", parameters, right, bodyStmt);
                                } else {
                                    parameters.add(varName);
                                    buildBlock("repeatKnownNum", "repeat %d: %s.inputOnly ++", parameters, right, bodyStmt);
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
                                    buildBlock("RepeatKnownNumDescending", "repeat %d: %s.inputOnly --", parameters, right, bodyStmt);
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
        buildBlock(opCode, spec, parameters, null, bodyStmt);
    }

    private void buildBlock(String opCode, String spec, ArrayList<String> parameters, Expression expression, Statement bodyStmt) {

        BlockBean forBlock = new BlockBean(String.valueOf(blockGeneratorCoordinator.idCounter().getAndIncrement()), spec, "c", "", opCode);
        if (expression != null) {
            if (expression instanceof IntegerLiteralExpr expr) {
                parameters.add(0, expr.getValue());
            } else {
                ArrayList<BlockBean> exprBlocks = blockGeneratorCoordinator.expressionBlockBuilder().build(expression, new RequiredBlockType("d", null), "");
                BlockBean bean = exprBlocks.get(exprBlocks.size() - 1);
                parameters.add(0, "@" + bean.id);
                blockGeneratorCoordinator.blockBeans().addAll(exprBlocks);
            }
        }
        forBlock.parameters = parameters;
        blockGeneratorCoordinator.blockBeans().add(forBlock);

        if (bodyStmt.isBlockStmt()) {
            var statements = bodyStmt.asBlockStmt().getStatements();
            forBlock.subStack1 = statements.isEmpty() ? -1 : blockGeneratorCoordinator.idCounter().get();
            blockGeneratorCoordinator.processStatements(statements);
        } else forBlock.subStack1 = -1;

        forBlock.nextBlock = blockGeneratorCoordinator.idCounter().get();
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
