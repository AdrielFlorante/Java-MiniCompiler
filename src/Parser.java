// Parser
class Parser {
    private final Lexer lexer;
    private Token currentToken;

    Parser(Lexer lexer) {
        this.lexer = lexer;
        currentToken = lexer.getNextToken();
    }

    private void eat(TokenType tokenType) {
        if (currentToken.type == tokenType) {
            currentToken = lexer.getNextToken();
        } else {
            throw new RuntimeException("Syntax Analysis Failed \n Unexpected token: " + currentToken);
        }
    }

    public VariableDeclaration parseVariableDeclaration() {
        eat(TokenType.INT); // Expect "int"
        String variableName = currentToken.value;
        eat(TokenType.IDENTIFIER); // Expect variable name
        eat(TokenType.ASSIGN); // Expect "="
        int value = Integer.parseInt(currentToken.value);
        eat(TokenType.NUMBER); // Expect number
        eat(TokenType.SEMICOLON); // Expect ";"
        return new VariableDeclaration(variableName, value);
    }

    public AST parse() {
        if (currentToken.type == TokenType.EOF) {
            return null; // End of input
        }
        return parseVariableDeclaration();
    }

}