package com.example.demo;


public class SineWave implements AudioComponent{

//Initialize Variables
    //used to store the frequency of the sine wave
    public float frequency_;
    double sampleRate=44100;


    //used to hold the generated sine wave audio data

    //CONSTRUCTOR
        //it takes in frequency and generate a sine wave and store it in the audioClip
    public SineWave(float frequency) {
        frequency_ = frequency;
    }



    //Helper method to get sampleValue at position 'i' in array


    //GET-AUDIO
    //method should create and fill in an AudioClip with sample values from a sine wave
    @Override
    public AudioClip getClip() {

        //a new AudioClip is created and assigned to the audioClip variable, and will be used to store the generated sine wave data
       AudioClip _audioClip = new AudioClip();
        //sets maxValue with the maximum value of a short data type. This value represents the maximum amplitude (loudness) of the sine wave
        int maxValue = Short.MAX_VALUE;

        //generate the samples of the sine wave and store them in the audioClip
        for (int i = 0; i < AudioClip.TOTAL_SAMPLES; i++) {
            int sampleValue = (int) ( maxValue * ( Math.sin (2 * Math.PI * frequency_ * i/sampleRate) ) );
            //Call setSample and store the sampleValue at the current position i in the audio data array.
            _audioClip.setSample(i, sampleValue);
        }
        return _audioClip;
    }

    @Override
    //can you connect something to this as an input
    public boolean hasInput() {
        return false;
    }

    @Override
    //connect another device to this input. For most classes implementing this interface,
    // this method will just store a reference to the AudioComponent parameter.
    // If the component doesn't accept inputs, you can assert( false ) in here.
    public void connectInput(AudioComponent input) {
        SineWave sn = (SineWave) input;

    }

    public void updateFrequency(int frequency){
        frequency_=frequency;
    }




}
