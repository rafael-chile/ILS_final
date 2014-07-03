package emcl.fub.blue.constraints;

import emcl.fub.blue.entity.Course;
import emcl.fub.blue.entity.Curriculum;
import emcl.fub.blue.entity.Timetable;

import java.util.HashMap;
import java.util.Map;


public class Constraints {

    private int[][] CH;//= new int[50][20];// MODIFY!!!
    private int[][] CH2 = new int[50][20];// MODIFY!!!


    public void curriculumClashes(Timetable tt) {
        int numTimeSlots = tt.getNumTeachingDays() * tt.getPeriodsPerDay();
        //CH = new int[tt.getCourses().size()][numTimeSlots];// sorted???
        HashMap<String, Curriculum> curricula = tt.getCurricula();

        HashMap<String, Course> courses = tt.getCourses();

        for (Map.Entry<String, Curriculum> entry : curricula.entrySet()) {
            // String key = entry.getKey();
            Curriculum c = entry.getValue();
            System.out.println("Id=" + c.getId());
            StringBuilder sb = new StringBuilder();
            int numCoursesCurrentCurricula = c.getCourses().size();

            System.out.println("number of courses " + numCoursesCurrentCurricula);
            for (int i = 0; i < numCoursesCurrentCurricula; i++) {
                for (int j = i + 1; j < numCoursesCurrentCurricula; j++) {
                    for (int h = 0; h < numTimeSlots; h++) {
                        sb.append("-" + CH[i][h] + " " + CH[j][h] + " 0\n");
                        sb.append("-CH[" + c.getCourses().get(i).getId() + "][" + h + "] CH[" + c.getCourses().get(j).getId() + "][" + h + "] 0\n");
                        courses.get(c.getCourses().get(i).getId());
                    }
                }
            }
            System.out.println(sb.toString());
        }

    }

    public void encodeVarCourse(Timetable tt) {
        int numTimeSlots = tt.getNumTeachingDays() * tt.getPeriodsPerDay();
        CH = new int[tt.getCourses().size()][numTimeSlots];// sorted???
        int numAllCourses = tt.getCourses().size();
        int cont = 0;
        for (int i = 0; i < numAllCourses; i++) {
            for (int h = 0; h < numTimeSlots; h++) {
                CH[i][h] = cont;
                cont++;
            }
        }
    }

}