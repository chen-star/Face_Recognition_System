/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teamproj;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

/**
 *
 * @author chenjiaxin
 */
public class TeamProj extends Application {

    private class CustomPane extends StackPane {

        public CustomPane(Button btn) {
            this.getChildren().add(btn);
            setPadding(new Insets(10, 10, 10, 10));
        }

        public CustomPane(CheckBox box) {
            this.getChildren().add(box);
            setPadding(new Insets(10, 10, 10, 10));
        }

        public CustomPane(ImageView img) {
            this.getChildren().add(img);
            //System.out.println("set success");
            setPadding(new Insets(10, 10, 10, 10));
        }
    }

    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button();
        btn.setText("Start camera");

        CheckBox haarClassifier = new CheckBox("haarClassifier");
        CheckBox lbpClassifier = new CheckBox("lbpClassifier");

//        Image img = new Image("http://tool.lu/imageholder/");
//        System.out.println("img get success");
//        System.out.println(img.getHeight());
        ImageView originalFrame = new ImageView();
//        originalFrame.setImage(img);
//        originalFrame.setFitHeight(900);
//        originalFrame.setFitWidth(700);
//        originalFrame.setPreserveRatio(true);
//        originalFrame.setSmooth(true);

        RecoController controller = new RecoController(btn, haarClassifier, lbpClassifier, originalFrame);

        BorderPane root = new BorderPane();
        root.setTop(originalFrame);
        root.setTop(new CustomPane(btn));
        CustomPane imgPane = new CustomPane(originalFrame);
        root.setCenter(imgPane);
        root.setLeft(new CustomPane(haarClassifier));
        //root.setRight(new CustomPane(lbpClassifier));

        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("btn event!");
                controller.startCamera();
            }
        });

        haarClassifier.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("haar event!");
                controller.haarSelected(event);
            }
        });

        Scene scene = new Scene(root, 1200, 800);

        controller.setScene(scene);
        controller.setPane(imgPane);

        primaryStage.setTitle("Student Face Recognition System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        launch(args);
    }

}
