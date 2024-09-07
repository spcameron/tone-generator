package com.spcmusic.ui;

import com.spcmusic.domain.Tone;

import java.text.DecimalFormat;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ToneBox {
    private final Tone TONE;
    private TextField frequencyInput;
    private Slider frequencySlider;

    public ToneBox() {
        TONE = new Tone();
    }

    public ToneBox(Tone tone) {
        this.TONE = tone;
    }

    public Pane buildPane() {
        Pane pane = new VBox();

        HBox frequencyBox = buildFrequencyBox();
        HBox volumeBox = buildVolumeBox();
        HBox outputBox = buildOutputBox();
        HBox oscillatorBox = buildOscillatorBox();
        HBox transpositionBox = buildTranspositionBox();
        HBox controlsBox = buildControlsBox();

        pane.getChildren().add(frequencyBox);
        pane.getChildren().add(volumeBox);
        pane.getChildren().add(new Separator());
        pane.getChildren().add(outputBox);
        pane.getChildren().add(new Separator());
        pane.getChildren().add(oscillatorBox);
        pane.getChildren().add(new Separator());
        pane.getChildren().add(transpositionBox);
        pane.getChildren().add(new Separator());
        pane.getChildren().add(controlsBox);

        return pane;
    }

    public double getFrequency() {
        return TONE.getFrequency();
    }

    public void updateFrequency(double newFrequency) {
        DecimalFormat df = new DecimalFormat("#.00");
        TONE.setFrequency(newFrequency);
        TONE.getOscillator().frequency.set(newFrequency);
        frequencyInput.setText(df.format(TONE.getFrequency()));
        frequencySlider.setValue(TONE.getFrequency());
    }

    private HBox buildFrequencyBox() {
        DecimalFormat df = new DecimalFormat("#.00");
        Label frequencyLabel = new Label("Frequency (Hz):");
        this.frequencyInput = new TextField(df.format(TONE.getFrequency()));
        this.frequencySlider = new Slider(0, 20000, TONE.getFrequency());

        frequencyInput.setOnAction(event -> {
            double newFrequency = Double.parseDouble(frequencyInput.getText());
            TONE.setFrequency(newFrequency);
            TONE.getOscillator().frequency.set(newFrequency);
            frequencySlider.setValue(TONE.getFrequency());
        });

        frequencySlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            double newFrequency = newValue.doubleValue();
            TONE.setFrequency(newFrequency);
            TONE.getOscillator().frequency.set(newFrequency);
            frequencyInput.setText(df.format(TONE.getFrequency()));
        });

        HBox box = new HBox(frequencyLabel, frequencyInput, frequencySlider);
        box.setAlignment(Pos.CENTER);
        box.setSpacing(10);
        box.setPadding(new Insets(10, 10, 10, 10));

        return box;
    }

    private HBox buildVolumeBox() {
        DecimalFormat df = new DecimalFormat("#.0");
        Label volumeLabel = new Label("Volume (%)");
        TextField volumeInput = new TextField(df.format(TONE.getAmplitude() * 100));
        Slider volumeSlider = new Slider(0, 100, (TONE.getAmplitude() * 100));

        volumeInput.setOnAction(event -> {
            double newAmplitude = Double.parseDouble(volumeInput.getText()) / 100.0;
            TONE.setAmplitude(newAmplitude);
            TONE.getOscillator().amplitude.set(newAmplitude);
            volumeSlider.setValue(TONE.getAmplitude() * 100);
        });

        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            double newAmplitude = newValue.doubleValue() / 100.0;
            TONE.setAmplitude(newAmplitude);
            TONE.getOscillator().amplitude.set(newAmplitude);
            volumeInput.setText(df.format(TONE.getAmplitude() * 100));
        });

        HBox box = new HBox(volumeLabel, volumeInput, volumeSlider);
        box.setAlignment(Pos.CENTER);
        box.setSpacing(10);
        box.setPadding(new Insets(10, 10, 10, 10));

        return box;
    }

    private HBox buildOutputBox() {
        ToggleGroup outputGroup = new ToggleGroup();

        ToggleButton outputLeftButton = new ToggleButton("Left");
        outputLeftButton.setToggleGroup(outputGroup);
        outputLeftButton.setOnAction(event -> {
            TONE.setOutputLeftOnly();
        });

        ToggleButton outputRightButton = new ToggleButton("Right");
        outputRightButton.setToggleGroup(outputGroup);
        outputRightButton.setOnAction(event -> {
            TONE.setOutputRightOnly();
        });

        ToggleButton outputLeftAndRightButton = new ToggleButton("L+R");
        outputLeftAndRightButton.setToggleGroup(outputGroup);
        outputLeftAndRightButton.setOnAction(event -> {
            TONE.setOutputLeftAndRight();
        });

        outputLeftAndRightButton.setSelected(true);
        HBox box = new HBox(outputLeftButton, outputLeftAndRightButton, outputRightButton);
        box.setAlignment(Pos.CENTER);
        box.setSpacing(10);
        box.setPadding(new Insets(10, 10, 10, 10));

        return box;
    }

    private HBox buildOscillatorBox() {
        ToggleGroup oscillatorGroup = new ToggleGroup();

        ToggleButton sineButton = new ToggleButton("Sine");
        sineButton.setToggleGroup(oscillatorGroup);
        sineButton.setOnAction(event -> {
            TONE.setOscillatorType("Sine");
        });

        ToggleButton squareButton = new ToggleButton("Square");
        squareButton.setToggleGroup(oscillatorGroup);
        squareButton.setOnAction(event -> {
            TONE.setOscillatorType("Square");
        });

        ToggleButton triangleButton = new ToggleButton("Triangle");
        triangleButton.setToggleGroup(oscillatorGroup);
        triangleButton.setOnAction(event -> {
            TONE.setOscillatorType("Triangle");
        });

        ToggleButton sawtoothButton = new ToggleButton("Sawtooth");
        sawtoothButton.setToggleGroup(oscillatorGroup);
        sawtoothButton.setOnAction(event -> {
            TONE.setOscillatorType("Sawtooth");
        });

        sineButton.setSelected(true);
        HBox box = new HBox(sineButton, squareButton, triangleButton, sawtoothButton);
        box.setAlignment(Pos.CENTER);
        box.setSpacing(10);
        box.setPadding(new Insets(10, 10, 10, 10));

        return box;
    }

    private HBox buildControlsBox() {
        ToggleGroup controlsGroup = new ToggleGroup();

        ToggleButton playButton = new ToggleButton("Play");
        playButton.setToggleGroup(controlsGroup);
        playButton.setOnAction(event -> {
            TONE.play();
        });

        ToggleButton pauseButton = new ToggleButton("Pause");
        pauseButton.setToggleGroup(controlsGroup);
        pauseButton.setOnAction(event -> {
            TONE.pause();
        });

        HBox box = new HBox(playButton, pauseButton);
        box.setAlignment(Pos.CENTER);
        box.setSpacing(10);
        box.setPadding(new Insets(10, 10, 10, 10));

        return box;
    }

    private HBox buildTranspositionBox() {
        Button octaveUpButton = new Button("Octave Higher");
        octaveUpButton.setOnAction(event -> {
            updateFrequency(getFrequency() * 2.0);
        });

        Button octaveDownButton = new Button("Octave Lower");
        octaveDownButton.setOnAction(event -> {
            updateFrequency(getFrequency() / 2.0);
        });

        HBox box = new HBox(octaveUpButton, octaveDownButton);
        box.setAlignment(Pos.CENTER);
        box.setSpacing(10);
        box.setPadding(new Insets(10, 10, 10, 10));

        return box;
    }
}