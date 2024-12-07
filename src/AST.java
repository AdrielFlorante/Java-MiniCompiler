// AST Nodes
interface AST { }

class VariableDeclaration implements AST {
    String variableName;
    String value;

    VariableDeclaration(String variableName, String value) {
        this.variableName = variableName;
        this.value = value;
    }

}
