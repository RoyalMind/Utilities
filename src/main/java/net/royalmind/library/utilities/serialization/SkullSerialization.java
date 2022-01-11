package net.royalmind.library.utilities.serialization;

import org.bukkit.inventory.meta.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import net.royalmind.library.utilities.json.*;

public class SkullSerialization
{
    public static JSONObject serializeSkull(final SkullMeta skullMeta) {
        try {
            final JSONObject jsonObject = new JSONObject();
            if (skullMeta.hasOwner()) {
                jsonObject.put("owner", skullMeta.getOwner());
            }
            return jsonObject;
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static String serializeSkullAsString(final SkullMeta skullMeta) {
        return serializeSkullAsString(skullMeta, false);
    }

    public static String serializeSkullAsString(final SkullMeta skullMeta, final boolean b) {
        return serializeSkullAsString(skullMeta, b, 5);
    }

    public static String serializeSkullAsString(final SkullMeta skullMeta, final boolean b, final int n) {
        try {
            if (b) {
                return serializeSkull(skullMeta).toString(n);
            }
            return serializeSkull(skullMeta).toString();
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static SkullMeta getSkullMeta(final String s) {
        try {
            return getSkullMeta(new JSONObject(s));
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static SkullMeta getSkullMeta(final JSONObject jsonObject) {
        try {
            final SkullMeta skullMeta = (SkullMeta)new ItemStack(Material.LEGACY_SKULL_ITEM).getItemMeta();
            if (jsonObject.has("owner")) {
                skullMeta.setOwner(jsonObject.getString("owner"));
            }
            return skullMeta;
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
