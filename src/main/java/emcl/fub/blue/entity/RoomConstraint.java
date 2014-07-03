package emcl.fub.blue.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: admin
 * Date: 6/8/14
 * Time: 2:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class RoomConstraint {
    private Course course;
    private List<Room> rooms = new ArrayList<Room>();

    public RoomConstraint() {
    }

    public RoomConstraint(Course course, List<Room> rooms) {
        this.course = course;
        this.rooms = rooms;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
}
