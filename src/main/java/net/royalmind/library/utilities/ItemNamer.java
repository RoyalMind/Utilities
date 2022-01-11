package net.royalmind.library.utilities;

import org.bukkit.*;
import java.util.*;
import org.bukkit.inventory.meta.*;
import org.bukkit.inventory.*;

public class ItemNamer
{
    private List<String> lore;
    private String name;
    private Material material;

    public ItemNamer() {
        this.lore = new ArrayList<String>();
        this.name = null;
        this.material = Material.AIR;
    }

    public void addLore(final String s) {
        this.lore.add(ChatColor.translateAlternateColorCodes('&', "&r" + s));
    }

    public void addLore(final String s, final Object... array) {
        this.lore.add(ChatColor.translateAlternateColorCodes('&', "&r" + String.format(s, array)));
    }

    public void addLore(final List<String> list) {
        final Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            this.lore.add(ChatColor.translateAlternateColorCodes('&', "&r" + iterator.next()));
        }
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setNameOriginal(final String name) {
        this.name = name;
    }

    public void setMaterial(final Material material) {
        this.material = material;
    }

    public List<String> getLore() {
        return this.lore;
    }

    public ItemStack setOwnerHead(final String owner, final ItemStack itemStack) {
        final SkullMeta itemMeta = (SkullMeta)itemStack.getItemMeta();
        itemMeta.setOwner(owner);
        itemStack.setItemMeta((ItemMeta)itemMeta);
        return itemStack;
    }

    public String getName() {
        return this.name;
    }

    public Material getMaterial() {
        return this.material;
    }

    public ItemStack setLore(final ItemStack itemStack) {
        final ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore((List)this.getLore());
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public ItemStack setDisplayName(final ItemStack itemStack) {
        final ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(this.getName());
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public ItemStack getItem(final ItemStack itemStack) {
        if (this.material != Material.AIR) {
            itemStack.setType(this.getMaterial());
        }
        final ItemMeta itemMeta = itemStack.getItemMeta();
        if (this.name != null) {
            itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', this.getName()));
        }
        if (this.lore.size() > 0) {
            itemMeta.setLore((List)this.getLore());
        }
        itemMeta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public ItemStack getItem() {
        return this.getItem(new ItemStack(Material.AIR));
    }
}
