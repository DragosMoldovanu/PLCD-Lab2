package com.company.FiniteAutomatas;

import java.util.ArrayList;
import java.util.HashMap;

public class FiniteAutomaton {
    private ArrayList<String> states;
    private ArrayList<String> alphabet;
    private String start;
    private ArrayList<String> finals;
    private HashMap<Line, String> transitions;

    public FiniteAutomaton() {
        states = new ArrayList<>();
        alphabet = new ArrayList<>();
        start = "";
        finals = new ArrayList<>();
        transitions = new HashMap<>();
    }

    public FiniteAutomaton(ArrayList<String> states, ArrayList<String> alphabet, String start, ArrayList<String> finals, HashMap<Line, String> transitions) {
        this.states = states;
        this.alphabet = alphabet;
        this.start = start;
        this.finals = finals;
        this.transitions = transitions;
    }

    public boolean isDeterministic() {
        for (String state : states) {
            ArrayList<String> symbols = new ArrayList<>();
            for (Line line : transitions.keySet()) {
                if (line.getStart().equals(state)) {
                    if (symbols.contains(transitions.get(line)))
                        return false;
                    symbols.add(transitions.get(line));
                }
            }
        }
        return true;
    }

    public boolean isValidPath(String path) {
        return advancePath(start, path);
    }

    private boolean advancePath(String state, String path) {
        if (path.equals("")) {
            return finals.contains(state);
        }
        ArrayList<Boolean> pathResults = new ArrayList<>();
        for (Line line : transitions.keySet()) {
            if (line.getStart().equals(state) && transitions.get(line).equals(String.valueOf(path.charAt(0)))) {
                pathResults.add(advancePath(line.getEnd(), path.substring(1)));
            }
        }
        for (boolean result : pathResults) {
            if (result)
                return true;
        }
        return false;
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

    public void setStates(ArrayList<String> states) {
        this.states = states;
    }

    public void setAlphabet(ArrayList<String> alphabet) {
        this.alphabet = alphabet;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public void setFinals(ArrayList<String> finals) {
        this.finals = finals;
    }

    public void setTransitions(HashMap<Line, String> transitions) {
        this.transitions = transitions;
    }
}