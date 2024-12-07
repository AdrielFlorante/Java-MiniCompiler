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
            throw new RuntimeException("Semantic Analysis Failed");
        }
    }

    public String printSymbolTable() {
        return symbolTable.printSymbolTable();
    }
}