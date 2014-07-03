package emcl.fub.blue.entity;

/**
 * Created with IntelliJ IDEA.
 * User: admin
 * Date: 6/8/14
 * Time: 1:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class TimeSlot implements Cloneable {
    private Integer day;
    private Integer period;

    public TimeSlot() {
    }

    public TimeSlot(Integer day, Integer period) {
        this.day = day;
        this.period = period;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimeSlot timeSlot = (TimeSlot) o;

        if (day != null ? !day.equals(timeSlot.day) : timeSlot.day != null) return false;
        if (period != null ? !period.equals(timeSlot.period) : timeSlot.period != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = day != null ? day.hashCode() : 0;
        result = 31 * result + (period != null ? period.hashCode() : 0);
        return result;
    }
}
