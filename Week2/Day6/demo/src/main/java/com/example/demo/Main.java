package com.example.demo;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

public class Main {
    public static void main(String[] args) throws LineUnavailableException {

        // Get properties from the system about samples rates, etc.
// AudioSystem is a class from the Java standard library.
        Clip c = AudioSystem.getClip(); // Note, this is different from our AudioClip class.

// This is the format that we're following, 44.1 KHz mono audio, 16 bits per sample.
        AudioFormat format16 = new AudioFormat( 44100, 16, 1, true, false );

        //===============================================================================//
        /*                            GENERATE WAVES                                     */
        //===============================================================================//

        //-----MIXER WAVES------
        AudioComponent gen = new SineWave(440); // Your code
        AudioComponent gen2 = new SineWave(200); // Your code


        //-----VOLUME ADJUSTER-----
        VolumeAdjuster lowerVolume = new VolumeAdjuster(.5);
        // Connect the sine wave as the input for your volume object
        lowerVolume.connectInput(gen);
        lowerVolume.connectInput(gen2);

        //--------MIXER-------
        Mixer mixedWaves = new Mixer();
        mixedWaves.connectInput(gen);
        mixedWaves.connectInput(gen2);

        //--------SQUARE WAVE---------
        // Create a SquareWave object with a frequency of 440 Hz and a duration of 2 seconds
        SquareWave squareWave = new SquareWave(440);

        //--------VF SINE WAVE---------
        // Create an instance of VFSineWave
        VFSineWave vfsineWave = new VFSineWave();
        // Create an instance of LinearRamp
        LinearRamp linearRamp = new LinearRamp(50, 1000);
        // Connect the LinearRamp as input to the VFSineWave
        vfsineWave.connectInput(linearRamp);
        // Get the audio clip from VFSineWave
        //AudioClip clip = vfsineWave.getClip();

                //~~~~~~~~~~~~~~~~~~~~~
                /*   AUDIO-CLIP      */
                //~~~~~~~~~~~~~~~~~~~~~

                //SINE WAVE AUDIO-CLIP
                //AudioClip clip = gen.getClip();

                //VOLUME ADJUSTER AUDIO-CLIP
                //AudioClip clip = lowerVolume.getClip();

                //SQUARE WAVE AUDIO-CLIP
                //AudioClip clip = squareWave.getClip();

                //MIXER WAVE
                //AudioClip clip = mixedWaves.getClip();

                //VF SINE WAVE
                AudioClip clip = vfsineWave.getClip();


        //===============================================================================//

        c.open( format16, clip.getData(), 0, clip.getData().length ); // Reads data from our byte array to play it.

        System.out.println( "About to play..." );
        c.start(); // Plays it.
        c.loop( 2 ); // Plays it 2 more times if desired, so 6 seconds total

// Makes sure the program doesn't quit before the sound plays.
        while( c.getFramePosition() < AudioClip.TOTAL_SAMPLES || c.isActive() || c.isRunning() ){
            // Do nothing while we wait for the note to play.
        }

        System.out.println( "Done." );
        c.close();

    }
}
