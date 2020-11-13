package com.company.Domain;

import com.company.FiniteAutomatas.FiniteAutomaton;
import com.company.FiniteAutomatas.Line;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Scanner {
    private final SymbolTable table;
    private final String inputFile;
    private final String tokenFile;
    private final HashMap<String, Integer> separators;
    private final HashMap<String, Integer> keywords;
    private final HashMap<String, Integer> types;
    private final ArrayList<Entry> PIF;
    private static int stId = 1;

    private FiniteAutomaton integerAutomata;
    private FiniteAutomaton booleanAutomata;
    private FiniteAutomaton identifierAutomata;

    public Scanner(SymbolTable table, String inputFile, String tokenFile) {
        this.table = table;
        this.inputFile = inputFile;
        this.tokenFile = tokenFile;
        this.separators = new HashMap<>();
        this.keywords = new HashMap<>();
        this.types = new HashMap<>();
        this.PIF = new ArrayList<>();

        loadTokens();
        loadAutomatas();
    }

    private void loadTokens() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(tokenFile)));
            String line;
            while (!(line = reader.readLine()).equals("")) {
                String[] token = line.split(" ", 2);
                separators.put(token[0], Integer.parseInt(token[1]));
            }
            while (!(line = reader.readLine()).equals("")) {
                String[] token = line.split(" ", 2);
                keywords.put(token[0], Integer.parseInt(token[1]));
            }
            while ((line = reader.readLine()) != null) {
                String[] token = line.split(" ", 2);
                types.put(token[0], Integer.parseInt(token[1]));
            }
            // Clear output files
            File pif = new File("pif.out.txt");
            if (pif.delete())
                pif.createNewFile();
            File st = new File("st.out.txt");
            if (st.delete())
                st.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadAutomatas() {
        // Read Integer Automata
        integerAutomata = new FiniteAutomaton();
        createAutomata(integerAutomata, "integer.txt");

        // Read Boolean Automata
        booleanAutomata = new FiniteAutomaton();
        createAutomata(booleanAutomata, "boolean.txt");

        // Read Identifier Automata
        identifierAutomata = new FiniteAutomaton();
        createAutomata(identifierAutomata, "identifier.txt");
    }

    private void createAutomata(FiniteAutomaton automata, String file) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            ArrayList<String> states = new ArrayList<>();
            while (!(line = reader.readLine()).equals("")) {
                states.add(line);
            }
            ArrayList<String> alphabet = new ArrayList<>();
            while (!(line = reader.readLine()).equals("")) {
                alphabet.add(line);
            }
            String start = reader.readLine();
            reader.readLine();
            ArrayList<String> finals = new ArrayList<>();
            while (!(line = reader.readLine()).equals("")) {
                finals.add(line);
            }
            HashMap<Line, String> transitions = new HashMap<>();
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(" ");
                transitions.put(new Line(tokens[0], tokens[1]), tokens[2]);
            }
            automata.setStates(states);
            automata.setAlphabet(alphabet);
            automata.setStart(start);
            automata.setFinals(finals);
            automata.setTransitions(transitions);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void scan() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));
            String read;
            int line = 1;
            while ((read = reader.readLine()) != null) {
                String row = read.replaceAll("\t", "");
                separate(row, line);
                line += 1;
            }
            // Write to File
            PrintWriter writer = new PrintWriter("pif.out.txt");
            for (Entry entry : PIF)
                writer.println(entry.getTokenId() + " " + entry.getStId());
            writer.close();
            writer = new PrintWriter("st.out.txt");
            writer.println(table.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void separate(String token, int line) {
        if (token.equals(""))
            return;
        String separator = null;
        String[] tokens;

        // Check for Strings
        tokens = token.split("\"", -1);
        if (tokens.length > 1) {
            if (tokens.length % 2 == 0) {
                System.out.println("Lexical error on line " + line + ": String opened but not closed");
                return;
            }
            for (int i = 0; i < tokens.length; i++) {
                if (i % 2 == 0) {
                    separate(tokens[i], line);
                }
                else {
                    PIF.add(new Entry(separators.get("\\\""), -1));
                    processString(tokens[i]);
                    PIF.add(new Entry(separators.get("\\\""), -1));
                }
            }
            return;
        }

        // Check for Characters
        tokens = token.split("'", -1);
        if (tokens.length > 1) {
            if (tokens.length % 2 == 0) {
                System.out.println("Lexical error on line " + line + ": Char opened but not closed");
                return;
            }
            for (int i = 0; i < tokens.length; i++) {
                if (i % 2 == 0) {
                    separate(tokens[i], line);
                }
                else {
                    PIF.add(new Entry(separators.get("'"), -1));
                    processChar(tokens[i], line);
                    PIF.add(new Entry(separators.get("'"), -1));
                }
            }
            return;
        }

        // Check for spaces
        tokens = token.split(" ", -1);
        if (tokens.length > 1) {
            for (String s : tokens) {
                separate(s, line);
            }
            return;
        }

        for (String delimiter : separators.keySet()) {
            tokens = token.split(delimiter, -1);
            if (tokens.length > 1) {
                separator = delimiter;
                break;
            }
        }
        if (separator == null)
            identify(token, line);
        else if (separator.equals("+") || separator.equals("-")) {
            separate(tokens[0], line);
            for (int i = 1; i < tokens.length; i++) {
                if (tokens[i - 1].length() == 0) {
                    separate(separator + tokens[i], line);
                }
                else if (separators.containsKey(String.valueOf(tokens[i - 1].charAt(tokens[i - 1].length() - 1)))) {
                    separate(separator + tokens[i], line);
                }
                else {
                    PIF.add(new Entry(separators.get(separator), -1));
                    separate(tokens[i], line);
                }
            }
        }
        else {
            separate(tokens[0], line);
            for (int i = 1; i < tokens.length; i++)
            {
                PIF.add(new Entry(separators.get(separator), -1));
                separate(tokens[i], line);
            }
        }
    }

    private void identify(String token, int line) {
        for (String keyword : keywords.keySet()) {
            if (keyword.equals(token)) {
                PIF.add(new Entry(keywords.get(keyword), -1));
                return;
            }
        }
        if (scanInteger(token))
            return;
        if (scanBool(token))
            return;
        if (scanIdentifier(token))
            return;
        System.out.println("Lexical error on line " + line + ": Invalid token - " + token);
    }

    private void processString(String token) {
        PIF.add(new Entry(types.get("STRING"), -1));
    }

    private void processChar(String token, int line) {
        if (token.length() > 1) {
            System.out.println("Lexical error on line " + line + ": Invalid character constant - '" + token + "'");
            return;
        }
        PIF.add(new Entry(types.get("CHAR"), -1));
    }

    private boolean scanInteger(String token) {
        if (integerAutomata.isValidPath(token)) {
            table.addNode("INT", stId);
            PIF.add(new Entry(types.get("INT"), stId));
            stId += 1;
            return true;
        }
        return false;
    }

    private boolean scanBool(String token) {
        if (booleanAutomata.isValidPath(token)) {
            table.addNode("BOOL", stId);
            PIF.add(new Entry(types.get("BOOL"), stId));
            stId += 1;
            return true;
        }
        return false;
    }

    private boolean scanIdentifier(String token) {
        if (identifierAutomata.isValidPath(token)) {
            table.addNode(token, stId);
            PIF.add(new Entry(types.get("IDENTIFIER"), stId));
            stId += 1;
            return true;
        }
        return false;
    }
}
