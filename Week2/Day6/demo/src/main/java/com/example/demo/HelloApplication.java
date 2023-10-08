package com.example.demo;

import javafx.animation.FillTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;


import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

public class HelloApplication extends Application {

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
    private final HBox root = new HBox(15);

    // Create the main content of the application
    private Parent createContent() throws MidiUnavailableException {
        loadChannel();
        root.setPrefSize(700, 300);

        // Create NoteView for each note and add them to the layout
        notes.forEach(note -> {
            NoteView view = new NoteView(note);
            root.getChildren().addAll(view);
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

    // Start the JavaFX application
    @Override
    public void start(Stage stage) throws IOException, MidiUnavailableException {
        Scene scene = new Scene(createContent());

        // Handle key presses
        scene.setOnKeyPressed(e -> onKeyPress(e.getCode()));
        stage.setScene(scene);
        stage.show();
    }

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
        private Rectangle bg = new Rectangle(50, 150, Color.WHITE);

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

    public static void main(String[] args) {
        launch();
    }
}
