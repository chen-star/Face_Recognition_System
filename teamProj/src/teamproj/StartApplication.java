package teamproj;

import teamproj.controller.RecoController;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import org.opencv.core.Core;
import teamproj.controller.RefreshController;
import teamproj.view.ChangePane;

/**
 *
 * @author chenjiaxin
 */
public class StartApplication extends Application {

    private class CustomPane extends StackPane {

        public CustomPane(Button btn) {
            this.getChildren().add(btn);
            setPadding(new Insets(10, 10, 10, 10));
        }

        public CustomPane(Text text) {
            this.getChildren().add(text);
            //setPadding(new Insets(10, 10, 10, 10));
        }

        public CustomPane(ImageView img) {
            this.getChildren().add(img);
            //System.out.println("set success");
            setPadding(new Insets(10, 10, 10, 10));
        }
    }

    @Override
    public void start(Stage primaryStage) {
        // bottom: button
        Button btn = new Button();
        btn.setText("Start camera");

        // botton: change count and reason
        Button btn2 = new Button();
        btn2.setText("Refresh");

        // button box
        HBox h = new HBox();
        h.getChildren().addAll(btn, btn2);
        h.setAlignment(Pos.CENTER);
        h.setSpacing(60);
        

        // mid: recognition frame
        Image img_big = new Image("file:/Users/chenjiaxin/Desktop/cmu1/java/team/teamProj/src/teamproj/icon/big_cmu1.jpg", 1280, 720, true, true);
        ImageView originalFrame = new ImageView(img_big);

        // right: information region
        VBox box = new VBox();

        // default img
        Image img = new Image("file:/Users/chenjiaxin/Desktop/cmu1/java/team/teamProj/src/teamproj/icon/default.jpeg", 300, 400, true, true);
        ImageView defaultImg = new ImageView(img);
        // student information
        Text studentInfoText = new Text("  Student infomation will be shown here!!");
        box.getChildren().addAll(defaultImg, new CustomPane(studentInfoText));

        // border pane to contain all the nodes
        BorderPane root = new BorderPane();
        root.setBottom(h);
        CustomPane imgPane = new CustomPane(originalFrame);
        root.setCenter(imgPane);
        root.setRight(box);

        // call controller to control business
        RecoController controller = new RecoController(btn, studentInfoText, defaultImg, box, originalFrame);

        // start button event listener
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("btn event!");
                controller.startCamera();
                //controller.recog();
            }
        });

        // refresh button event listener
        btn2.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("btn2 event!");
                // change count, reason and time
                ChangePane change = new ChangePane();
                RefreshController refresh = new RefreshController();
                refresh.store(change.show());
            }
        });

        // put root pane onto scene
        Scene scene = new Scene(root, 1700, 900);

        controller.setScene(scene);
        controller.setPane(imgPane);

        // put scene onto primaryStage
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
