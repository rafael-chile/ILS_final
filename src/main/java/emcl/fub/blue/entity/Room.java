package emcl.fub.blue.entity;

/**
 * Created with IntelliJ IDEA.
 * User: admin
 * Date: 6/8/14
 * Time: 1:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class Room {
    private String id;                  //number of the Room
    private Integer capacity;           //number of students (typically from 20 to 1000)
    private String building;            //number of builging where lecture takes place

    public Room() {
    }

    public Room(String id, Integer capacity, String building) {
        this.id = id;
        this.capacity = capacity;
        this.building = building;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }
}
