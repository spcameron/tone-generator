package com.spcmusic.ui;

import com.spcmusic.logic.EDOFactory;
import com.spcmusic.logic.IntervalBuilder;

import java.util.HashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;

public class IntervalBox {
    private final ToneBox FUNDAMENTAL;
    private final ToneBox OVERTONE;

    private double referencePitch;
    private String fundamentalPitchClass;
    private String fundamentalRegister;
    private String overtoneTemperament;
    private String overtoneInterval;

    public IntervalBox() {
        this.FUNDAMENTAL = new ToneBox();
        this.OVERTONE = new ToneBox();
    }

    public IntervalBox(ToneBox left, ToneBox right) {
        this.FUNDAMENTAL = left;
        this.OVERTONE = right;
    }

    public Pane buildPane() {
        Pane pane = new VBox();

        VBox fundamentalOptions = buildFundamentalOptions();
        HBox setFrequencies = buildSetFrequenciesBox();
        VBox overtoneOptions = buildOvertoneOptions();

        pane.getChildren().add(fundamentalOptions);
        pane.getChildren().add(new Separator());
        pane.getChildren().add(setFrequencies);
        pane.getChildren().add(new Separator());
        pane.getChildren().add(overtoneOptions);

        return pane;
    }

    private VBox buildFundamentalOptions() {
        // reference pitch
        Label referenceLabel = new Label ("Reference \"A\" (Hz):");
        ObservableList<String> fundamentalOptions = FXCollections.observableArrayList(
                "435",
                "436",
                "437",
                "438",
                "439",
                "440",
                "441",
                "442",
                "443",
                "445"
        );
        ComboBox<String> referenceComboBox = new ComboBox<>(fundamentalOptions);
        referenceComboBox.setValue("440");
        this.referencePitch = Double.parseDouble(referenceComboBox.getValue());
        referenceComboBox.setMinWidth(100);
        referenceComboBox.setMaxWidth(100);
        referenceComboBox.setOnAction(event -> {
           this.referencePitch = Double.parseDouble(referenceComboBox.getValue());
        });

        HBox referenceBox = new HBox(referenceLabel, referenceComboBox);
        referenceBox.setAlignment(Pos.CENTER_RIGHT);
        referenceBox.setSpacing(10);
        referenceBox.setPadding(new Insets(10, 10, 10, 10));

        // pitch class
        Label pitchClassLabel = new Label("Pitch Class:");
        ObservableList<String> pitchClassOptions = FXCollections.observableArrayList(
                "C",
                "C# / Db",
                "D",
                "D# / Eb",
                "E",
                "F",
                "F# / Gb",
                "G",
                "G# / Ab",
                "A",
                "A# / Bb",
                "B"
        );
        ComboBox<String> pitchClassComboBox = new ComboBox<>(pitchClassOptions);
        pitchClassComboBox.setPromptText("– Choose Pitch Class –");
        pitchClassComboBox.setMinWidth(200);
        pitchClassComboBox.setMaxWidth(200);
        pitchClassComboBox.setOnAction(event -> {
            this.fundamentalPitchClass = pitchClassComboBox.getValue();
        });

        HBox pitchClassBox = new HBox(pitchClassLabel, pitchClassComboBox);
        pitchClassBox.setAlignment(Pos.CENTER_RIGHT);
        pitchClassBox.setSpacing(10);
        pitchClassBox.setPadding(new Insets(10, 10, 10, 10));

        // octave register
        Label octaveLabel = new Label("Octave Register:");
        ObservableList<String> octaveOptions = FXCollections.observableArrayList(
                "0",
                "1",
                "2",
                "3",
                "4",
                "5",
                "6",
                "7"
        );
        ComboBox<String> octaveComboBox = new ComboBox<>(octaveOptions);
        octaveComboBox.setPromptText("– Choose Octave Register –");
        octaveComboBox.setMinWidth(200);
        octaveComboBox.setMaxWidth(200);
        octaveComboBox.setOnAction(event -> {
            this.fundamentalRegister = octaveComboBox.getValue();
        });

        HBox octaveBox = new HBox(octaveLabel, octaveComboBox);
        octaveBox.setAlignment(Pos.CENTER_RIGHT);
        octaveBox.setSpacing(10);
        octaveBox.setPadding(new Insets(10, 10, 10, 10));

        VBox box = new VBox(referenceBox, pitchClassBox, octaveBox);
        box.setAlignment(Pos.CENTER);
        box.setSpacing(10);
        box.setPadding(new Insets(10, 10, 22, 10));

        return box;
    }

    private HBox buildSetFrequenciesBox() {
        Button fundamentalButton = new Button("<- Set Fundamental Tone");
        fundamentalButton.setOnAction(event -> {
            String fundamental = this.fundamentalPitchClass + " " + this.fundamentalRegister;
            EDOFactory edoFactory = new EDOFactory(this.referencePitch);
            HashMap<String, Double> edoMap = edoFactory.getEdoMap();
            this.FUNDAMENTAL.updateFrequency(edoMap.get(fundamental));
        });
        fundamentalButton.setAlignment(Pos.CENTER_LEFT);

        Button overtoneButton = new Button("Set Interval Tone ->");
        overtoneButton.setOnAction(event -> {
            IntervalBuilder builder = new IntervalBuilder(FUNDAMENTAL.getFrequency(), overtoneTemperament, overtoneInterval);
            OVERTONE.updateFrequency(builder.getOvertoneFrequency());
        });
        overtoneButton.setAlignment(Pos.CENTER_RIGHT);

        HBox box = new HBox(fundamentalButton, overtoneButton);
        box.setAlignment(Pos.CENTER);
        box.setSpacing(10);
        box.setPadding(new Insets(10, 10, 10, 10));

        return box;
    }

    private VBox buildOvertoneOptions() {
        // intonation options
        Label intonationLabel = new Label("Intonation:");
        ObservableList<String> intonationOptions = FXCollections.observableArrayList(
                "Just Intonation",
                "Equal Temperament",
                "Pythagorean",
                "Hindustani/Carnatic"
        );
        ComboBox<String> intonationComboBox = new ComboBox<>(intonationOptions);
        intonationComboBox.setPromptText("– Choose the intonation –");
        intonationComboBox.setMinWidth(200);
        intonationComboBox.setMaxWidth(200);

        HBox intonationBox = new HBox(intonationLabel, intonationComboBox);
        intonationBox.setAlignment(Pos.CENTER_RIGHT);
        intonationBox.setSpacing(10);
        intonationBox.setPadding(new Insets(10, 10, 10, 10));

        // interval options
        Label intervalLabel = new Label("Interval:");
        ObservableList<String> intervalOptions = FXCollections.observableArrayList();
        ComboBox<String> intervalComboBox = new ComboBox<>(intervalOptions);
        intervalComboBox.setMinWidth(200);
        intervalComboBox.setMaxWidth(200);

        HBox intervalBox = new HBox(intervalLabel, intervalComboBox);
        intervalBox.setAlignment(Pos.CENTER_RIGHT);
        intervalBox.setSpacing(10);
        intervalBox.setPadding(new Insets(10, 10, 10, 10));

        intonationComboBox.setOnAction(event -> {
            intervalComboBox.setPromptText("– Choose the interval –");
            updateIntervalComboBox(intonationComboBox.getValue(), intervalOptions);
            this.overtoneTemperament = intonationComboBox.getValue();
        });

        intervalComboBox.setOnAction(event -> {
            this.overtoneInterval = intervalComboBox.getValue();
        });

        VBox box = new VBox(intonationBox, intervalBox);
        box.setAlignment(Pos.CENTER);
        box.setSpacing(10);
        box.setPadding(new Insets(10, 10, 10, 10));

        return box;
    }

    private void updateIntervalComboBox(String intonation, ObservableList<String> intervalOptions) {
        switch (intonation) {
            case "Just Intonation" -> {
                intervalOptions.clear();
                intervalOptions.addAll(
                        "Perfect Unison",
                        "Augmented Unison",
                        "Minor Second",
                        "Major Second",
                        "Augmented Second",
                        "Minor Third",
                        "Major Third",
                        "Diminished Fourth",
                        "Perfect Fourth",
                        "Augmented Fourth",
                        "Diminished Fifth",
                        "Perfect Fifth",
                        "Augmented Fifth",
                        "Minor Sixth",
                        "Major Sixth",
                        "Minor Seventh",
                        "Major Seventh",
                        "Perfect Octave"
                );
            }
            case "Equal Temperament" -> {
                intervalOptions.clear();
                intervalOptions.addAll(
                        "Perfect Unison",
                        "Minor Second",
                        "Major Second",
                        "Minor Third",
                        "Major Third",
                        "Perfect Fourth",
                        "Augmented Fourth / Diminished Fifth",
                        "Perfect Fifth",
                        "Minor Sixth",
                        "Major Sixth",
                        "Minor Seventh",
                        "Major Seventh",
                        "Perfect Octave"
                );
            }
            case "Pythagorean" -> {
                intervalOptions.clear();
                intervalOptions.addAll(
                        "Perfect Unison",
                        "Minor Second",
                        "Major Second",
                        "Minor Third",
                        "Major Third",
                        "Perfect Fourth",
                        "Augmented Fourth",
                        "Diminished Fifth",
                        "Perfect Fifth",
                        "Minor Sixth",
                        "Major Sixth",
                        "Minor Seventh",
                        "Major Seventh",
                        "Perfect Octave"
                );
            }
            case "Hindustani/Carnatic" -> {
                intervalOptions.clear();
                intervalOptions.addAll(
                        "1 - S",
                        "2 - r1",
                        "3 - r2",
                        "4 - R1",
                        "5 - R2",
                        "6 - g1",
                        "7 - g2",
                        "8 - G1",
                        "9 - G2",
                        "10 - M1",
                        "11 - M2",
                        "12 - m1",
                        "13 - m2",
                        "14 - P",
                        "15 - d1",
                        "16 - d2",
                        "17 - D1",
                        "18 - D2",
                        "19 - n1",
                        "20 - n2",
                        "21 - N1",
                        "22 - N2",
                        "1 - S'"
                );
            }
            default -> intervalOptions.clear();
        }
    }
}