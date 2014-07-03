package emcl.fub.blue.constraints;

import emcl.fub.blue.entity.Course;
import emcl.fub.blue.entity.Room;


public class CR {

    private Course course;
    private Room room;

    public CR() {
    }

    public CR(Course course, Room room) {
        this.course = course;
        this.room = room;
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



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CR cr = (CR) o;

        if (course != null ? !course.equals(cr.course) : cr.course != null) return false;
        if (room != null ? !room.equals(cr.room) : cr.room != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = course != null ? course.hashCode() : 0;
        result = 31 * result + (room != null ? room.hashCode() : 0);
        return result;
    }

}
