package com.example.synthesizer;

import java.util.Arrays;

public class AudioClip {

    //Duration is 2 seconds
     public static double duration_ = 2.0;

    //Sample rate is 44100 samples per second
     public static int sampleRate_=44100;

    //member variable that contains the actual byte array
     public byte[] audioClipArray;

    // Define the total number of samples
        //multiply by 2 there are 2 bytes per sample, and the array stores each byte separately
    public static final int TOTAL_SAMPLES = ((int) (duration_ * sampleRate_));

    // Constructor to initialize the byte array
    public AudioClip() {
        //multiply by 2 there are 2 bytes per sample, and the array stores each byte separately
        audioClipArray = new byte[TOTAL_SAMPLES *2];
    }



    //METHOD INT GET-SAMPLE
    // (return the sample at index i as an int)
    /*You will need to use bitwise operators to perform these conversions! The ints that
    are passed/returned should be in the range of shorts.*/

    public int getSample (int index){
        //Get location of each byte
        byte firstbyte = audioClipArray[index*2];
        byte secondbyte = audioClipArray[index*2 +1];
        //Initialize return type as integer
        int result = 0;

        //Turn byte 2 into a short (under the hood it will be Byte2-0)
        //Turn byte 1 into a short (under the hood it will be 0-Byte1)

        //Compare byte1 with byt2 ( (Byte1-0) with (0-Byte2) ) will produce (Byte1-Byte2)
        result = secondbyte<<8 | (firstbyte & 0xFF);
        return result;
    }


    //method setSample( index, value )
    //(return/set the sample passed as an int)
    /*You will need to use bitwise operators to perform these conversions! The ints that
    are passed/returned should be in the range of shorts.*/

     public  void setSample( int index, int value ){
        //Isolate byte1
        int b1= (value >> 8);
        int b2 = (value & 0xFF);

        //Convert int rep byte1 and byte2 to bytes
        byte byte1 = (byte) b1;
        byte byte2 = (byte) b2;

        //Change the value of byte 1 and byte2 in the array
        audioClipArray[(index*2)+1] = byte1;
        audioClipArray[(index*2)] = byte2;


    }



    //A method byte[] getData()
    // returns the array
    // (returning a copy, check out Arrays.copyOf)
    // We need this method because the Java library that actually plays sounds expects our
    // data as an array of bytes.

    byte[] getData(){
        return Arrays.copyOf(audioClipArray, (TOTAL_SAMPLES *2));
    }





}
