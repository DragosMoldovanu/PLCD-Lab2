package com.company.Parser;

import java.util.ArrayList;

public class Configuration {
    public String state;
    public int position;
    public ArrayList<WorkingElement> working;
    public ArrayList<String> input;

    public Configuration(String state, int position, ArrayList<WorkingElement> working, ArrayList<String> input) {
        this.state = state;
        this.position = position;
        this.working = working;
        this.input = input;
    }
}
