package net.royalmind.library.utilities.serialization;

import org.bukkit.*;

public class Util
{
    public static boolean isNum(final String s) {
        try {
            Integer.parseInt(s);
            return true;
        }
        catch (NumberFormatException ex) {
            return false;
        }
    }

    public static boolean isLeatherArmor(final Material material) {
        return material == Material.LEATHER_HELMET || material == Material.LEATHER_CHESTPLATE || material == Material.LEATHER_LEGGINGS || material == Material.LEATHER_BOOTS;
    }

    public static boolean keyFound(final String[] array, final String s) {
        for (int length = array.length, i = 0; i < length; ++i) {
            if (array[i].equalsIgnoreCase(s)) {}
        }
        return false;
    }
}
