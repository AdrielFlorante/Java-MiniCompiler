import java.util.ArrayList;
import java.util.List;

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
        // Parse an "int" declaration
        if (currentToken.type == TokenType.INT) {
            eat(TokenType.INT); // Expect "int"
            String variableName = currentToken.value;
            eat(TokenType.IDENTIFIER); // Expect variable name
            if (currentToken.type == TokenType.SEMICOLON) {
                eat(TokenType.SEMICOLON);
                return new VariableDeclaration(variableName, null); // No value assigned
            } else if (currentToken.type == TokenType.ASSIGN) {
                eat(TokenType.ASSIGN); // Expect "="
                String value = currentToken.value;
                eat(TokenType.NUMBER); // Expect a number
                eat(TokenType.SEMICOLON); // Expect ";"
                return new VariableDeclaration(variableName, value);
            }
        }
        // Parse a "string" declaration
        else if (currentToken.type == TokenType.STRING) {
            eat(TokenType.STRING); // Expect "string"
            String variableName = currentToken.value;
            eat(TokenType.IDENTIFIER); // Expect variable name
            if (currentToken.type == TokenType.SEMICOLON) {
                eat(TokenType.SEMICOLON);
                return new VariableDeclaration(variableName, null); // No value assigned
            } else if (currentToken.type == TokenType.ASSIGN) {
                eat(TokenType.ASSIGN); // Expect "="
                String value = currentToken.value;
                eat(TokenType.STRING); // Expect string value
                eat(TokenType.SEMICOLON); // Expect ";"
                return new VariableDeclaration(variableName, value);
            }
        }
        throw new RuntimeException("Syntax Analysis Failed");
    }

    public List<AST> parse() {
        List<AST> nodes = new ArrayList<>();
        while (currentToken.type != TokenType.EOF) {
            nodes.add(parseVariableDeclaration());
        }
        return nodes;
    }
}
