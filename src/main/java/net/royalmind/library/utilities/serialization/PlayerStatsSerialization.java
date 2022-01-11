package net.royalmind.library.utilities.serialization;

import java.util.*;

import org.bukkit.potion.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import net.royalmind.library.utilities.json.*;

public class PlayerStatsSerialization
{
    public static JSONObject serializePlayerStats(final Player player) {
        try {
            final JSONObject jsonObject = new JSONObject();
            jsonObject.put("can-fly", player.getAllowFlight());
            jsonObject.put("display-name", player.getDisplayName());
            jsonObject.put("exhaustion", player.getExhaustion());
            jsonObject.put("exp", player.getExp());
            jsonObject.put("flying", player.isFlying());
            jsonObject.put("food", player.getFoodLevel());
            jsonObject.put("gamemode", player.getGameMode().name());
            jsonObject.put("health", player.getHealthScale());
            jsonObject.put("level", player.getLevel());
            jsonObject.put("potion-effects", PotionEffectSerialization.serializeEffects(player.getActivePotionEffects()));
            jsonObject.put("saturation", player.getSaturation());
            return jsonObject;
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static String serializePlayerStatsAsString(final Player player) {
        return serializePlayerStatsAsString(player, false);
    }

    public static String serializePlayerStatsAsString(final Player player, final boolean b) {
        return serializePlayerStatsAsString(player, b, 5);
    }

    public static String serializePlayerStatsAsString(final Player player, final boolean b, final int n) {
        try {
            if (b) {
                return serializePlayerStats(player).toString(n);
            }
            return serializePlayerStats(player).toString();
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static void applyPlayerStats(final Player player, final String s) {
        try {
            applyPlayerStats(player, new JSONObject(s));
        }
        catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    public static void applyPlayerStats(final Player player, final JSONObject jsonObject) {
        try {
            if (jsonObject.has("can-fly")) {
                player.setAllowFlight(jsonObject.getBoolean("can-fly"));
            }
            if (jsonObject.has("display-name")) {
                player.setDisplayName(jsonObject.getString("display-name"));
            }
            if (jsonObject.has("exhaustion")) {
                player.setExhaustion((float)jsonObject.getDouble("exhaustion"));
            }
            if (jsonObject.has("exp")) {
                player.setExp((float)jsonObject.getDouble("exp"));
            }
            if (jsonObject.has("flying")) {
                player.setFlying(jsonObject.getBoolean("flying"));
            }
            if (jsonObject.has("food")) {
                player.setFoodLevel(jsonObject.getInt("food"));
            }
            if (jsonObject.has("health")) {
                player.setHealth(jsonObject.getDouble("health"));
            }
            if (jsonObject.has("gamemode")) {
                player.setGameMode(GameMode.valueOf(jsonObject.getString("gamemode")));
            }
            if (jsonObject.has("level")) {
                player.setLevel(jsonObject.getInt("level"));
            }
            if (jsonObject.has("potion-effects")) {
                PotionEffectSerialization.setPotionEffects(jsonObject.getString("potion-effects"), (LivingEntity)player);
            }
            if (jsonObject.has("saturation")) {
                player.setSaturation((float)jsonObject.getDouble("saturation"));
            }
        }
        catch (JSONException ex) {
            ex.printStackTrace();
        }
    }
}
