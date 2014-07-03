package emcl.fub.blue.constraints;

import emcl.fub.blue.entity.*;
import org.apache.commons.collections.BidiMap;
import org.apache.commons.collections.bidimap.DualHashBidiMap;

import java.util.*;


public class CRConstraint {
    //RoomOccupancy
    public static ArrayList<ArrayList<Integer>> roomClashes(Timetable tt) {
        ArrayList<ArrayList<Integer>> clauses = new ArrayList<ArrayList<Integer>>();

        HashMap<String, Course> courses = tt.getCourses();
        HashMap<String, Course> courses2 = new HashMap<String, Course>(tt.getCourses());

        StringBuilder sb = new StringBuilder();
        System.out.println("Room clashes");
        for (Map.Entry<String, Course> entry : courses.entrySet()) {
            String key = entry.getKey();
            Course c = entry.getValue();

            // REMOVE FROM ENTRY 2 COURSE c
            courses2.entrySet().remove(entry);
            for (Map.Entry<String, Course> entry2 : courses2.entrySet()) {
                Course c2 = entry2.getValue();
                //for each timeslot for that pair of courses
                for (int k = 0; k < tt.getNumTeachingDays(); k++) {
                    for (int p = 0; p < tt.getPeriodsPerDay(); p++) {
                        //FOR EACH ROOM
                        HashMap<String, Room> allRooms = tt.getRooms();
                        for (Map.Entry<String, Room> entryR : allRooms.entrySet()) {

                            CH ch1 = new CH(c, new TimeSlot(new Integer(k), new Integer(p)));
                            CH ch2 = new CH(c2, new TimeSlot(new Integer(k), new Integer(p)));
                            CR cr1 = new CR(c, entryR.getValue());
                            CR cr2 = new CR(c2, entryR.getValue());
                            ArrayList<Integer> clause = new ArrayList<Integer>();
                            clause.add(Integer.parseInt(Encoding.getChEnc().getKey(ch1).toString()) * -1);
                            clause.add(Integer.parseInt(Encoding.getChEnc().getKey(ch2).toString()) * -1);
                            clause.add(Integer.parseInt(Encoding.getCrEnc().getKey(cr1).toString()) * -1);
                            clause.add(Integer.parseInt(Encoding.getCrEnc().getKey(cr2).toString()) * -1);
                            clauses.add(clause);
                        }
                    }
                }
            }

        }
        return clauses;

    }

    // ROOM CAPACITY
    public static ArrayList< ArrayList<Integer>> roomCapacity(Timetable tt) {
        System.out.print("++++++++ ROOM CAPACITY +++++++");
        ArrayList< ArrayList<Integer>> clauses = new ArrayList<ArrayList<Integer>>();

        HashMap<String, Course> courses = tt.getCourses();
        HashMap<String, Room> allRooms = tt.getRooms();
        for (Map.Entry<String, Course> entry : courses.entrySet()) {
            String key = entry.getKey();
            Course c = entry.getValue();
            for (Map.Entry<String, Room> entryR : allRooms.entrySet()) {
                Room r = entryR.getValue();
                if (c.getNumOfStudents() > r.getCapacity()) {
                    CR cr = new CR(c, r);
                    ArrayList<Integer> clause = new ArrayList<Integer>();
                    clause.add(Integer.parseInt(Encoding.getCrEnc().getKey(cr).toString()) * (-1));
                    clauses.add(clause);
                }
            }
        }
        return clauses;
    }



    // ROOM SUITABILITY
    public static ArrayList<ArrayList<Integer>> roomSuitability(Timetable tt) {
        System.out.print("++++++++ ROOM SUITABILITY +++++++");
        ArrayList<ArrayList<Integer>> clauses = new ArrayList<ArrayList<Integer>>();
        HashMap<String, RoomConstraint> roomConstraints = tt.getRoomComstraints();

        // IF ROOM FOR COURSE IS FORBIDDEN, ADD CLAUSE
        for (Map.Entry<String, RoomConstraint> entryrc : roomConstraints.entrySet()) {
            RoomConstraint rc = entryrc.getValue();
            Course c = rc.getCourse();
            List<Room> rs = rc.getRooms();
            for (Room r : rs) {
                CR cr = new CR(c, r);
                ArrayList<Integer> clause = new ArrayList<Integer>();
                clause.add(Integer.parseInt(Encoding.getCrEnc().getKey(cr).toString()) * -1);
                clauses.add(clause);
            }
        }
        return clauses;
    }
}
