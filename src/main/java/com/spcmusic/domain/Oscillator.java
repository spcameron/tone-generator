package com.spcmusic.domain;

import com.jsyn.unitgen.*;

public class Oscillator {

    UnitOscillator unitOscillator;

    public Oscillator(String oscillatorType, double frequency, double amplitude) {
        switch (oscillatorType) {
            case "Sine" -> unitOscillator = new SineOscillator();
            case "Square" -> unitOscillator = new SquareOscillator();
            case "Triangle" -> unitOscillator = new TriangleOscillator();
            case "Sawtooth" -> unitOscillator = new SawtoothOscillator();
            default -> throw new RuntimeException("Unknown Oscillator type: " + oscillatorType);
        }

        unitOscillator.frequency.set(frequency);
        unitOscillator.amplitude.set(amplitude);
    }

    public UnitOscillator getUnitOscillator() {
        return unitOscillator;
    }
}