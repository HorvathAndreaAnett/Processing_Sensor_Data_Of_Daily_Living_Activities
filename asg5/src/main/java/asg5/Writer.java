package asg5;

import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Writer {

    private File output;
    private FileWriter writer;

    public void writeTask1(List<MonitoredData> activities) {
        String name = "Task_1.txt";
        this.output = new File(name);
        try {
            output.createNewFile();
            writer = new FileWriter(output);

            activities.forEach(activity -> {
                try {
                    writer.write(activity.toString() + "\n");
                } catch (Exception e) {
                }
            });

            writer.close();
        } catch (Exception e) {
        }
    }

    public void writeTask2(int numberOfDays) {
        String name = "Task_2.txt";
        this.output = new File(name);
        try {
            output.createNewFile();
            writer = new FileWriter(output);

            writer.write("Number of different days over the monitoring periond: " + numberOfDays);

            writer.close();
        } catch (Exception e) {
        }
    }

    public void writeTask3(Map<String, Long> activityTypes) {
        String name = "Task_3.txt";
        this.output = new File(name);
        try {
            output.createNewFile();
            writer = new FileWriter(output);

            writer.write("Number of times each activity appeared over the monitoring period: \n\n");

            activityTypes.entrySet().forEach(entry -> {
                try {
                    writer.write(entry.getKey() + ": " + entry.getValue() + "\n");
                } catch (Exception e) {
                }
            });

            writer.close();
        } catch (Exception e) {
        }
    }

    public void writeTask4(Map<Integer, Map<String, Long>> activityOccurancesInADay) {
        String name = "Task_4.txt";
        this.output = new File(name);
        try {
            output.createNewFile();
            writer = new FileWriter(output);

            writer.write("Number of times each activity has appeared for each day over the monitoring period: \n");

            activityOccurancesInADay.entrySet().forEach(entryDay -> {
                try {
                    writer.write("\nDay " + entryDay.getKey() + "\n");
                } catch (Exception e) {
                }
                entryDay.getValue().entrySet().forEach(entryActivity -> {
                    try {
                        writer.write(entryActivity.getKey() + ": " + entryActivity.getValue() + "\n");
                    } catch (Exception e) {
                    }
                });
            });

            writer.close();
        } catch (Exception e) {
        }
    }

    static Function<Long, String> timeIntervalToString = time -> time / 3600 + ":" + (time % 3600) / 60 + ":" + time % 60;

    public void writeTask5(Map<String, Long> totalMonitoringTime) {
        String name = "Task_5.txt";
        this.output = new File(name);
        try {
            output.createNewFile();
            writer = new FileWriter(output);

            writer.write("The entire duration of each activity over the monitoring period: \n\n");

            totalMonitoringTime.entrySet().forEach(entry -> {
                try {
                    writer.write(entry.getKey() + ": " + timeIntervalToString.apply(entry.getValue()) + "\n");
                } catch (Exception e) {
                }
            });

            writer.close();
        } catch (Exception e) {
        }
    }

    public void writeTask6(List<String> shortActivities) {
        String name = "Task_6.txt";
        this.output = new File(name);
        try {
            output.createNewFile();
            writer = new FileWriter(output);

            writer.write("Activities that have more than 90% of the monitoring records with duration less than 5 minutes: \n\n");

            shortActivities.forEach(activity -> {
                try {
                    writer.write(activity + "\n");
                } catch (Exception e) {
                }
            });

            writer.close();
        } catch (Exception e) {
        }
    }
}
