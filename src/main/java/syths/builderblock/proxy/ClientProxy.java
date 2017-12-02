package syths.builderblock.proxy;

import com.syths.builderblock.BuilderBlock;
import com.syths.builderblock.init.BlockBase;
import com.syths.builderblock.init.ItemBase;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;	

public class ClientProxy extends CommonProxy {
	
	public void registerItemRenderer(Item item, int meta, String id) {
		 ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(BuilderBlock.modId + ":" + id, "inventory"));
	}
	
}