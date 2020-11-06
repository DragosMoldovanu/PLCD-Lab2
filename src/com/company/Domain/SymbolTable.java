package com.company.Domain;

public class SymbolTable {
    private IdentifierNode root;

    public SymbolTable() {
        root = null;
    }

    public void addNode(String identifier, int id) {
        if (root == null)
            root = new IdentifierNode(new Identifier(identifier, id));
        else
            findPosition(root, identifier, id);
    }

    private void findPosition(IdentifierNode node, String identifier, int id) {
        if (node.getIdentifier().getName().equals(identifier))
            return;
        if (identifier.compareTo(node.getIdentifier().getName()) < 0) {
            if (node.getLeft() == null)
                node.setLeft(new IdentifierNode(new Identifier(identifier, id)));
            else
                findPosition(node.getLeft(), identifier, id);
        }
        else {
            if (node.getRight() == null)
                node.setRight(new IdentifierNode(new Identifier(identifier, id)));
            else
                findPosition(node.getRight(), identifier, id);
        }
    }

    public Identifier getFromName(String identifier) {
        if (root == null)
            throw new RuntimeException("Identifier does not exist");
        return findNodeByName(root, identifier);
    }

    private Identifier findNodeByName(IdentifierNode node, String identifier) {
        if (node.getIdentifier().getName().equals(identifier))
            return node.getIdentifier();
        if (identifier.compareTo(node.getIdentifier().getName()) < 0)
            return findNodeByName(node.getLeft(), identifier);
        else
            return findNodeByName(node.getRight(), identifier);
    }

    public Identifier getFromId(int id) {
        return findNodeById(root, id);
    }

    private Identifier findNodeById(IdentifierNode node, int id) {
        if (node.getIdentifier().getId() == id)
            return node.getIdentifier();
        if (node.getLeft() != null) {
            Identifier left = findNodeById(node.getLeft(), id);
            if (left != null)
                return left;
        }
        if (node.getRight() != null)
            return findNodeById(node.getRight(), id);
        return null;
    }

    public String toString() {
        return convertToString(root);
    }

    private String convertToString(IdentifierNode node) {
        if (node == null)
            return "";
        return node.getIdentifier().getName() + " " + node.getIdentifier().getId() + "\n"
                + convertToString(node.getLeft())
                + convertToString(node.getRight());
    }
}
