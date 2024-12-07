// Lexer (Tokenizer)
class Lexer {
    private final String text;
    private int pos = 0;
    private char currentChar;

    // Constructor that initializes the lexer with the input text
    Lexer(String text) {
        this.text = text;
        currentChar = text.charAt(pos);
    }

    // Advances to the next character in the input text
    private void advance() {
        pos++;
        currentChar = pos < text.length() ? text.charAt(pos) : '\0';
    }

    // Skips whitespace characters
    private void skipWhitespace() {
        while (Character.isWhitespace(currentChar)) {
            advance();
        }
    }

    // Extracts an identifier (variable name)
    private String identifier() {
        StringBuilder result = new StringBuilder();
        while (Character.isLetterOrDigit(currentChar) || currentChar == '_') {
            result.append(currentChar);
            advance();
        }
        return result.toString();
    }

    // Extracts a number (integer literal)
    private String number() {
        StringBuilder result = new StringBuilder();
        while (Character.isDigit(currentChar)) {
            result.append(currentChar);
            advance();
        }
        return result.toString();
    }

    private String string() {
        StringBuilder result = new StringBuilder();
        advance(); // Skip the opening quote
        while (currentChar != '"' && currentChar != '\0') {
            result.append(currentChar);
            advance();
        }
        advance(); // Skip the closing quote
        return result.toString();
    }


    // Main method to get the next token from the input text
    public Token getNextToken() {
        while (currentChar != '\0') { // While we haven't reached the end of input
            if (Character.isWhitespace(currentChar)) {
                skipWhitespace(); // Skip whitespace characters
                continue;
            }

            // Handle identifiers (e.g., "x", "y")
            if (Character.isLetter(currentChar)) {
                String value = identifier();
                if (value.equals("int")) {
                    return new Token(TokenType.INT, value); // Return INT keyword
                }
                else if (value.equals("String")) {
                    return new Token(TokenType.STRING, value);// Return STRING keyword
                }
                return new Token(TokenType.IDENTIFIER, value); // Return identifier
            }

            // Handle numbers (e.g., 5, 10)
            if (Character.isDigit(currentChar)) {
                return new Token(TokenType.NUMBER, number()); // Return number
            }

            // Handles string values
            if (currentChar == '"') {
                String value = string();
                return new Token(TokenType.STRING, value); // Return string literal
            }

            // Handle assignment operator (=)
            if (currentChar == '=') {
                advance();
                return new Token(TokenType.ASSIGN, "="); // Return assignment operator
            }

            // Handle semicolon (;)
            if (currentChar == ';') {
                advance();
                return new Token(TokenType.SEMICOLON, ";"); // Return semicolon
            }

            // If we encounter an unexpected character, throw an error
            throw new RuntimeException("Lexical Error: Unexpected character: " + currentChar);
        }

        // Return EOF (End Of File) token when we reach the end of the input text
        return new Token(TokenType.EOF, "");
    }
}
