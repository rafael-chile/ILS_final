package emcl.fub.blue.constraints;


import emcl.fub.blue.entity.Course;
import emcl.fub.blue.entity.TimeSlot;
import emcl.fub.blue.entity.Timetable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class CDConstraint {
//check!!
    public static ArrayList<ArrayList<Integer>> minWorkingDays(Timetable tt) {
        System.out.println("Min working days");
        ArrayList<ArrayList<Integer>> clauses = new ArrayList<ArrayList<Integer>>();
        for (Course c: tt.getCourses().values()) {
            ArrayList<Integer> clause = new ArrayList<Integer>();
            for (int i = 0; i < tt.getNumTeachingDays(); i++) {
                CD cd = new CD(c, i);
                clause.add(Integer.parseInt(Encoding.getCdEnc().getKey(cd).toString()));

                //System.out.print(Integer.parseInt(Encoding.getCdEnc().getKey(cd).toString()) + " ");
            }
            //System.out.println(" "+c.getMinNumOfDays());
            clause.add(c.getMinNumOfDays());
            clauses.add(clause);
            System.out.println(clause);
            Encoding.numClauses++;
        }
        return clauses;
    }

    /**
     *   Encodes relation between ch and cd:
     *   if some course takes place in time slot h, it also takes place in the day corresponding to h
     * @param tt timetable
     * @return clauses
     */
    public static ArrayList<ArrayList<Integer>> takePlaceInDay(Timetable tt) {
        System.out.println("course takes place in timeslot corresponding to this day");
        ArrayList<ArrayList<Integer>> clauses = new ArrayList<ArrayList<Integer>>();
        for(CH ch: (Collection<CH>)Encoding.getChEnc().values()) {
            CD cd = new CD(ch.getCourse(), ch.getTimeSlot().getDay().intValue());
            ArrayList<Integer> cl = new ArrayList<Integer>();
            cl.add((-1)* Integer.parseInt(Encoding.getChEnc().getKey(ch).toString()));
            cl.add(Integer.parseInt(Encoding.getCdEnc().getKey(cd).toString()));
            System.out.println((-1)* Integer.parseInt(Encoding.getChEnc().getKey(ch).toString())+" "+
                    Integer.parseInt(Encoding.getCdEnc().getKey(cd).toString()));
            clauses.add(cl);
            Encoding.numClauses++;
        }
        return clauses;
    }

    /**
     * Encodes relation between ch and cd:
     * If some course takes place in a day d, it must also occurs in some of the timeslots of d.
     * @param tt
     * @return
     */
    public static ArrayList<ArrayList<Integer>> takePlaceInTimeSlot(Timetable tt) {
        System.out.println("If some course takes place in a day d, it must also occurs in some of the timeslots of d.");
        ArrayList<ArrayList<Integer>> clauses = new ArrayList<ArrayList<Integer>>();
        for (CD cd: (Collection<CD>)Encoding.getCdEnc().values()) {
            ArrayList<Integer> cl = new ArrayList<Integer>();
            cl.add((-1)*Integer.parseInt(Encoding.getCdEnc().getKey(cd).toString()));
            System.out.print((-1) * Integer.parseInt(Encoding.getCdEnc().getKey(cd).toString()) + " ");
            for (int i = 0; i< tt.getPeriodsPerDay(); i++) {
                CH ch = new CH(cd.getCourse(), new TimeSlot(cd.getDay(), i));
                cl.add(Integer.parseInt(Encoding.getChEnc().getKey(ch).toString()));
                System.out.print(Integer.parseInt(Encoding.getChEnc().getKey(ch).toString()) + " ");
            }
            System.out.println();
            clauses.add(cl);
            Encoding.numClauses++;
        }

        return clauses;
    }



}
