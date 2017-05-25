package Model;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Created by blackhatt on 15/05/2017.
 */
public class ErrorHandler {

    public static void popUp(String title, String text, String button) {

        Stage popupwindow = new Stage();

        popupwindow.initModality(Modality.APPLICATION_MODAL);

        popupwindow.setTitle(title);
        Label label1 = new Label(text);
        Button button1 = new Button(button);
        button1.setOnAction(e -> popupwindow.close());

        VBox layout = new VBox(10);

        layout.getChildren().addAll(label1, button1);
        layout.setAlignment(Pos.CENTER);

        Scene scene1 = new Scene(layout, 300, 250);
        layout.setStyle("-fx-background-image: url(\"backGroundImage.png\");");
        label1.getStylesheets().add("stylesheet.css");
        button1.getStylesheets().add("stylesheet.css");

        popupwindow.setScene(scene1);
        popupwindow.showAndWait();
    }

    public static void bruteForceSafe(Label label, Button button, int time) {


        label.setVisible(true);
        label.setText("You have entered a wrong password or username too many times...");
        button.setDisable(true);
        final IntegerProperty i = new SimpleIntegerProperty(time);
        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.seconds(1),
                        event -> {
                            i.set(i.get() - 1);
                            if(i.get() == 1)
                                label.setText("Try again now");
                            if(i.get() == 0){
                               button.setDisable(false);
                               label.setVisible(false);
                            }
                        }
                )
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }

    public static void emptyFieldsHandler(){

    }
}
