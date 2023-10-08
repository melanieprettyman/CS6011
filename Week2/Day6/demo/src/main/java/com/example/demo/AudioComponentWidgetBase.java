package com.example.demo;

import javafx.event.ActionEvent;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

//Inherits AutoPane, BorderPane..ect all panes
public class AudioComponentWidgetBase extends Pane {
    AudioComponent audioComponent_;
    AnchorPane parent_;

    Slider slider_;

    static Label frequenceLabel_;

    double mouseXpos, mouseYpos, widgetXpos, widgetYpos;

    Line line_;

    HBox baseLayout;

    VBox rightSide;

    VBox leftSide;


    // Constructor for AudioComponentWidgetBase
    // Parameters:
    // - audioComponent: The audio component for this widget
    // - pane: The parent AnchorPane where this widget will be placed
    // - sliderLabel: The label text for the slider (e.g., "Frequency" or "Volume")
    // - lowerRange: The lower range value for the slider
    // - upperRange: The upper range value for the slider
    // - sliderValue: The initial value of the slider
    AudioComponentWidgetBase(AudioComponent audioComponent, AnchorPane pane, float lowerRange, float upperRange, int sliderValue){
        // Initialize references to the audio component and parent pane
        audioComponent_ = audioComponent;
        parent_ = pane;

//BASE-BOX
        // Create an HBox to combine left and right sides of the widget
        baseLayout = new HBox();

        // Apply styles to the base-layout box
        String cssPanel = "-fx-background-color: #F4BBFE ; -fx-border-color: white; -fx-border-width: 2px;";
        baseLayout.setStyle(cssPanel);

//RIGHT-SIDE BOX
        // Create a VBox for the right side box containing close button and circle-output
        rightSide = new VBox();

        // Create a close button and set an action for it
        Button closeBtn = new Button("x");
        closeBtn.setStyle("-fx-font-family: 'Comic Sans MS';-fx-border-color: white; -fx-border-width: 2px;");
        closeBtn.setOnAction(this::closeWidget);

        // Create an output circle
        Circle output = new Circle(10);
        output.setFill(Color.BLACK);

        // Handle events for drawing connection lines (mouse events)
        output.setOnMousePressed(e->startConnection(e,output));
        output.setOnMouseDragged(e->moveConnection(e, output));
        output.setOnMouseReleased(e->endConnection(e,output));


                // Add the close button and output circle to the right-side box
                rightSide.getChildren().add(closeBtn);
                rightSide.getChildren().add(output);
                // Style the right side box
                rightSide.setAlignment(Pos.CENTER);
                rightSide.setPadding(new Insets(5));
                rightSide.setSpacing(5);



        // Create a slider to control the parameter (e.g., frequency or volume)
        slider_ = new Slider(lowerRange, upperRange, sliderValue);

//LEFT-SIDE BOX
        // Create a VBox for the left side box containing labels and sliders
        leftSide = new VBox();
        // Create a label for displaying the parameter label (e.g., "Frequency" or "Volume")
        frequenceLabel_ = new Label("SineWave");
        frequenceLabel_.setStyle("-fx-font-family: 'Comic Sans MS';-fx-text-fill: #9B55E0");

        // Add the label and slider to the left side box
        leftSide.getChildren().addAll(frequenceLabel_, slider_);

        // Update positions during drag-and-drop
        leftSide.setOnMousePressed(this::getPosinf);
        leftSide.setOnMouseDragged(this::moveWidget);

        // Handle slider dragging events
        slider_.setOnMouseDragged(this::handleSlider);

        //Add RIGHT-SIDE TO LEFT-SIDE to base layout
        baseLayout.getChildren().add(leftSide);
        baseLayout.getChildren().add(rightSide);

        //ADD base layout to constructor
        this.getChildren().add(baseLayout);

        //Set position
        this.setLayoutX(50);
        this.setLayoutY(50);

    }



    protected void endConnection(MouseEvent e, Circle output) {
        Circle speaker = SynthesizeApplication.speaker;
        Bounds speakerBounds = speaker.localToScene(speaker.getBoundsInLocal());


        double distance = Math.sqrt(Math.pow(speakerBounds.getCenterX() - e.getSceneX(), 2.0));
        Math.pow(speakerBounds.getCenterY() - e.getSceneY(), 2.0);

                if(distance < 10){
                    SynthesizeApplication.connectedWidgets_.add(this);
                    //better to create a new array list for connected widgets only
                    //the wave to some array list
                }
                else {
                    parent_.getChildren().remove(line_);
                    line_= null;
                }
    }

    protected void moveConnection(MouseEvent e, Circle output) {
        Bounds parentBounds = parent_.getBoundsInParent();
        line_.setEndX(e.getSceneX()-parentBounds.getMinX());
        line_.setEndY(e.getSceneY()-parentBounds.getMinY());
    }

    protected void startConnection(MouseEvent e, Circle output) {

        if(line_ != null){
            parent_.getChildren().remove(line_);
        }

        Bounds parentBounds = parent_.getBoundsInParent();
        Bounds bounds = output.localToScene(output.getBoundsInLocal());

        line_ = new Line();
        line_.setStrokeWidth(5);

        line_.setStartX(bounds.getCenterX() - parentBounds.getMinX());
        line_.setStartY(bounds.getCenterY() - parentBounds.getMinY());

        line_.setEndX(e.getSceneX());
        line_.setEndY(e.getSceneY());

        parent_.getChildren().add(line_);

    }

    protected void moveWidget(MouseEvent e) {
        double deltaX = e.getSceneX() - mouseXpos;
        double deltaY = e.getSceneY() - mouseYpos;

        this.relocate(deltaX + widgetXpos, deltaY + widgetYpos);
    }

    protected void getPosinf(MouseEvent e) {
        mouseXpos = e.getSceneX();
        mouseYpos = e.getSceneY();
        widgetXpos = this.getLayoutX();
        widgetYpos = this.getLayoutY();


    }

    //Handle slider dragging event and update label
    protected void handleSlider(Object e) {
       AudioComponent ac_ = getAudioComponent();
        // Get the value of the frequency slider
        int sliderValue = (int) slider_.getValue();
        // Update the label text with the selected frequency
        frequenceLabel_.setText("SineWave  " + sliderValue + " Hz");
        // Update the slideFrequency field
        ((SineWave) ac_).updateFrequency(sliderValue);

    }

    protected void closeWidget(ActionEvent e) {
        parent_.getChildren().remove(this);
        SynthesizeApplication.widgets_.remove(this);

        SynthesizeApplication.connectedWidgets_.remove(this);
        parent_.getChildren().remove(line_);
    }

    public AudioComponent getAudioComponent(){
        return audioComponent_;
    }





}



