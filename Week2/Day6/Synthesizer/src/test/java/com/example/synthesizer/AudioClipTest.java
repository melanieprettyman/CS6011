package com.example.synthesizer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.example.synthesizer.AudioClip.*;
import static org.junit.jupiter.api.Assertions.*;

class AudioClipTest {

    AudioClip testclip = new AudioClip();
    @Test
    public void tests(){



        for(int i = Short.MIN_VALUE; i<= Short.MAX_VALUE; i++){
            int index=0;
            testclip.setSample(index,i);
            int get = testclip.getSample(index);
            Assertions.assertEquals(get, i);
        }


    }

}