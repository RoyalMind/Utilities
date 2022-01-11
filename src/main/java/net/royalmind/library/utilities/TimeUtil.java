package net.royalmind.library.utilities;

import java.util.*;

public class TimeUtil
{
    private static String _day;
    private static String _hour;
    private static String _minute;
    private static String _seccond;
    private static String _days;
    private static String _hours;
    private static String _minutes;
    private static String _secconds;

    public static String formatTimeReaming(final String s, final long n) {
        if (n > 0L) {
            long n2 = n - System.currentTimeMillis();
            int n3 = 0;
            int i = 0;
            int j = 0;
            int k = 0;
            while (n2 >= 1000L) {
                ++k;
                n2 -= 1000L;
            }
            while (k >= 60) {
                ++j;
                k -= 60;
            }
            while (j >= 60) {
                ++i;
                j -= 60;
            }
            while (i >= 24) {
                ++n3;
                i -= 24;
            }
            return timeFormat(s, n3, i, j, k);
        }
        return null;
    }

    public static String formatTime(final int n) {
        if (n > 0) {
            int i = n;
            int j = 0;
            int k = 0;
            int n2 = 0;
            while (i >= 60) {
                ++j;
                i -= 60;
            }
            while (j >= 60) {
                ++k;
                j -= 60;
            }
            while (k >= 24) {
                ++n2;
                k -= 24;
            }
            final StringBuilder sb = new StringBuilder();
            if (n2 > 0) {
                sb.append("{DAYS} " + ((n2 > 1) ? TimeUtil._days : TimeUtil._day));
            }
            if (k > 0) {
                sb.append("{HOURS} " + ((k > 1) ? TimeUtil._hours : TimeUtil._hour));
            }
            if (j > 0) {
                sb.append("{MINUTES} " + ((j > 1) ? TimeUtil._minutes : TimeUtil._minute));
            }
            if (i > 0) {
                sb.append("{SECONDS} " + ((i > 1) ? TimeUtil._secconds : TimeUtil._seccond));
            }
            return timeFormat(sb.toString(), n2, k, j, i);
        }
        return null;
    }

    public static String timeFormat(final String s, final int n, final int n2, final int n3, final int n4) {
        return s.replace("{DAYS}", "" + n).replace("{HOURS}", "" + n2).replace("{MINUTES}", "" + n3).replace("{SECONDS}", "" + n4);
    }

    public static Date addDate(final Date date, final String s, final int n) {
        final Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(date.getTime());
        switch (s) {
            case "seconds": {
                instance.add(13, n);
                break;
            }
            case "minutes": {
                instance.add(12, n);
                break;
            }
            case "hours": {
                instance.add(10, n);
                break;
            }
            case "days": {
                instance.add(5, n);
                break;
            }
            case "weeks": {
                instance.add(4, n);
                break;
            }
            case "months": {
                instance.add(2, n);
                break;
            }
            case "years": {
                instance.add(1, n);
                break;
            }
        }
        return instance.getTime();
    }

    public static double round(double n, final int n2) {
        if (n2 < 0) {
            throw new IllegalArgumentException();
        }
        final long n3 = (long)Math.pow(10.0, n2);
        n *= n3;
        return Math.round(n) / n3;
    }

    public static long getDiff(final Long n) {
        return n - System.currentTimeMillis();
    }

    public static boolean isFuture(final long n) {
        return n - System.currentTimeMillis() > 0L;
    }

    public static boolean isPass(final long n) {
        return n - System.currentTimeMillis() < 0L;
    }

    static
    {
        TimeUtil._day = "dia ";
        TimeUtil._hour = "hora ";
        TimeUtil._minute = "minuto ";
        TimeUtil._seccond = "segundo ";
        TimeUtil._days = "dias ";
        TimeUtil._hours = "horas ";
        TimeUtil._minutes = "minutos ";
        TimeUtil._secconds = "segundos ";
    }
}
