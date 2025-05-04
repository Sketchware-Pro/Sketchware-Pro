package pro.sketchware.blocks.generator.interfaces;

import com.github.javaparser.ast.stmt.Statement;

import pro.sketchware.blocks.generator.records.HandlerContext;

public interface StatementHandler {

    boolean canHandle(Statement stmt);

    void handle(Statement stmt, int id, HandlerContext context);

}