package nl.openminetopia.utils;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.*;
import java.util.List;

/**
 * Easily create itemstacks, without messing your hands. <i>Note that if you do
 * use this in one of your projects, leave this notice.</i> <i>Please do credit
 * me if you do use this in one of your projects.</i>
 *
 * @author NonameSL
 */
public class ItemBuilder {
    private ItemStack is;

    /**
     * Create a new ItemBuilder from scratch.
     *
     * @param m The material to create the ItemBuilder with.
     */
    public ItemBuilder(Material m) {
        this(m, 1);
    }

    /**
     * Create a new ItemBuilder over an existing itemstack.
     *
     * @param is The itemstack to create the ItemBuilder over.
     */
    public ItemBuilder(ItemStack is) {
        this.is = is;
    }

    /**
     * Create a new ItemBuilder from scratch.
     *
     * @param m      The material of the item.
     * @param amount The amount of the item.
     */
    public ItemBuilder(Material m, int amount) {
        is = new ItemStack(m, amount);
    }

    /**
     * Create a new ItemBuilder from scratch.
     *
     * @param m          The material of the item.
     * @param amount     The amount of the item.
     * @param durability The tier of the item.
     */
    public ItemBuilder(Material m, int amount, byte durability) {
        is = new ItemStack(m, amount, durability);
    }

    /**
     * Clone the ItemBuilder into a new one.
     *
     * @return The cloned instance.
     */
    public ItemBuilder clone() {
        return new ItemBuilder(is);
    }

    public ItemBuilder setNBT(String key, String value) {
        is = NBTEditor.set(is, value, key);
        return this;
    }

    public ItemBuilder makeUnbreakable(boolean unbreakable) {
        is = NBTEditor.set(is, unbreakable ? (byte) 1 : (byte) 0, "Unbreakable");
        return this;
    }

    /**
     * Change the durability of the item.
     *
     * @param dur The durability to set it to.
     */
    public ItemBuilder setDurability(short dur) {
        ItemMeta im = is.getItemMeta();
        Damageable damageable = (Damageable) im;
        damageable.setDamage(dur);
        return this;
    }

    /**
     * Set the displayname of the item.
     *
     * @param name The name to change it to.
     */
    public ItemBuilder setName(Component name) {
        ItemMeta im = is.getItemMeta();
        im.displayName(name);
        is.setItemMeta(im);
        return this;
    }

    /**
     * Sets the displayname of the item and changes colors
     *
     * @param name new name
     */
    public ItemBuilder setColoredName(String name) {
        return setName(MessageUtils.format(name));
    }

    /**
     * Add an unsafe enchantment.
     *
     * @param ench  The enchantment to add.
     * @param level The level to put the enchant on.
     */
    public ItemBuilder addUnsafeEnchantment(Enchantment ench, int level) {
        is.addUnsafeEnchantment(ench, level);
        return this;
    }

    /**
     * Remove a certain enchant from the item.
     *
     * @param ench The enchantment to remove
     */
    public ItemBuilder removeEnchantment(Enchantment ench) {
        is.removeEnchantment(ench);
        return this;
    }

    /**
     * Add an enchant to the item.
     *
     * @param ench  The enchant to add
     * @param level The level
     */
    public ItemBuilder addEnchant(Enchantment ench, int level) {
        ItemMeta im = is.getItemMeta();
        im.addEnchant(ench, level, true);
        is.setItemMeta(im);
        return this;
    }

    /**
     * Add multiple enchants at once.
     *
     * @param enchantments The enchants to add.
     */
    public ItemBuilder addEnchantments(Map<Enchantment, Integer> enchantments) {
        is.addEnchantments(enchantments);
        return this;
    }

    /**
     * Sets infinity durability on the item by setting the durability to
     * Short.MAX_VALUE.
     */
    public ItemBuilder setInfinityDurability() {
        ItemMeta im = is.getItemMeta();
        Damageable damageable = (Damageable) im;
        damageable.setDamage(Short.MAX_VALUE);
        return this;
    }

    /**
     * Re-sets the lore.
     *
     * @param lore The lore to set it to.
     */
    public ItemBuilder setLore(Component... lore) {
        ItemMeta im = is.getItemMeta();
        im.lore(Arrays.asList(lore));
        is.setItemMeta(im);
        return this;
    }

    /**
     * Re-sets the lore.
     *
     * @param lore The lore to set it to.
     */
    public ItemBuilder setLore(List<Component> lore) {
        ItemMeta im = is.getItemMeta();
        im.lore(lore);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder removeLoreLine(Component line) {
        ItemMeta im = is.getItemMeta();
        List<Component> lore = new ArrayList<>(im.lore());
        if (!lore.contains(line))
            return this;
        lore.remove(line);
        im.lore(lore);
        is.setItemMeta(im);
        return this;
    }

    /**
     * Remove a lore line.
     *
     * @param index The index of the lore line to remove.
     */
    public ItemBuilder removeLoreLine(int index) {
        ItemMeta im = is.getItemMeta();
        List<Component> lore = new ArrayList<>(im.lore());
        if (index < 0 || index > lore.size())
            return this;
        lore.remove(index);
        im.lore(lore);
        is.setItemMeta(im);
        return this;
    }

    /**
     * Add a lore line.
     *
     * @param line The lore line to add.
     */
    public ItemBuilder addLoreLine(String line) {
        ItemMeta im = is.getItemMeta();
        List<Component> lore = new ArrayList<>();
        if (im.hasLore()) lore = new ArrayList<>(im.lore());
        lore.add(MessageUtils.format(line));
        im.lore(lore);
        is.setItemMeta(im);
        return this;
    }

    /**
     * Add a lore line.
     *
     * @param line The lore line to add.
     * @param pos  The index of where to put it.
     */
    public ItemBuilder addLoreLine(String line, int pos) {
        ItemMeta im = is.getItemMeta();
        List<Component> lore = new ArrayList<>(im.lore());
        lore.set(pos, MessageUtils.format(line));
        im.lore(lore);
        is.setItemMeta(im);
        return this;
    }

    /**
     * Sets the armor color of a leather armor piece. Works only on leather armor
     * pieces.
     *
     * @param color The color to set it to.
     */
    public ItemBuilder setLeatherArmorColor(Color color) {
        try {
            LeatherArmorMeta im = (LeatherArmorMeta) is.getItemMeta();
            im.setColor(color);
            is.setItemMeta(im);
        } catch (ClassCastException expected) {
        }
        return this;
    }

    /**
     * Set the owner of a skull.
     *
     * @param owner The owner of the desired skull.
     */
    public ItemBuilder setSkullOwner(Player owner) {
        SkullMeta im = (SkullMeta) is.getItemMeta();
        im.setOwningPlayer(owner);
        is.setItemMeta(im);
        return this;
    }

    /**
     * Set the owner of a skull.
     *
     * @param owner The owner of the desired skull.
     */
    public ItemBuilder setSkullOwner(OfflinePlayer owner) {
        SkullMeta im = (SkullMeta) is.getItemMeta();
        im.setOwningPlayer(owner);
        is.setItemMeta(im);
        return this;
    }

    /**
     * Set the owner of a skull.
     *
     * @param owner The owner of the desired skull.
     */
    public ItemBuilder setSkullOwner(String owner) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(owner);
        SkullMeta im = (SkullMeta) is.getItemMeta();
        im.setOwningPlayer(offlinePlayer);
        is.setItemMeta(im);
        return this;
    }

    /**
     * Set the flags of an item.
     *
     * @param itemFlag The itemFlag you want to add.
     */
    public ItemBuilder setItemFlag(ItemFlag itemFlag) {
        ItemMeta im = is.getItemMeta();
        im.addItemFlags(itemFlag);
        is.setItemMeta(im);
        return this;
    }

    /**
     * Set the flags of an item.
     *
     * @param itemFlag The itemFlag you want to add.
     */
    public ItemBuilder setItemFlag(ItemFlag[] itemFlag) {
        ItemMeta im = is.getItemMeta();
        im.addItemFlags(itemFlag);
        is.setItemMeta(im);
        return this;
    }

    /**
     * Set the Custom Model Data of an item.
     *
     * @param data The custom model data value.
     */
    public ItemBuilder setCustomModelData(Integer data) {
        ItemMeta im = is.getItemMeta();
        im.setCustomModelData(data);
        is.setItemMeta(im);
        return this;
    }

    /**
     * Retrieves the itemstack from the ItemBuilder.
     *
     * @return The itemstack created/modified by the ItemBuilder instance.
     */
    public ItemStack toItemStack() {
        return is;
    }
}