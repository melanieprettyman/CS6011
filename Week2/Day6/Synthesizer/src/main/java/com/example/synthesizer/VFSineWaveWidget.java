package com.example.synthesizer;
import javafx.scene.layout.AnchorPane;


// Inherit from the AudioComponentWidgetBase class, specializing in VF Sine Wave widgets
public class VFSineWaveWidget extends AudioComponentWidgetBase {

    // Constructor for VFSineWaveWidget
    VFSineWaveWidget(AudioComponent audioComponent, AnchorPane pane, String sliderLabel, float v, float v1, int i) {
        // Call the constructor of the base class (AudioComponentWidgetBase)
        super(audioComponent, pane, sliderLabel, v, v1, i);

        // Frequency sider is not used so remove it from the widget
        leftSide.getChildren().remove(slider_);
    }

    @Override
    protected void handleSlider(Object e) {
        // The handleSlider method is not implemented in this class
        // This is intentional, as VF SineWave widgets do not use sliders
    }
}




