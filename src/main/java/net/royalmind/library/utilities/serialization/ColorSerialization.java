package net.royalmind.library.utilities.serialization;

import org.bukkit.*;
import net.royalmind.library.utilities.json.*;

public class ColorSerialization
{
    public static JSONObject serializeColor(final Color color) {
        try {
            final JSONObject jsonObject = new JSONObject();
            jsonObject.put("red", color.getRed());
            jsonObject.put("green", color.getGreen());
            jsonObject.put("blue", color.getBlue());
            return jsonObject;
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static Color getColor(final String s) {
        try {
            return getColor(new JSONObject(s));
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static Color getColor(final JSONObject jsonObject) {
        try {
            int int1 = 0;
            int int2 = 0;
            int int3 = 0;
            if (jsonObject.has("red")) {
                int1 = jsonObject.getInt("red");
            }
            if (jsonObject.has("green")) {
                int2 = jsonObject.getInt("green");
            }
            if (jsonObject.has("blue")) {
                int3 = jsonObject.getInt("blue");
            }
            return Color.fromRGB(int1, int2, int3);
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static String serializeColorAsString(final Color color) {
        return serializeColorAsString(color, false);
    }

    public static String serializeColorAsString(final Color color, final boolean b) {
        return serializeColorAsString(color, b, 5);
    }

    public static String serializeColorAsString(final Color color, final boolean b, final int n) {
        try {
            if (b) {
                return serializeColor(color).toString(n);
            }
            return serializeColor(color).toString();
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
