package net.royalmind.library.utilities.serialization;

import org.bukkit.inventory.meta.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import net.royalmind.library.utilities.json.*;

public class LeatherArmorSerialization
{
    public static JSONObject serializeArmor(final LeatherArmorMeta leatherArmorMeta) {
        try {
            final JSONObject jsonObject = new JSONObject();
            jsonObject.put("color", ColorSerialization.serializeColor(leatherArmorMeta.getColor()));
            return jsonObject;
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static String serializeArmorAsString(final LeatherArmorMeta leatherArmorMeta) {
        return serializeArmorAsString(leatherArmorMeta, false);
    }

    public static String serializeArmorAsString(final LeatherArmorMeta leatherArmorMeta, final boolean b) {
        return serializeArmorAsString(leatherArmorMeta, b, 5);
    }

    public static String serializeArmorAsString(final LeatherArmorMeta leatherArmorMeta, final boolean b, final int n) {
        try {
            if (b) {
                return serializeArmor(leatherArmorMeta).toString(n);
            }
            return serializeArmor(leatherArmorMeta).toString();
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static LeatherArmorMeta getLeatherArmorMeta(final String s) {
        try {
            return getLeatherArmorMeta(new JSONObject(s));
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static LeatherArmorMeta getLeatherArmorMeta(final JSONObject jsonObject) {
        try {
            final LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta)new ItemStack(Material.LEATHER_HELMET, 1).getItemMeta();
            if (jsonObject.has("color")) {
                leatherArmorMeta.setColor(ColorSerialization.getColor(jsonObject.getJSONObject("color")));
            }
            return leatherArmorMeta;
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
