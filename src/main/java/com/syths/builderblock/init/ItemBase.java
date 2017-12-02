package com.syths.builderblock.init;

import com.syths.builderblock.items.ItemStick;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBase {
	
	public static ItemStick stick;
	
	public static void init() {
		
		stick = new ItemStick();
		
	}
	
	@SideOnly (Side.CLIENT)
	public static void initModels() {
		
		ItemStick.initModel(stick);
		
	}
}
