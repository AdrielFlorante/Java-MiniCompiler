// Lexer (Tokenizer)
class Lexer {
    private final String text;
    private int pos = 0;
    private char currentChar;

    Lexer(String text) {
        this.text = text;
        currentChar = text.charAt(pos);
    }

    private void advance() {
        pos++;
        currentChar = pos < text.length() ? text.charAt(pos) : '\0';
    }

    private void skipWhitespace() {
        while (Character.isWhitespace(currentChar)) {
            advance();
        }
    }

    private String identifier() {
        StringBuilder result = new StringBuilder();
        while (Character.isLetterOrDigit(currentChar)) {
            result.append(currentChar);
            advance();
        }
        return result.toString();
    }

    private String number() {
        StringBuilder result = new StringBuilder();
        while (Character.isDigit(currentChar)) {
            result.append(currentChar);
            advance();
        }
        return result.toString();
    }

    public Token getNextToken() {
        while (currentChar != '\0') {
            if (Character.isWhitespace(currentChar)) {
                skipWhitespace();
                continue;
            }
            if (Character.isLetter(currentChar)) {
                String value = identifier();
                if (value.equals("int")) {
                    return new Token(TokenType.INT, value);
                }
                return new Token(TokenType.IDENTIFIER, value);
            }
            if (Character.isDigit(currentChar)) {
                return new Token(TokenType.NUMBER, number());
            }
            if (currentChar == '=') {
                advance();
                return new Token(TokenType.ASSIGN, "=");
            }
            if (currentChar == ';') {
                advance();
                return new Token(TokenType.SEMICOLON, ";");
            }
            throw new RuntimeException("Unexpected character: " + currentChar);
        }
        return new Token(TokenType.EOF, "");
    }
}