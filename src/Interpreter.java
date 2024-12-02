// Interpreter
class Interpreter {
    private final SymbolTable symbolTable;

    Interpreter() {
        symbolTable = new SymbolTable();
    }

    public void interpret(AST node) {
        if (node instanceof VariableDeclaration) {
            VariableDeclaration varDecl = (VariableDeclaration) node;
            symbolTable.declare(varDecl.variableName, varDecl.value);
        } else {
            throw new RuntimeException("Unexpected AST node");
        }
    }

    public void printSymbolTable() {
        symbolTable.printSymbolTable();
    }
}