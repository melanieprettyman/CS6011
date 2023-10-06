package com.example.synthesizer;

public class VolumeAdjuster implements AudioComponent{

    private AudioComponent input_;
    public double volumeScale_;

    public VolumeAdjuster(double volumeScale) {
        volumeScale_ = volumeScale;
    }

    //get a clip from their input and modify it somehow and return the updated clip
    @Override
    public AudioClip getClip() {
        // Get the input clip
        AudioClip original = input_.getClip();

        // Create a new audio clip for the modified sound
        AudioClip result = new AudioClip(); //= Some modification of the original clip.

        //Loop through total samples and modify the sound
        for (int i = 0; i < original.TOTAL_SAMPLES; i++) {
            //Get the sample value from the original clip
            //Adjust the sample value by scaling it with the volumeScale (sample)

            int adjustedSample = (int)(volumeScale_*original.getSample(i));
            // Ensure the adjustedSample value stays within the valid range (clamp the sounds)
            int max = Short.MAX_VALUE;
            int min = Short.MIN_VALUE;

            // Set the adjusted sample value in the result clip
            if (adjustedSample < min) {
                adjustedSample=min;
            }
             else if (adjustedSample > max) {
            adjustedSample = max;
            }
            result.setSample(i, adjustedSample);
        }

        //return result;
        return result;
    }

    @Override
    public boolean hasInput() {
        return false;
    }

    @Override
    // Connect a AudioComponent as the input to the VFSineWave
    public void connectInput(AudioComponent input) {
        input_ = input;

    }

    public void getVolumeScale(int volume){
        volumeScale_=volume;
    }


}
