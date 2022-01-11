package net.royalmind.library.utilities;

import java.util.regex.*;

public class DateUtil
{
    public static long parseDateDiff(final String s) {
        final Matcher matcher = Pattern.compile("(?:([0-9]+)\\s*y[a-z]*[,\\s]*)?(?:([0-9]+)\\s*mo[a-z]*[,\\s]*)?(?:([0-9]+)\\s*w[a-z]*[,\\s]*)?(?:([0-9]+)\\s*d[a-z]*[,\\s]*)?(?:([0-9]+)\\s*h[a-z]*[,\\s]*)?(?:([0-9]+)\\s*m[a-z]*[,\\s]*)?(?:([0-9]+)\\s*(?:s[a-z]*)?)?", 2).matcher(s);
        long n = 0L;
        boolean b = false;
        while (matcher.find()) {
            if (matcher.group() != null && !matcher.group().isEmpty()) {
                for (int i = 0; i < matcher.groupCount(); ++i) {
                    if (matcher.group(i) != null && !matcher.group(i).isEmpty()) {
                        b = true;
                        break;
                    }
                }
                if (!b) {
                    continue;
                }
                if (matcher.group(1) != null && !matcher.group(1).isEmpty()) {
                    n += 31556926 * Integer.parseInt(matcher.group(1));
                }
                if (matcher.group(2) != null && !matcher.group(2).isEmpty()) {
                    n += 2629743 * Integer.parseInt(matcher.group(2));
                }
                if (matcher.group(3) != null && !matcher.group(3).isEmpty()) {
                    n += 604800 * Integer.parseInt(matcher.group(3));
                }
                if (matcher.group(4) != null && !matcher.group(4).isEmpty()) {
                    n += 86400 * Integer.parseInt(matcher.group(4));
                }
                if (matcher.group(5) != null && !matcher.group(5).isEmpty()) {
                    n += 3600 * Integer.parseInt(matcher.group(5));
                }
                if (matcher.group(6) != null && !matcher.group(6).isEmpty()) {
                    n += 60 * Integer.parseInt(matcher.group(6));
                }
                if (matcher.group(7) == null || matcher.group(7).isEmpty()) {
                    continue;
                }
                n += Integer.parseInt(matcher.group(7));
            }
        }
        if (!b) {
            return -1L;
        }
        return n * 1000L;
    }
}
