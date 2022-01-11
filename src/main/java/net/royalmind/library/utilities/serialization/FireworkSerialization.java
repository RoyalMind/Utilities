package net.royalmind.library.utilities.serialization;

import java.util.*;

import org.bukkit.inventory.meta.*;
import org.bukkit.inventory.*;
import org.bukkit.*;
import net.royalmind.library.utilities.json.*;

public class FireworkSerialization
{
    public static FireworkMeta getFireworkMeta(final String s) {
        return getFireworkMeta(s);
    }

    public static FireworkMeta getFireworkMeta(final JSONObject jsonObject) {
        try {
            final FireworkMeta fireworkMeta = (FireworkMeta)new ItemStack(Material.LEGACY_FIREWORK).getItemMeta();
            fireworkMeta.setPower(jsonObject.optInt("power", 1));
            final JSONArray jsonArray = jsonObject.getJSONArray("effects");
            for (int i = 0; i < jsonArray.length(); ++i) {
                final FireworkEffect fireworkEffect = FireworkEffectSerialization.getFireworkEffect(jsonArray.getJSONObject(i));
                if (fireworkEffect != null) {
                    fireworkMeta.addEffect(fireworkEffect);
                }
            }
            return fireworkMeta;
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static JSONObject serializeFireworkMeta(final FireworkMeta fireworkMeta) {
        try {
            final JSONObject jsonObject = new JSONObject();
            jsonObject.put("power", fireworkMeta.getPower());
            final JSONArray jsonArray = new JSONArray();
            final Iterator<FireworkEffect> iterator = fireworkMeta.getEffects().iterator();
            while (iterator.hasNext()) {
                jsonArray.put(FireworkEffectSerialization.serializeFireworkEffect(iterator.next()));
            }
            jsonObject.put("effects", jsonArray);
            return jsonObject;
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static String serializeFireworkMetaAsString(final FireworkMeta fireworkMeta) {
        return serializeFireworkMetaAsString(fireworkMeta, false);
    }

    public static String serializeFireworkMetaAsString(final FireworkMeta fireworkMeta, final boolean b) {
        return serializeFireworkMetaAsString(fireworkMeta, false, 5);
    }

    public static String serializeFireworkMetaAsString(final FireworkMeta fireworkMeta, final boolean b, final int n) {
        return Serializer.toString(serializeFireworkMeta(fireworkMeta), b, n);
    }
}
