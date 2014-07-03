package emcl.fub.blue.constraints;


import emcl.fub.blue.entity.Curriculum;
import emcl.fub.blue.entity.TimeSlot;

/**
 * Created with IntelliJ IDEA.
 * User: admin
 * Date: 6/17/14
 * Time: 1:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class KH {
    private Curriculum curriculum;
    private TimeSlot timeslot;

    public KH() {
    }

    public KH(Curriculum curriculum, TimeSlot timeslot) {
        this.curriculum = curriculum;
        this.timeslot = timeslot;
    }

    public Curriculum getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(Curriculum curriculum) {
        this.curriculum = curriculum;
    }

    public TimeSlot getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(TimeSlot timeslot) {
        this.timeslot = timeslot;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KH kh = (KH) o;

        if (curriculum != null ? !curriculum.equals(kh.curriculum) : kh.curriculum != null) return false;
        if (timeslot != null ? !timeslot.equals(kh.timeslot) : kh.timeslot != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = curriculum != null ? curriculum.hashCode() : 0;
        result = 31 * result + (timeslot != null ? timeslot.hashCode() : 0);
        return result;
    }
}
