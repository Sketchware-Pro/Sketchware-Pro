package pro.sketchware.blocks.generator.components.analyzers;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.stmt.*;

import java.util.Arrays;
import java.util.List;

public class BlockReturnAnalyzer { // Taken from JavaCode2Blocks PR #1641

    private static final List<Class<?>> SUPPORTED_STATEMENTS = Arrays.asList(
            ReturnStmt.class,
            BreakStmt.class,
            IfStmt.class,
            SwitchStmt.class,
            BlockStmt.class
    );

    private boolean hasAnyMissingReturn(Statement stmt) {
        return !alwaysReturns(stmt);
    }

    public boolean hasAnyMissingReturn(String eventCode) {
        try {
            BlockStmt body = StaticJavaParser.parseBlock("{" + eventCode + "}");
            for (Statement statement : body.getStatements()) {
                if (!SUPPORTED_STATEMENTS.contains(statement.getClass())) {
                    return false;
                }
            }
            return hasAnyMissingReturn(body);
        } catch (Exception e) {
            return false;
        }
    }

    private boolean alwaysReturns(Statement stmt) {
        if (stmt instanceof ReturnStmt) return true;
        if (stmt instanceof BreakStmt) return false;

        if (stmt instanceof BlockStmt block) {
            for (Statement statement : block.getStatements()) {
                if (alwaysReturns(statement)) return true;
            }
            return false;
        }

        if (stmt instanceof IfStmt ifStmt) {
            if (ifStmt.getElseStmt().isPresent()) {
                return alwaysReturns(ifStmt.getThenStmt()) &&
                        alwaysReturns(ifStmt.getElseStmt().get());
            } else {
                return false;
            }
        }

        if (stmt instanceof SwitchStmt switchStmt) {
            boolean hasDefault = false;

            for (SwitchEntry entry : switchStmt.getEntries()) {
                if (entry.getLabels().isEmpty()) {
                    hasDefault = true;
                }

                boolean foundReturn = false;
                for (Statement s : entry.getStatements()) {
                    if (alwaysReturns(s)) {
                        foundReturn = true;
                        break;
                    }
                }

                if (!foundReturn) {
                    return false;
                }
            }

            return hasDefault;
        }

        return false;
    }
}
