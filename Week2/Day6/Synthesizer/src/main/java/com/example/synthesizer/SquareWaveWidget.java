package com.example.synthesizer;

import javafx.scene.layout.AnchorPane;

public class SquareWaveWidget extends AudioComponentWidgetBase{
    SquareWaveWidget(AudioComponent audioComponent, AnchorPane pane, String sliderLabel, float lowerRange, float upperRange, int sliderValue) {
        super(audioComponent, pane, sliderLabel, lowerRange, upperRange, sliderValue);
    }

    @Override
    protected void handleSlider(Object e) {
        AudioComponent ac_ = getAudioComponent();
        // Get the value of the frequency slider
        int sliderValue = (int) slider_.getValue();

        // Update the label text with the selected frequency
        frequenceLabel_.setText("SquareWave  " + sliderValue + " Hz");
        // Update the slideFrequency field
        ((SquareWave) ac_).updateFrequency(sliderValue);
    }
}
