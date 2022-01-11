package net.royalmind.library.utilities;

import java.util.*;
import java.io.*;

public class TextUtils
{
    public static String join(final CharSequence charSequence, final Object[] array) {
        final StringBuilder sb = new StringBuilder();
        int n = 1;
        for (final Object o : array) {
            if (n != 0) {
                n = 0;
            }
            else {
                sb.append(charSequence);
            }
            sb.append(o);
        }
        return sb.toString();
    }

    public static String join(final CharSequence charSequence, final Iterable<?> iterable) {
        final StringBuilder sb = new StringBuilder();
        int n = 1;
        for (final Object next : iterable) {
            if (n != 0) {
                n = 0;
            }
            else {
                sb.append(charSequence);
            }
            sb.append(next);
        }
        return sb.toString();
    }

    public static String getStringFromInputStream(final InputStream inputStream) {
        BufferedReader bufferedReader = null;
        final StringBuilder sb = new StringBuilder();
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                }
                catch (IOException ex2) {
                    ex2.printStackTrace();
                }
            }
        }
        finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                }
                catch (IOException ex3) {
                    ex3.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
}
