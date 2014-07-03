package emcl.fub.blue.constraints;

import emcl.fub.blue.entity.Course;

/**
 * Created with IntelliJ IDEA.
 * User: admin
 * Date: 6/17/14
 * Time: 11:20 AM
 * To change this template use File | Settings | File Templates.
 */
public class CD {

    private Course course;
    private int day;

    public CD() {
    }

    public CD(Course course, int day) {
        this.course = course;
        this.day = day;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CD cd = (CD) o;

        if (day != cd.day) return false;
        if (course != null ? !course.equals(cd.course) : cd.course != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = course != null ? course.hashCode() : 0;
        result = 31 * result + day;
        return result;
    }
}
