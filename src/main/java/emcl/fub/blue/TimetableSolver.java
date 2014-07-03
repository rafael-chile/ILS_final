package emcl.fub.blue;

import emcl.fub.blue.constraints.*;
import emcl.fub.blue.entity.Timetable;
import emcl.fub.blue.parser.XMLParser;
import emcl.fub.blue.solver.SolverUtil;
import org.sat4j.reader.ParseFormatException;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.TimeoutException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class TimetableSolver {
    public static List<CHR> solveTimetable(String filePath) throws ContradictionException, IOException, TimeoutException, ParseFormatException, URISyntaxException {
        Timetable tt = XMLParser.parseXMLFile(filePath);
        //Encoding of variables
        Encoding.encodeCH(tt);
        Encoding.encodeCR(tt);
        Encoding.encodeCD(tt);
        Encoding.encodeKH(tt);
        Encoding.encodeCHR(tt);

        SolverUtil s = new SolverUtil(Encoding.getNumVar(),Encoding.numClauses);

        //Hard
        s.addHard(KHConstraint.curAndCourseInTimeSlot(tt));
        s.addHard(KHConstraint.curBelongToCur(tt));

        s.addHard(CDConstraint.takePlaceInDay(tt));
        s.addHard(CDConstraint.takePlaceInTimeSlot(tt));

        s.addHard(CHRConstraint.chrAndChAssigned(tt));
        s.addHard(CHRConstraint.chrOneOfRoomsAssigned(tt));
        s.addHard(CHRConstraint.chrAndCrAssigned(tt));
        s.addHard(CHRConstraint.chrCourseShouldBeAssigned(tt));

        s.addHard(CHConstraint.curriculumClashes(tt));
        s.addHard(CHConstraint.timeAvailability(tt));
        s.addHard(CHConstraint.teacherClashes(tt));

        s.addHard(CRConstraint.roomSuitability(tt));

        s.addHard(CHRConstraint.roomClashes(tt));
        s.addHard(CRConstraint.roomClashes(tt));

        s.addExactly(CHConstraint.numberLectures(tt));
        s.addAtLeast(CDConstraint.minWorkingDays(tt));
        //Soft

        s.addAtLeast(KHConstraint.studentMaxLoad2(tt));
        s.addAtLeast(KHConstraint.studentMinLoad(tt));

        s.addSoft(CHRConstraint.roomCapacity(tt));
        s.addSoft(CRConstraint.roomCapacity(tt));

        s.addSoft(KHConstraint.windows(tt));
        s.addSoft(CHRConstraint.doubleLectures(tt));

        s.getMaxSatSolver().setExpectedNumberOfClauses(Encoding.numClauses+100);
        int[] model= s.solveSAT();
        // int[] model = s.optimize();
        List<CHR> resultTimetable = Encoding.decodeModel(model);
        return resultTimetable;
    }
}
