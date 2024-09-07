package com.spcmusic.logic;

import java.util.HashMap;
import java.util.ArrayList;

public class EDOFactory {

    private double pitchReference;
    private HashMap<String, Double> edoMap;

    public EDOFactory() {
        new EDOFactory(440.0);
    }

    public EDOFactory(double pitchReference) {
        this.pitchReference = pitchReference;
        this.edoMap = buildEdoMap();
    }

    public HashMap<String, Double> getEdoMap() {
        return this.edoMap;
    }

    private HashMap<String, Double> buildEdoMap() {
        // Pn = pitchReference * (FREQUENCY_RATIO)^(n - 58) where n = index + 1
        final double FREQUENCY_RATIO = 1.059463;

        HashMap<String, Double> edoMap = new HashMap<>();
        ArrayList<String> edoArray = buildEdoArray();

        for (int index = 0; index < edoArray.size(); index++) {
            int n = index + 1;
            int exponent = n - 58;
            double frequency = pitchReference * Math.pow(FREQUENCY_RATIO, exponent);
            String pitch = edoArray.get(index);
            edoMap.put(pitch, frequency);
        }

        return edoMap;
    }

    private ArrayList<String> buildPitchClassArray() {
        ArrayList<String> list = new ArrayList<>();

        list.add("C");
        list.add("C# / Db");
        list.add("D");
        list.add("D# / Eb");
        list.add("E");
        list.add("F");
        list.add("F# / Gb");
        list.add("G");
        list.add("G# / Ab");
        list.add("A");
        list.add("A# / Bb");
        list.add("B");

        return list;
    }

    private ArrayList<String> buildRegisterArray() {
        ArrayList<String> list = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            list.add(String.valueOf(i));
        }

        return list;
    }

    private ArrayList<String> buildEdoArray() {
        ArrayList<String> pitchClassArray = buildPitchClassArray();
        ArrayList<String> registerArray = buildRegisterArray();
        ArrayList<String> edoArray = new ArrayList<>();

        for (String register : registerArray) {
            for (String pitchClass : pitchClassArray) {
                String currentValue = pitchClass + " " + register;
                edoArray.add(currentValue);
            }
        }

        return edoArray;
    }
}