package ua.edu.sumdu.j2se.pyrih.tasks.util;

/**
 * Class converts seconds value into days, hours, minutes and seconds.
 */
public class TimeConverter {

    /**
     * Returns time interval (days, hours, minutes and seconds) as a string.
     * If one of the values ​​0 it is not displayed in the console.
     * <p>
     * Examples:
     * Input : 369121517
     * Output : 4272 days 5 hours 45 minutes 17 seconds
     *
     * @param n an integer value in seconds.
     * @return string value.
     */
    public static String convertSecondsToDay(int n) {
        StringBuilder b = new StringBuilder();

        int days = n / (24 * 3600);
        if (days > 1) b.append(days).append(" ").append("days ");
        else if (days > 0) b.append(days).append(" ").append("day ");

        n = n % (24 * 3600);
        int hours = n / 3600;
        if (hours > 1) b.append(hours).append(" ").append("hours ");
        else if (hours > 0) b.append(hours).append(" ").append("hour ");

        n %= 3600;
        int minutes = n / 60;
        if (minutes > 1) b.append(minutes).append(" ").append("minutes ");
        else if (minutes > 0) b.append(minutes).append(" ").append("minute ");

        n %= 60;
        int seconds = n;
        if (seconds > 1) b.append(seconds).append(" ").append("seconds ");
        else if (seconds > 0) b.append(seconds).append(" ").append("second ");

        return b.toString();
    }
}
