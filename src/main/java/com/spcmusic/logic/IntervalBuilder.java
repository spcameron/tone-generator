package com.spcmusic.logic;

import java.util.HashMap;

public class IntervalBuilder {

    private final double FUNDAMENTAL_FREQUENCY;
    private final double OVERTONE_FREQUENCY;
    private final String INTERVAL;
    private final HashMap<String, Double> RATIO_MAP;

    public IntervalBuilder(double fundamentalFrequency, String intonation, String interval) {
        this.FUNDAMENTAL_FREQUENCY = fundamentalFrequency;
        this.INTERVAL = interval;
        this.RATIO_MAP = switch (intonation) {
            case "Just Intonation" -> generateFiveLimitMap();
            case "Equal Temperament" -> generate12TETMap();
            case "Pythagorean" -> generatePythagoreanMap();
            case "Hindustani/Carnatic" -> generateIndianMap();
            default -> throw new RuntimeException("Unexpected value: " + intonation);
        };
        this.OVERTONE_FREQUENCY = generateOvertoneFrequency();
    }

    public double getOvertoneFrequency() {
        return OVERTONE_FREQUENCY;
    }

    private double generateOvertoneFrequency() {
        return RATIO_MAP.get(INTERVAL) * FUNDAMENTAL_FREQUENCY;
    }

    private HashMap<String, Double> generateFiveLimitMap() {
        HashMap<String, Double> newRatioMap = new HashMap<>();

        newRatioMap.put("Perfect Unison", 1.0);
        newRatioMap.put("Augmented Unison", 25.0 / 24.0);
        newRatioMap.put("Minor Second", 16.0 / 15.0);
        newRatioMap.put("Major Second", 9.0 / 8.0);
        newRatioMap.put("Augmented Second", 75.0 / 64.0);
        newRatioMap.put("Minor Third", 6.0 / 5.0);
        newRatioMap.put("Major Third", 5.0 / 4.0);
        newRatioMap.put("Diminished Fourth", 32.0 / 25.0);
        newRatioMap.put("Perfect Fourth", 4.0 / 3.0);
        newRatioMap.put("Augmented Fourth", 45.0 / 32.0);
        newRatioMap.put("Diminished Fifth", 64.0 / 45.0);
        newRatioMap.put("Perfect Fifth", 3.0 / 2.0);
        newRatioMap.put("Augmented Fifth", 25.0 / 16.0);
        newRatioMap.put("Minor Sixth", 8.0 / 5.0);
        newRatioMap.put("Major Sixth", 5.0 / 3.0);
        newRatioMap.put("Minor Seventh", 9.0 / 5.0);
        newRatioMap.put("Major Seventh", 15.0 / 8.0);
        newRatioMap.put("Perfect Octave", 2.0);

        return newRatioMap;
    }

    private HashMap<String, Double> generate12TETMap() {
        HashMap<String, Double> newRatioMap = new HashMap<>();

        newRatioMap.put("Perfect Unison", 1.0);
        newRatioMap.put("Minor Second", 1.059463);
        newRatioMap.put("Major Second", 1.122462);
        newRatioMap.put("Minor Third", 1.189207);
        newRatioMap.put("Major Third", 1.259921);
        newRatioMap.put("Perfect Fourth", 1.334839);
        newRatioMap.put("Augmented Fourth / Diminished Fifth", 1.414213);
        newRatioMap.put("Perfect Fifth", 1.498307);
        newRatioMap.put("Minor Sixth", 1.587401);
        newRatioMap.put("Major Sixth", 1.681792);
        newRatioMap.put("Minor Seventh", 1.781797);
        newRatioMap.put("Major Seventh", 1.887748);
        newRatioMap.put("Perfect Octave", 2.0);

        return newRatioMap;
    }

    private HashMap<String, Double> generatePythagoreanMap() {
        HashMap<String, Double> newRatioMap = new HashMap<>();

        newRatioMap.put("Perfect Unison", 1.0);
        newRatioMap.put("Minor Second", 256.0 / 243.0);
        newRatioMap.put("Major Second", 9.0 / 8.0);
        newRatioMap.put("Minor Third", 32.0 / 27.0);
        newRatioMap.put("Major Third", 81.0 / 64.0);
        newRatioMap.put("Perfect Fourth", 4.0 / 3.0);
        newRatioMap.put("Augmented Fourth", 729.0 / 512.0);
        newRatioMap.put("Diminished Fifth", 1024.0 / 729.0);
        newRatioMap.put("Perfect Fifth", 3.0 / 2.0);
        newRatioMap.put("Minor Sixth", 128.0 / 81.0);
        newRatioMap.put("Major Sixth", 27.0 / 16.0);
        newRatioMap.put("Minor Seventh", 16.0 / 9.0);
        newRatioMap.put("Major Seventh", 243.0 / 128.0);
        newRatioMap.put("Perfect Octave", 2.0);

        return newRatioMap;
    }

    private HashMap<String, Double> generateIndianMap() {
        HashMap<String, Double> newRatioMap = new HashMap<>();

        newRatioMap.put("1 - S", 1.0);
        newRatioMap.put("2 - r1", 256.0 / 243.0);
        newRatioMap.put("3 - r2", 16.0 / 15.0);
        newRatioMap.put("4 - R1", 10.0 / 9.0);
        newRatioMap.put("5 - R2", 9.0 / 8.0);
        newRatioMap.put("6 - g1", 32.0 / 27.0);
        newRatioMap.put("7 - g2", 6.0 / 5.0);
        newRatioMap.put("8 - G1", 5.0 / 4.0);
        newRatioMap.put("9 - G2", 81.0 / 64.0);
        newRatioMap.put("10 - M1", 4.0 / 3.0);
        newRatioMap.put("11 - M2", 27.0 / 20.0);
        newRatioMap.put("12 - m1", 45.0 / 32.0);
        newRatioMap.put("13 - m2", 729.0 / 512.0);
        newRatioMap.put("14 - P", 3.0 / 2.0);
        newRatioMap.put("15 - d1", 128.0 / 81.0);
        newRatioMap.put("16 - d2", 8.0 / 5.0);
        newRatioMap.put("17 - D1", 5.0 / 3.0);
        newRatioMap.put("18 - D2", 27.0 / 16.0);
        newRatioMap.put("19 - n1", 16.0 / 9.0);
        newRatioMap.put("20 - n2", 9.0 / 5.0);
        newRatioMap.put("21 - N1", 15.0 / 8.0);
        newRatioMap.put("22 - N2", 243.0 / 128.0);
        newRatioMap.put("1 - S'", 2.0);

        return newRatioMap;
    }
}