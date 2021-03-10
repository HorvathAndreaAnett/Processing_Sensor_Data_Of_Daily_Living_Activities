package asg5;

import java.util.Date;

public class MonitoredData {

    private Date startTime;
    private Date endTime;
    private String activityType;

    public MonitoredData(Date startTime, Date endTime, String activityType) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.activityType = activityType;
    }

    @Override
    public String toString() {
        return "MonitoredData{" +
                "startTime = " + startTime +
                ", endTime = " + endTime +
                ", activityType = '" + activityType + '\'' +
                '}';
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public String getActivityType() {
        return activityType;
    }
}
