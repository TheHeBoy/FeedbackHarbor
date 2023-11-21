package cn.hh.harbor.framework.expression;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;

/**
 * OrExpression 的扩展类(会在原有表达式两端加上括号)
 */
public class OrExpressionX extends OrExpression {

    public OrExpressionX() {
    }

    public OrExpressionX(Expression leftExpression, Expression rightExpression) {
        this.setLeftExpression(leftExpression);
        this.setRightExpression(rightExpression);
    }

    @Override
    public String toString() {
        return "(" + super.toString() + ")";
    }

}
