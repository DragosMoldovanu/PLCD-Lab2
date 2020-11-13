package com.company;

import com.company.Domain.Scanner;
import com.company.Domain.SymbolTable;
import com.company.FiniteAutomatas.FiniteAutomaton;
import com.company.FiniteAutomatas.Line;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    /*public static void main(String[] args) {
        SymbolTable table = new SymbolTable();
        Scanner scanner = new Scanner(table, "p1.txt", "token.in.txt");
        scanner.scan();
    }*/

    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("FA.in.txt")));
            String data;
            ArrayList<String> states = new ArrayList<>();
            ArrayList<String> alphabet = new ArrayList<>();
            String start;
            ArrayList<String> finals = new ArrayList<>();
            HashMap<Line, String> transitions = new HashMap<>();
            while (!(data = reader.readLine()).equals(""))
                states.add(data);
            while (!(data = reader.readLine()).equals(""))
                alphabet.add(data);
            start = reader.readLine();
            reader.readLine();
            while (!(data = reader.readLine()).equals(""))
                finals.add(data);
            while ((data = reader.readLine()) != null) {
                String[] tokens = data.split(" ");
                transitions.put(new Line(tokens[0], tokens[1]), tokens[2]);
            }
            FiniteAutomaton FA = new FiniteAutomaton(states, alphabet, start, finals, transitions);

            if (!FA.isDeterministic()) {
                System.out.println("Automaton is not deterministic");
                return;
            }

            reader = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                System.out.println("Finite Automata Data:");
                System.out.println("0 - exit");
                System.out.println("1 - states");
                System.out.println("2 - alphabet");
                System.out.println("3 - transitions");
                System.out.println("4 - final states");

                int choice = Integer.parseInt(reader.readLine());
                if (choice == 0)
                    return;
                else if (choice == 1) {
                    for (String state : FA.getStates()) {
                        System.out.println(state);
                    }
                }
                else if (choice == 2) {
                    for (String token : FA.getAlphabet()) {
                        System.out.println(token);
                    }
                }
                else if (choice == 3) {
                    for (Line line : FA.getTransitions().keySet()) {
                        System.out.println(line.getStart() + " -> " + line.getEnd() + " = " + FA.getTransitions().get(line));
                    }
                }
                else if (choice == 4) {
                    for (String state : FA.getFinals()) {
                        System.out.println(state);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
