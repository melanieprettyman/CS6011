package com.example.synthesizer;

import static com.example.synthesizer.AudioClip.TOTAL_SAMPLES;
import static com.example.synthesizer.AudioClip.duration_;

public class SquareWave implements AudioComponent {
    private final int frequency_;

    // Constructor to initialize the SquareWave with inputted frequency
    public SquareWave(int frequency) {
        frequency_ = frequency;
    }

    @Override
    public AudioClip getClip() {
        // Define the maximum amplitude value for a short
        int maxValue = Short.MAX_VALUE;

        // Create an AudioClip object to store the generated square wave
        AudioClip audioClip = new AudioClip();

        // Loop through each sample in the audio clip
        for (int i = 0; i < audioClip.TOTAL_SAMPLES; i++) {
            // Calculate the waveform value for the current sample
            double waveForm = (frequency_ * ((double) i / AudioClip.sampleRate_)) % 1;

            int sampleValue;

            // If the waveform value is greater than 0.5, set the sample to maxValue,
            // otherwise, set it to -maxValue, creating the square wave pattern
            if (waveForm > 0.5) {
                sampleValue = maxValue;
            } else {
                sampleValue = -maxValue;
            }

            // Set the calculated sample value in the audio clip
            audioClip.setSample(i, sampleValue);
        }

        // Return the generated audio clip containing the square wave
        return audioClip;
    }

    @Override
    public boolean hasInput() {
        // SquareWave does not have inputs, so it always returns false
        return false;
    }

    @Override
    public void connectInput(AudioComponent input) {
        // No inputs for SquareWave, generates wave independently
    }
}
