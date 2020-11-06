package com.company.Domain;

public class Entry {
    private int tokenId;
    private int stId;

    public Entry(int tokenId, int stId) {
        this.tokenId = tokenId;
        this.stId = stId;
    }

    public int getTokenId() {
        return tokenId;
    }

    public int getStId() {
        return stId;
    }
}
