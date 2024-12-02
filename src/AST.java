// AST Nodes
interface AST { }

class VariableDeclaration implements AST {
    String variableName;
    int value;

    VariableDeclaration(String variableName, int value) {
        this.variableName = variableName;
        this.value = value;
    }
}
