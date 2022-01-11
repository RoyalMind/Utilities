package net.royalmind.library.utilities.serialization;

import org.bukkit.inventory.*;
import org.bukkit.*;
import org.bukkit.enchantments.*;
import org.bukkit.inventory.meta.*;
import java.util.*;
import net.royalmind.library.utilities.json.*;

public class SingleItemSerialization
{
    public static JSONObject serializeItemInInventory(final ItemStack itemStack, final int n) {
        return serializeItems(itemStack, true, n);
    }

    public static JSONObject serializeItem(final ItemStack itemStack) {
        return serializeItems(itemStack, false, 0);
    }

    private static JSONObject serializeItems(final ItemStack itemStack, final boolean b, final int n) {
        try {
            final JSONObject jsonObject = new JSONObject();
            if (itemStack == null) {
                return null;
            }
            final int typeId = itemStack.getType().getId();
            final int amount = itemStack.getAmount();
            final short durability = itemStack.getDurability();
            final boolean hasItemMeta = itemStack.hasItemMeta();
            Object displayName = null;
            Object serializeEnchantments = null;
            Object o = null;
            final Material type = itemStack.getType();
            JSONObject jsonObject2 = null;
            JSONObject serializeArmor = null;
            JSONObject serializeSkull = null;
            JSONObject serializeFireworkMeta = null;
            if (type == Material.LEGACY_BOOK_AND_QUILL || type == Material.WRITTEN_BOOK) {
                jsonObject2 = BookSerialization.serializeBookMeta((BookMeta)itemStack.getItemMeta());
            }
            else if (type == Material.ENCHANTED_BOOK) {
                jsonObject2 = BookSerialization.serializeEnchantedBookMeta((EnchantmentStorageMeta)itemStack.getItemMeta());
            }
            else if (Util.isLeatherArmor(type)) {
                serializeArmor = LeatherArmorSerialization.serializeArmor((LeatherArmorMeta)itemStack.getItemMeta());
            }
            else if (type == Material.LEGACY_SKULL_ITEM) {
                serializeSkull = SkullSerialization.serializeSkull((SkullMeta)itemStack.getItemMeta());
            }
            else if (type == Material.LEGACY_FIREWORK) {
                serializeFireworkMeta = FireworkSerialization.serializeFireworkMeta((FireworkMeta)itemStack.getItemMeta());
            }
            if (hasItemMeta) {
                final ItemMeta itemMeta = itemStack.getItemMeta();
                if (itemMeta.hasDisplayName()) {
                    displayName = itemMeta.getDisplayName();
                }
                if (itemMeta.hasLore()) {
                    o = itemMeta.getLore().toArray(new String[0]);
                }
                if (itemMeta.hasEnchants()) {
                    serializeEnchantments = EnchantmentSerialization.serializeEnchantments(itemMeta.getEnchants());
                }
            }
            jsonObject.put("id", typeId);
            jsonObject.put("amount", amount);
            jsonObject.put("data", durability);
            if (b) {
                jsonObject.put("index", n);
            }
            if (displayName != null) {
                jsonObject.put("name", displayName);
            }
            if (serializeEnchantments != null) {
                jsonObject.put("enchantments", serializeEnchantments);
            }
            if (o != null) {
                jsonObject.put("lore", o);
            }
            if (jsonObject2 != null && jsonObject2.length() > 0) {
                jsonObject.put("book-meta", jsonObject2);
            }
            if (serializeArmor != null && serializeArmor.length() > 0) {
                jsonObject.put("armor-meta", serializeArmor);
            }
            if (serializeSkull != null && serializeSkull.length() > 0) {
                jsonObject.put("skull-meta", serializeSkull);
            }
            if (serializeFireworkMeta != null && serializeFireworkMeta.length() > 0) {
                jsonObject.put("firework-meta", serializeFireworkMeta);
            }
            return jsonObject;
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static ItemStack getItem(final String s) {
        return getItem(s, 0);
    }

    public static ItemStack getItem(final String s, final int n) {
        try {
            return getItem(new JSONObject(s), n);
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static ItemStack getItem(final JSONObject jsonObject) {
        return getItem(jsonObject, 0);
    }

    public static ItemStack getItem(final JSONObject jsonObject, final int n) {
        try {
            final String int1 = jsonObject.getString("id");
            final int optInt = jsonObject.optInt("amount", 1);
            final int optInt2 = jsonObject.optInt("data", 0);
//            final String name = jsonObject.getString("name");
//            final int amount = jsonObject.optInt("amount", 1);
//            final int data =  jsonObject.optInt("data", 0);

            String string = null;
            Map<Enchantment, Integer> enchantments = null;
            ArrayList<String> lore = null;
            if (jsonObject.has("name")) {
                string = jsonObject.getString("name");
            }
            if (jsonObject.has("enchantments")) {
                enchantments = EnchantmentSerialization.getEnchantments(jsonObject.getString("enchantments"));
            }
            if (jsonObject.has("lore")) {
                final JSONArray jsonArray = jsonObject.getJSONArray("lore");
                lore = new ArrayList<String>();
                for (int i = 0; i < jsonArray.length(); ++i) {
                    lore.add(jsonArray.getString(i));
                }
            }
            if (Material.getMaterial(int1) == null) {
                throw new IllegalArgumentException("Item " + n + " - No Material found with id of " + int1);
            }
            final Material material = Material.getMaterial(int1);
            final ItemStack itemStack = new ItemStack(material, optInt, (short)optInt2);
            if ((material == Material.LEGACY_BOOK_AND_QUILL || material == Material.WRITTEN_BOOK) && jsonObject.has("book-meta")) {
                itemStack.setItemMeta((ItemMeta)BookSerialization.getBookMeta(jsonObject.getJSONObject("book-meta")));
            }
            else if (material == Material.ENCHANTED_BOOK && jsonObject.has("book-meta")) {
                itemStack.setItemMeta((ItemMeta)BookSerialization.getEnchantedBookMeta(jsonObject.getJSONObject("book-meta")));
            }
            else if (Util.isLeatherArmor(material) && jsonObject.has("armor-meta")) {
                itemStack.setItemMeta((ItemMeta)LeatherArmorSerialization.getLeatherArmorMeta(jsonObject.getJSONObject("armor-meta")));
            }
            else if (material == Material.LEGACY_SKULL_ITEM && jsonObject.has("skull-meta")) {
                itemStack.setItemMeta((ItemMeta)SkullSerialization.getSkullMeta(jsonObject.getJSONObject("skull-meta")));
            }
            else if (material == Material.LEGACY_FIREWORK && jsonObject.has("firework-meta")) {
                itemStack.setItemMeta((ItemMeta)FireworkSerialization.getFireworkMeta(jsonObject.getJSONObject("firework-meta")));
            }
            final ItemMeta itemMeta = itemStack.getItemMeta();
            if (string != null) {
                itemMeta.setDisplayName(string);
            }
            if (lore != null) {
                itemMeta.setLore((List)lore);
            }
            itemStack.setItemMeta(itemMeta);
            if (enchantments != null) {
                itemStack.addUnsafeEnchantments((Map)enchantments);
            }
            return itemStack;
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static String serializeItemInInventoryAsString(final ItemStack itemStack, final int n) {
        return serializeItemInInventoryAsString(itemStack, n, false);
    }

    public static String serializeItemInInventoryAsString(final ItemStack itemStack, final int n, final boolean b) {
        return serializeItemInInventoryAsString(itemStack, n, b, 5);
    }

    public static String serializeItemInInventoryAsString(final ItemStack itemStack, final int n, final boolean b, final int n2) {
        try {
            if (b) {
                return serializeItemInInventory(itemStack, n).toString(n2);
            }
            return serializeItemInInventory(itemStack, n).toString();
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static String serializeItemAsString(final ItemStack itemStack) {
        return serializeItemAsString(itemStack, false);
    }

    public static String serializeItemAsString(final ItemStack itemStack, final boolean b) {
        return serializeItemAsString(itemStack, b, 5);
    }

    public static String serializeItemAsString(final ItemStack itemStack, final boolean b, final int n) {
        try {
            if (b) {
                return serializeItem(itemStack).toString(n);
            }
            return serializeItem(itemStack).toString();
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
