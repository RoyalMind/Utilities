package net.royalmind.library.utilities.serialization;

import org.bukkit.entity.*;
import org.bukkit.*;
import net.royalmind.library.utilities.json.*;

public class WolfSerialization
{
    public static JSONObject serializeWolf(final Wolf wolf) {
        try {
            final JSONObject serializeEntity = LivingEntitySerialization.serializeEntity((LivingEntity)wolf);
            serializeEntity.put("collar-color", ColorSerialization.serializeColor(wolf.getCollarColor().getColor()));
            return serializeEntity;
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static String serializeWolfAsString(final Wolf wolf) {
        return serializeWolfAsString(wolf, false);
    }

    public static String serializeWolfAsString(final Wolf wolf, final boolean b) {
        return serializeWolfAsString(wolf, b, 5);
    }

    public static String serializeWolfAsString(final Wolf wolf, final boolean b, final int n) {
        try {
            if (b) {
                return serializeWolf(wolf).toString(n);
            }
            return serializeWolf(wolf).toString();
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static Wolf spawnWolf(final Location location, final String s) {
        try {
            return spawnWolf(location, new JSONObject(s));
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static Wolf spawnWolf(final Location location, final JSONObject jsonObject) {
        try {
            final Wolf wolf = (Wolf)LivingEntitySerialization.spawnEntity(location, jsonObject);
            wolf.setCollarColor(DyeColor.getByColor(ColorSerialization.getColor(jsonObject.getString("collar-color"))));
            return wolf;
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
