package net.royalmind.library.utilities;

import java.lang.reflect.*;
import java.util.*;

import com.mojang.authlib.*;
import com.mojang.authlib.properties.*;

import org.bukkit.*;
import org.bukkit.inventory.meta.*;
import org.bukkit.potion.*;
import org.bukkit.enchantments.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;

public class ItemUtils
{
    public static ItemStack getSkull(final String s) {
        final ItemStack itemStack = new ItemStack(Material.LEGACY_SKULL_ITEM, 1, (short)3);
        final ItemMeta itemMeta = itemStack.getItemMeta();
        final GameProfile gameProfile = new GameProfile(UUID.randomUUID(), (String)null);
        gameProfile.getProperties().put("textures", new Property("textures", s));
        Field declaredField = null;
        try {
            declaredField = itemMeta.getClass().getDeclaredField("profile");
        }
        catch (NoSuchFieldException ex) {
            ex.printStackTrace();
        }
        catch (SecurityException ex2) {
            ex2.printStackTrace();
        }
        declaredField.setAccessible(true);
        try {
            declaredField.set(itemMeta, gameProfile);
        }
        catch (IllegalArgumentException ex3) {
            ex3.printStackTrace();
        }
        catch (IllegalAccessException ex4) {
            ex4.printStackTrace();
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack parseItem(final String s) {
        final List<String> list = Arrays.asList(s.split(" "));
        if (list.size() < 2) {
            final String[] split = list.get(0).split(":");
            return new ItemStack(Material.getMaterial(split[0].toUpperCase()), 1, (short)((split.length == 2) ? Integer.parseInt(split[1]) : 0));
        }
        ItemStack itemStack = null;
        try {
            if (list.get(0).contains(":")) {
                final Material material = Material.getMaterial(list.get(0).split(":")[0].toUpperCase());
                final int int1 = Integer.parseInt(list.get(1));
                if (int1 < 1) {
                    return null;
                }
                itemStack = new ItemStack(material, int1, (short)Integer.parseInt(list.get(0).split(":")[1].toUpperCase()));
            }
            else {
                itemStack = new ItemStack(Material.getMaterial(list.get(0).toUpperCase()), Integer.parseInt(list.get(1)));
            }
            if (list.size() > 2) {
                for (int i = 2; i < list.size(); ++i) {
                    if (list.get(i).split(":")[0].equalsIgnoreCase("name")) {
                        final ItemMeta itemMeta = itemStack.getItemMeta();
                        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', list.get(i).split(":")[1].replaceAll("_", " ")));
                        itemStack.setItemMeta(itemMeta);
                    }
                    else if (list.get(i).split(":")[0].equalsIgnoreCase("color")) {
                        final LeatherArmorMeta itemMeta2 = (LeatherArmorMeta)itemStack.getItemMeta();
                        itemMeta2.setColor(getColor(list.get(i).split(":")[1].toUpperCase()));
                        itemStack.setItemMeta((ItemMeta)itemMeta2);
                    }
                    else {
                        itemStack.addUnsafeEnchantment(getEnchant(list.get(i).split(":")[0]), Integer.parseInt(list.get(i).split(":")[1]));
                    }
                }
            }
        }
        catch (Exception ex) {}
        return itemStack;
    }

    public static PotionEffect parseEffect(final List<String> list) {
        if (list.size() < 2) {
            return null;
        }
        PotionEffect potionEffect = null;
        try {
            final PotionEffectType potionType = getPotionType(list.get(0));
            int n;
            if (Integer.parseInt(list.get(1)) == -1) {
                n = Integer.MAX_VALUE;
            }
            else {
                n = 20 * Integer.parseInt(list.get(1));
            }
            potionEffect = new PotionEffect(potionType, n, Integer.parseInt(list.get(2)));
        }
        catch (Exception ex) {}
        return potionEffect;
    }

    private static PotionEffectType getPotionType(final String s) {
        final String lowerCase = s.toLowerCase();
        switch (lowerCase) {
            case "speed": {
                return PotionEffectType.SPEED;
            }
            case "slowness": {
                return PotionEffectType.SLOW;
            }
            case "haste": {
                return PotionEffectType.FAST_DIGGING;
            }
            case "miningfatique": {
                return PotionEffectType.SLOW_DIGGING;
            }
            case "strength": {
                return PotionEffectType.INCREASE_DAMAGE;
            }
            case "instanthealth": {
                return PotionEffectType.HEAL;
            }
            case "instantdamage": {
                return PotionEffectType.HARM;
            }
            case "jumpboost": {
                return PotionEffectType.JUMP;
            }
            case "nausea": {
                return PotionEffectType.CONFUSION;
            }
            case "regeneration": {
                return PotionEffectType.REGENERATION;
            }
            case "resistance": {
                return PotionEffectType.DAMAGE_RESISTANCE;
            }
            case "fireresistance": {
                return PotionEffectType.FIRE_RESISTANCE;
            }
            case "waterbreathing": {
                return PotionEffectType.WATER_BREATHING;
            }
            case "invisibility": {
                return PotionEffectType.INVISIBILITY;
            }
            case "blindness": {
                return PotionEffectType.BLINDNESS;
            }
            case "nightvision": {
                return PotionEffectType.NIGHT_VISION;
            }
            case "hunger": {
                return PotionEffectType.HUNGER;
            }
            case "weakness": {
                return PotionEffectType.WEAKNESS;
            }
            case "poison": {
                return PotionEffectType.POISON;
            }
            case "wither": {
                return PotionEffectType.WITHER;
            }
            case "healthboost": {
                return PotionEffectType.HEALTH_BOOST;
            }
            case "absorption": {
                return PotionEffectType.ABSORPTION;
            }
            case "saturation": {
                return PotionEffectType.SATURATION;
            }
            default: {
                return null;
            }
        }
    }

    private static Enchantment getEnchant(final String s) {
        final String lowerCase = s.toLowerCase();
        switch (lowerCase) {
            case "protection": {
                return Enchantment.PROTECTION_ENVIRONMENTAL;
            }
            case "projectileprotection": {
                return Enchantment.PROTECTION_PROJECTILE;
            }
            case "fireprotection": {
                return Enchantment.PROTECTION_FIRE;
            }
            case "featherfall": {
                return Enchantment.PROTECTION_FALL;
            }
            case "blastprotection": {
                return Enchantment.PROTECTION_EXPLOSIONS;
            }
            case "respiration": {
                return Enchantment.OXYGEN;
            }
            case "aquaaffinity": {
                return Enchantment.WATER_WORKER;
            }
            case "sharpness": {
                return Enchantment.DAMAGE_ALL;
            }
            case "smite": {
                return Enchantment.DAMAGE_UNDEAD;
            }
            case "baneofarthropods": {
                return Enchantment.DAMAGE_ARTHROPODS;
            }
            case "knockback": {
                return Enchantment.KNOCKBACK;
            }
            case "fireaspect": {
                return Enchantment.FIRE_ASPECT;
            }
            case "depthstrider": {
                return Enchantment.DEPTH_STRIDER;
            }
            case "looting": {
                return Enchantment.LOOT_BONUS_MOBS;
            }
            case "power": {
                return Enchantment.ARROW_DAMAGE;
            }
            case "punch": {
                return Enchantment.ARROW_KNOCKBACK;
            }
            case "flame": {
                return Enchantment.ARROW_FIRE;
            }
            case "infinity": {
                return Enchantment.ARROW_INFINITE;
            }
            case "efficiency": {
                return Enchantment.DIG_SPEED;
            }
            case "silktouch": {
                return Enchantment.SILK_TOUCH;
            }
            case "unbreaking": {
                return Enchantment.DURABILITY;
            }
            case "fortune": {
                return Enchantment.LOOT_BONUS_BLOCKS;
            }
            case "luckofthesea": {
                return Enchantment.LUCK;
            }
            case "luck": {
                return Enchantment.LUCK;
            }
            case "lure": {
                return Enchantment.LURE;
            }
            case "thorns": {
                return Enchantment.THORNS;
            }
            default: {
                return null;
            }
        }
    }

    public static Color getColor(final String s) {
        if (s.equalsIgnoreCase("AQUA")) {
            return Color.AQUA;
        }
        if (s.equalsIgnoreCase("BLACK")) {
            return Color.BLACK;
        }
        if (s.equalsIgnoreCase("BLUE")) {
            return Color.BLUE;
        }
        if (s.equalsIgnoreCase("FUCHSIA")) {
            return Color.FUCHSIA;
        }
        if (s.equalsIgnoreCase("GRAY")) {
            return Color.GRAY;
        }
        if (s.equalsIgnoreCase("GREEN")) {
            return Color.GREEN;
        }
        if (s.equalsIgnoreCase("LIME")) {
            return Color.LIME;
        }
        if (s.equalsIgnoreCase("MAROON")) {
            return Color.MAROON;
        }
        if (s.equalsIgnoreCase("NAVY")) {
            return Color.NAVY;
        }
        if (s.equalsIgnoreCase("OLIVE")) {
            return Color.OLIVE;
        }
        if (s.equalsIgnoreCase("ORANGE")) {
            return Color.ORANGE;
        }
        if (s.equalsIgnoreCase("PURPLE")) {
            return Color.PURPLE;
        }
        if (s.equalsIgnoreCase("RED")) {
            return Color.RED;
        }
        if (s.equalsIgnoreCase("SILVER")) {
            return Color.SILVER;
        }
        if (s.equalsIgnoreCase("TEAL")) {
            return Color.TEAL;
        }
        if (s.equalsIgnoreCase("WHITE")) {
            return Color.WHITE;
        }
        if (s.equalsIgnoreCase("YELLOW")) {
            return Color.YELLOW;
        }
        return null;
    }

    public static boolean isEnchanted(final ItemStack itemStack) {
        return itemStack.containsEnchantment(Enchantment.ARROW_DAMAGE) || itemStack.containsEnchantment(Enchantment.ARROW_DAMAGE) || itemStack.containsEnchantment(Enchantment.ARROW_FIRE) || itemStack.containsEnchantment(Enchantment.ARROW_INFINITE) || itemStack.containsEnchantment(Enchantment.ARROW_KNOCKBACK) || itemStack.containsEnchantment(Enchantment.DAMAGE_ALL) || itemStack.containsEnchantment(Enchantment.DAMAGE_ARTHROPODS) || itemStack.containsEnchantment(Enchantment.DAMAGE_UNDEAD) || itemStack.containsEnchantment(Enchantment.DIG_SPEED) || itemStack.containsEnchantment(Enchantment.DURABILITY) || itemStack.containsEnchantment(Enchantment.FIRE_ASPECT) || itemStack.containsEnchantment(Enchantment.KNOCKBACK) || itemStack.containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS) || itemStack.containsEnchantment(Enchantment.LOOT_BONUS_MOBS) || itemStack.containsEnchantment(Enchantment.LUCK) || itemStack.containsEnchantment(Enchantment.LURE) || itemStack.containsEnchantment(Enchantment.OXYGEN) || itemStack.containsEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL) || itemStack.containsEnchantment(Enchantment.PROTECTION_EXPLOSIONS) || itemStack.containsEnchantment(Enchantment.PROTECTION_FALL) || itemStack.containsEnchantment(Enchantment.PROTECTION_FIRE) || itemStack.containsEnchantment(Enchantment.PROTECTION_PROJECTILE) || itemStack.containsEnchantment(Enchantment.SILK_TOUCH) || itemStack.containsEnchantment(Enchantment.THORNS) || itemStack.containsEnchantment(Enchantment.WATER_WORKER);
    }

    public static ItemStack name(final ItemStack itemStack, final String s, final String... array) {
        final ItemMeta itemMeta = itemStack.getItemMeta();
        if (!s.isEmpty()) {
            itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', s));
        }
        if (array.length > 0) {
            final ArrayList<String> lore = new ArrayList<String>(array.length);
            for (int length = array.length, i = 0; i < length; ++i) {
                lore.add(ChatColor.translateAlternateColorCodes('&', array[i]));
            }
            itemMeta.setLore((List)lore);
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static int countTotalItens(final Player player) {
        return countTotalItens((Inventory)player.getInventory());
    }

    public static int countTotalItens(final Inventory inventory) {
        int n = 0;
        final ItemStack[] contents = inventory.getContents();
        for (int length = contents.length, i = 0; i < length; ++i) {
            if (contents[i] != null) {
                ++n;
            }
        }
        return n;
    }

    public static int getEmptySlots(final Player player) {
        return getEmptySlots((Inventory)player.getInventory());
    }

    public static int getEmptySlots(final Inventory inventory) {
        int n = 0;
        final ItemStack[] contents = inventory.getContents();
        for (int length = contents.length, i = 0; i < length; ++i) {
            if (contents[i] == null) {
                ++n;
            }
        }
        return n;
    }
}
