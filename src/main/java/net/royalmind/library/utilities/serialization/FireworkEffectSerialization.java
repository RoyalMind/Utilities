package net.royalmind.library.utilities.serialization;

import java.util.*;
import org.bukkit.*;
import net.royalmind.library.utilities.json.*;

public class FireworkEffectSerialization
{
    public static FireworkEffect getFireworkEffect(final String s) {
        return getFireworkEffect(s);
    }

    public static FireworkEffect getFireworkEffect(final JSONObject jsonObject) {
        try {
            final FireworkEffect.Builder builder = FireworkEffect.builder();
            final JSONArray jsonArray = jsonObject.getJSONArray("colors");
            for (int i = 0; i < jsonArray.length(); ++i) {
                builder.withColor(ColorSerialization.getColor(jsonArray.getJSONObject(i)));
            }
            final JSONArray jsonArray2 = jsonObject.getJSONArray("fade-colors");
            for (int j = 0; j < jsonArray2.length(); ++j) {
                builder.withFade(ColorSerialization.getColor(jsonArray.getJSONObject(j)));
            }
            if (jsonObject.getBoolean("flicker")) {
                builder.withFlicker();
            }
            if (jsonObject.getBoolean("trail")) {
                builder.withTrail();
            }
            builder.with(FireworkEffect.Type.valueOf(jsonObject.getString("type")));
            return builder.build();
        }
        catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        catch (JSONException ex2) {
            ex2.printStackTrace();
        }
        return null;
    }

    public static JSONObject serializeFireworkEffect(final FireworkEffect fireworkEffect) {
        try {
            final JSONObject jsonObject = new JSONObject();
            final JSONArray jsonArray = new JSONArray();
            final Iterator<Color> iterator = fireworkEffect.getColors().iterator();
            while (iterator.hasNext()) {
                jsonArray.put(ColorSerialization.serializeColor(iterator.next()));
            }
            jsonObject.put("colors", jsonArray);
            final JSONArray jsonArray2 = new JSONArray();
            final Iterator<Color> iterator2 = fireworkEffect.getFadeColors().iterator();
            while (iterator2.hasNext()) {
                jsonArray2.put(ColorSerialization.serializeColor(iterator2.next()));
            }
            jsonObject.put("fade-colors", jsonArray2);
            jsonObject.put("flicker", fireworkEffect.hasFlicker());
            jsonObject.put("trail", fireworkEffect.hasTrail());
            jsonObject.put("type", fireworkEffect.getType().name());
            return jsonObject;
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static String serializeFireworkEffectAsString(final FireworkEffect fireworkEffect) {
        return serializeFireworkEffectAsString(fireworkEffect, false);
    }

    public static String serializeFireworkEffectAsString(final FireworkEffect fireworkEffect, final boolean b) {
        return serializeFireworkEffectAsString(fireworkEffect, false, 5);
    }

    public static String serializeFireworkEffectAsString(final FireworkEffect fireworkEffect, final boolean b, final int n) {
        return Serializer.toString(serializeFireworkEffect(fireworkEffect), b, n);
    }
}
