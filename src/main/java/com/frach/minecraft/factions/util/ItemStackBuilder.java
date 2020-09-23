package com.frach.minecraft.factions.util;

import java.io.*;
import org.bukkit.inventory.meta.*;
import org.bukkit.enchantments.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import java.util.*;

public class ItemStackBuilder implements Serializable {

    private ItemStack item;
    private ItemMeta meta;
    private EnchantmentStorageMeta storage;
    private List<String> lore;
    private boolean glow;
    private SkullMeta skullMeta;

    public ItemStackBuilder(final ItemStack item) {
        this.glow = false;
        this.item = item;
        if (item.getType() == Material.ENCHANTED_BOOK) {
            this.storage = (EnchantmentStorageMeta)item.getItemMeta();
            this.lore = ((this.storage != null && this.storage.hasLore()) ? this.storage.getLore() : new ArrayList<>());
        }
        else if (item.getType() == Material.PLAYER_HEAD) {
            this.skullMeta = (SkullMeta)item.getItemMeta();
            this.lore = ((this.skullMeta != null && this.skullMeta.hasLore()) ? this.skullMeta.getLore() : new ArrayList<>());
        }
        else {
            this.meta = item.getItemMeta();
            this.lore = ((this.meta != null && this.meta.hasLore()) ? this.meta.getLore() : new ArrayList<>());
        }
    }
    
    public ItemStackBuilder(final Material material) {
        this(new ItemStack(material));
    }
    
    public ItemStackBuilder setLore(final List<String> lore) {
        this.lore = lore;
        return this;
    }
    
    public ItemStackBuilder setType(final Material type) {
        this.item.setType(type);
        return this;
    }
    
    public ItemStackBuilder setOwner(final String owner) {
        if (this.item.getType() == Material.PLAYER_HEAD) {
            this.skullMeta.setOwner(owner);
            return this;
        }
        return this;
    }
    
    public ItemStackBuilder setName(final String name) {
        if (this.item.getType() == Material.ENCHANTED_BOOK) {
            this.storage.setDisplayName(name);
            return this;
        }
        if (this.item.getType() == Material.PLAYER_HEAD) {
            this.skullMeta.setDisplayName(name);
            return this;
        }
        this.meta.setDisplayName(name);
        return this;
    }
    
    public ItemStackBuilder addLore(final String... l) {
        for (final String x : l) {
            this.lore.add(x);
        }
        return this;
    }
    
    public ItemStackBuilder addLoreList(final List<String> l) {
        for (final String s : l) {
            this.lore.add(s.replace("&", "\ufffd"));
        }
        return this;
    }

    public ItemStackBuilder addStoredEnchantment(final Enchantment e, final int level) {
        this.storage.addStoredEnchant(e, level, true);
        return this;
    }
    
    public ItemStackBuilder addEnchantment(final Enchantment e, final int level) {
        this.meta.addEnchant(e, level, true);
        return this;
    }

    public ItemStackBuilder addEnchantments(HashMap<Enchantment, Integer> enchants) {
        enchants.forEach((this::addEnchantment));

        return this;
    }

    public ItemStackBuilder setDurability(final int durability) {
        this.item.setDurability((short)durability);
        return this;
    }
    
    public ItemStackBuilder setAmount(final int amount) {
        this.item.setAmount(amount);
        return this;
    }
    
    public ItemStackBuilder clearLore() {
        this.lore = new ArrayList<String>();
        return this;
    }
    
    public ItemStackBuilder replaceLore(final String oldLore, final String newLore) {
        for (int i = 0; i < this.lore.size(); ++i) {
            if (this.lore.get(i).contains(oldLore)) {
                this.lore.remove(i);
                this.lore.add(i, newLore);
                break;
            }
        }
        return this;
    }
    
    public ItemStack build() {
        if (this.item.getType() == Material.ENCHANTED_BOOK) {
            if (!this.lore.isEmpty()) {
                this.storage.setLore(this.lore);
                this.lore.clear();
            }
            this.item.setItemMeta(this.storage);
            return this.item;
        }
        if (this.item.getType() == Material.PLAYER_HEAD) {
            if (!this.lore.isEmpty()) {
                this.skullMeta.setLore(this.lore);
                this.lore.clear();
            }
            this.item.setItemMeta(this.skullMeta);
            return this.item;
        }
        if (!this.lore.isEmpty()) {
            this.meta.setLore(this.lore);
            this.lore.clear();
        }
        this.item.setItemMeta(this.meta);
        if (this.glow) {
            this.setGlow(true);
        }
        return this.item;
    }

    public ItemStackBuilder setGlow(final boolean glow) {
        if (glow) {
            this.addEnchantment(Enchantment.DURABILITY, 1);
            this.addFlag(ItemFlag.HIDE_ENCHANTS);
        }
        else {
            this.removeFlag(ItemFlag.HIDE_ENCHANTS);
        }
        return this;
    }

    public ItemStackBuilder addFlag(final ItemFlag... flags) {
        if (this.item.getType() == Material.ENCHANTED_BOOK) {
            this.storage.addItemFlags(flags);
        }
        else if (this.item.getType() == Material.PLAYER_HEAD) {
            this.skullMeta.addItemFlags(flags);
        }
        else {
            this.meta.addItemFlags(flags);
        }
        return this;
    }
    
    public ItemStackBuilder removeFlag(final ItemFlag... flags) {
        if (this.item.getType() == Material.ENCHANTED_BOOK) {
            this.storage.removeItemFlags(flags);
        }
        else if (this.item.getType() == Material.PLAYER_HEAD) {
            this.skullMeta.removeItemFlags(flags);
        }
        else {
            this.meta.removeItemFlags(flags);
        }
        return this;
    }

}