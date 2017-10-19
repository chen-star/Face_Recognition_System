
package teamproj.controller;

import java.util.ArrayList;
import teamproj.model.StudentCollection;
import teamproj.model.StudentInfo;

/**
 *
 * @author chenjiaxin
 */
public class InfoDispatcher {

    StudentCollection collection = new StudentCollection();

    /**
     *
     * @param predictedLabel
     * @return
     */
    public ArrayList<String> afterRecog(int predictedLabel) {
        
        ArrayList<StudentInfo> students = collection.getAllStudents();
        ArrayList<String> result = new ArrayList<>();
        //System.out.println("get students array ok");
        boolean found = false;
        for (StudentInfo s : students) {
            if (s.getLabel() == predictedLabel) {
                display(s);
                found = true;
                result.add(s.toString());
                result.add(s.getPhoto());
                //System.out.println("result1: "+result.get(1));
                return result;
            }
        }
        // 如果数据库里没有这个学生
        // 要新加入这个学生
        if (!found) {
            //
//            StudentInfo s = new StudentInfo("name", "andrewId", 22, "program", "photo", predictedLabel);
//            students.add(s);
//            display(s);
//                    
        }
        return result;
    }

    //display student info in back platform

    /**
     *
     * @param s
     * @return
     */
    public String display(StudentInfo s) {
        System.out.println("-----------------------------------------------------------------");
        System.out.println("This is student: " + s.getName());
        System.out.println("Basic information:\n");
        System.out.println(s.toString());
        return s.toString();
    }

}
