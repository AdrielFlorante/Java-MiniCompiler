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
        //IF INTEGER
        if(currentToken.type == TokenType.INT){
            eat(TokenType.INT); // Expect "int"
            String variableName = currentToken.value;
            eat(TokenType.IDENTIFIER); // Expect variable name
            //IF NO VALUE
            if(currentToken.type == TokenType.SEMICOLON){
                eat(TokenType.SEMICOLON);
                return new VariableDeclaration(variableName, null);
            }
            else if(currentToken.type == TokenType.ASSIGN){
                eat(TokenType.ASSIGN); // Expect "="
                String value = currentToken.value;
                eat(TokenType.NUMBER); // Expect number
                eat(TokenType.SEMICOLON); // Expect ";"
                return new VariableDeclaration(variableName, value);
            }
        }
        //IF STRING
        else if (currentToken.type == TokenType.STRING) {
            eat(TokenType.STRING);
            String variableName = currentToken.value;
            eat(TokenType.IDENTIFIER);
            //IF NO VALUE
            if(currentToken.type == TokenType.SEMICOLON){
                eat(TokenType.SEMICOLON);
                return new VariableDeclaration(variableName, null);
            }
            else if(currentToken.type == TokenType.ASSIGN){
                eat(TokenType.ASSIGN);
                String value = currentToken.value;
                eat(TokenType.STRING);
                eat(TokenType.SEMICOLON);
                return new VariableDeclaration(variableName, value);
            }
        }
        throw new RuntimeException("Syntax Analysis Failed");
    }

    public AST parse() {
        if (currentToken.type == TokenType.EOF) {
            return null; // End of input
        }
        return parseVariableDeclaration();
    }

}