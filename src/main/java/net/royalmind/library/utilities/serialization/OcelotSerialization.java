package net.royalmind.library.utilities.serialization;

import org.bukkit.entity.*;
import org.bukkit.*;
import net.royalmind.library.utilities.json.*;

public class OcelotSerialization
{
    public static JSONObject serializeOcelot(final Ocelot ocelot) {
        try {
            final JSONObject serializeEntity = LivingEntitySerialization.serializeEntity((LivingEntity)ocelot);
            serializeEntity.put("type", ocelot.getCatType().name());
            return serializeEntity;
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static String serializeOcelotAsString(final Ocelot ocelot) {
        return serializeOcelotAsString(ocelot, false);
    }

    public static String serializeOcelotAsString(final Ocelot ocelot, final boolean b) {
        return serializeOcelotAsString(ocelot, b, 5);
    }

    public static String serializeOcelotAsString(final Ocelot ocelot, final boolean b, final int n) {
        try {
            if (b) {
                return serializeOcelot(ocelot).toString(n);
            }
            return serializeOcelot(ocelot).toString();
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static Ocelot spawnOcelot(final Location location, final String s) {
        try {
            return spawnOcelot(location, new JSONObject(s));
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static Ocelot spawnOcelot(final Location location, final JSONObject jsonObject) {
        try {
            final Ocelot ocelot = (Ocelot)LivingEntitySerialization.spawnEntity(location, jsonObject);
            if (jsonObject.has("type")) {
                ocelot.setCatType(Ocelot.Type.valueOf(jsonObject.getString("type")));
            }
            return ocelot;
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
