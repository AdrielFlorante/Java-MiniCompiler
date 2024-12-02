import java.util.*;

// Token Types
enum TokenType {
    INT, IDENTIFIER, NUMBER, ASSIGN, SEMICOLON, EOF
}

// Token Class
class Token {
    TokenType type;
    String value;

    Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format("Token(%s, %s)", type, value);
    }
}