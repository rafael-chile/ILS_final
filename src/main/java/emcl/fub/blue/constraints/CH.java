package emcl.fub.blue.constraints;

import emcl.fub.blue.entity.Course;
import emcl.fub.blue.entity.TimeSlot;

/**
 * Created with IntelliJ IDEA.
 * User: admin
 * Date: 6/9/14
 * Time: 9:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class CH {
    private Course course;
    private TimeSlot timeSlot;

    public CH() {
    }

    public CH(Course course, TimeSlot timeSlot) {
        this.course = course;
        this.timeSlot = timeSlot;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CH ch = (CH) o;

        if (course != null ? !course.equals(ch.course) : ch.course != null) return false;
        if (timeSlot != null ? !timeSlot.equals(ch.timeSlot) : ch.timeSlot != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = course != null ? course.hashCode() : 0;
        result = 31 * result + (timeSlot != null ? timeSlot.hashCode() : 0);
        return result;
    }
}
