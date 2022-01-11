package net.royalmind.library.utilities;

import java.util.*;

public class StringUtil
{
    public static String randomString(final int n) {
        final String s = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        final int length = s.length();
        String string = new String();
        final Random random = new Random();
        for (int i = 0; i < n; ++i) {
            string += s.charAt(random.nextInt(length));
        }
        return string;
    }
}
