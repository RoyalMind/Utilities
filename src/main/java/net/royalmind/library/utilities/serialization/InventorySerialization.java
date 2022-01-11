package net.royalmind.library.utilities.serialization;

import org.bukkit.*;
import org.bukkit.inventory.*;
import java.util.*;
import java.io.*;
import org.bukkit.entity.*;
import net.royalmind.library.utilities.json.*;

public class InventorySerialization
{
    public static JSONArray serializeInventory(final Inventory inventory) {
        final JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < inventory.getSize(); ++i) {
            final JSONObject serializeItemInInventory = SingleItemSerialization.serializeItemInInventory(inventory.getItem(i), i);
            if (serializeItemInInventory != null) {
                jsonArray.put(serializeItemInInventory);
            }
        }
        return jsonArray;
    }

    public static JSONObject serializePlayerInventory(final PlayerInventory playerInventory) {
        try {
            final JSONObject jsonObject = new JSONObject();
            final JSONArray serializeInventory = serializeInventory((Inventory)playerInventory);
            final JSONArray serializeInventory2 = serializeInventory(playerInventory.getArmorContents());
            jsonObject.put("inventory", serializeInventory);
            jsonObject.put("armor", serializeInventory2);
            return jsonObject;
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static String serializePlayerInventoryAsString(final PlayerInventory playerInventory) {
        return serializePlayerInventoryAsString(playerInventory, false);
    }

    public static String serializePlayerInventoryAsString(final PlayerInventory playerInventory, final boolean b) {
        return serializePlayerInventoryAsString(playerInventory, b, 5);
    }

    public static String serializePlayerInventoryAsString(final PlayerInventory playerInventory, final boolean b, final int n) {
        try {
            if (b) {
                return serializePlayerInventory(playerInventory).toString(n);
            }
            return serializePlayerInventory(playerInventory).toString();
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static String serializeInventoryAsString(final Inventory inventory) {
        return serializeInventoryAsString(inventory, false);
    }

    public static String serializeInventoryAsString(final Inventory inventory, final boolean b) {
        return serializeInventoryAsString(inventory, b, 5);
    }

    public static String serializeInventoryAsString(final Inventory inventory, final boolean b, final int n) {
        try {
            if (b) {
                return serializeInventory(inventory).toString(n);
            }
            return serializeInventory(inventory).toString();
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static String serializeInventoryAsString(final ItemStack[] array) {
        return serializeInventoryAsString(array, false);
    }

    public static String serializeInventoryAsString(final ItemStack[] array, final boolean b) {
        return serializeInventoryAsString(array, b, 5);
    }

    public static String serializeInventoryAsString(final ItemStack[] array, final boolean b, final int n) {
        try {
            if (b) {
                return serializeInventory(array).toString(n);
            }
            return serializeInventory(array).toString();
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static JSONArray serializeInventory(final ItemStack[] array) {
        final JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < array.length; ++i) {
            final JSONObject serializeItemInInventory = SingleItemSerialization.serializeItemInInventory(array[i], i);
            if (serializeItemInInventory != null) {
                jsonArray.put(serializeItemInInventory);
            }
        }
        return jsonArray;
    }

    public static Inventory getInventory(final String s) {
        final Inventory inventory = Bukkit.createInventory((InventoryHolder)null, 54);
        inventory.setContents(getInventory(s, 54));
        return inventory;
    }

    public static ItemStack[] getInventory(final String s, final int n) {
        try {
            return getInventory(new JSONArray(s), n);
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static ItemStack[] getInventory(final JSONArray jsonArray, final int n) {
        try {
            final ItemStack[] array = new ItemStack[n];
            for (int i = 0; i < jsonArray.length(); ++i) {
                final JSONObject jsonObject = jsonArray.getJSONObject(i);
                final int int1 = jsonObject.getInt("index");
                if (int1 > n) {
                    throw new IllegalArgumentException("index found is greator than expected size (" + int1 + ">" + n + ")");
                }
                if (int1 > array.length || int1 < 0) {
                    throw new IllegalArgumentException("Item " + i + " - Slot " + int1 + " does not exist in this inventory");
                }
                array[int1] = SingleItemSerialization.getItem(jsonObject);
            }
            return array;
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static ItemStack[] getInventory(final File file, final int n) {
        String string = "";
        try {
            final Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                string = string + scanner.nextLine() + "\n";
            }
            scanner.close();
            return getInventory(string, n);
        }
        catch (FileNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static void setInventory(final InventoryHolder inventoryHolder, final String s) {
        setInventory(inventoryHolder.getInventory(), s);
    }

    public static void setInventory(final InventoryHolder inventoryHolder, final JSONArray jsonArray) {
        setInventory(inventoryHolder.getInventory(), jsonArray);
    }

    public static void setInventory(final Inventory inventory, final String s) {
        try {
            setInventory(inventory, new JSONArray(s));
        }
        catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    public static void setInventory(final Inventory inventory, final JSONArray jsonArray) {
        final ItemStack[] inventory2 = getInventory(jsonArray, inventory.getSize());
        inventory.clear();
        for (int i = 0; i < inventory2.length; ++i) {
            final ItemStack itemStack = inventory2[i];
            if (itemStack != null) {
                inventory.setItem(i, itemStack);
            }
        }
    }

    public static void setPlayerInventory(final Player player, final String s) {
        try {
            setPlayerInventory(player, new JSONObject(s));
        }
        catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    public static void setPlayerInventory(final Player player, final JSONObject jsonObject) {
        try {
            final PlayerInventory inventory = player.getInventory();
            final ItemStack[] inventory2 = getInventory(jsonObject.getJSONArray("armor"), 4);
            inventory.clear();
            inventory.setArmorContents(inventory2);
            setInventory((InventoryHolder)player, jsonObject.getJSONArray("inventory"));
        }
        catch (JSONException ex) {
            ex.printStackTrace();
        }
    }
}
