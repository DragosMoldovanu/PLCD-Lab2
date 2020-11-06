package com.company.FiniteAutomatas;

import java.util.ArrayList;
import java.util.HashMap;

public class FiniteAutomata {
    private final ArrayList<String> states;
    private final ArrayList<String> alphabet;
    private final String start;
    private final ArrayList<String> finals;
    private final HashMap<Line, String> transitions;

    public FiniteAutomata(ArrayList<String> states, ArrayList<String> alphabet, String start, ArrayList<String> finals, HashMap<Line, String> transitions) {
        this.states = states;
        this.alphabet = alphabet;
        this.start = start;
        this.finals = finals;
        this.transitions = transitions;
    }

    public ArrayList<String> getStates() {
        return states;
    }

    public ArrayList<String> getAlphabet() {
        return alphabet;
    }

    public String getStart() {
        return start;
    }

    public ArrayList<String> getFinals() {
        return finals;
    }

    public HashMap<Line, String> getTransitions() {
        return transitions;
    }
}