package com.company.Domain;

public class IdentifierNode {
    private Identifier identifier;
    private IdentifierNode left;
    private IdentifierNode right;

    public IdentifierNode(Identifier identifier) {
        this.identifier = identifier;
        this.left = null;
        this.right = null;
    }

    public IdentifierNode getLeft() {
        return left;
    }

    public IdentifierNode getRight() {
        return right;
    }

    public void setLeft(IdentifierNode left) {
        this.left = left;
    }

    public void setRight(IdentifierNode right) {
        this.right = right;
    }

    public Identifier getIdentifier() {
        return identifier;
    }
}
