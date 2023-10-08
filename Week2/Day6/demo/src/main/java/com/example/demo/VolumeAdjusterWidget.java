package com.example.demo;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;


public class VolumeAdjusterWidget extends AudioComponentWidgetBase {

    // Add a separate label for this widget to display the volume
    private final Label volumeLabel_;

    VolumeAdjusterWidget(AudioComponent audioComponent, AnchorPane pane, float v, float v1, int i) {
        // Call the constructor of the base class (AudioComponentWidgetBase)
        super(audioComponent, pane, v, v1, i);

        // Remove the right-side VBox (close button and circle-output). Not needed in this widget
        baseLayout.getChildren().remove(rightSide);
        leftSide.getChildren().removeAll(frequenceLabel_);

        // Set the position of this widget
        this.setLayoutX(500);
        this.setLayoutY(600);

        // Create a label specific to this widget to display the volume
        volumeLabel_ = new Label("Volume: " + getFormattedVolume());
        // Apply styles to the label (font family and text color)
        volumeLabel_.setStyle("-fx-font-family: 'Comic Sans MS';-fx-text-fill: #9B55E0");
        // Add the volume label to the left-side VBox of this widget
        leftSide.getChildren().add(volumeLabel_);
    }

    // Override the handleSlider method from the base class
    // This method is called when the volume slider is dragged
    @Override
    protected void handleSlider(Object e) {
        AudioComponent ac_ = getAudioComponent();
        // Get the value of the volume slider
        float sliderValue = (float) slider_.getValue();
        // Update the label text with the selected volume
        volumeLabel_.setText("Volume: " + getFormattedVolume());
        // Update the volume scale field of the associated audio component
        ((VolumeAdjuster) ac_).getVolumeScale((int) sliderValue);
    }

    // Helper method to format the volume value with two decimal places
    private String getFormattedVolume() {
        float sliderValue = (float) slider_.getValue();
        // Format the volume value with two decimal places
        return String.format("%.2f", sliderValue);
    }

}




