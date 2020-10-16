package com.company;

import com.company.Domain.Identifier;
import com.company.Domain.SymbolTable;

public class Main {

    public static void main(String[] args) {
        SymbolTable table = new SymbolTable();
        table.addNode("b", 1);
        table.addNode("d", 2);
        table.addNode("a", 3);
        table.addNode("c", 4);

        Identifier identifier;
        identifier = table.getFromName("c");
        System.out.println(identifier.getName() + " " + identifier.getId());
        identifier = table.getFromName("a");
        System.out.println(identifier.getName() + " " + identifier.getId());
        identifier = table.getFromId(2);
        System.out.println(identifier.getName() + " " + identifier.getId());
        identifier = table.getFromId(4);
        System.out.println(identifier.getName() + " " + identifier.getId());
    }
}
