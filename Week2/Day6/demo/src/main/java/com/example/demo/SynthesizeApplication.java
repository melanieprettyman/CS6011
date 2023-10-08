package com.example.demo;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import javax.sound.sampled.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.input.KeyCode;

import javafx.animation.FillTransition;
import javafx.scene.layout.HBox;
import javafx.util.Duration;


public class SynthesizeApplication extends Application {
//~~~~~~~~~~~~~~~~
//    PIANO
//~~~~~~~~~~~~~~~~
// List of notes with their names, corresponding KeyCode, and MIDI numbers
    private List<Note> notes = Arrays.asList(
            new Note("C", KeyCode.A, 60),
            new Note("D", KeyCode.S, 62),
            new Note("E", KeyCode.D, 64),
            new Note("F", KeyCode.F, 65),
            new Note("G", KeyCode.G, 67),
            new Note("A", KeyCode.H, 69),
            new Note("B", KeyCode.J, 71),
            new Note("C", KeyCode.K, 72),
            new Note("D", KeyCode.L, 74),
            new Note("E", KeyCode.SEMICOLON, 76)
    );

    // MIDI channel for playing notes
    private MidiChannel channel;

    // HBox layout for displaying notes
    private final VBox root = new VBox(15);

     //Create the main content of the application
    private VBox createContent() throws MidiUnavailableException {
        loadChannel();
        root.setPrefSize(175, 600);

        // Create NoteView for each note and add them to the layout
        notes.forEach(note -> {
            NoteView view = new NoteView(note);
            root.getChildren().addAll(view);
            root.setStyle("-fx-background-color: #FEE3FF");
        VBox leftPanel = new VBox();
        leftPanel.getChildren().add(root);
        });
        return root;
    }

    // Load the MIDI channel for playing notes
    private void loadChannel() throws MidiUnavailableException {
        Synthesizer synth = MidiSystem.getSynthesizer();
        synth.open();
        synth.loadInstrument(synth.getDefaultSoundbank().getInstruments()[0]);
        channel = synth.getChannels()[0];
    }
//~~~~~~~~~~~~~~~~~~~
//    SOUND-WAVES
//~~~~~~~~~~~~~~~~~~~
    private Clip c; // Declare a Clip field for audio playback
    AnchorPane mainCenter;

    HBox bottomPanel;


    public static ArrayList<com.example.demo.AudioComponentWidgetBase> widgets_ = new ArrayList<>();
    public static ArrayList<AudioComponentWidgetBase> connectedWidgets_ = new ArrayList<AudioComponentWidgetBase>();

    public static Circle speaker;

    public static VolumeAdjusterWidget acw;
    public static VolumeAdjuster changeVolume;


//------------------------------------------------
//                SYNTHESIZER APP
//------------------------------------------------

    @Override
    public void start(Stage stage) throws MidiUnavailableException, FileNotFoundException {
        // Create a layout using AnchorPane
        BorderPane mainLayout = new BorderPane();

        // Set the title of the application window
        stage.setTitle("♫ Synthesizer App ♫");

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
        speaker = new Circle(550, 350, 40);
        //Style speaker
        speaker.setFill(Color.LIGHTPINK);

        //Creating an image
        Image image = new Image(new FileInputStream("/Users/melanieprettyman/Desktop/MSD/CS6011/CS6011/Week2/Day6/Synthesizer/src/main/java/com/example/synthesizer/speaker.png"));
        //Setting the image view
        ImageView imageView = new ImageView(image);
        //Setting the position of the image
        imageView.setX(473);
        imageView.setY(270);
        //setting the fit height and width of the image view
        imageView.setFitHeight(160);
        imageView.setFitWidth(247);

        //Add speaker to center panel
        mainCenter.getChildren().addAll(speaker, imageView);

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

        //PIANO

//        root.setStyle("-fx-background-color: #FEE3FF");
//        VBox leftPanel = new VBox();
//        leftPanel.getChildren().add(root);


        //----------COMBINE-PANELS-----------
        //Add panels to main layout
        mainLayout.setCenter(mainCenter);
        mainLayout.setRight(rightPanel);
        mainLayout.setBottom(bottomPanel);

        Group groupscene = new Group (mainLayout,createContent());

        //~~~~PIANO~~~~~
       // mainLayout.setLeft(leftPanel);
        // Create a scene and set it on the stage
        Scene scene = new Scene(groupscene, 840, 675);
        // Enable focus and set focus on the piano
        scene.setOnKeyPressed(e -> onKeyPress(e.getCode()));

        stage.setScene(scene);


        //~~~~PIANO~~~~
        // Set focus on the piano keys for key events
        //root.requestFocus();
        // Handle key presses for piano keys
        //root.setOnKeyPressed(e -> onKeyPress(e.getCode()));

        stage.show();
    }

//-----------------------------------------
//                FUNCTIONS
//-----------------------------------------

    // Create a VFSineWave widget when the corresponding button is clicked
    private void VFSineWaveWidget(ActionEvent actionEvent) {
        // Create an instance of VFSineWave
        VFSineWave vfsineWave = new VFSineWave();
        // Create an instance of LinearRamp
        LinearRamp linearRamp = new LinearRamp(50, 1000);
        // Connect the LinearRamp as input to the VFSineWave
        vfsineWave.connectInput(linearRamp);

        // Create a VFSineWaveWidget and add it to the mainCenter pane
        VFSineWaveWidget acw = new VFSineWaveWidget(vfsineWave, mainCenter, 0, 0, 0);
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
        AudioComponentWidgetBase acw = new AudioComponentWidgetBase(sineWave, mainCenter, 50, 2000, 10);
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
        SquareWaveWidget acw = new SquareWaveWidget(squareWave, mainCenter, 50, 2000, 10);
        mainCenter.getChildren().add(acw);

        // Add the widget to the widgets array list
        widgets_.add(acw);
    }

    // Creates a volume adjustment widget
    private void createVolume() {
        // Create a VolumeAdjusterWidget
        acw = new VolumeAdjusterWidget(changeVolume, mainCenter, 0.0f, 10f, 1);
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




//~~~~~~~~~~~~~~~~
//    PIANO
//~~~~~~~~~~~~~~~~
    // Handle key presses for playing notes and visual effects
    private void onKeyPress(KeyCode key) {
        root.getChildren()
                .stream()
                .map(view -> (NoteView) view)
                .filter(view -> view.note.key.equals(key))
                .forEach(view -> {
                    FillTransition ft = new FillTransition(
                            Duration.seconds(0.15),
                            view.bg,
                            Color.WHITE,
                            Color.BLACK
                    );
                    ft.setCycleCount(2);
                    ft.setAutoReverse(true);
                    ft.play();
                    channel.noteOn(view.note.number, 90);
                });
    }



    // Custom JavaFX Node for displaying a note with its name
    public static class NoteView extends StackPane {
        private Note note;
        private Rectangle bg = new Rectangle(172, 48, Color.WHITE);

        NoteView(Note note) {
            this.note = note;
            bg.setStroke(Color.BLACK);
            bg.setStrokeWidth(2.5);

            getChildren().addAll(bg, new Text(note.name));
        }
    }

    // Custom class representing a musical note
    private static class Note {
        private final String name;
        private final KeyCode key;
        private final int number;

        Note(String name, KeyCode key, int number) {
            this.name = name;
            this.key = key;
            this.number = number;
        }
    }


    // The main entry point for the JavaFX application
    public static void main (String[]args){
        launch();
    }

}
