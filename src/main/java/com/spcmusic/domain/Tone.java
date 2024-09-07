package com.spcmusic.domain;

import com.jsyn.*;
import com.jsyn.unitgen.*;

public class Tone {
    private final Synthesizer SYNTH;
    private UnitOscillator oscillator;
    private LineOut lineOut;

    private boolean leftChannel;
    private boolean rightChannel;
    private boolean isPlaying;

    private double frequency;
    private double amplitude;
    private String oscillatorType;

    public Tone() {
        SYNTH = JSyn.createSynthesizer();

        leftChannel = true;
        rightChannel = true;
        isPlaying = false;

        setFrequency(440);
        setAmplitude(0.5);
        setOscillatorType("Sine");

        updateOscillator();
    }

    public double getFrequency() {
        return frequency;
    }

    public void setFrequency(double frequency) {
        this.frequency = frequency;
    }

    public double getAmplitude() {
        return amplitude;
    }

    public void setAmplitude(double amplitude) {
        this.amplitude = amplitude;
    }

    public String getOscillatorType() {
        return oscillatorType;
    }

    public void setOscillatorType(String oscillatorType) {
        this.oscillatorType = oscillatorType;
        updateOscillator();
    }

    public UnitOscillator getOscillator() {
        return oscillator;
    }

    public void setOscillator(UnitOscillator oscillator) {
        this.oscillator = oscillator;
    }

    public void setOutputLeftAndRight() {
        leftChannel = true;
        rightChannel = true;
        updateOutput();
    }

    public void setOutputLeftOnly() {
        leftChannel = true;
        rightChannel = false;
        updateOutput();
    }

    public void setOutputRightOnly() {
        leftChannel = false;
        rightChannel = true;
        updateOutput();
    }

    private void updateOutput() {
        if (leftChannel && rightChannel) {
            oscillator.output.connect(0, lineOut.input, 0);
            oscillator.output.connect(0, lineOut.input, 1);
        }

        if (leftChannel && !rightChannel) {
            oscillator.output.connect(0, lineOut.input, 0);
            oscillator.output.disconnect(0, lineOut.input, 1);
        }

        if (!leftChannel && rightChannel) {
            oscillator.output.disconnect(0, lineOut.input, 0);
            oscillator.output.connect(0, lineOut.input, 1);
        }
    }

    private void updateOscillator() {
        if (isPlaying) {
            lineOut.stop();
        }

        lineOut = new LineOut();

        setOscillator(new Oscillator(getOscillatorType(), getFrequency(), getAmplitude()).getUnitOscillator());
        updateOutput();

        SYNTH.add(oscillator);
        SYNTH.add(lineOut);

        if (isPlaying) {
            lineOut.start();
        }
    }

    public void play() {
        updateOscillator();
        if (!isPlaying) {
            lineOut.start();
            SYNTH.start();
            isPlaying = true;
        }
    }

    public void pause() {
        lineOut.stop();
        SYNTH.stop();
        isPlaying = false;
    }
}