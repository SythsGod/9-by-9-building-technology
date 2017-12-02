package com.syths.builderblock.init;

import com.syths.builderblock.BuilderBlock;
import com.syths.builderblock.blocks.BlockBuilder;

import net.minecraft.block.Block;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockBase {
	
	public static BlockBuilder blockBuilder;
//	public static BakedModelBlock
	
	public static void init() {
		blockBuilder = new BlockBuilder(BuilderBlock.modId + ".blockbuilder", "blockbuilder");
	}
	
	@SideOnly(Side.CLIENT)
	public static void initModels() {
		blockBuilder.initModel();
	}
}
