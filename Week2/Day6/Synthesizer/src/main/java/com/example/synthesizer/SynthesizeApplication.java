package com.example.synthesizer;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;


public class SynthesizeApplication extends Application {
    private Clip c; // Declare a Clip field for audio playback
    AnchorPane mainCenter;

    public static ArrayList<AudioComponentWidgetBase> widgets_ = new ArrayList<>();
    public static ArrayList<AudioComponentWidgetBase> connectedWidgets_ = new ArrayList<>();


    public static Circle speaker;


    @Override
    public void start(Stage stage) {
        // Create a layout using AnchPane
        BorderPane mainLayout = new BorderPane();

        // Set the title of the application window
        stage.setTitle("Synthesizer");

        String cssLayoutbot = "-fx-border-color: #000000;\n" +
                "-fx-border-insets: 0;\n" +
                "-fx-border-width: 3;\n" +
                "-fx-border-style: line;\n";

        //----------RIGHT PANEL---------- (Wave Buttons/Vol Button and creating widgets)
        //Create right panel
        VBox rightPanel = new VBox();
        //Create sineWave button
        Button sinewaveButton = new Button("SineWave");
        sinewaveButton.setStyle("-fx-font-family: 'Comic Sans MS'");

        //Style right panel
        rightPanel.setStyle(cssLayoutbot + "-fx-background-color: #b570e3");
        rightPanel.setPadding(new Insets(4));
        //Set sineWave button to create a sine-wave widget when clicked
        sinewaveButton.setOnAction(this::creatWidget);

        //Create volume button and set actions
        Button volButton = new Button("Volume:");
        volButton.setStyle("-fx-font-family: 'Comic Sans MS'");


        volButton.setOnAction(e1 -> {
            try {
                createVolume(e1);
            } catch (LineUnavailableException e) {
                throw new RuntimeException(e);
            }
        });

        //Add buttons to the right panel
        rightPanel.getChildren().addAll(sinewaveButton, volButton);


        //----------CENTER  PANEL---------- (Speaker)
        //Create center panel
        mainCenter = new AnchorPane();
        //Style center panel
        mainCenter.setStyle("-fx-background-color: #e5a7e8");

        //Create speaker
        speaker = new Circle(400, 200, 15);
        //Style speaker
        speaker.setFill(Color.DARKBLUE);
        //Add speaker to center panel
        mainCenter.getChildren().add(speaker);


        //----------BOTTOM  PANEL---------- (Play button)
        //Create bottom panel
        HBox bottomPanel = new HBox();
        //Style bottom panel
        bottomPanel.setStyle(cssLayoutbot + "-fx-background-color: #800062");

        //Create play button
        Button playButton = new Button("⫸");
        bottomPanel.setAlignment(Pos.CENTER);
        playButton.setStyle("-fx-background-color: rgba(241,141,188,0.03); -fx-text-fill: white; -fx-border-color: white; -fx-border-width: 2px; -fx-font-family: 'Comic Sans MS'");

        //Add play button the bottom panel
        bottomPanel.getChildren().add(playButton);
        // Set an action for the play button, calling the playAudio function
        playButton.setOnAction(e -> {
            try {
                playAudio(e);
            } catch (LineUnavailableException ex) {
                throw new RuntimeException(ex);
            }
        });


        //Add panels to main layout
        mainLayout.setCenter(mainCenter);
        mainLayout.setRight(rightPanel);
        mainLayout.setBottom(bottomPanel);

        // Create a scene and set it on the stage
        Scene scene = new Scene(mainLayout, 1000, 1000);
        stage.setScene(scene);

        stage.show();
    }




    //Creates a widget
    private void creatWidget(ActionEvent e) {
        int frequency = 50;
        //Create audio component of type sineWave
        AudioComponent sineWave = new SineWave(frequency);
        //create widget that takes sineWave and mainCenter pane
        AudioComponentWidgetBase acw = new AudioComponentWidgetBase(sineWave, mainCenter, "SineWave", 50, 2000, 10);
        //Add widget to main
        mainCenter.getChildren().add(acw);

        //Add to widget array list
        widgets_.add(acw);
    }

    private void createVolume(ActionEvent e) throws LineUnavailableException {
        int volumeScale = 1;
        VolumeAdjuster lowerVolume = null;
        for (AudioComponentWidgetBase widget : connectedWidgets_) {
            AudioComponent ac = widget.audioComponent_;

            lowerVolume = new VolumeAdjuster(volumeScale);
            // Connect the sine wave as the input for your volume object
            lowerVolume.connectInput(ac);
            // Create a widget that takes sineWave and mainCenter pane
        }
            VolumeAdjusterWidget acw = new VolumeAdjusterWidget(lowerVolume, mainCenter, "Volume", 0.5f, 2.0f, 1);
            // Add widget to main
            mainCenter.getChildren().add(acw);
            // Add to widget array list
            widgets_.add(acw);
        }

        private void playAudio(ActionEvent e) throws LineUnavailableException {

            // Create a Clip for audio playback
            Clip c = AudioSystem.getClip();
            // Define the audio format
            AudioFormat format16 = new AudioFormat(44100, 16, 1, true, false);

            //Mixer
            Mixer mixer = new Mixer();
            for (AudioComponentWidgetBase w : connectedWidgets_) {
                AudioComponent ac = w.audioComponent_;
                mixer.connectInput(ac);
            }

            AudioClip clip = mixer.getClip();
            c.open(format16, clip.getData(), 0, clip.getData().length);

            AudioListener listener = new AudioListener(c);

            // Start audio playback
            c.start();
            //Line listener
            c.addLineListener(listener);

        }


    // The main entry point for the JavaFX application
    public static void main (String[]args){
        launch();
    }

}
