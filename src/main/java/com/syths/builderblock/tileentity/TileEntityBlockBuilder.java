package com.syths.builderblock.tileentity;

import com.syths.builderblock.blocks.BlockBuilder;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import scala.reflect.internal.Trees.This;

public class TileEntityBlockBuilder extends TileEntity implements ITickable, IInventory {
	
	public static final int FUEL_SLOTS_COUNT = 1;
	public static final int BLOCK_SLOTS_COUNT = 3;
	public static final int TOTAL_SLOTS_COUNT = FUEL_SLOTS_COUNT + BLOCK_SLOTS_COUNT;
	
	public static final int FIRST_FUEL_SLOT = 0;
	public static final int FIRST_BLOCK_SLOT = FIRST_FUEL_SLOT + FUEL_SLOTS_COUNT;
	
	private ItemStack[] itemStack = new ItemStack[TOTAL_SLOTS_COUNT];
	
	// Number of burn ticks left on current piece of fuel
	private int  burnTimeRemaining;
	
	// Initial value for currently burning fuel
	private int burnTimeInitialValue;
	
	// Number of ticks item HAS been cooking
	private int cookTime;
	
	// Number of ticks required to cook an item
	private static final short COOK_TIME_FOR_COMPLETION = 200; // 10 seconds (vanilla)
	
	public double fractionOfFuelRemaining() {
		if (burnTimeInitialValue <= 0) return 0; // Not burning
		
		double fraction = burnTimeRemaining / (double)burnTimeInitialValue;
		return MathHelper.clamp_double(fraction, 0.0, 1.0);
	}
	
	public int secondsOfFuelRemaining() {
		if (burnTimeRemaining <= 0) return 0; // Not burning
		
		return burnTimeRemaining / 20; // Divide by 20 (ticks) to result in seconds
	}
	
	public double fractionOfCookTimeComplete() {
		double fraction = cookTime / (double) COOK_TIME_FOR_COMPLETION;
		return MathHelper.clamp_double(fraction, 0.0, 1.0);
	}
	
	
	public TileEntityBlockBuilder() {
		
	}

	@Override
	public void update() {
		if (!this.hasWorldObj()) return; // Prevent a crash if the tile_entity doesn't have a world object
		
		World world = this.getWorld(); // Get the world object stored in a local variable for easier access
		
		if(!world.isRemote) { // Make sure to send these changes to the SERVER not the CLIENT
		
			if(BlockBuilder.isBuilding && !worldObj.isRemote) {
				int totalBlocksToBuild = BlockBuilder.structure.size(); // Get size of the entire structure (should somewhat equal the total number of blocks being placed
//				System.out.println(totalBlocksToBuild);
				
				if(totalBlocksToBuild - BlockBuilder.blocksBuild > 0) {
					if(world.isAirBlock(BlockBuilder.structure.get(BlockBuilder.blocksBuild).pos) || BlockBuilder.blocksBuild > 320) {
						this.worldObj.setBlockState(BlockBuilder.structure.get(BlockBuilder.blocksBuild).pos, BlockBuilder.structure.get(BlockBuilder.blocksBuild).state);
					} else {
						// TO DO
						// A block wasn't air so couldn't place new block
					}
					
					BlockBuilder.blocksBuild++;
				} else {
					System.out.print("Turning off building");
					BlockBuilder.isBuilding = false;
					BlockBuilder.blocksBuild = 0;
					BlockBuilder.structure.clear();
				}
			}
		}
	}
	
	public static ItemStack getSmeltingResultForItem(ItemStack stack) {
		return FurnaceRecipes.instance().getSmeltingResult(stack);
	}
	
	public static short getItemBurnTime(ItemStack stack) {
		int burnTime = TileEntityFurnace.getItemBurnTime(stack);
		return (short) MathHelper.clamp_int(burnTime, 0, Short.MAX_VALUE);
	}
	
	static public boolean isItemValidForFuelSlot(ItemStack stack) {
		return TileEntityFurnace.isItemFuel(stack);
	}
	
	static public boolean isItemValidForBuilding(ItemStack stack) {
		
		if(!(stack.getItem() instanceof ItemBlock)) {
			return false;
		}
		
		Block cobblestone = Blocks.COBBLESTONE;
		Block glass = Blocks.GLASS;
		
		Block itemBlock = Block.getBlockFromItem(stack.getItem());
		
		return itemBlock == cobblestone || itemBlock == glass;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasCustomName() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getSizeInventory() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getInventoryStackLimit() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void openInventory(EntityPlayer player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeInventory(EntityPlayer player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getField(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getFieldCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}
}