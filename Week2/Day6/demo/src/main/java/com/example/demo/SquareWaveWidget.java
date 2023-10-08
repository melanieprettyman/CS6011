package com.example.demo;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class SquareWaveWidget extends AudioComponentWidgetBase{
    private final Label squareWaveLabel_;

    SquareWaveWidget(AudioComponent audioComponent, AnchorPane pane, float lowerRange, float upperRange, int sliderValue) {
        super(audioComponent, pane, lowerRange, upperRange, sliderValue);

        leftSide.getChildren().removeAll(frequenceLabel_, slider_);
        squareWaveLabel_ = new Label("Square Wave" );
        squareWaveLabel_.setStyle("-fx-font-family: 'Comic Sans MS';-fx-text-fill: #9B55E0");

        leftSide.getChildren().addAll(squareWaveLabel_, slider_);


    }

    @Override
    protected void handleSlider(Object e) {
        AudioComponent ac_ = getAudioComponent();
        // Get the value of the frequency slider
        int sliderValue = (int) slider_.getValue();

        // Update the label text with the selected frequency
        squareWaveLabel_.setText("SquareWave  " + sliderValue + " Hz");
        // Update the slideFrequency field
        ((SquareWave) ac_).updateFrequency(sliderValue);
    }
}
