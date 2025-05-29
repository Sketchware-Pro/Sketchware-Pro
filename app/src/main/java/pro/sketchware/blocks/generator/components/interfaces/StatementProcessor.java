package pro.sketchware.blocks.generator.components.interfaces;

import com.github.javaparser.ast.stmt.Statement;

public interface StatementProcessor {
    void processStatement(Statement stmt);
}