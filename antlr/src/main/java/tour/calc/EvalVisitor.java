package tour.calc;

import java.util.HashMap;
import java.util.Map;

/**
 * 说明:
 * 作者: LDL
 * 日期: 2016/6/13.
 */
public class EvalVisitor extends LabeledExprBaseVisitor<Integer> {

    /**
     * "memory" for our calculator; variable/value pairs go here
     */
    Map<String, Integer> memory = new HashMap<>();

    /**
     * ID '=' expr NEWLINE
     */
    @Override
    public Integer visitAssign(LabeledExprParser.AssignContext ctx) {
        String id = ctx.ID().getText(); //id是=左边的
        int value = visit(ctx.expr()); //计算=右边的
        memory.put(id, value);
        return value;
    }

    /**
     * expr NEWLINE
     */
    @Override
    public Integer visitPrintExpr(LabeledExprParser.PrintExprContext ctx) {
        Integer value = visit(ctx.expr());
        System.out.println(value);
        return 0;
    }

    /**
     * INT
     */
    @Override
    public Integer visitInt(LabeledExprParser.IntContext ctx) {
        return Integer.valueOf(ctx.INT().getText());
    }

    /**
     * ID
     */
    @Override
    public Integer visitId(LabeledExprParser.IdContext ctx) {
        String id = ctx.ID().getText();
        if (memory.containsKey(id)) return memory.get(id);
        return 0;
    }

    /**
     * expr op=('*'|'/') expr
     */
    @Override
    public Integer visitMulDiv(LabeledExprParser.MulDivContext ctx) {
        int left = visit(ctx.expr(0));
        int right = visit(ctx.expr(1));
        if (ctx.op.getType() == LabeledExprParser.MUL) {
            return left * right;
        }

        return left / right;
    }

    /**
     * expr op=('+'|'-') expr
     */
    @Override
    public Integer visitAddSub(LabeledExprParser.AddSubContext ctx) {
        int left = visit(ctx.expr(0)); // get value of left subexpression
        int right = visit(ctx.expr(1)); // get value of right subexpression
        if (ctx.op.getType() == LabeledExprParser.ADD) {
            return left + right;
        }
        return left - right; // must be SUB
    }

    /**
     * '(' expr ')'
     */
    @Override
    public Integer visitParens(LabeledExprParser.ParensContext ctx) {
        return visit(ctx.expr()); // return child expr's value
    }

    @Override
    public Integer visitClear(LabeledExprParser.ClearContext ctx) {
        System.out.println(memory);
        memory.clear();
        System.out.println(memory);
        return 0;
    }
}