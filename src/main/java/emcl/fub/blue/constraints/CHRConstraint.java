package emcl.fub.blue.constraints;

import emcl.fub.blue.entity.*;
import org.apache.commons.collections.BidiMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CHRConstraint {

    /**
     * Relation between ch and chr:
     * if some chr variable is assigned then the corresponding ch one has also to be so
     *
     * @param tt
     * @return clauses
     */
    public static ArrayList<ArrayList<Integer>> chrAndChAssigned(Timetable tt) {
        System.out.println("if some chr variable is assigned then the corresponding ch one has also to be so");
        ArrayList<ArrayList<Integer>> clauses = new ArrayList<ArrayList<Integer>>();
        for (CHR chr : (Collection<CHR>) Encoding.getChrEnc().values()) {
            ArrayList<Integer> cl = new ArrayList<Integer>();
            cl.add((-1) * Integer.parseInt(Encoding.getChrEnc().getKey(chr).toString()));
            System.out.print((-1) * Integer.parseInt(Encoding.getChrEnc().getKey(chr).toString()) + " ");
            CH ch = new CH(chr.getCourse(), chr.getTimeSlot());
            cl.add(Integer.parseInt(Encoding.getChEnc().getKey(ch).toString()));
            clauses.add(cl);
            Encoding.numClauses++;
        }
        return clauses;
    }

    /**
     * Relation between ch and chr:
     * if some course c is assigned to some timeslot h then it has also to take place in some room
     *
     * @param tt timetable
     * @return clauses
     */
    public static ArrayList<ArrayList<Integer>> chrOneOfRoomsAssigned(Timetable tt) {
        System.out.println("if some course c is assigned to some timeslot h then it has also to take place in some room");
        ArrayList<ArrayList<Integer>> clauses = new ArrayList<ArrayList<Integer>>();
        for (CH ch : (Collection<CH>) Encoding.getChEnc().values()) {

            ArrayList<Integer> cl = new ArrayList<Integer>();
            cl.add((-1) * Integer.parseInt(Encoding.getChEnc().getKey(ch).toString()));
            System.out.print((-1) * Integer.parseInt(Encoding.getChEnc().getKey(ch).toString()) + " ");
            for (Room room : tt.getRooms().values()) {
                CHR chr = new CHR(ch.getCourse(), room, ch.getTimeSlot());
                cl.add(Integer.parseInt(Encoding.getChrEnc().getKey(chr).toString()));
            }
            clauses.add(cl);
            Encoding.numClauses++;
        }
        return clauses;
    }


    /**
     * Relation between cr and chr:
     * if some chr variable is assigned then the corresponding ch one has also to be so
     *
     * @param tt
     * @return clauses
     */
    public static ArrayList<ArrayList<Integer>> chrAndCrAssigned(Timetable tt) {
        System.out.println(" if some course c has been assigned to some room r then a room had to be assigned to that course at some time");
        ArrayList<ArrayList<Integer>> clauses = new ArrayList<ArrayList<Integer>>();
        for (CHR chr : (Collection<CHR>) Encoding.getChrEnc().values()) {
            ArrayList<Integer> cl = new ArrayList<Integer>();
            cl.add((-1) * Integer.parseInt(Encoding.getChrEnc().getKey(chr).toString()));
            CR cr = new CR(chr.getCourse(), chr.getRoom());
            cl.add(Integer.parseInt(Encoding.getCrEnc().getKey(cr).toString()));
            clauses.add(cl);
            Encoding.numClauses++;
        }
        return clauses;
    }

    /**
     * Relation between cr and chr:
     * if some course c has been assigned to some room r then a room had to be assigned to that course at some time
     *
     * @param tt
     * @return clauses
     */
    public static ArrayList<ArrayList<Integer>> chrCourseShouldBeAssigned(Timetable tt) {
        System.out.println("if some chr variable is assigned then the corresponding ch one has also to be so");
        ArrayList<ArrayList<Integer>> clauses = new ArrayList<ArrayList<Integer>>();
        for (CR cr : (Collection<CR>) Encoding.getCrEnc().values()) {
            ArrayList<Integer> cl = new ArrayList<Integer>();
            cl.add((-1) * Integer.parseInt(Encoding.getCrEnc().getKey(cr).toString()));
            for (int i = 0; i < tt.getNumTeachingDays(); i++) {
                for (int j = 0; j < tt.getPeriodsPerDay(); j++) {
                    TimeSlot time = new TimeSlot(i, j);
                    CHR chr = new CHR(cr.getCourse(), cr.getRoom(), time);
                    cl.add(Integer.parseInt(Encoding.getChrEnc().getKey(chr).toString()));
                }
            }
            clauses.add(cl);
            Encoding.numClauses++;
        }
        return clauses;
    }

    /**
     * Room clashes
     * For each pair of courses c and c', time sot hand room r: ~chr(c, h, r) or ~chr(c', h, r)
     *
     * @param tt timetable
     * @return clauses
     */
    public static ArrayList<ArrayList<Integer>> roomClashes(Timetable tt) {
        System.out.println("Room clashes");
        ArrayList<ArrayList<Integer>> clauses = new ArrayList<ArrayList<Integer>>();

        for (CHR chr : (Collection<CHR>) Encoding.getChrEnc().values()) {
            //copyCHR.removeValue(chr);
            for (CHR chr2 : (Collection<CHR>) Encoding.getChrEnc().values()) {
                // we could take chr from second collection!!
                if (!chr.equals(chr2) && chr.getRoom().equals(chr2.getRoom()) && chr.getTimeSlot().equals(chr2.getTimeSlot())) {
                    ArrayList<Integer> cl = new ArrayList<Integer>();
                    cl.add((-1) * Integer.parseInt(Encoding.getChrEnc().getKey(chr).toString()));
                    cl.add((-1) * Integer.parseInt(Encoding.getChrEnc().getKey(chr2).toString()));
                    clauses.add(cl);
                    Encoding.numClauses++;
                }
            }
        }
        return clauses;
    }


    /**
     * Room capacity constraint
     *
     * @param tt timetable
     * @return clauses
     */
    public static ArrayList<ArrayList<Integer>> roomCapacity(Timetable tt) {
        System.out.println("Room capacity");
        ArrayList<ArrayList<Integer>> clauses = new ArrayList<ArrayList<Integer>>();
        for (CR cr : (Collection<CR>) Encoding.getCrEnc().values()) {
            if (cr.getRoom().getCapacity() < cr.getCourse().getNumOfStudents()) {
                for (int i = 0; i < tt.getNumTeachingDays(); i++) {
                    for (int j = 0; j < tt.getPeriodsPerDay(); j++) {
                        ArrayList<Integer> cl = new ArrayList<Integer>();
                        CHR chr = new CHR(cr.getCourse(), cr.getRoom(), new TimeSlot(i, j));
                        cl.add((-1) * Integer.parseInt(Encoding.getChrEnc().getKey(chr).toString()));
                        clauses.add(cl);
                    }
                }

            }
        }
        return clauses;
    }

    /**
     * Double Lectures
     *
     * @param tt timetable
     * @return clauses
     */
    public static ArrayList<ArrayList<Integer> > doubleLectures(Timetable tt) {
        System.out.println("Double Lectures");
        HashMap<String, Course> courses = tt.getCourses();
        ArrayList<ArrayList<Integer> > clauses = new ArrayList<ArrayList<Integer>> ();

        for (Map.Entry<String, Course> entry : courses.entrySet()) {
            for (int d = 0; d< tt.getNumTeachingDays(); d++){
                //ArrayList<Integer> cl = new ArrayList<Integer>();
                for (int i = 0; i< tt.getPeriodsPerDay()-2; i++){
                    CH ch = new CH(entry.getValue(), new TimeSlot(d, i));
                    CH ch2 = new CH(entry.getValue(), new TimeSlot(d, i+1));
                    String res=Encoding.getChEnc().getKey(ch).toString();
                    String res2=Encoding.getChEnc().getKey(ch2).toString();

                    for (int k =i+2; k< tt.getPeriodsPerDay(); k++){// for each period between i and j
                        ArrayList<Integer> cl = new ArrayList<Integer>();
                        CH ch3 = new CH(entry.getValue(), new TimeSlot(d, k));
                        cl.add((-1)*Integer.parseInt(res));
                        cl.add((-1)*Integer.parseInt(Encoding.getChEnc().getKey(ch3).toString()));
                        cl.add(Integer.parseInt(res2));
                        System.out.println(cl);
                        clauses.add(cl);
                        Encoding.numClauses++;
                    }

                }
            }
        }
        return clauses;
    }


}
