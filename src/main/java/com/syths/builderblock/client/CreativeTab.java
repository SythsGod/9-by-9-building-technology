package com.syths.builderblock.client;

import com.syths.builderblock.BuilderBlock;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CreativeTab extends CreativeTabs {
	
	//public static final CreativeTab INSTANCE = new CreativeTab();
	
	public CreativeTab() {
		super(BuilderBlock.modId);
	}
	
	@Override
	public Item getTabIconItem() {
		return Items.ARROW;
	}
}
