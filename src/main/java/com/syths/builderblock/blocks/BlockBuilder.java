package com.syths.builderblock.blocks;

import java.util.ArrayList;
import java.util.Random;

import com.syths.builderblock.BuilderBlock;
import com.syths.builderblock.tileentity.TileEntityBlockBuilder;
import com.syths.builderblock.util.GuiHandler;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockBuilder extends Block {
	
	public static int blocksBuild = 0;
	public static boolean isBuilding = false;
	
	public static ArrayList<BlockPlace> structure = new ArrayList<BlockPlace>();
	
	public BlockBuilder(String unlocalizedName, String registryName) {
		super(Material.ROCK);
		setUnlocalizedName(unlocalizedName);
		setRegistryName(registryName);
		
		setHardness(3F);
		setResistance(5F);
		
		setCreativeTab(BuilderBlock.creativeTab);
		
		GameRegistry.register(this);
		GameRegistry.register(new ItemBlock(this), getRegistryName());
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
		
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityBlockBuilder();		
	}
	
	public static class BlockPlace {
		public Block block;
		public BlockPos pos;
		public IBlockState state;
		
		public BlockPlace(BlockPos pos, Block block, IBlockState state) {
			this.pos = pos;
			this.block = block;
			this.state = state;
		}
		
		public void setPos(BlockPos pos) {
			this.pos = pos;
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void initModel() {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(this);
	}
	
//	@Override
//	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ,
//			int meta, EntityLivingBase placer) {
//		
//		BlockPos newPos = pos.add(new BlockPos(2, 0, 2));
//
//		if(!worldIn.isRemote) {
//			worldIn.setBlockState(newPos, Blocks.BEDROCK.getDefaultState());
//		}
//		
//		return super.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer);
//	}
	
//	public void update(World world) {
//		System.out.println("You're in the update() function!");
//		
//		if(isBuilding && !world.isRemote) {
//			System.out.println("We're in! It's remote! Let's see this size!");
//			int totalBlocksToBuild = structure.size();
//			
//			if(totalBlocksToBuild - blocksBuild > 0) {
//				System.out.println("Placing some blocks for you!");
//				world.setBlockState(structure.get(blocksBuild).pos, structure.get(blocksBuild).state);
//				blocksBuild++;
//			} else {
//				System.out.print("Turning off building");
//				isBuilding = false;
//				blocksBuild = 0;
//				structure.clear();
//			}
//		}
//	}
	
	public ArrayList<BlockPlace> setStructure(BlockPos centerPos) {

		// blocks
		Block cobble = Blocks.COBBLESTONE;
		Block glass = Blocks.GLASS;
		Block air = Blocks.AIR;
		
		// doors
		boolean north = true;
		boolean south = true;
		boolean east = true;
		boolean west = true;
		
		// return var
		ArrayList<BlockPlace> structure = new ArrayList<BlockPlace>();
		
		// Set starting points for all sides (It's how I do things, haaaaah #shame)
		BlockPos startingPoint = new BlockPos(centerPos.getX() + 5, centerPos.getY(), centerPos.getZ() - 5);
		BlockPos topStartingPoint = new BlockPos(centerPos.getX() + 4, centerPos.getY() + 5, centerPos.getZ() - 4);
		BlockPos northStartingPoint  = new BlockPos(centerPos.getX() + 5, centerPos.getY(), centerPos.getZ() - 1);
		BlockPos southStartingPoint  = new BlockPos(centerPos.getX() - 5, centerPos.getY(), centerPos.getZ() - 1);
		BlockPos westStartingPoint  = new BlockPos(centerPos.getX() - 1, centerPos.getY(), centerPos.getZ() - 5);
		BlockPos eastStartingPoint  = new BlockPos(centerPos.getX() - 1, centerPos.getY(), centerPos.getZ() + 5);
		
		// Setup the entire structure
		
		for(int y = 0; y <= 5; y++) {
		// NORTH
		structure.add(new BlockPlace(startingPoint.add(new BlockPos(0, y, 0)), cobble, cobble.getBlockState().getBaseState()));
		structure.add(new BlockPlace(startingPoint.add(new BlockPos(0, y, 1)), cobble, cobble.getBlockState().getBaseState()));
		structure.add(new BlockPlace(startingPoint.add(new BlockPos(0, y, 2)), cobble, cobble.getBlockState().getBaseState()));
		structure.add(new BlockPlace(startingPoint.add(new BlockPos(0, y, 3)), cobble, cobble.getBlockState().getBaseState()));
		structure.add(new BlockPlace(startingPoint.add(new BlockPos(0, y, 4)), cobble, cobble.getBlockState().getBaseState()));
		structure.add(new BlockPlace(startingPoint.add(new BlockPos(0, y, 5)), cobble, cobble.getBlockState().getBaseState()));
		structure.add(new BlockPlace(startingPoint.add(new BlockPos(0, y, 6)), cobble, cobble.getBlockState().getBaseState()));
		structure.add(new BlockPlace(startingPoint.add(new BlockPos(0, y, 7)), cobble, cobble.getBlockState().getBaseState()));
		structure.add(new BlockPlace(startingPoint.add(new BlockPos(0, y, 8)), cobble, cobble.getBlockState().getBaseState()));
		structure.add(new BlockPlace(startingPoint.add(new BlockPos(0, y, 9)), cobble, cobble.getBlockState().getBaseState()));
		structure.add(new BlockPlace(startingPoint.add(new BlockPos(0, y, 10)), cobble, cobble.getBlockState().getBaseState()));
		
		// SOUTH
		structure.add(new BlockPlace(startingPoint.add(new BlockPos(-10, y, 0)), cobble, cobble.getBlockState().getBaseState()));
		structure.add(new BlockPlace(startingPoint.add(new BlockPos(-10, y, 1)), cobble, cobble.getBlockState().getBaseState()));
		structure.add(new BlockPlace(startingPoint.add(new BlockPos(-10, y, 2)), cobble, cobble.getBlockState().getBaseState()));
		structure.add(new BlockPlace(startingPoint.add(new BlockPos(-10, y, 3)), cobble, cobble.getBlockState().getBaseState()));
		structure.add(new BlockPlace(startingPoint.add(new BlockPos(-10, y, 4)), cobble, cobble.getBlockState().getBaseState()));
		structure.add(new BlockPlace(startingPoint.add(new BlockPos(-10, y, 5)), cobble, cobble.getBlockState().getBaseState()));
		structure.add(new BlockPlace(startingPoint.add(new BlockPos(-10, y, 6)), cobble, cobble.getBlockState().getBaseState()));
		structure.add(new BlockPlace(startingPoint.add(new BlockPos(-10, y, 7)), cobble, cobble.getBlockState().getBaseState()));
		structure.add(new BlockPlace(startingPoint.add(new BlockPos(-10, y, 8)), cobble, cobble.getBlockState().getBaseState()));
		structure.add(new BlockPlace(startingPoint.add(new BlockPos(-10, y, 9)), cobble, cobble.getBlockState().getBaseState()));
		structure.add(new BlockPlace(startingPoint.add(new BlockPos(-10, y, 10)), cobble, cobble.getBlockState().getBaseState()));
		
		// EAST
		structure.add(new BlockPlace(startingPoint.add(new BlockPos(-1, y, 0)), cobble, cobble.getBlockState().getBaseState()));
		structure.add(new BlockPlace(startingPoint.add(new BlockPos(-2, y, 0)), cobble, cobble.getBlockState().getBaseState()));
		structure.add(new BlockPlace(startingPoint.add(new BlockPos(-3, y, 0)), cobble, cobble.getBlockState().getBaseState()));
		structure.add(new BlockPlace(startingPoint.add(new BlockPos(-4, y, 0)), cobble, cobble.getBlockState().getBaseState()));
		structure.add(new BlockPlace(startingPoint.add(new BlockPos(-5, y, 0)), cobble, cobble.getBlockState().getBaseState()));
		structure.add(new BlockPlace(startingPoint.add(new BlockPos(-6, y, 0)), cobble, cobble.getBlockState().getBaseState()));
		structure.add(new BlockPlace(startingPoint.add(new BlockPos(-7, y, 0)), cobble, cobble.getBlockState().getBaseState()));
		structure.add(new BlockPlace(startingPoint.add(new BlockPos(-8, y, 0)), cobble, cobble.getBlockState().getBaseState()));
		structure.add(new BlockPlace(startingPoint.add(new BlockPos(-9, y, 0)), cobble, cobble.getBlockState().getBaseState()));
		
		// WEST
		structure.add(new BlockPlace(startingPoint.add(new BlockPos(-1, y, 10)), cobble, cobble.getBlockState().getBaseState()));
		structure.add(new BlockPlace(startingPoint.add(new BlockPos(-2, y, 10)), cobble, cobble.getBlockState().getBaseState()));
		structure.add(new BlockPlace(startingPoint.add(new BlockPos(-3, y, 10)), cobble, cobble.getBlockState().getBaseState()));
		structure.add(new BlockPlace(startingPoint.add(new BlockPos(-4, y, 10)), cobble, cobble.getBlockState().getBaseState()));
		structure.add(new BlockPlace(startingPoint.add(new BlockPos(-5, y, 10)), cobble, cobble.getBlockState().getBaseState()));
		structure.add(new BlockPlace(startingPoint.add(new BlockPos(-6, y, 10)), cobble, cobble.getBlockState().getBaseState()));
		structure.add(new BlockPlace(startingPoint.add(new BlockPos(-7, y, 10)), cobble, cobble.getBlockState().getBaseState()));
		structure.add(new BlockPlace(startingPoint.add(new BlockPos(-8, y, 10)), cobble, cobble.getBlockState().getBaseState()));
		structure.add(new BlockPlace(startingPoint.add(new BlockPos(-9, y, 10)), cobble, cobble.getBlockState().getBaseState()));
		}
		
		// TOP (INSIDE)
		// ROW 1
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(0, 0, 0)), cobble, cobble.getBlockState().getBaseState()));
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(0, 0, 1)), cobble, cobble.getBlockState().getBaseState()));
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(0, 0, 2)), cobble, cobble.getBlockState().getBaseState()));
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(0, 0, 3)), cobble, cobble.getBlockState().getBaseState()));
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(0, 0, 4)), cobble, cobble.getBlockState().getBaseState()));
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(0, 0, 5)), cobble, cobble.getBlockState().getBaseState()));
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(0, 0, 6)), cobble, cobble.getBlockState().getBaseState()));
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(0, 0, 7)), cobble, cobble.getBlockState().getBaseState()));
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(0, 0, 8)), cobble, cobble.getBlockState().getBaseState()));
		
		// ROW 2
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(-1, 0, 0)), cobble, cobble.getBlockState().getBaseState()));
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(-1, 0, 1)), glass, glass.getBlockState().getBaseState()));
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(-1, 0, 2)), glass, glass.getBlockState().getBaseState()));
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(-1, 0, 3)), glass, glass.getBlockState().getBaseState()));
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(-1, 0, 4)), cobble, cobble.getBlockState().getBaseState()));
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(-1, 0, 5)), glass, glass.getBlockState().getBaseState()));
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(-1, 0, 6)), glass, glass.getBlockState().getBaseState()));
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(-1, 0, 7)), glass, glass.getBlockState().getBaseState()));
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(-1, 0, 8)), cobble, cobble.getBlockState().getBaseState()));
		
		// ROW 3
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(-2, 0, 0)), cobble, cobble.getBlockState().getBaseState()));
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(-2, 0, 1)), glass, glass.getBlockState().getBaseState()));
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(-2, 0, 2)), glass, glass.getBlockState().getBaseState()));
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(-2, 0, 3)), glass, glass.getBlockState().getBaseState()));
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(-2, 0, 4)), cobble, cobble.getBlockState().getBaseState()));
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(-2, 0, 5)), glass, glass.getBlockState().getBaseState()));
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(-2, 0, 6)), glass, glass.getBlockState().getBaseState()));
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(-2, 0, 7)), glass, glass.getBlockState().getBaseState()));
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(-2, 0, 8)), cobble, cobble.getBlockState().getBaseState()));
		
		// ROW 4
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(-3, 0, 0)), cobble, cobble.getBlockState().getBaseState()));
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(-3, 0, 1)), glass, glass.getBlockState().getBaseState()));
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(-3, 0, 2)), glass, glass.getBlockState().getBaseState()));
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(-3, 0, 3)), glass, glass.getBlockState().getBaseState()));
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(-3, 0, 4)), cobble, cobble.getBlockState().getBaseState()));
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(-3, 0, 5)), glass, glass.getBlockState().getBaseState()));
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(-3, 0, 6)), glass, glass.getBlockState().getBaseState()));
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(-3, 0, 7)), glass, glass.getBlockState().getBaseState()));
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(-3, 0, 8)), cobble, cobble.getBlockState().getBaseState()));
		
		// ROW 5
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(-4, 0, 0)), cobble, cobble.getBlockState().getBaseState()));
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(-4, 0, 1)), cobble, cobble.getBlockState().getBaseState()));
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(-4, 0, 2)), cobble, cobble.getBlockState().getBaseState()));
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(-4, 0, 3)), cobble, cobble.getBlockState().getBaseState()));
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(-4, 0, 4)), cobble, cobble.getBlockState().getBaseState()));
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(-4, 0, 5)), cobble, cobble.getBlockState().getBaseState()));
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(-4, 0, 6)), cobble, cobble.getBlockState().getBaseState()));
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(-4, 0, 7)), cobble, cobble.getBlockState().getBaseState()));
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(-4, 0, 8)), cobble, cobble.getBlockState().getBaseState()));
		
		// ROW 6
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(-5, 0, 0)), cobble, cobble.getBlockState().getBaseState()));
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(-5, 0, 1)), glass, glass.getBlockState().getBaseState()));
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(-5, 0, 2)), glass, glass.getBlockState().getBaseState()));
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(-5, 0, 3)), glass, glass.getBlockState().getBaseState()));
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(-5, 0, 4)), cobble, cobble.getBlockState().getBaseState()));
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(-5, 0, 5)), glass, glass.getBlockState().getBaseState()));
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(-5, 0, 6)), glass, glass.getBlockState().getBaseState()));
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(-5, 0, 7)), glass, glass.getBlockState().getBaseState()));
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(-5, 0, 8)), cobble, cobble.getBlockState().getBaseState()));
		
		// ROW 7
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(-6, 0, 0)), cobble, cobble.getBlockState().getBaseState()));
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(-6, 0, 1)), glass, glass.getBlockState().getBaseState()));
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(-6, 0, 2)), glass, glass.getBlockState().getBaseState()));
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(-6, 0, 3)), glass, glass.getBlockState().getBaseState()));
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(-6, 0, 4)), cobble, cobble.getBlockState().getBaseState()));
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(-6, 0, 5)), glass, glass.getBlockState().getBaseState()));
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(-6, 0, 6)), glass, glass.getBlockState().getBaseState()));
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(-6, 0, 7)), glass, glass.getBlockState().getBaseState()));
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(-6, 0, 8)), cobble, cobble.getBlockState().getBaseState()));
		
		// ROW 8
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(-7, 0, 0)), cobble, cobble.getBlockState().getBaseState()));
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(-7, 0, 1)), glass, glass.getBlockState().getBaseState()));
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(-7, 0, 2)), glass, glass.getBlockState().getBaseState()));
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(-7, 0, 3)), glass, glass.getBlockState().getBaseState()));
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(-7, 0, 4)), cobble, cobble.getBlockState().getBaseState()));
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(-7, 0, 5)), glass, glass.getBlockState().getBaseState()));
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(-7, 0, 6)), glass, glass.getBlockState().getBaseState()));
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(-7, 0, 7)), glass, glass.getBlockState().getBaseState()));
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(-7, 0, 8)), cobble, cobble.getBlockState().getBaseState()));
		
		// ROW 9
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(-8, 0, 0)), cobble, cobble.getBlockState().getBaseState()));
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(-8, 0, 1)), cobble, cobble.getBlockState().getBaseState()));
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(-8, 0, 2)), cobble, cobble.getBlockState().getBaseState()));
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(-8, 0, 3)), cobble, cobble.getBlockState().getBaseState()));
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(-8, 0, 4)), cobble, cobble.getBlockState().getBaseState()));
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(-8, 0, 5)), cobble, cobble.getBlockState().getBaseState()));
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(-8, 0, 6)), cobble, cobble.getBlockState().getBaseState()));
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(-8, 0, 7)), cobble, cobble.getBlockState().getBaseState()));
		structure.add(new BlockPlace(topStartingPoint.add(new BlockPos(-8, 0, 8)), cobble, cobble.getBlockState().getBaseState()));
		
		// HOLES
		// EAST
		if(east) {
			structure.add(new BlockPlace(northStartingPoint.add(new BlockPos(0, 0, 0)), air, air.getBlockState().getBaseState()));
			structure.add(new BlockPlace(northStartingPoint.add(new BlockPos(0, 0, 1)), air, air.getBlockState().getBaseState()));
			structure.add(new BlockPlace(northStartingPoint.add(new BlockPos(0, 0, 2)), air, air.getBlockState().getBaseState()));
			structure.add(new BlockPlace(northStartingPoint.add(new BlockPos(0, 1, 0)), air, air.getBlockState().getBaseState()));
			structure.add(new BlockPlace(northStartingPoint.add(new BlockPos(0, 1, 1)), air, air.getBlockState().getBaseState()));
			structure.add(new BlockPlace(northStartingPoint.add(new BlockPos(0, 1, 2)), air, air.getBlockState().getBaseState()));
			structure.add(new BlockPlace(northStartingPoint.add(new BlockPos(0, 2, 0)), air, air.getBlockState().getBaseState()));
			structure.add(new BlockPlace(northStartingPoint.add(new BlockPos(0, 2, 1)), air, air.getBlockState().getBaseState()));
			structure.add(new BlockPlace(northStartingPoint.add(new BlockPos(0, 2, 2)), air, air.getBlockState().getBaseState()));
		}
		
		// WEST
		if(west) {
			structure.add(new BlockPlace(southStartingPoint.add(new BlockPos(0, 0, 0)), air, air.getBlockState().getBaseState()));
			structure.add(new BlockPlace(southStartingPoint.add(new BlockPos(0, 0, 1)), air, air.getBlockState().getBaseState()));
			structure.add(new BlockPlace(southStartingPoint.add(new BlockPos(0, 0, 2)), air, air.getBlockState().getBaseState()));
			structure.add(new BlockPlace(southStartingPoint.add(new BlockPos(0, 1, 0)), air, air.getBlockState().getBaseState()));
			structure.add(new BlockPlace(southStartingPoint.add(new BlockPos(0, 1, 1)), air, air.getBlockState().getBaseState()));
			structure.add(new BlockPlace(southStartingPoint.add(new BlockPos(0, 1, 2)), air, air.getBlockState().getBaseState()));
			structure.add(new BlockPlace(southStartingPoint.add(new BlockPos(0, 2, 0)), air, air.getBlockState().getBaseState()));
			structure.add(new BlockPlace(southStartingPoint.add(new BlockPos(0, 2, 1)), air, air.getBlockState().getBaseState()));
			structure.add(new BlockPlace(southStartingPoint.add(new BlockPos(0, 2, 2)), air, air.getBlockState().getBaseState()));
		}
		
		// SOUTH
		if(south) {
			structure.add(new BlockPlace(eastStartingPoint.add(new BlockPos(0, 0, 0)), air, air.getBlockState().getBaseState()));
			structure.add(new BlockPlace(eastStartingPoint.add(new BlockPos(1, 0, 0)), air, air.getBlockState().getBaseState()));
			structure.add(new BlockPlace(eastStartingPoint.add(new BlockPos(2, 0, 0)), air, air.getBlockState().getBaseState()));
			structure.add(new BlockPlace(eastStartingPoint.add(new BlockPos(0, 1, 0)), air, air.getBlockState().getBaseState()));
			structure.add(new BlockPlace(eastStartingPoint.add(new BlockPos(1, 1, 0)), air, air.getBlockState().getBaseState()));
			structure.add(new BlockPlace(eastStartingPoint.add(new BlockPos(2, 1, 0)), air, air.getBlockState().getBaseState()));
			structure.add(new BlockPlace(eastStartingPoint.add(new BlockPos(0, 2, 0)), air, air.getBlockState().getBaseState()));
			structure.add(new BlockPlace(eastStartingPoint.add(new BlockPos(1, 2, 0)), air, air.getBlockState().getBaseState()));
			structure.add(new BlockPlace(eastStartingPoint.add(new BlockPos(2, 2, 0)), air, air.getBlockState().getBaseState()));
		}
	
		// NORTH
		if(north) {
			structure.add(new BlockPlace(westStartingPoint.add(new BlockPos(0, 0, 0)), air, air.getBlockState().getBaseState()));
			structure.add(new BlockPlace(westStartingPoint.add(new BlockPos(1, 0, 0)), air, air.getBlockState().getBaseState()));
			structure.add(new BlockPlace(westStartingPoint.add(new BlockPos(2, 0, 0)), air, air.getBlockState().getBaseState()));
			structure.add(new BlockPlace(westStartingPoint.add(new BlockPos(0, 1, 0)), air, air.getBlockState().getBaseState()));
			structure.add(new BlockPlace(westStartingPoint.add(new BlockPos(1, 1, 0)), air, air.getBlockState().getBaseState()));
			structure.add(new BlockPlace(westStartingPoint.add(new BlockPos(2, 1, 0)), air, air.getBlockState().getBaseState()));
			structure.add(new BlockPlace(westStartingPoint.add(new BlockPos(0, 2, 0)), air, air.getBlockState().getBaseState()));
			structure.add(new BlockPlace(westStartingPoint.add(new BlockPos(1, 2, 0)), air, air.getBlockState().getBaseState()));
			structure.add(new BlockPlace(westStartingPoint.add(new BlockPos(2, 2, 0)), air, air.getBlockState().getBaseState()));
		}
		
		return structure;
	}
	
	 
		 
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
			
		if (worldIn.isRemote) {
			return true;
		}
		
		System.out.println("Opening GUI...");
		playerIn.openGui(BuilderBlock.instance, GuiHandler.getGuiID(), worldIn, pos.getX(), pos.getY(), pos.getZ());
		
		// Get position of builder block
		BlockPos builderBlockPos = pos;
		
		if(!isBuilding) {
			//structure = setStructure(builderBlockPos);
		}
		
		// Start building
//		System.out.println("Starting the building process...");
		//isBuilding = true;
			
//		return super.onBlockActivated(worldIn, pos, state, playerIn, hand, heldItem, side, hitX, hitY, hitZ);
		return true;
	}
}
