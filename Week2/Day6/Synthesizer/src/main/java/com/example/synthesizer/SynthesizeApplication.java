package com.example.synthesizer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;


public class SynthesizeApplication extends Application {
    private Clip c; // Declare a Clip field for audio playback
    int slideFrequency = 0; // Store the slider frequency value

    @Override
    public void start(Stage stage) {
        // Set the title of the application window
        stage.setTitle("Synthesizer");

        // Create a slider to control the frequency
        // Frequency range: 50 to 2000 Hz
        // Step value for slider changes: 10
        Slider frequencySlider = new Slider(50, 2000, 10);

        // Create a play button
        Button playButton = new Button("Play");

        // Set an action for the play button, calling the handlePlayPress function
        playButton.setOnAction(e -> {
            try {
                // Call the handlePlayPress function with the current slider frequency
                handlePlayPress(slideFrequency);
            } catch (LineUnavailableException ex) {
                throw new RuntimeException(ex);
            }
        });

        // Create a label for displaying frequency information
        Label frequenceLabel = new Label("Sinewave");

        // Update the label when the slider is dragged
        frequencySlider.setOnMouseDragged(e -> handleSlider(e, frequencySlider, frequenceLabel));

        // Create a layout using AnchorPane
        AnchorPane mainLayout = new AnchorPane();

        // Create a VBox to hold frequency controls
        VBox frequencyBox = new VBox(10);
        // Position the frequency box in the middle of the screen
        frequencyBox.relocate(500, 500);
        // Set the frequency box with pink background
        frequencyBox.setStyle("-fx-padding: 10; -fx-background-color: #cc8ace");

        // Add the slider, label, and play button to the frequency box
        frequencyBox.getChildren().addAll(frequenceLabel, frequencySlider, playButton);

        // Add the frequency box to the AnchorPane
        mainLayout.getChildren().add(frequencyBox);

        // Create a scene and set it on the stage
        Scene scene = new Scene(mainLayout, 1000, 1000);
        stage.setScene(scene);

        stage.show();
    }

    // Handle slider dragging event and update label
    private void handleSlider(MouseEvent e, Slider frequencySlider, Label frequenceLabel) {
        // Get the value of the frequency slider
        int result = (int) frequencySlider.getValue();
        // Update the label text with the selected frequency
        frequenceLabel.setText("SineWave  " + result + " Hz");
        // Update the slideFrequency field
        slideFrequency = result;
    }

    // Method to stop audio playback
    private void stopPlayback() {
        if (c != null && c.isRunning()) {
            c.stop();
            c.close();
        }
    }

    // Handle play button press event
    private void handlePlayPress(int frequency) throws LineUnavailableException {
        // Create a SquareWave generator with the selected frequency
        AudioComponent gen = new SineWave(frequency);
        // Generate an AudioClip
        AudioClip clip = gen.getClip();

        // Create a Clip for audio playback
        Clip c = AudioSystem.getClip();
        // Define the audio format
        AudioFormat format16 = new AudioFormat(44100, 16, 1, true, false);

        // Open the Clip with the audio data
        c.open(format16, clip.getData(), 0, clip.getData().length);
        // Start audio playback
        c.start();

        // Makes sure the program doesn't quit before the sound plays.
        while (c.getFramePosition() < AudioClip.TOTAL_SAMPLES || c.isActive() || c.isRunning()) {
            // Do nothing while waiting for the note to play.
        }

        // Close the audio clip
        c.close();
    }

    // The main entry point for the JavaFX application
    public static void main(String[] args) {
        launch();
    }
}
