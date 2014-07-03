package emcl.fub.blue.constraints;

import emcl.fub.blue.entity.Course;
import emcl.fub.blue.entity.Curriculum;
import emcl.fub.blue.entity.TimeSlot;
import emcl.fub.blue.entity.Timetable;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: admin
 * Date: 6/17/14
 * Time: 2:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class KHConstraint {

    /**
     * Relation between ch and kh:
     * If some course takes place in time slot h, all the curricula k1, k2...kn to which c
     * belongs occur in h
     *
     * @param tt timetable
     * @return clauses
     */
    //CORRECT
    public static ArrayList<ArrayList<Integer>> curAndCourseInTimeSlot(Timetable tt) {
        ArrayList<ArrayList<Integer>> clauses = new ArrayList<ArrayList<Integer>>();
        for (CH ch : (Collection<CH>) Encoding.getChEnc().values()) {


            ArrayList<Curriculum> cur = tt.getCurriculaByCourse(ch.getCourse());
            for (Curriculum c: cur) {

                ArrayList<Integer> cl = new ArrayList<Integer>();
                cl.add((-1) * Integer.parseInt(Encoding.getChEnc().getKey(ch).toString())  );
                KH kh = new KH(c, ch.getTimeSlot());
                cl.add(Integer.parseInt(Encoding.getKhEnc().getKey(kh).toString()));
                clauses.add(cl);
                System.out.println(" course in h , then all cuarricula in h" + cl);
                Encoding.numClauses++;

            }

        }
        return clauses;
    }

    /**
     * Relation between ch and kh:
     * If some curriculum k takes place in time slot h, then at lest one of the courses belonging to k
     * must also occur in h
     * @param tt timetable
     * @return clauses
     */
    public static ArrayList<ArrayList<Integer>> curBelongToCur(Timetable tt) {
        System.out.println("If some curriculum k takes place in time slot h, then at lest one of the courses belonging to k\n" +
                "     * must also occur in h");
        ArrayList<ArrayList<Integer>> clauses = new ArrayList<ArrayList<Integer>>();
        for (KH kh : (Collection<KH>) Encoding.getKhEnc().values()) {
            ArrayList<Integer> cl = new ArrayList<Integer>();
            cl.add((-1) * Integer.parseInt(Encoding.getKhEnc().getKey(kh).toString())  );
            for (Course c : kh.getCurriculum().getCourses()) {
                CH ch = new CH(c, kh.getTimeslot());
                cl.add(Integer.parseInt(Encoding.getChEnc().getKey(ch).toString()));
            }
            clauses.add(cl);
            System.out.println("Curriculum k in h, courses of k in h" + cl);
            Encoding.numClauses++;
        }
        return clauses;
    }

    /**
     * Windows:
     * For each curriculum K
     * For every day D and pairs of periods i,j (j later than i)
     * @param tt timetable
     * @return clauses
     */
    public static ArrayList<ArrayList<Integer>> windows(Timetable tt) {
        System.out.println("Windows constraint");
        ArrayList<ArrayList<Integer>> clauses = new ArrayList<ArrayList<Integer>>();
        for ( Curriculum cur: tt.getCurricula().values()) {
            for (int d = 0; d< tt.getNumTeachingDays(); d++){
                //ArrayList<Integer> cl = new ArrayList<Integer>();
                for (int i = 0; i< tt.getPeriodsPerDay()-2; i++){
                        KH kh = new KH(cur, new TimeSlot(d, i));
                        KH kh2 = new KH(cur, new TimeSlot(d, i+1));
                        String res=Encoding.getKhEnc().getKey(kh).toString();
                        String res2=Encoding.getKhEnc().getKey(kh2).toString();

                        for (int k =i+2; k< tt.getPeriodsPerDay(); k++){// for each period between i and j
                            ArrayList<Integer> cl = new ArrayList<Integer>();
                            KH kh3 = new KH(cur, new TimeSlot(d, k));
                            cl.add((-1)*Integer.parseInt(res));
                            cl.add((-1)*Integer.parseInt(Encoding.getKhEnc().getKey(kh3).toString()));
                            cl.add(Integer.parseInt(res2));
                            System.out.println(cl);
                            clauses.add(cl);
                            Encoding.numClauses++;
                        }

                }
                System.out.println(" day "+d);
            }
        }
        return clauses;
    }

    // STUDENT MIN MAX LOAD
    // For all KH in a day D, at most k should ne true
    public static ArrayList<ArrayList<Integer>> studentMaxLoad(Timetable tt) {
        System.out.println("MaxLoad constraint");
        ArrayList<ArrayList<Integer>> clauses = new ArrayList<ArrayList<Integer>>();
        for ( Curriculum cur: tt.getCurricula().values()) {
            System.out.println(cur.getId().toString());
            for (int d = 0; d< tt.getNumTeachingDays(); d++){
                ArrayList<Integer> cl = new ArrayList<Integer>();
                for (int p = 0; p< tt.getPeriodsPerDay(); p++){
                    KH kh = new KH(cur, new TimeSlot(d, p));
                    cl.add(Integer.parseInt(Encoding.getKhEnc().getKey(kh).toString()));
                }
                int maxNumberDays = tt.getMaxDailyLecture();
                cl.add(maxNumberDays);
                clauses.add(cl);
                System.out.println(cl);
                Encoding.numClauses++;
            }
        }
        return clauses;
    }
    // For all KH in a day D, at most k should ne true
    public static ArrayList<ArrayList<Integer>> studentMinLoad(Timetable tt) {
        System.out.println("MaxLoad constraint");
        ArrayList<ArrayList<Integer>> clauses = new ArrayList<ArrayList<Integer>>();
        for ( Curriculum cur: tt.getCurricula().values()) {
            System.out.println(cur.getId().toString());
            for (int d = 0; d< tt.getNumTeachingDays(); d++){
                ArrayList<Integer> cl = new ArrayList<Integer>();
                for (int p = 0; p< tt.getPeriodsPerDay(); p++){
                    KH kh = new KH(cur, new TimeSlot(d, p));
                    cl.add(Integer.parseInt(Encoding.getKhEnc().getKey(kh).toString()));
                }
                int minNumberDays = tt.getMinDailyLect();
                cl.add(minNumberDays);
                clauses.add(cl);
                System.out.println(cl);
                Encoding.numClauses++;
            }
        }
        return clauses;
    }

    //  JUST FOR TEST!!!!

    // For all KH in a day D, at most k should ne true
    public static ArrayList<ArrayList<Integer>> studentMaxLoad2(Timetable tt) {
        System.out.println("MaxLoad constraint");
        ArrayList<ArrayList<Integer>> clauses = new ArrayList<ArrayList<Integer>>();
        for ( Curriculum cur: tt.getCurricula().values()) {
            System.out.println(cur.getId().toString());
            for (int d = 0; d< tt.getNumTeachingDays(); d++){
                ArrayList<Integer> cl = new ArrayList<Integer>();
                for (int p = 0; p< tt.getPeriodsPerDay(); p++){
                    KH kh = new KH(cur, new TimeSlot(d, p));
                    cl.add((-1)*Integer.parseInt(Encoding.getKhEnc().getKey(kh).toString()));
                }
                int maxNumberDays = tt.getMaxDailyLecture();
                cl.add(tt.getPeriodsPerDay()- maxNumberDays);
                clauses.add(cl);
                System.out.println(cl);
                Encoding.numClauses++;
            }
        }
        return clauses;
    }


}
