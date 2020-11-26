package com.company.Parser;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Grammar {
    private ArrayList<String> nonTerminals;
    private ArrayList<String> terminals;
    private String starting;
    private final HashMap<String, ArrayList<ArrayList<String>>> productions = new HashMap<>();

    private void readGrammar(String file) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

            // NonTerminals
            String line = reader.readLine();
            nonTerminals.addAll(Arrays.asList(line.split("=")[1].split(" ")));

            // Terminals
            line = reader.readLine();
            terminals.addAll(Arrays.asList(line.split("=")[1].split(" ")));

            // Starting Symbols
            starting = reader.readLine().split("=")[1];

            // Productions
            line = reader.readLine();
            String productions = line.split("=")[1];
            String left = productions.split("->")[0];
            String right = productions.split("->")[1];
            String[] options = right.split(":");
            ArrayList<ArrayList<String>> rightSide = new ArrayList<>();
            for (String option : options) {
                rightSide.add(new ArrayList<>(Arrays.asList(option.split(","))));
            }
            this.productions.put(left, rightSide);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getNonTerminals() {
        return nonTerminals;
    }

    public ArrayList<String> getTerminals() {
        return terminals;
    }

    public String getStarting() {
        return starting;
    }

    public HashMap<String, ArrayList<ArrayList<String>>> getProductions() {
        return productions;
    }
}
