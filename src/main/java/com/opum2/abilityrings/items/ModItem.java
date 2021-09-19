package com.opum2.abilityrings.items;

import com.opum2.abilityrings.init.ModCreativeTab;

import net.minecraft.world.item.Item;

public class ModItem extends Item {
	public static final Item.Properties DEFAULT_PROPERTIES = new Item.Properties();
	
	private String localizedName;
	
	public ModItem() {
		super(DEFAULT_PROPERTIES);
	}
	
	public ModItem(Item.Properties properties) {
		super(properties);
	}

	public ModItem(String name) {
		this(name, DEFAULT_PROPERTIES.tab(ModCreativeTab.MOD_TAB));
	}
	
	public ModItem(String name, Item.Properties properties) {
		super(properties);
		this.setLocalizedName(name);
	}
	
	
	public String getLocalizedName() {
		return this.localizedName;
	}
	
	public void setLocalizedName(String name) {
		this.localizedName = name;
	}
}
