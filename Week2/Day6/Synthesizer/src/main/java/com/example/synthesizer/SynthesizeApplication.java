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
import java.util.ArrayList;


public class SynthesizeApplication extends Application {
    private Clip c; // Declare a Clip field for audio playback
    AnchorPane mainCenter;

    public static ArrayList<AudioComponentWidgetBase> widgets_ = new ArrayList<>();
    public static ArrayList<AudioComponentWidgetBase> connectedWidgets_ = new ArrayList<>();

    public static Circle speaker;

    public static VolumeAdjusterWidget acw;
    public static VolumeAdjuster changeVolume;


    @Override
    public void start(Stage stage) {
        // Create a layout using AnchorPane
        BorderPane mainLayout = new BorderPane();

        // Set the title of the application window
        stage.setTitle("Synthesizer");

        // CSS styles for layout and right panel
        String cssLayoutbot = "-fx-border-color: white;\n" +
                "-fx-border-insets: 0;\n" +
                "-fx-border-width: 3;\n" +
                "-fx-border-style: line;\n";

        String cssRightPanel = "-fx-font-family: 'Comic Sans MS';-fx-background-color: #FCC7EB ; -fx-border-color: white; -fx-border-width: 2px;-fx-text-fill: #9B55E0";

        //----------RIGHT PANEL-------------------------------------------------- (Wave Buttons/creating widgets)
        //Create right panel
        VBox rightPanel = new VBox();

        //Style right panel
        rightPanel.setStyle(cssLayoutbot + "-fx-background-color: #A478CF");
        rightPanel.setPadding(new Insets(4));

        //SINE-WAVE BUTTON
        Button sinewaveButton = new Button("SineWave");
        sinewaveButton.setStyle(cssRightPanel);
        //Set sineWave button to create a sine-wave widget when clicked
        sinewaveButton.setOnAction(this::createWidget);

        //SQUARE-WAVE BUTTON
        Button squareButton = new Button("SquareWave");
        squareButton.setStyle(cssRightPanel);
        //Set squareButton to create a sine-wave widget when clicked
        squareButton.setOnAction(this::squareWaveWidget);

        //VF-SINE-WAVE BUTTON
        Button VFSineWaveButton = new Button("VF-SineWave");
        VFSineWaveButton.setStyle(cssRightPanel);
        //Set VFSineWaveButton to create a sine-wave widget when clicked
        VFSineWaveButton.setOnAction(this::VFSineWaveWidget);


        //Add buttons to the right panel
        rightPanel.getChildren().addAll(sinewaveButton, squareButton, VFSineWaveButton);
        rightPanel.setPadding(new Insets(5));
        rightPanel.setSpacing(5);

        //----------CENTER  PANEL------------------------------------------------------- (Speaker and volume widget)
        //Create center panel
        mainCenter = new AnchorPane();
        //Style center panel
        mainCenter.setStyle("-fx-background-color: #FEE3FF");

        //Create speaker
        speaker = new Circle(400, 200, 15);
        //Style speaker
        speaker.setFill(Color.BLACK);
        //Add speaker to center panel
        mainCenter.getChildren().add(speaker);
        createVolume();


        //----------BOTTOM  PANEL------------------------------------------------------(Play button)
        //Create bottom panel
        HBox bottomPanel = new HBox();
        //Style bottom panel
        bottomPanel.setStyle(cssLayoutbot + "-fx-background-color: #F08BCB");

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


        //----------COMBINE-PANELS-----------
        //Add panels to main layout
        mainLayout.setCenter(mainCenter);
        mainLayout.setRight(rightPanel);
        mainLayout.setBottom(bottomPanel);

        // Create a scene and set it on the stage
        Scene scene = new Scene(mainLayout, 1000, 1000);
        stage.setScene(scene);

        stage.show();
    }

    // Create a VFSineWave widget when the corresponding button is clicked
    private void VFSineWaveWidget(ActionEvent actionEvent) {
        // Create an instance of VFSineWave
        VFSineWave vfsineWave = new VFSineWave();
        // Create an instance of LinearRamp
        LinearRamp linearRamp = new LinearRamp(50, 1000);
        // Connect the LinearRamp as input to the VFSineWave
        vfsineWave.connectInput(linearRamp);

        // Create a VFSineWaveWidget and add it to the mainCenter pane
        VFSineWaveWidget acw = new VFSineWaveWidget(vfsineWave, mainCenter, "VF-SineWave", 0, 0, 0);
        mainCenter.getChildren().add(acw);

        // Add the widget to the widgets array list
        widgets_.add(acw);
    }

    // Creates a SineWave widget when the corresponding button is clicked
    private void createWidget(ActionEvent e) {
        int frequency = 50;
        // Create an audio component of type SineWave
        AudioComponent sineWave = new SineWave(frequency);
        // Create a widget that takes the SineWave and mainCenter pane
        AudioComponentWidgetBase acw = new AudioComponentWidgetBase(sineWave, mainCenter, "SineWave", 50, 2000, 10);
        mainCenter.getChildren().add(acw);

        // Add the widget to the widgets array list
        widgets_.add(acw);
    }

    // Creates a SquareWave widget when the corresponding button is clicked
    private void squareWaveWidget(ActionEvent e) {
        int frequency = 50;
        // Create an audio component of type SquareWave
        AudioComponent squareWave = new SquareWave(frequency);
        // Create a widget that takes the SquareWave and mainCenter pane
        SquareWaveWidget acw = new SquareWaveWidget(squareWave, mainCenter, "SquareWave", 50, 2000, 10);
        mainCenter.getChildren().add(acw);

        // Add the widget to the widgets array list
        widgets_.add(acw);
    }

    // Creates a volume adjustment widget
    private void createVolume() {
        // Create a VolumeAdjusterWidget
        acw = new VolumeAdjusterWidget(changeVolume, mainCenter, "Volume", 0.0f, 10f, 1);
        // Add the widget to the mainCenter pane
        mainCenter.getChildren().add(acw);
    }

    // Play audio when the play button is clicked
    private void playAudio(ActionEvent e) throws LineUnavailableException {
        // Create a Clip for audio playback
        Clip c = AudioSystem.getClip();
        // Define the audio format
        AudioFormat format16 = new AudioFormat(44100, 16, 1, true, false);

        // Create a Mixer to mix audio from connected widgets
        Mixer mixer = new Mixer();
        for (AudioComponentWidgetBase w : connectedWidgets_) {
            AudioComponent ac = w.audioComponent_;
            mixer.connectInput(ac);
        }

        // Get the volume scale from the slider
        double volumeScale = acw.slider_.getValue();

        // Create a VolumeAdjuster and connect it to the mixer
        VolumeAdjuster volumeAdjuster = new VolumeAdjuster(volumeScale);
        volumeAdjuster.connectInput(mixer);

        // Get the audio clip from the volume adjuster
        AudioClip clip = volumeAdjuster.getClip();

        // Open the Clip with the audio format and clip data
        c.open(format16, clip.getData(), 0, clip.getData().length);

        // Create an AudioListener for the Clip
        AudioListener listener = new AudioListener(c);

        // Start audio playback
        c.start();

        // Add a line listener to the Clip
        c.addLineListener(listener);
    }




    // The main entry point for the JavaFX application
    public static void main (String[]args){
        launch();
    }

}
