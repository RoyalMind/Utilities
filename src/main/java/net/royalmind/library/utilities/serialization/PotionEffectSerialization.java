package net.royalmind.library.utilities.serialization;

import java.util.*;
import org.bukkit.potion.*;
import org.bukkit.entity.*;

public class PotionEffectSerialization
{
    public static String serializeEffects(final Collection<PotionEffect> collection) {
        String string = "";
        for (final PotionEffect potionEffect : collection) {
            string = string + potionEffect.getType().getId() + ":" + potionEffect.getDuration() + ":" + potionEffect.getAmplifier() + ";";
        }
        return string;
    }

    public static Collection<PotionEffect> getPotionEffects(final String s) {
        final ArrayList<PotionEffect> list = new ArrayList<PotionEffect>();
        if (s.isEmpty()) {
            return list;
        }
        final String[] split = s.split(";");
        for (int i = 0; i < split.length; ++i) {
            final String[] split2 = split[i].split(":");
            if (split2.length < 3) {
                throw new IllegalArgumentException(s + " - PotionEffect " + i + " (" + split[i] + "): split must at least have a length of 3");
            }
            if (!Util.isNum(split2[0])) {
                throw new IllegalArgumentException(s + " - PotionEffect " + i + " (" + split[i] + "): id is not an integer");
            }
            if (!Util.isNum(split2[1])) {
                throw new IllegalArgumentException(s + " - PotionEffect " + i + " (" + split[i] + "): duration is not an integer");
            }
            if (!Util.isNum(split2[2])) {
                throw new IllegalArgumentException(s + " - PotionEffect " + i + " (" + split[i] + "): amplifier is not an integer");
            }
            final int int1 = Integer.parseInt(split2[0]);
            final int int2 = Integer.parseInt(split2[1]);
            final int int3 = Integer.parseInt(split2[2]);
            final PotionEffectType byId = PotionEffectType.getById(int1);
            if (byId == null) {
                throw new IllegalArgumentException(s + " - PotionEffect " + i + " (" + split[i] + "): no PotionEffectType with id of " + int1);
            }
            list.add(new PotionEffect(byId, int2, int3));
        }
        return list;
    }

    public static void addPotionEffects(final String s, final LivingEntity livingEntity) {
        livingEntity.addPotionEffects((Collection)getPotionEffects(s));
    }

    public static void setPotionEffects(final String s, final LivingEntity livingEntity) {
        final Iterator<PotionEffect> iterator = livingEntity.getActivePotionEffects().iterator();
        while (iterator.hasNext()) {
            livingEntity.removePotionEffect(iterator.next().getType());
        }
        addPotionEffects(s, livingEntity);
    }
}
