/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teamproj.view;

import java.util.ArrayList;
import java.util.Optional;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

/**
 *
 * @author chenjiaxin
 */
public class InfoInput {

    public ArrayList<String> showInput() {

        Dialog<ArrayList<String>> dialog = new Dialog<>();
        dialog.setTitle("Personal Information Input");
        dialog.setHeaderText("Carnegie Mellon University");

        // 设置头部图片
        dialog.setGraphic(new ImageView(new Image("file:/Users/chenjiaxin/Desktop/cmu1/java/team/teamProj/src/teamproj/icon/cmu.jpg")));

        ButtonType loginButtonType = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, cancel);

        GridPane grid = new GridPane();

        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField name = new TextField();
        name.setPromptText("Rachel Sun");

        TextField id = new TextField();
        id.setPromptText("yitians");

        TextField age = new TextField();
        age.setPromptText("22");

        TextField program = new TextField();
        program.setPromptText("MISM");

        TextField photo = new TextField();
        photo.setPromptText("photo path");

        TextField reason = new TextField();
        reason.setPromptText("complaints");

        grid.add(new Label("Name:"), 0, 0);
        grid.add(name, 1, 0);
        grid.add(new Label("AndrewId:"), 0, 1);
        grid.add(id, 1, 1);
        grid.add(new Label("Age:"), 0, 2);
        grid.add(age, 1, 2);
        grid.add(new Label("Program:"), 0, 3);
        grid.add(program, 1, 3);
        grid.add(new Label("Photo:"), 0, 4);
        grid.add(photo, 1, 4);
        grid.add(new Label("Reason:"), 0, 5);
        grid.add(reason, 1, 5);

        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);

        loginButton.setDisable(true);

        // Java 8 lambda 表达式进行校验
        name.textProperty()
                .addListener((observable, oldValue, newValue) -> {
                    loginButton.setDisable(newValue.trim().isEmpty() || id.getText().trim().isEmpty());
                }
                );

        id.textProperty()
                .addListener((observable, oldValue, newValue) -> {
                    loginButton.setDisable(newValue.trim().isEmpty() || name.getText().trim().isEmpty());
                }
                );
        age.textProperty()
                .addListener((observable, oldValue, newValue) -> {
                    loginButton.setDisable(newValue.trim().isEmpty() || id.getText().trim().isEmpty());
                }
                );

        dialog.getDialogPane()
                .setContent(grid);

// 默认光标在用户名上
        Platform.runLater(
                () -> name.requestFocus());

// 登录按钮后，将结果转为arrayList
        ArrayList<String> result = new ArrayList<>();
        dialog.setResultConverter(dialogButton
                -> {
            if (dialogButton == loginButtonType) {
                result.add(name.getText());
                result.add(id.getText());
                result.add(age.getText());
                result.add(program.getText());
                result.add(photo.getText());
                result.add(reason.getText());
                return result;
            }
            return null;
        }
        );

        dialog.showAndWait();
        return result;
    }

}
