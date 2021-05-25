package com.elkard.tripmanager.utils;

public class DateUtils {

    public static String formatDate(String day)
    {
        return day.substring(6,8) + "." + day.substring(4,6) + "." + day.substring(0,4);
    }

    public static String formatDateRange(String begDate, String endDate)
    {
        String begDay = getDay(begDate);
        String begMonth = getMonth(begDate);
        String begYear = getYear(begDate);

        String endDay = getDay(endDate);
        String endMonth = getMonth(endDate);
        String endYear = getYear(endDate);

        if (begYear.equals(endYear))
        {
            if (begMonth.equals(endMonth))
                return begDay + " - " + endDay +"." + begMonth + "." + begYear;

            return begDay + "." + begMonth + " - " + endDay + "." + endMonth + "." + begYear;
        }

        return formatDate(begDay) + " - " + formatDate(endDay);
    }

    private static String getDay(String date)
    {
        return date.substring(6, 8);
    }

    private static String getMonth(String date)
    {
        return date.substring(4, 6);
    }

    private static String getYear(String date)
    {
        return date.substring(0, 4);
    }
}
