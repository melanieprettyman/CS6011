package com.example.synthesizer;
// LinearRamp generates a linear ramp waveform
// It transitions from a 'start' value to a 'stop' value over time

public class LinearRamp implements AudioComponent {
    // Starting value of the linear ramp
    private final double start_;
    // Ending value of the linear ramp
    private final double stop_;

    public LinearRamp(double start, double stop) {
        start_ = start;
        stop_ = stop;
    }

    // Generate an audio clip representing the linear ramp waveform
    @Override
    public AudioClip getClip() {
        // Create a new audio clip to store linear wave
        AudioClip audioClip = new AudioClip();

        int numSamples = audioClip.TOTAL_SAMPLES;

        // Iterate through each sample and calculate the linear ramp value for each
        for (int i = 0; i < numSamples; i++) {
            double sampleValue = (start_ * (numSamples - i) + stop_ * i) / numSamples;
            // Set the calculated sample value in the audio clip
            audioClip.setSample(i, (int) sampleValue);
        }
        return audioClip;
    }

    @Override
    public boolean hasInput() {
        return false;
    }

    @Override
    public void connectInput(AudioComponent input) {
        // No inputs, generates a linear ramp which will be the input for variable-freq-wave
    }
}

