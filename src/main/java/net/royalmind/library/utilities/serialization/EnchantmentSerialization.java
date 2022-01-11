package net.royalmind.library.utilities.serialization;

import org.bukkit.enchantments.*;
import java.util.*;
import org.bukkit.inventory.*;

public class EnchantmentSerialization
{
    public static String serializeEnchantments(final Map<Enchantment, Integer> map) {
        String string = "";
        for (final Enchantment enchantment : map.keySet()) {
            string = string + enchantment.getName() + ":" + map.get(enchantment) + ";";
        }
        return string;
    }

    public static Map<Enchantment, Integer> getEnchantments(final String s) {
        final HashMap<Enchantment, Integer> hashMap = new HashMap<Enchantment, Integer>();
        if (s.isEmpty()) {
            return hashMap;
        }
        final String[] split = s.split(";");
        for (int i = 0; i < split.length; ++i) {
            final String[] split2 = split[i].split(":");
            if (split2.length < 2) {
                throw new IllegalArgumentException(s + " - Enchantment " + i + " (" + split[i] + "): split must at least have a length of 2");
            }
            if (!Util.isNum(split2[0])) {
                throw new IllegalArgumentException(s + " - Enchantment " + i + " (" + split[i] + "): id is not an integer");
            }
            if (!Util.isNum(split2[1])) {
                throw new IllegalArgumentException(s + " - Enchantment " + i + " (" + split[i] + "): level is not an integer");
            }
            final int int1 = Integer.parseInt(split2[0]);
            final int int2 = Integer.parseInt(split2[1]);
            final Enchantment byId = Enchantment.getByName(int1+" ");   //Enchantment.getById(int1);
            if (byId == null) {
                throw new IllegalArgumentException(s + " - Enchantment " + i + " (" + split[i] + "): no Enchantment with id of " + int1);
            }
            hashMap.put(byId, int2);
        }
        return hashMap;
    }

//    public static Map<Enchantment, Integer> getEnchantsFromOldFormat(final String s) {
//        final HashMap<Enchantment, Integer> hashMap = new HashMap<Enchantment, Integer>();
//        if (s.length() == 0) {
//            return hashMap;
//        }
//        final String string = Long.parseLong(s, 32) + "";
//        System.out.println(string);
//        for (int i = 0; i < string.length(); i += 3) {
//            hashMap.put(Enchantment.getById(Integer.parseInt(string.substring(i, i + 2))), Integer.parseInt(string.charAt(i + 2) + ""));
//        }
//        return hashMap;
//    }

//    public static String convert(final String s) {
//        return serializeEnchantments(getEnchantsFromOldFormat(s));
//    }

//    public static Map<Enchantment, Integer> convertAndGetEnchantments(final String s) {
//        return getEnchantments(convert(s));
//    }

    public static void addEnchantments(final String s, final ItemStack itemStack) {
        itemStack.addUnsafeEnchantments((Map)getEnchantments(s));
    }
}
