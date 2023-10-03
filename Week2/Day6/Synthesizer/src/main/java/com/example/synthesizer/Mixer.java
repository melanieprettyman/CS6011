package com.example.synthesizer;


import java.util.ArrayList;

public class Mixer implements AudioComponent{

   public  ArrayList<AudioClip> input_ = new ArrayList<AudioClip>();

    @Override
    public AudioClip getClip() {
        AudioClip result = new AudioClip();

        //loop through array list of waves
        for(AudioClip wave: input_){
            //for each wave loop through the wave
            for (int i = 0; i < AudioClip.TOTAL_SAMPLES; i++) {
                //Add sample to results audio-clip (to add waves you add corresponding amplitudes)
                int Sample = (int)(result.getSample(i) + wave.getSample(i));
                // Ensure the adjustedSample value stays within the valid range (clamp the sounds)
                int max = Short.MAX_VALUE;
                int min = Short.MIN_VALUE;

                // Set the adjusted sample value in the result clip
                if (Sample < min) {
                    Sample=min;
                }
                else if (Sample > max) {
                    Sample = max;
                }
                result.setSample(i, Sample);
            }
        }
        //return result;
        return result;
    }

    @Override
    public boolean hasInput() {
        return false;
    }

    @Override
    public void connectInput(AudioComponent input) {

        VolumeAdjuster lowerVolume = new VolumeAdjuster(.5);
        // Connect the sine wave as the input for your volume object
        lowerVolume.connectInput(input);

        input_.add(lowerVolume.getClip());

    }
}
