package com.company.Domain;

public class Identifier {
    private String name;
    private int id;

    public Identifier(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
