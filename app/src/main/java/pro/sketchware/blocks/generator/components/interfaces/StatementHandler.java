package pro.sketchware.blocks.generator.components.interfaces;

import com.github.javaparser.ast.stmt.Statement;

public interface StatementHandler {

    boolean canHandle(Statement stmt);

    void handle(Statement stmt);

}