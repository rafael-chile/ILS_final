package emcl.fub.blue.constraints;

import emcl.fub.blue.entity.Course;
import emcl.fub.blue.entity.Room;
import emcl.fub.blue.entity.TimeSlot;

/**
 * Created with IntelliJ IDEA.
 * User: admin
 * Date: 6/17/14
 * Time: 3:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class CHR {
    private Course course;
    private Room room;
    private TimeSlot timeSlot;

    public CHR() {
    }

    public CHR(Course course, Room room, TimeSlot timeSlot) {
        this.course = course;
        this.room = room;
        this.timeSlot = timeSlot;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
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

        CHR chr = (CHR) o;

        if (course != null ? !course.equals(chr.course) : chr.course != null) return false;
        if (room != null ? !room.equals(chr.room) : chr.room != null) return false;
        if (timeSlot != null ? !timeSlot.equals(chr.timeSlot) : chr.timeSlot != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = course != null ? course.hashCode() : 0;
        result = 31 * result + (room != null ? room.hashCode() : 0);
        result = 31 * result + (timeSlot != null ? timeSlot.hashCode() : 0);
        return result;
    }
}
