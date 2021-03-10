package asg5;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Monitor {

    private String fileName;
    private List<MonitoredData> activities = new ArrayList<MonitoredData>();
    private Writer w = new Writer();

    public Monitor(String fileName) {
        this.fileName = fileName;
    }

    static Function<String, Date> convStringToDate = date -> {
        try {
            return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(date);
        } catch (Exception e) {
        }
        return null;
    };

    static Function<Date, Date> getSimpleDateFormat = date -> {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return formatter.parse(formatter.format(date));
        } catch (Exception e) {
        }
        return null;
    };

    //compute the time interval between two dates in seconds
    static BiFunction<Date, Date, Long> getTimeInterval = (dateBeginning, dateEnd) -> Math.abs(dateEnd.getTime() - dateBeginning.getTime()) / 1000;

    public void task1() {
        Stream<String> stream;
        try {
            stream = Files.lines(Paths.get(fileName));
            activities = stream.map(line -> line.split("[\t]+")) //split each line by tabs
                    .map(attribute -> new MonitoredData(convStringToDate.apply(attribute[0]), convStringToDate.apply(attribute[1]), attribute[2])) //store the tokens in a MonitoredData object; the first two are the dates and have to be converted to date
                    .collect(Collectors.toList()); //store each object in a list
        } catch (Exception e) {
        }

        w.writeTask1(activities); //create the .txt output file
    }

    public void task2() {
        List<Date> days = new ArrayList<Date>();
        activities.forEach(item -> days.add(getSimpleDateFormat.apply(item.getStartTime()))); //add in a list all the start days without the hour difference
        int numberOfDays = (int) days.stream().distinct().count(); //count the distinct days in the list

        w.writeTask2(numberOfDays); //create the .txt output file
    }

    public void task3() {
        Map<String, Long> activityTypes = activities.stream()
                .collect(Collectors.groupingBy(MonitoredData::getActivityType, Collectors.counting())); //group the activities by their label and map them to the number of their occurances

        w.writeTask3(activityTypes); //create the .txt output file
    }

    public void task4() {
        Map<Integer, Map<String, Long>> activityOccurancesInADay = activities.stream()
                .collect(Collectors.groupingBy(d -> d.getStartTime().getDate(), //map to the day of the start date
                        Collectors.groupingBy(MonitoredData::getActivityType, //map another map -> the number of times an activity appears each day
                                Collectors.counting())));

        w.writeTask4(activityOccurancesInADay); //create the .txt output file
    }

    public void task5() {
        Map<String, Long> totalMonitoringTime = activities.stream()
                .collect(Collectors.groupingBy(MonitoredData::getActivityType, //map to each activity type the sum of the time intervals of each of its occurrences
                        Collectors.summingLong(d -> getTimeInterval.apply(d.getStartTime(), d.getEndTime()))));

        w.writeTask5(totalMonitoringTime); //create the .txt output file
    }

    public void task6() {
        Map<String, Long> activityOccurances = activities.stream()
                .collect(Collectors.groupingBy(MonitoredData::getActivityType, Collectors.counting())); //map each activity type to its number of occurrences
        Map<String, Long> activitiesShortTimings = activities.stream() //map each activity to its number of occurrences with a time interval less than 5 min
                .filter(activity -> getTimeInterval.apply(activity.getStartTime(), activity.getEndTime()) < 300) //5 min = 300 sec
                .collect(Collectors.groupingBy(MonitoredData::getActivityType, Collectors.counting()));

        Function<String, Long> getValueFromActivitiesShortTimings = activity -> { //get the value in the activitiesShortTimings map when the key is known
            for (Map.Entry<String, Long> entry : activitiesShortTimings.entrySet()) {
                if (entry.getKey().equals(activity)) {
                    return entry.getValue();
                }
            }
            return Long.valueOf(0);
        };

        List<String> shortActivities = activityOccurances.entrySet().stream() //collect in a list the activities with more than 90% of the activities less than 5 min
                .filter(item -> getValueFromActivitiesShortTimings.apply(item.getKey()) / item.getValue() > 0.9) //compute the percentage
                .map(item -> item.getKey())
                .collect(Collectors.toList());

        w.writeTask6(shortActivities); //create the .txt output file
    }

}
