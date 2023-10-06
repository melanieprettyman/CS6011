package com.example.synthesizer;

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

    Slider frequencySLider_;

    Label frequenceLabel_;

    double mouseXpos, mouseYpos, widgetXpos, widgetYpos;

    Line line_;

    //constructor
    AudioComponentWidgetBase(AudioComponent audioComponent, AnchorPane pane, String sliderLabel, float lowerRange, float upperRange, int sliderValue){
        //pass audio competent and pane
        audioComponent_ =audioComponent;
        parent_ =pane;

    //HBOX to combine LEFT-SIDE AND RIGHT-SIDE
        HBox baseLayout = new HBox();
        baseLayout.setStyle("-fx-border-color: black; -fx-background-color: WHITE");

    //VBOX FOR THE RIGHT SIDE BOX (CLOSE BUTTON and circle-OUTPUT)
        VBox rightSide = new VBox();
        //create button, set actions, add style
        Button closeBtn = new Button("x");
        closeBtn.setStyle("-fx-font-family: 'Comic Sans MS'");
        closeBtn.setOnAction(this::closeWidget);
        //create output circle
        Circle output = new Circle(10);
        output.setFill(Color.BLACK);

        //Handel drawing the line - 3 event
        output.setOnMousePressed(e->startConnection(e,output));
        output.setOnMouseDragged(e->moveConnection(e, output));
        output.setOnMouseReleased(e->endConnection(e,output));


                //add close button and output circle to the right-side
                rightSide.getChildren().add(closeBtn);
                rightSide.getChildren().add(output);
                //right side box style
                rightSide.setAlignment(Pos.CENTER);
                rightSide.setPadding(new Insets(5));
                rightSide.setSpacing(5);



            //CREATE SLIDER
                // Create a slider to control the frequency
                // Range: 50 to 2000 Hz
                // Step value for slider changes: 10
        frequencySLider_ = new Slider(lowerRange, upperRange, sliderValue);



        VBox leftSide = new VBox();
        //Create label
        frequenceLabel_ = new Label(sliderLabel);
        frequenceLabel_.setStyle("-fx-font-family: 'Comic Sans MS'");

        //Add slider and label to left side
        leftSide.getChildren().addAll(frequenceLabel_,frequencySLider_);

        //Update postion when dragged
        leftSide.setOnMousePressed(this::getPosinf);
        leftSide.setOnMouseDragged(this::moveWidget);

        //Handel Slider
        frequencySLider_.setOnMouseDragged(this::handleSlider);

        //Add RIGHT-SIDE TO LEFT-SIDE to base layout
        baseLayout.getChildren().add(leftSide);
        baseLayout.getChildren().add(rightSide);

        //ADD base layout to constructor
        this.getChildren().add(baseLayout);

        //Set position
        this.setLayoutX(50);
        this.setLayoutY(50);

    }

    private void endConnection(MouseEvent e, Circle output) {
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

    private void moveConnection(MouseEvent e, Circle output) {
        Bounds parentBounds = parent_.getBoundsInParent();
        line_.setEndX(e.getSceneX()-parentBounds.getMinX());
        line_.setEndY(e.getSceneY()-parentBounds.getMinY());
    }

    private void startConnection(MouseEvent e, Circle output) {

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

    private void moveWidget(MouseEvent e) {
        double deltaX = e.getSceneX() - mouseXpos;
        double deltaY = e.getSceneY() - mouseYpos;

        this.relocate(deltaX + widgetXpos, deltaY + widgetYpos);
            //this.relocate(e.getSceneX(), e.getScene())
    }

    private void getPosinf(MouseEvent e) {
        mouseXpos = e.getSceneX();
        mouseYpos = e.getSceneY();
        widgetXpos = this.getLayoutX();
        widgetYpos = this.getLayoutY();


    }

    //Handle slider dragging event and update label
    protected void handleSlider(Object e) {
       AudioComponent ac_ = getAudioComponent();
        // Get the value of the frequency slider
        int sliderValue = (int) frequencySLider_.getValue();
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
    public Slider getFrequencySlider() {
        return frequencySLider_;
    }
}



