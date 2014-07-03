package emcl.fub.blue.constraints;

import emcl.fub.blue.entity.*;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: admin
 * Date: 6/9/14
 * Time: 9:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class CHConstraint {

    public static ArrayList<ArrayList<Integer>> curriculumClashes(Timetable tt) {
        ArrayList<ArrayList<Integer>> clauses = new ArrayList<ArrayList<Integer>>();
        HashMap<String, Curriculum> curricula = tt.getCurricula();

        for (Map.Entry<String, Curriculum> entry : curricula.entrySet()) {
            Curriculum c = entry.getValue();
            System.out.println("Curricula Id=" + c.getId());
            StringBuilder sb = new StringBuilder();
            int numCoursesCurrentCurricula = c.getCourses().size();
            System.out.println("number of courses " + numCoursesCurrentCurricula);
            for (int i = 0; i < numCoursesCurrentCurricula; i++) {
                for (int j = i + 1; j < numCoursesCurrentCurricula; j++) {
                    for (int k = 0; k < tt.getNumTeachingDays(); k++) {
                        for (int p = 0; p < tt.getPeriodsPerDay(); p++) {
                            CH ch1 = new CH(c.getCourses().get(i), new TimeSlot(new Integer(k), new Integer(p)));
                            CH ch2 = new CH(c.getCourses().get(j), new TimeSlot(new Integer(k), new Integer(p)));
                            sb.append("-" + Encoding.getChEnc().getKey(ch1) + " -" + Encoding.getChEnc().getKey(ch2) + " 0\n");
                            ArrayList<Integer> clause = new ArrayList<Integer>();
                            clause.add(Integer.parseInt(Encoding.getChEnc().getKey(ch1).toString()) * -1);
                            clause.add(Integer.parseInt(Encoding.getChEnc().getKey(ch2).toString()) * -1);
                            clauses.add(clause);
                        }
                    }
                }
            }
            // System.out.println(sb.toString());

        }

        return clauses;
    }

    public static ArrayList<ArrayList<Integer>> teacherClashes(Timetable tt) {
        //for each two courses with same teacher

        ArrayList<ArrayList<Integer>> clauses = new ArrayList<ArrayList<Integer>>();

        HashMap<String, Course> courses = tt.getCourses();
        HashMap<String, Course> courses2 = new HashMap<String, Course>(tt.getCourses());
        StringBuilder sb = new StringBuilder();
        System.out.println("Teacher clashes");
        for (Map.Entry<String, Course> entry : courses.entrySet()) {
            String key = entry.getKey();
            Course c = entry.getValue();
            courses2.entrySet().remove(entry);
            for (Map.Entry<String, Course> entry2 : courses2.entrySet()) {
                Course c2 = entry2.getValue();
                if (c.getTeacher().equals(c2.getTeacher())) {
                    for (int k = 0; k < tt.getNumTeachingDays(); k++) {
                        for (int p = 0; p < tt.getPeriodsPerDay(); p++) {
                            CH ch1 = new CH(c, new TimeSlot(new Integer(k), new Integer(p)));
                            CH ch2 = new CH(c2, new TimeSlot(new Integer(k), new Integer(p)));
                            ArrayList<Integer> clause = new ArrayList<Integer>();
                            clause.add(Integer.parseInt(Encoding.getChEnc().getKey(ch1).toString()) * -1);
                            clause.add(Integer.parseInt(Encoding.getChEnc().getKey(ch2).toString()) * -1);
                            clauses.add(clause);
                        }
                    }
                }
            }
        }
        return clauses;
    }

    //For each course c, exactly X number of lectures (vars form the set CHch,CHch',CHch'' ....) must be true
    public static ArrayList<ArrayList<Integer>> numberLectures(Timetable tt) {
        HashMap<String, Course> courses = tt.getCourses();

        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
        Integer numExactly;//=new Integer(0);
        for (Map.Entry<String, Course> entry : courses.entrySet()) {
            ArrayList<Integer> literals = new ArrayList<Integer>();
            String key = entry.getKey();
            Course c = entry.getValue();
            for (int k = 0; k < tt.getNumTeachingDays(); k++) {
                for (int p = 0; p < tt.getPeriodsPerDay(); p++) {
                    CH ch1 = new CH(c, new TimeSlot(new Integer(k), new Integer(p)));
                    System.out.print(Encoding.getChEnc().getKey(ch1));
                    literals.add(Integer.parseInt(Encoding.getChEnc().getKey(ch1).toString()));
                }
            }
            Integer exactlyVal = c.getNumOfLectures();
            System.out.println(" " + exactlyVal);
            literals.add(exactlyVal);                   // LAST ELEMENT IS THE NUMBER OF LITERALS TO BE SATISFIED
            result.add(literals);
        }
        return result;
    }

    // TIME AVAILABILITY
    public static ArrayList<ArrayList<Integer> > timeAvailability(Timetable tt) {
        System.out.println("Time Avaliability clashes");
        ArrayList<ArrayList<Integer> > clauses = new ArrayList<ArrayList<Integer>> ();
        HashMap<String, PeriodConstraint> periodConstraints = tt.getPeriodConstraints();
        // IF TIMESLOT IS FORBIDDEN, ADD CLAUSE
        for (Map.Entry<String, PeriodConstraint> entrypc : periodConstraints.entrySet()) {
            PeriodConstraint pc = entrypc.getValue();
            Course cp = pc.getCourse();
            List<TimeSlot> ts = pc.getTimeSlots();
            for (TimeSlot entryts : ts) {
                CH ch1 = new CH(cp, entryts);
                System.out.println("-" + Encoding.getChEnc().getKey(ch1) + "  " + ch1.getCourse().getId() + ch1.getTimeSlot().getDay() + ch1.getTimeSlot().getPeriod());
                ArrayList<Integer> clause = new ArrayList<Integer>();
                clause.add(Integer.parseInt(Encoding.getChEnc().getKey(ch1).toString()) * -1);
                clauses.add(clause);
            }
            //  System.out.println(Encoding.getChEnc().getKey(ch1));
        }
        return clauses;
    }
}
