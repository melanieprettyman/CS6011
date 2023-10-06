package com.example.synthesizer;

import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.*;






public class VolumeAdjusterWidget extends AudioComponentWidgetBase {


    VolumeAdjusterWidget(AudioComponent audioComponent, AnchorPane pane, String sliderLabel, float v, float v1, int i) {
        super(audioComponent, pane, sliderLabel,v,v1,i);


        this.setLayoutX(200);
        this.setLayoutY(600);
    }


    @Override
    protected void handleSlider(Object e) {
        AudioComponent ac_ = getAudioComponent();
        // Get the value of the frequency slider
        float sliderValue = (float) frequencySLider_.getValue();
        // Format the volume value to display only two decimal places
        String formattedVolume = String.format("%.2f", sliderValue);
        // Update the label text with the selected volume
        frequenceLabel_.setText("Volume: " + formattedVolume);
        // Update the slideFrequency field
        ((VolumeAdjuster) ac_).getVolumeScale((int) sliderValue);

    }



}

