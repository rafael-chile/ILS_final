package emcl.fub.blue.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: admin
 * Date: 6/8/14
 * Time: 2:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class Curriculum {
    private String id;
    private List<Course> courses = new ArrayList<Course>();

    public Curriculum() {
    }

    public Curriculum(String id, List<Course> courses) {
        this.id = id;
        this.courses = courses;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Curriculum that = (Curriculum) o;

        if (courses != null ? !courses.equals(that.courses) : that.courses != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (courses != null ? courses.hashCode() : 0);
        return result;
    }




}
