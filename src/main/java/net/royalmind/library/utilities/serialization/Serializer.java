package net.royalmind.library.utilities.serialization;

import java.io.*;
import java.util.*;
import net.royalmind.library.utilities.json.*;

public class Serializer
{
    public static String toString(final JSONObject jsonObject) {
        return toString(jsonObject, true);
    }

    public static String toString(final JSONObject jsonObject, final boolean b) {
        return toString(jsonObject, b, 5);
    }

    public static String toString(final JSONObject jsonObject, final boolean b, final int n) {
        try {
            if (b) {
                return jsonObject.toString(n);
            }
            return jsonObject.toString();
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static JSONObject getObjectFromFile(final File file) throws FileNotFoundException, JSONException {
        return getObjectFromStream(new FileInputStream(file));
    }

    public static JSONObject getObjectFromStream(final InputStream inputStream) throws JSONException {
        return new JSONObject(getStringFromStream(inputStream));
    }

    public static String getStringFromFile(final File file) throws FileNotFoundException {
        return getStringFromStream(new FileInputStream(file));
    }

    public static String getStringFromStream(final InputStream inputStream) {
        final Scanner scanner = new Scanner(inputStream);
        String string = "";
        while (scanner.hasNextLine()) {
            string = string + scanner.nextLine() + "\n";
        }
        scanner.close();
        return string.trim();
    }
}
