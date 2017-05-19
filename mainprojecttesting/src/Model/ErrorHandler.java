package Model;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
        label1.setStyle("-fx-text-fill: #FFFFFF;");
        button1.setStyle("-fx-background-color: #5B5B5B;\n" +
                         "-fx-border: 5px;\n" +
                         "-fx-border-color: #000000;\n" +
                         "-fx-border-radius: 10.0;\n" +
                         "-fx-text-fill: #FFFFFF;\n" +
                         "-fx-background-radius: 6,5,4;\n" +
                         "-fx-background-insets: 2,3,4;\n" +
                         "-fx-focus-color: #E8E8E8;");

        popupwindow.setScene(scene1);
        popupwindow.showAndWait();
    }
}
