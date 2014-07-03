package emcl.fub.blue.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: admin
 * Date: 6/8/14
 * Time: 3:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class PeriodConstraint {
    private Course course;
    private List<TimeSlot> timeSlots = new ArrayList<TimeSlot>();

    public PeriodConstraint() {
    }

    public PeriodConstraint(Course course, List<TimeSlot> timeSlots) {
        this.course = course;
        this.timeSlots = timeSlots;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<TimeSlot> getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(List<TimeSlot> timeSlots) {
        this.timeSlots = timeSlots;
    }
}
