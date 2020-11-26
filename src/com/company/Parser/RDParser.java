package com.company.Parser;

import java.util.ArrayList;

public class RDParser {
    private Grammar grammar;

    private void expand(Configuration configuration) {
        // Get A and y1
        String A = configuration.input.remove(0);
        ArrayList<String> y = grammar.getProductions().get(A).get(0);

        // Add A on top of Working Stack and y1 on top of Input Stack
        configuration.working.add(0, new WorkingElement("NonTerminal", A, 1));
        for (int i = y.size() - 1; i >= 0; i--) {
            configuration.input.add(0, y.get(i));
        }
    }

    private void advance(Configuration configuration) {
        // Advance the position and pop from Input to add to top of Working
        configuration.position += 1;
        configuration.working.add(0, new WorkingElement("Terminal", configuration.input.remove(0), -1));
    }

    private void insuccess(Configuration configuration) {
        // Change state to "back"
        configuration.state = "b";
    }

    private void back(Configuration configuration) {
        // Go back in position and pop from Working to add to top of Input;
        configuration.position -= 1;
        configuration.input.add(0, configuration.working.remove(0).element);
    }

    private void retry(Configuration configuration) {
        // Get A and its index from Working Stack
        String A = configuration.working.get(0).element;
        int index = configuration.working.remove(0).index;
        if (index < grammar.getProductions().get(A).size()) {
            // If there's productions left, change state to "normal" and change the production on top of Input
            configuration.state = "q";
            configuration.working.add(0, new WorkingElement("NonTerminal", A, index + 1));
            ArrayList<String> y = grammar.getProductions().get(A).get(index + 1);
            for (int i = y.size() - 1; i >= 0; i--) {
                configuration.input.add(0, y.get(i));
            }
        } else if (index == 1 && A.equals(grammar.getStarting())) {
            // If the index is 1 and A is the starting NonTerminal, change state to "error"
            configuration.state = "e";
        } else {
            // Otherwise just put the NonTerminal back on top of the Input Stack
            configuration.input.add(0, configuration.working.remove(0).element);
        }
    }

    private void success(Configuration configuration) {
        // Change state to "final"
        configuration.state = "f";
    }
}
