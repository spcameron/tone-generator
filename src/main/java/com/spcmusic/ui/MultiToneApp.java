package com.spcmusic.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MultiToneApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle("Interval Player");
        stage.setResizable(false);

        ToneBox leftToneBox = new ToneBox();
        ToneBox rightToneBox = new ToneBox();
        IntervalBox centerBox = new IntervalBox(leftToneBox, rightToneBox);

        Pane leftTonePane = leftToneBox.buildPane();
        Pane rightTonePane = rightToneBox.buildPane();
        Pane centerPane = centerBox.buildPane();

        BorderPane layout = new BorderPane();
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.setLeft(leftTonePane);
        layout.setRight(rightTonePane);
        layout.setCenter(centerPane);

        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.show();
    }
}