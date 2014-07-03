package emcl.fub.blue.entity;

/**
 * Created with IntelliJ IDEA.
 * User: admin
 * Date: 6/8/14
 * Time: 1:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class Course implements Cloneable {

    private String id;                  // course Id
    private String teacher;             // teacher of course
    private Integer numOfLectures;      // number of lectures to be scheduled in distinct periods
    private Integer minNumOfDays;       // min number of Days that the lectures of the course should be spread in
    private Integer numOfStudents;      // number of students attending the course
    private Boolean doubleLectures;     // if the lectures should be double

    public Course() {
    }

    public Course(String id, String teacher, Integer numOfLectures, Integer minNumOfDays, Integer numOfStudents, Boolean doubleLectures) {
        this.id = id;
        this.teacher = teacher;
        this.numOfLectures = numOfLectures;
        this.minNumOfDays = minNumOfDays;
        this.numOfStudents = numOfStudents;
        this.doubleLectures = doubleLectures;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public Integer getNumOfLectures() {
        return numOfLectures;
    }

    public void setNumOfLectures(Integer numOfLectures) {
        this.numOfLectures = numOfLectures;
    }

    public Integer getMinNumOfDays() {
        return minNumOfDays;
    }

    public void setMinNumOfDays(Integer minNumOfDays) {
        this.minNumOfDays = minNumOfDays;
    }

    public Integer getNumOfStudents() {
        return numOfStudents;
    }

    public void setNumOfStudents(Integer numOfStudents) {
        this.numOfStudents = numOfStudents;
    }

    public Boolean getDoubleLectures() {
        return doubleLectures;
    }

    public void setDoubleLectures(Boolean doubleLectures) {
        this.doubleLectures = doubleLectures;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
/*        Course clone = null;
        try{
            clone = (Course) super.clone(); }
        catch(CloneNotSupportedException e){ throw new RuntimeException(e);*/


        return (Course)super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;

        if (doubleLectures != null ? !doubleLectures.equals(course.doubleLectures) : course.doubleLectures != null)
            return false;
        if (id != null ? !id.equals(course.id) : course.id != null) return false;
        if (minNumOfDays != null ? !minNumOfDays.equals(course.minNumOfDays) : course.minNumOfDays != null)
            return false;
        if (numOfLectures != null ? !numOfLectures.equals(course.numOfLectures) : course.numOfLectures != null)
            return false;
        if (numOfStudents != null ? !numOfStudents.equals(course.numOfStudents) : course.numOfStudents != null)
            return false;
        if (teacher != null ? !teacher.equals(course.teacher) : course.teacher != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (teacher != null ? teacher.hashCode() : 0);
        result = 31 * result + (numOfLectures != null ? numOfLectures.hashCode() : 0);
        result = 31 * result + (minNumOfDays != null ? minNumOfDays.hashCode() : 0);
        result = 31 * result + (numOfStudents != null ? numOfStudents.hashCode() : 0);
        result = 31 * result + (doubleLectures != null ? doubleLectures.hashCode() : 0);
        return result;
    }
}

