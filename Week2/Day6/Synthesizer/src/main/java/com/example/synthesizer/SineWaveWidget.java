package com.example.synthesizer;

import javafx.scene.layout.VBox;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

//public class SinewaveWidget extends AudioComponentWidgetBase {

//    SinewaveWidget(){
//        createSliderWidget(50,2000,10,"SineWave");
//    }
//
//    //Create SineWave
//SinewaveWidget sineWave = new SinewaveWidget();
//
//
//    // Define an action for the play button
//        playButton.setOnAction(e -> {
//
//        //set frequency to the value of the frequency value
//        float frequency = (float) frequencySlider.getValue();
//
//        // Generate audio using the SineWave component with the selected frequency
//        SineWave sineWave = new SineWave(frequency);
//        AudioClip clip = sineWave.getClip();
//
//        // Stop the previous playback (if any)
//        //stopPlayback();
//
//        // Play the audio clip
//        Clip c = null;
//        try {
//            c = AudioSystem.getClip();
//            AudioFormat format16 = new AudioFormat(AudioClip.sampleRate_, 16, 1, true, false);
//            c.open(format16, clip.getData(), 0, clip.getData().length);
//            c.start();
//        } catch (LineUnavailableException ex) {
//            throw new RuntimeException(ex);
//        }
//
//        // Makes sure the program doesn't quit before the sound plays.
//        while (c.getFramePosition() < AudioClip.TOTAL_SAMPLES || c.isActive() || c.isRunning()) {
//            // Do nothing while we wait for the note to play.
//        }
//        c.close();
//    });
//}


