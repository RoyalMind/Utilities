package net.royalmind.library.utilities.serialization;

import java.util.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;
import org.bukkit.enchantments.*;
import net.royalmind.library.utilities.json.*;

public class BookSerialization
{
    public static BookMeta getBookMeta(final String s) {
        try {
            return getBookMeta(new JSONObject(s));
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static BookMeta getBookMeta(final JSONObject jsonObject) {
        try {
            final BookMeta bookMeta = (BookMeta)new ItemStack(Material.WRITTEN_BOOK, 1).getItemMeta();
            String string = null;
            String string2 = null;
            JSONArray jsonArray = null;
            if (jsonObject.has("title")) {
                string = jsonObject.getString("title");
            }
            if (jsonObject.has("author")) {
                string2 = jsonObject.getString("author");
            }
            if (jsonObject.has("pages")) {
                jsonArray = jsonObject.getJSONArray("pages");
            }
            if (string != null) {
                bookMeta.setTitle(string);
            }
            if (string2 != null) {
                bookMeta.setAuthor(string2);
            }
            if (jsonArray != null) {
                final String[] pages = new String[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); ++i) {
                    String string3 = jsonArray.getString(i);
                    if (string3.isEmpty() || string3 == null) {
                        string3 = "";
                    }
                    pages[i] = string3;
                }
                bookMeta.setPages(pages);
            }
            return bookMeta;
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static JSONObject serializeBookMeta(final BookMeta bookMeta) {
        try {
            final JSONObject jsonObject = new JSONObject();
            if (bookMeta.hasTitle()) {
                jsonObject.put("title", bookMeta.getTitle());
            }
            if (bookMeta.hasAuthor()) {
                jsonObject.put("author", bookMeta.getAuthor());
            }
            if (bookMeta.hasPages()) {
                jsonObject.put("pages", bookMeta.getPages().toArray(new String[0]));
            }
            return jsonObject;
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static String serializeBookMetaAsString(final BookMeta bookMeta) {
        return serializeBookMetaAsString(bookMeta, false);
    }

    public static String serializeBookMetaAsString(final BookMeta bookMeta, final boolean b) {
        return serializeBookMetaAsString(bookMeta, b, 5);
    }

    public static String serializeBookMetaAsString(final BookMeta bookMeta, final boolean b, final int n) {
        try {
            if (b) {
                return serializeBookMeta(bookMeta).toString(n);
            }
            return serializeBookMeta(bookMeta).toString();
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static EnchantmentStorageMeta getEnchantedBookMeta(final String s) {
        try {
            return getEnchantedBookMeta(new JSONObject(s));
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static EnchantmentStorageMeta getEnchantedBookMeta(final JSONObject jsonObject) {
        try {
            final EnchantmentStorageMeta enchantmentStorageMeta = (EnchantmentStorageMeta)new ItemStack(Material.ENCHANTED_BOOK, 1).getItemMeta();
            if (jsonObject.has("enchantments")) {
                final Map<Enchantment, Integer> enchantments = EnchantmentSerialization.getEnchantments(jsonObject.getString("enchantments"));
                for (final Enchantment enchantment : enchantments.keySet()) {
                    enchantmentStorageMeta.addStoredEnchant(enchantment, (int)enchantments.get(enchantment), true);
                }
            }
            return enchantmentStorageMeta;
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static JSONObject serializeEnchantedBookMeta(final EnchantmentStorageMeta enchantmentStorageMeta) {
        try {
            final JSONObject jsonObject = new JSONObject();
            jsonObject.put("enchantments", EnchantmentSerialization.serializeEnchantments(enchantmentStorageMeta.getStoredEnchants()));
            return jsonObject;
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static String serializeEnchantedBookMetaAsString(final EnchantmentStorageMeta enchantmentStorageMeta) {
        return serializeEnchantedBookMetaAsString(enchantmentStorageMeta, false);
    }

    public static String serializeEnchantedBookMetaAsString(final EnchantmentStorageMeta enchantmentStorageMeta, final boolean b) {
        return serializeEnchantedBookMetaAsString(enchantmentStorageMeta, b, 5);
    }

    public static String serializeEnchantedBookMetaAsString(final EnchantmentStorageMeta enchantmentStorageMeta, final boolean b, final int n) {
        try {
            if (b) {
                return serializeEnchantedBookMeta(enchantmentStorageMeta).toString(n);
            }
            return serializeEnchantedBookMeta(enchantmentStorageMeta).toString();
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
