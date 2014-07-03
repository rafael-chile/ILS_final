package emcl.fub.blue.constraints;

import emcl.fub.blue.entity.*;
import org.apache.commons.collections.BidiMap;
import org.apache.commons.collections.bidimap.DualHashBidiMap;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: admin
 * Date: 6/9/14
 * Time: 9:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class Encoding {
    private static BidiMap chEnc = new DualHashBidiMap();
    private static BidiMap crEnc = new DualHashBidiMap();
    private static BidiMap cdEnc = new DualHashBidiMap();
    private static BidiMap khEnc = new DualHashBidiMap();
    private static BidiMap chrEnc = new DualHashBidiMap();
    //private static HashMap<String, CH> chEnc = new HashMap<String, CH>();
    private static int numVar = 1;
    public static int numClauses = 0;


    public static int getNumVar() {
        return numVar;
    }

    public static void encodeCH(Timetable tt) {
        System.out.println("Encoding CH");
        numVar = 1;
        for (Course value : tt.getCourses().values()) {
            for (int i = 0; i < tt.getNumTeachingDays(); i++) {
                for (int j = 0; j < tt.getPeriodsPerDay(); j++) {
                    CH ch = new CH();
                    ch.setCourse(value);
                    TimeSlot timeSlot = new TimeSlot();
                    timeSlot.setDay(i);
                    timeSlot.setPeriod(j);
                    ch.setTimeSlot(timeSlot);
                    chEnc.put(String.valueOf(numVar), ch);
                    // System.out.println(String.valueOf(numVar)+" "+ch.getCourse().getId()+" "+ch.getTimeSlot().getDay()+" "+ch.getTimeSlot().getPeriod());
                    numVar++;
                }
            }
        }

    }

    public static void encodeCR(Timetable tt) {
        System.out.println("Encoding CR");
        for (Course value : tt.getCourses().values()) {
            for (Room valueR : tt.getRooms().values()) {
                CR cr = new CR();
                cr.setCourse(value);
                cr.setRoom(valueR);
                crEnc.put(String.valueOf(numVar), cr);
                // System.out.println(String.valueOf(numVar)+" "+cr.getCourse().getId()+" "+cr.getRoom().getId());
                numVar++;
            }
        }
    }

    public static void encodeCD(Timetable tt) {
        for (Course c : tt.getCourses().values()) {
            for (int i = 0; i < tt.getNumTeachingDays(); i++) {
                CD cd = new CD(c, i);
                cdEnc.put(String.valueOf(numVar), cd);
                //System.out.println(String.valueOf(numVar)+" "+cd.getCourse().getId()+" "+ i);
                numVar++;
            }
        }
    }

    public static void encodeKH(Timetable tt) {
        for (Curriculum c : tt.getCurricula().values()) {
            for (int i = 0; i < tt.getNumTeachingDays(); i++) {
                for (int j = 0; j < tt.getPeriodsPerDay(); j++) {
                    KH kh = new KH(c, new TimeSlot(i, j));
                    khEnc.put(String.valueOf(numVar), kh);
                    // System.out.println(numVar+" "+kh.getCurriculum().getId()+" "+ i+ " "+j);
                    numVar++;
                }
            }
        }
    }

    public static void encodeCHR(Timetable tt) {
        System.out.println("Encoding CHR");
        for (CH ch : (Collection<CH>) Encoding.getChEnc().values()) {
            for (Room r : tt.getRooms().values()) {
                CHR chr = new CHR(ch.getCourse(), r, ch.getTimeSlot());
                chrEnc.put(numVar, chr);
                numVar++;
                //  System.out.println(numVar+" "+ chr.getCourse().getId()+" "+ chr.getRoom().getId()+" "+chr.getTimeSlot().getDay()+" "+chr.getTimeSlot().getPeriod());
            }
        }
    }


    // NEW
    public static List<CHR> decodeModel(int[] model) throws IOException {
        List<CHR> result = new ArrayList<CHR>();
        StringBuilder sb = new StringBuilder();
        for (int m : model) {
            if (m > 0) {
                if (chrEnc.containsKey(m)) {
                    CHR chr = (CHR) chrEnc.get(m);
                    result.add(chr);
                    System.out.println("course " + chr.getCourse().getId().toString() + " room " + chr.getRoom().getId().toString() + " timeslot-day " + chr.getTimeSlot().getDay().toString() + " timeslot-hour " + chr.getTimeSlot().getPeriod().toString());
                    sb.append(chr.getCourse().getId().toString() + " " + chr.getRoom().getId().toString() + " " + chr.getTimeSlot().getDay().toString() + " " + chr.getTimeSlot().getPeriod().toString() + "\n");
                }

            }
        }
        File file = new File("solution.sol");

        // if file doesnt exists, then create it
        if (!file.exists()) {
            file.createNewFile();
        }

        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(sb.toString());
        bw.close();
        return result;
    }


    public static BidiMap getCdEnc() {
        return cdEnc;
    }

    public static BidiMap getChEnc() {
        return chEnc;
    }

    public static BidiMap getCrEnc() {
        return crEnc;
    }

    public static BidiMap getKhEnc() {
        return khEnc;
    }

    public static BidiMap getChrEnc() {
        return chrEnc;
    }
}
