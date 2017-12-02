package com.syths.builderblock.util;

import com.syths.builderblock.GUIs.GuiBlockBuilder;
import com.syths.builderblock.container.ContainerBlockBuilder;
import com.syths.builderblock.tileentity.TileEntityBlockBuilder;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
	
	public static final int GUIID_TileEntityBlockBuilder = 31;
	
	public static int getGuiID() {
		return GUIID_TileEntityBlockBuilder;
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID != getGuiID()) {
			System.err.println("Invalid ID: expected " + getGuiID() + ", received " + ID);
		}
		
		BlockPos xyz = new BlockPos(x, y, z);
		TileEntity tileEntity = world.getTileEntity(xyz);
		
		if(tileEntity instanceof TileEntityBlockBuilder) {
			TileEntityBlockBuilder tileEntityBlockBuilder = (TileEntityBlockBuilder) tileEntity;
			return new ContainerBlockBuilder(player.inventory, tileEntityBlockBuilder);
		}
		
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID != getGuiID()) {
			System.err.println("Invalid ID: expected " + getGuiID() + ", received " + ID);
		}
		
		BlockPos xyz = new BlockPos(x, y, z);
		TileEntity tileEntity = world.getTileEntity(xyz);
		
		if(tileEntity instanceof TileEntityBlockBuilder) {
			TileEntityBlockBuilder tileEntityBlockBuilder = (TileEntityBlockBuilder) tileEntity;
			return new GuiBlockBuilder(player.inventory, tileEntityBlockBuilder);
		}
		
		return null;
	}

}
