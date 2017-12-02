package com.syths.builderblock;

import com.syths.builderblock.client.CreativeTab;
import com.syths.builderblock.init.BlockBase;
import com.syths.builderblock.init.ItemBase;
import com.syths.builderblock.tileentity.TileEntityBlockBuilder;
import com.syths.builderblock.util.GuiHandler;
import com.syths.builderblock.util.GuiHandlerRegistry;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import syths.builderblock.proxy.CommonProxy;

@Mod(modid = BuilderBlock.modId, name = BuilderBlock.name, version = BuilderBlock.version, acceptedMinecraftVersions = "[1.10.2]")

public class BuilderBlock {
	
	//useful
	//System.out.println("===========================================================================");

	@SidedProxy(serverSide = "syths.builderblock.proxy.CommonProxy", clientSide = "syths.builderblock.proxy.ClientProxy")
	public static CommonProxy proxy;
	
	public static final String modId = "builderblock";
	public static final String name = "Builder Block Mod";
	public static final String version = "0.0.1";
	
	public static CreativeTabs creativeTab = new CreativeTab();
	
	@Mod.Instance(modId)
	public static BuilderBlock instance;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		System.out.println(name + " is loading!");
		
		ItemBase.init();
		BlockBase.init();
		ItemBase.initModels();
		BlockBase.initModels();
		
		GameRegistry.registerTileEntity(TileEntityBlockBuilder.class, BuilderBlock.modId + "tilentity_blockbuilder");
		
		NetworkRegistry.INSTANCE.registerGuiHandler(BuilderBlock.instance, GuiHandlerRegistry.getInstance());
		GuiHandlerRegistry.getInstance().registerGuiHandler(new GuiHandler(), GuiHandler.getGuiID());
		
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {

	}
}