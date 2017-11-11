/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teamproj.view;

import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author chenjiaxin
 */
public class ReportPane {

    public void show(ArrayList<String> m1, ArrayList<String> m2) {
        Stage window = new Stage();

        window.setTitle("Report");

        window.initModality(Modality.APPLICATION_MODAL);

        window.setMinWidth(600);
        window.setMinHeight(400);

        Button button = new Button("Confirm");

        button.setOnAction(e
                -> window.close());

        Text report1 = new Text();
        Text report2 = new Text();
        report1.setText(dis1(m1));
        report2.setText(dis2(m2));

        VBox layout = new VBox(10);

        layout.getChildren()
                .addAll(report1, report2, button);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);

        window.setScene(scene);
        //使用showAndWait()先处理这个窗口，而如果不处理，main中的那个窗口不能响应

        window.showAndWait();
    }
    
    private String dis1(ArrayList<String> m1){
        int num = m1.size()/2;
        String result = "The students visiting in given dates:\n\n";
        result= result +  "   name                          andrew Id\n";
        for(int i=0; i<m1.size(); i+=2){
            result += "\n "+ m1.get(i)+ "         "+m1.get(i+1);
        }
        return result;
    }
    
    private String dis2(ArrayList<String> m2){
        String result = "\n\nThe frenquency of visiting students in given dates:\n\n";
        int num = m2.size()/4;
        result = result+ "    name                 andrew Id            count          gender";
        for(int i=0; i<m2.size(); i += 4){
            result += "\n" + m2.get(i) + "           "+m2.get(i+1)+"              "+m2.get(i+2)+"                "+m2.get(i+3);
        }
        return result;
    }

}
