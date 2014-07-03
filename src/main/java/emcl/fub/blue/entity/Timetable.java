package emcl.fub.blue.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created with IntelliJ IDEA.
 * User: admin
 * Date: 6/8/14
 * Time: 1:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class Timetable {

    private Integer numTeachingDays;        // number of teaching days
    private Integer periodsPerDay;          // number of periods per day
    private Integer minDailyLect;           // min number daily lectures
    private Integer maxDailyLecture;        // max number of daily lectures

    private HashMap<String, Course> courses;
    private HashMap<String, Room> rooms;
    private HashMap<String, Curriculum> curricula;
    private HashMap<String, PeriodConstraint> periodConstraints;
    private HashMap<String, RoomConstraint> roomComstraints;

    public Timetable(Integer numTeachingDays, Integer periodsPerDay, Integer minDailyLect, Integer maxDailyLecture,
                     HashMap<String, Course> courses, HashMap<String, Room> rooms, HashMap<String, Curriculum> curricula) {
        this.numTeachingDays = numTeachingDays;
        this.periodsPerDay = periodsPerDay;
        this.minDailyLect = minDailyLect;
        this.maxDailyLecture = maxDailyLecture;
        this.courses = courses;
        this.rooms = rooms;
        this.curricula = curricula;
    }

    public Timetable(Integer numTeachingDays, Integer periodsPerDay, Integer minDailyLect, Integer maxDailyLecture) {
        this.numTeachingDays = numTeachingDays;
        this.periodsPerDay = periodsPerDay;
        this.minDailyLect = minDailyLect;
        this.maxDailyLecture = maxDailyLecture;
    }

    public Timetable() {
    }

    public Integer getNumTeachingDays() {
        return numTeachingDays;
    }

    public void setNumTeachingDays(Integer numTeachingDays) {
        this.numTeachingDays = numTeachingDays;
    }

    public Integer getPeriodsPerDay() {
        return periodsPerDay;
    }

    public void setPeriodsPerDay(Integer periodsPerDay) {
        this.periodsPerDay = periodsPerDay;
    }

    public Integer getMinDailyLect() {
        return minDailyLect;
    }

    public void setMinDailyLect(Integer minDailyLect) {
        this.minDailyLect = minDailyLect;
    }

    public Integer getMaxDailyLecture() {
        return maxDailyLecture;
    }

    public void setMaxDailyLecture(Integer maxDailyLecture) {
        this.maxDailyLecture = maxDailyLecture;
    }

    public HashMap<String, Course> getCourses() {
        return courses;
    }

    public void setCourses(HashMap<String, Course> courses) {
        this.courses = courses;
    }

    public HashMap<String, Room> getRooms() {
        return rooms;
    }

    public void setRooms(HashMap<String, Room> rooms) {
        this.rooms = rooms;
    }

    public HashMap<String, Curriculum> getCurricula() {
        return curricula;
    }

    public void setCurricula(HashMap<String, Curriculum> curricula) {
        this.curricula = curricula;
    }

    public HashMap<String, PeriodConstraint> getPeriodConstraints() {
        return periodConstraints;
    }

    public void setPeriodConstraints(HashMap<String, PeriodConstraint> periodConstraints) {
        this.periodConstraints = periodConstraints;
    }

    public HashMap<String, RoomConstraint> getRoomComstraints() {
        return roomComstraints;
    }

    public void setRoomComstraints(HashMap<String, RoomConstraint> roomComstraints) {
        this.roomComstraints = roomComstraints;
    }

    public ArrayList<Curriculum> getCurriculaByCourse(Course course){
        ArrayList<Curriculum> cur = new ArrayList<Curriculum>();
        for (Curriculum c: curricula.values()) {
            for (Course cour: c.getCourses()) {
                if (cour.equals(course)) {
                    cur.add(c);
                    break;
                }
            }
        }

        return cur;
    }
}
