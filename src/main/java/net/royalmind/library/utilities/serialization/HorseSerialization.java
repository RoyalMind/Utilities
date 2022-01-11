package net.royalmind.library.utilities.serialization;

import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.*;
import net.royalmind.library.utilities.json.*;

public class HorseSerialization
{
    public static JSONObject serializeHorse(final Horse horse) {
        try {
            final JSONObject serializeEntity = LivingEntitySerialization.serializeEntity((LivingEntity)horse);
            serializeEntity.put("color", horse.getColor().name());
            serializeEntity.put("inventory", InventorySerialization.serializeInventory((Inventory)horse.getInventory()));
            serializeEntity.put("jump-strength", horse.getJumpStrength());
            serializeEntity.put("style", horse.getStyle());
            serializeEntity.put("variant", horse.getVariant());
            return serializeEntity;
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static String serializeHorseAsString(final Horse horse) {
        return serializeHorseAsString(horse, false);
    }

    public static String serializeHorseAsString(final Horse horse, final boolean b) {
        return serializeHorseAsString(horse, b, 5);
    }

    public static String serializeHorseAsString(final Horse horse, final boolean b, final int n) {
        try {
            if (b) {
                return serializeHorse(horse).toString(n);
            }
            return serializeHorse(horse).toString();
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static Horse spawnHorse(final Location location, final String s) {
        try {
            return spawnHorse(location, new JSONObject(s));
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static Horse spawnHorse(final Location location, final JSONObject jsonObject) {
        try {
            final Horse horse = (Horse)LivingEntitySerialization.spawnEntity(location, jsonObject);
            if (jsonObject.has("color")) {
                horse.setColor(Horse.Color.valueOf(jsonObject.getString("color")));
            }
            if (jsonObject.has("jump-strength")) {
                horse.setCustomName(jsonObject.getString("name"));
            }
            if (jsonObject.has("style")) {
                horse.setStyle(Horse.Style.valueOf(jsonObject.getString("style")));
            }
            if (jsonObject.has("inventory")) {
                PotionEffectSerialization.addPotionEffects(jsonObject.getString("potion-effects"), (LivingEntity)horse);
            }
            if (jsonObject.has("variant")) {
                horse.setVariant(Horse.Variant.valueOf(jsonObject.getString("variant")));
            }
            return horse;
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}