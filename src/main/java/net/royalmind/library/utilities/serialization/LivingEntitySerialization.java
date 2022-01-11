package net.royalmind.library.utilities.serialization;

import java.util.*;
import org.bukkit.potion.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import net.royalmind.library.utilities.json.*;

public class LivingEntitySerialization
{
    public static JSONObject serializeEntity(final LivingEntity livingEntity) {
        try {
            final JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", livingEntity.getCustomName());
            jsonObject.put("potion-effects", PotionEffectSerialization.serializeEffects(livingEntity.getActivePotionEffects()));
            jsonObject.put("type", livingEntity.getType().getTypeId());
            return jsonObject;
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static String serializeEntityAsString(final LivingEntity livingEntity) {
        return serializeEntityAsString(livingEntity, false);
    }

    public static String serializeEntityAsString(final LivingEntity livingEntity, final boolean b) {
        return serializeEntityAsString(livingEntity, b, 5);
    }

    public static String serializeEntityAsString(final LivingEntity livingEntity, final boolean b, final int n) {
        try {
            if (b) {
                return serializeEntity(livingEntity).toString(n);
            }
            return serializeEntity(livingEntity).toString();
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static LivingEntity spawnEntity(final Location location, final String s) {
        try {
            return spawnEntity(location, new JSONObject(s));
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static LivingEntity spawnEntity(final Location location, final JSONObject jsonObject) {
        try {
            if (!jsonObject.has("type")) {
                throw new IllegalArgumentException("The type of the entity cannot be determined");
            }
            final LivingEntity livingEntity = (LivingEntity)location.getWorld().spawnEntity(location, EntityType.fromId(jsonObject.getInt("type")));
            if (jsonObject.has("age") && livingEntity instanceof Ageable) {
                ((Ageable)livingEntity).setAge(jsonObject.getInt("age"));
            }
            if (jsonObject.has("health")) {
                livingEntity.setHealth(jsonObject.getDouble("health"));
            }
            if (jsonObject.has("name")) {
                livingEntity.setCustomName(jsonObject.getString("name"));
            }
            if (jsonObject.has("potion-effects")) {}
            PotionEffectSerialization.addPotionEffects(jsonObject.getString("potion-effects"), livingEntity);
            return livingEntity;
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
