package com.example.demo;
// VFSineWave generates a variable-frequency sine wave.
// The frequency of the sine wave comes as an input of the object linearRamps audioClip.
public class VFSineWave implements AudioComponent {
    private double phase_ = 0;

    //input component that affects the frequency
    private AudioComponent input_;


    @Override
    // Generate an audio clip w. the variable-frequency sine wave
    public AudioClip getClip() {
        int maxValue_ = Short.MAX_VALUE;
        // Create a new audio clip to store wave
        AudioClip audioClip = new AudioClip();
        // Get the audio clip from the connected input
        AudioClip inputClip = input_.getClip();

        // loop through each sample of the audio clip
        for (int i = 0; i < audioClip.TOTAL_SAMPLES; i++) {
            //Get sample value
            phase_ += 2 * Math.PI * inputClip.getSample(i) / audioClip.sampleRate_;
            int sampleValue = (int) (maxValue_ * Math.sin(phase_));

            // Set the calculated sample value in the audio clip
            audioClip.setSample(i, sampleValue);
        }
        return audioClip;
    }

    @Override
    public boolean hasInput() {
        return true;
    }

    @Override
    // Connect a AudioComponent as the input to this VFSineWave
    public void connectInput(AudioComponent input) {
        input_ = input;
    }


}