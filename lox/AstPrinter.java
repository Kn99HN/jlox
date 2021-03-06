package lox;

class AstPrinter implements Expr.Visitor<String> {
    String print(Expr expr) {
        return expr.accept(this);
    }

    @Override
    public String visitBinaryExpr(Expr.Binary expr) {
        return parenthesize(expr.operator.lexeme, expr.left, expr.right);
    }

    @Override
    public String visitGroupingExpr(Expr.Grouping expr) {
        return parenthesize("group", expr.expression);
    }

    @Override
    public String visitLiteralExpr(Expr.Literal expr) {
        if(expr.value == null) return "nil";
        return expr.value.toString();
    }

    @Override
    public String visitUnaryExpr(Expr.Unary expr) {
        return parenthesize(expr.operator.lexeme, expr. right);
    }

    private String parenthesize(String name, Expr... exprs) {
        StringBuilder builder = new StringBuilder();

        builder.append("(");
        for(Expr expr : exprs) {
            builder.append(" ");
            builder.append(expr.accept(this));
        }

        builder.append(" " + name);

        builder.append(")");

        return builder.toString();
    }

    public static void main(String[] args) {
        Expr expression = new Expr.Binary(
        new Expr.Binary(    
        new Expr.Literal(13), new Token(TokenType.PLUS, "+", null, 1), new Expr.Literal(10)),
            new Token(TokenType.STAR, "*", null, 1),
                new Expr.Binary(new Expr.Literal(2), new Token(TokenType.MINUS, "-", null, 1), new Expr.Literal(12))
        );

        System.out.println(new AstPrinter().print(expression));
    }
}