package emcl.fub.blue.parser;

import emcl.fub.blue.entity.*;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: admin
 * Date: 6/8/14
 * Time: 2:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class XMLHandler extends DefaultHandler {

    private static Logger log = Logger.getLogger(XMLHandler.class.getName());

    private boolean bcourses = false;
    private boolean brooms = false;
    private boolean bcurricula = false;
    private boolean bcurriculum = false;
    private boolean bPeriodConstraint = false;
    private boolean bRoomConstraint = false;

    private Timetable tt = new Timetable();

    private HashMap<String, Course> courses = new HashMap<String, Course>();
    private HashMap<String, Room> rooms = new HashMap<String, Room>();
    private HashMap<String, Curriculum> curricula = new HashMap<String, Curriculum>();
    private HashMap<String, PeriodConstraint> periodConstraints = new HashMap<String, PeriodConstraint>();
    private HashMap<String, RoomConstraint> roomComstraints = new HashMap<String, RoomConstraint>();

    private List<Course> courseForCur = new ArrayList<Course>();
    private List<TimeSlot> timeSlots = new ArrayList<TimeSlot>();
    private List<Room> roomConsts = new ArrayList<Room>();


    private Curriculum cur = new Curriculum();
    private PeriodConstraint pc = new PeriodConstraint();
    private RoomConstraint rc = new RoomConstraint();



    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        log.info("Start parsing...");

    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals("days"))
            tt.setNumTeachingDays(Integer.valueOf(attributes.getValue("value")));
        if (qName.equals("periods_per_day"))
            tt.setPeriodsPerDay(Integer.valueOf(attributes.getValue("value")));
        if (qName.equals("daily_lectures")) {
            tt.setMinDailyLect(Integer.valueOf(attributes.getValue("min")));
            tt.setMaxDailyLecture(Integer.valueOf(attributes.getValue("max")));
        }


        if (qName.equals("courses")) {
            bcourses = true;
        }
        if (qName.equals("rooms")) {
            brooms = true;
        }
        if (qName.equals("curricula")) {
            bcurricula = true;
        }
        if (qName.equals("curriculum")) {
            bcurriculum = true;
        }
        if (qName.equals("constraint") && attributes.getValue("type").equals("period")) {
            bPeriodConstraint = true;
        }
        if (qName.equals("constraint") && attributes.getValue("type").equals("room")) {
            bRoomConstraint = true;
        }

        if (qName.equals("course") && bcourses) {
            Course c = new Course();
            c.setId(attributes.getValue("id"));
            c.setTeacher(attributes.getValue("teacher"));
            c.setNumOfLectures(Integer.parseInt(attributes.getValue("lectures")));
            c.setMinNumOfDays(Integer.parseInt(attributes.getValue("min_days")));
            c.setNumOfStudents(Integer.parseInt(attributes.getValue("students")));

            if (attributes.getValue("double_lectures").equals("yes")) {
                c.setDoubleLectures(true);
            } else {
                c.setDoubleLectures(false);
            }
            courses.put(c.getId(), c);
        }

        if (qName.equals("room") && brooms) {
            Room room = new Room();
            room.setId(attributes.getValue("id"));
            room.setCapacity(Integer.parseInt(attributes.getValue("size")));
            room.setBuilding(attributes.getValue("building"));
            rooms.put(room.getId(),room);
        }

        if (bcurricula) {

            if (qName.equals("curriculum"))  {
                cur.setId(attributes.getValue("id"));
            }

            if (qName.equals("course") && bcurriculum){
                String idCourse= attributes.getValue("ref");
                courseForCur.add(courses.get(idCourse));
            }
        }

        if (bPeriodConstraint) {
            if (attributes.getValue("type")!=null && attributes.getValue("type").equals("period"))
                pc.setCourse(courses.get(attributes.getValue("course")));
            if (qName.equals("timeslot")){
                TimeSlot timeslot=new TimeSlot();
                timeslot.setDay(Integer.parseInt(attributes.getValue("day")));
                timeslot.setPeriod(Integer.parseInt(attributes.getValue("period")));
                timeSlots.add(timeslot);

            }

        }

        // constraints with "room" type
        if (bRoomConstraint){
            if(attributes.getValue("type")!=null && attributes.getValue("type").equals("room")){
                rc.setCourse(courses.get(attributes.getValue("course")));
            }
            if (qName.equals("room"))
                roomConsts.add(rooms.get(attributes.getValue("ref")));
        }



    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("courses")) {
            bcourses = false;
            tt.setCourses(courses);
        }

        if (qName.equals("rooms")) {
            brooms = false;
            tt.setRooms(rooms);
        }

        if (qName.equals("curriculum")) {
            bcurriculum = false;
            ArrayList<Course> coursesCopy = new ArrayList<Course>();
            for (Course c:courseForCur) {
                coursesCopy.add(c);
            }
            cur.setCourses(coursesCopy);
            curricula.put(cur.getId(), cur);
            courseForCur.clear();

            cur = new Curriculum();
        }

        if (qName.equals("curricula")) {
            bcurricula = false;
            tt.setCurricula(curricula);
        }

        if (qName.equals("constraint") && bPeriodConstraint){
            bPeriodConstraint = false;

            ArrayList<TimeSlot> timeSlotCopy = new ArrayList<TimeSlot>();
            for (TimeSlot t:timeSlots) {
                timeSlotCopy.add(t);
            }
            pc.setTimeSlots(timeSlotCopy);
            timeSlots.clear();

            periodConstraints.put(pc.getCourse().getId(), pc);
            pc = new PeriodConstraint();



        }

        if (qName.equals("constraint") && bRoomConstraint){
            bRoomConstraint = false;

            ArrayList<Room> roomsCopy = new ArrayList<Room>();
            for (Room r:roomConsts) {
                roomsCopy.add(r);
            }
            rc.setRooms(roomsCopy);
            roomConsts.clear();

            roomComstraints.put(rc.getCourse().getId(), rc);
            rc = new RoomConstraint();

        }

        if (qName.equals("constraints")) {
            tt.setRoomComstraints(roomComstraints);
            tt.setPeriodConstraints(periodConstraints);
        }
    }

    public Timetable getTimetable() {
        return tt;
    }


}
