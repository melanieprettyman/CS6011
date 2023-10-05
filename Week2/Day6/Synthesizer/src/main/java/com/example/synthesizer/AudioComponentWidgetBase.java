package com.example.synthesizer;
//
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.control.Slider;
//import javafx.scene.layout.AnchorPane;
//import javafx.scene.layout.VBox;
//
//public class AudioComponentWidgetBase {
//
//    public  Button playButton;
//    public VBox box;
//
//
//
//    AudioComponentWidgetBase(int xCoordinate, int yCoordinate){
//        //Make a sliderBox which will hold slider, label, and play button
//        box = new VBox(10);
//        //Put the sliderBox in the middle of the screen
//        box.relocate(xCoordinate,yCoordinate);
//    }
//
//    VBox createSliderWidget(int lowerRange, int upperRange, int dragAmount, String labelContent){
//        //Create slider
//        Slider slider_ = new Slider(lowerRange, upperRange, dragAmount);
//        //Create slider label
//        Label sliderLabel = new Label(labelContent);
//
//        //Create play button
//        playButton = new Button("Play");
//
//        //Make the sliderBox pink
//        AudioComponentWidgetBase sliderBox = new AudioComponentWidgetBase(500, 500);
//        sliderBox.setStyle("-fx-padding: 10; -fx-background-color: #cc8ace");
//
//        //Put the slider, label, playbutton inside the frequencyBox
//        sliderBox.getChildren().addAll(sliderLabel, slider_, playButton);
//        return sliderBox;
//
//    }
//
//
//    }

