package com.example.demo;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;


// Inherit from the AudioComponentWidgetBase class, specializing in VF Sine Wave widgets
public class VFSineWaveWidget extends AudioComponentWidgetBase {
    private final Label VFSineLabel_;

    // Constructor for VFSineWaveWidget
    VFSineWaveWidget(AudioComponent audioComponent, AnchorPane pane, float v, float v1, int i) {
        // Call the constructor of the base class (AudioComponentWidgetBase)
        super(audioComponent, pane, v, v1, i);

        // Frequency sider is not used so remove it from the widget
        leftSide.getChildren().removeAll(slider_, frequenceLabel_);

        VFSineLabel_ = new Label("VF-SineWave" );
        // Apply styles to the label (font family and text color)
        VFSineLabel_.setStyle("-fx-font-family: 'Comic Sans MS';-fx-text-fill: #9B55E0");
        // Add the volume label to the left-side VBox of this widget
        leftSide.getChildren().add(VFSineLabel_);
    }

    @Override
    protected void handleSlider(Object e) {

    }
}




