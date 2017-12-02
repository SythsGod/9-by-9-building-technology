//package com.syths.builderblock.blocks.bakedmodel;
//
//import com.syths.builderblock.BuilderBlock;
//import com.syths.builderblock.init.BlockBase;
//
//import net.minecraft.block.Block;
//import net.minecraft.block.material.Material;
//import net.minecraft.block.properties.IProperty;
//import net.minecraft.block.state.BlockStateContainer;
//import net.minecraft.block.state.IBlockState;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.renderer.block.model.ModelResourceLocation;
//import net.minecraft.client.renderer.block.statemap.StateMapperBase;
//import net.minecraft.item.Item;
//import net.minecraft.item.ItemBlock;
//import net.minecraft.util.EnumFacing;
//import net.minecraft.util.ResourceLocation;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.world.IBlockAccess;
//import net.minecraftforge.client.model.ModelLoader;
//import net.minecraftforge.common.property.ExtendedBlockState;
//import net.minecraftforge.common.property.IExtendedBlockState;
//import net.minecraftforge.common.property.IUnlistedProperty;
//import net.minecraftforge.fml.common.registry.GameRegistry;
//import net.minecraftforge.fml.relauncher.Side;
//import net.minecraftforge.fml.relauncher.SideOnly;
//
//public class BakedModelBlock extends Block {
//	
//	public static final UnlistedPropertyBlockAvailable NORTH = new UnlistedPropertyBlockAvailable("north");
//    public static final UnlistedPropertyBlockAvailable SOUTH = new UnlistedPropertyBlockAvailable("south");
//    public static final UnlistedPropertyBlockAvailable WEST = new UnlistedPropertyBlockAvailable("west");
//    public static final UnlistedPropertyBlockAvailable EAST = new UnlistedPropertyBlockAvailable("east");
//    public static final UnlistedPropertyBlockAvailable UP = new UnlistedPropertyBlockAvailable("up");
//    public static final UnlistedPropertyBlockAvailable DOWN = new UnlistedPropertyBlockAvailable("down");
//    
//    public BakedModelBlock() {
//    	super(Material.ROCK);
//    	setUnlocalizedName(BuilderBlock.modId + ".bakedmodelblock");
//    	setRegistryName("bakedmodelblock");
//    	GameRegistry.register(this);
//    	GameRegistry.register(new ItemBlock(this), getRegistryName());
//    }
//    
//    @SideOnly(Side.CLIENT)
//    public void initModel() {
//    	StateMapperBase ignoreState = new StateMapperBase() {    	
//	    	@Override
//	    	protected ModelResourceLocation getModelResourceLocation(IBlockState iBlockState) {
//	    		return ExampleBakedModel.BAKED_MODEL;
//	    	}
//    	};
//    	
//    	ModelLoader.setCustomStateMapper(this, ignoreState);
//    }
//    
//    @SideOnly(Side.CLIENT)
//    public void initItemModel() {
//    	Item itemBlock = Item.REGISTRY.getObject(new ResourceLocation(BuilderBlock.modId, "bakedmodelblock"));
//    	ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation(getRegistryName(), "inventory");
//    	final int DEFAULT_ITEM_SUBTYPE = 0;
//    	Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(itemBlock, DEFAULT_ITEM_SUBTYPE, itemModelResourceLocation);
//    }
//    
//    @Override
//    @SideOnly(Side.CLIENT)
//    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
//    	return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
//    }
//    
//    @Override
//    public boolean isBlockNormalCube(IBlockState blockState) {
//    	return false;
//    }
//    
//    @Override
//    public boolean isOpaqueCube(IBlockState blockState) {
//    	return false;
//    }
//    
//    @Override
//    protected BlockStateContainer createBlockState() {
//    	IProperty[] listedProperties = new IProperty[0]; // no listed properties
//    	IUnlistedProperty[] unlistedProperties = new IUnlistedProperty[] { NORTH, SOUTH, WEST, EAST, UP, DOWN };
//    	return new ExtendedBlockState(this, listedProperties, unlistedProperties);
//    }
//    
//    @Override
//    public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
//    	IExtendedBlockState extendedBlockState = (IExtendedBlockState) state;
//    	
//    	boolean north = isSameBlock(world, pos.north());
//    	boolean south = isSameBlock(world, pos.south());
//    	boolean west = isSameBlock(world, pos.west());
//    	boolean east = isSameBlock(world, pos.east());
//    	boolean up = isSameBlock(world, pos.up());
//    	boolean down = isSameBlock(world, pos.down());
//    	
//    	return extendedBlockState
//    			.withProperty(NORTH, north)
//    			.withProperty(SOUTH, south)
//    			.withProperty(WEST, west)
//    			.withProperty(EAST, east)
//    			.withProperty(UP, up)
//    			.withProperty(DOWN, down);
//    }
//    
//    private boolean isSameBlock(IBlockAccess world, BlockPos pos) {
//    	return world.getBlockState(pos).getBlock() == BlockBase.bakedModelBlock;
//    }
//    
//}
