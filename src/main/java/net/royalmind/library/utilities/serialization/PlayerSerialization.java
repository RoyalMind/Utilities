package net.royalmind.library.utilities.serialization;

import org.bukkit.entity.*;
import net.royalmind.library.utilities.json.*;

public class PlayerSerialization
{
    public static JSONObject serializePlayer(final Player player) {
        try {
            final JSONObject serializeEntity = LivingEntitySerialization.serializeEntity((LivingEntity)player);
            serializeEntity.put("ender-chest", InventorySerialization.serializeInventory(player.getEnderChest()));
            serializeEntity.put("inventory", InventorySerialization.serializePlayerInventory(player.getInventory()));
            serializeEntity.put("stats", PlayerStatsSerialization.serializePlayerStats(player));
            return serializeEntity;
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static String serializePlayerAsString(final Player player) {
        return serializePlayerAsString(player, false);
    }

    public static String serializePlayerAsString(final Player player, final boolean b) {
        return serializePlayerAsString(player, b, 5);
    }

    public static String serializePlayerAsString(final Player player, final boolean b, final int n) {
        try {
            if (b) {
                return serializePlayer(player).toString(n);
            }
            return serializePlayer(player).toString();
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static void setPlayer(final String s, final Player player) {
        try {
            setPlayer(new JSONObject(s), player);
        }
        catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    public static void setPlayer(final JSONObject jsonObject, final Player player) {
        try {
            if (jsonObject.has("ender-chest")) {
                InventorySerialization.setInventory(player.getEnderChest(), jsonObject.getJSONArray("ender-chest"));
            }
            if (jsonObject.has("inventory")) {
                InventorySerialization.setPlayerInventory(player, jsonObject.getJSONObject("inventory"));
            }
            if (jsonObject.has("stats")) {
                PlayerStatsSerialization.applyPlayerStats(player, jsonObject.getJSONObject("stats"));
            }
        }
        catch (JSONException ex) {
            ex.printStackTrace();
        }
    }
}
