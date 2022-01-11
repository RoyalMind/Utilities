package net.royalmind.library.utilities.serialization;

import org.bukkit.*;
import net.royalmind.library.utilities.json.*;

public class LocationSerialization
{
    public static JSONObject serializeLocation(final Location location) {
        try {
            final JSONObject jsonObject = new JSONObject();
            jsonObject.put("x", location.getX());
            jsonObject.put("y", location.getY());
            jsonObject.put("z", location.getZ());
            jsonObject.put("pitch", location.getPitch());
            jsonObject.put("yaw", location.getYaw());
            jsonObject.put("world", location.getWorld().getName());
            return jsonObject;
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static String serializeLocationAsString(final Location location) {
        return serializeLocationAsString(location, false);
    }

    public static String serializeLocationAsString(final Location location, final boolean b) {
        return serializeLocationAsString(location, b, 5);
    }

    public static String serializeLocationAsString(final Location location, final boolean b, final int n) {
        try {
            if (b) {
                return serializeLocation(location).toString(n);
            }
            return serializeLocation(location).toString();
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static Location getLocationMeta(final String s) {
        try {
            return getLocationMeta(new JSONObject(s));
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static Location getLocationMeta(final JSONObject jsonObject) {
        try {
            return new Location(Bukkit.createWorld(new WorldCreator(jsonObject.getString("world"))), jsonObject.getDouble("x"), jsonObject.getDouble("y"), jsonObject.getDouble("z"), Float.parseFloat(jsonObject.getDouble("yaw") + ""), Float.parseFloat(jsonObject.getDouble("pitch") + ""));
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
