package com.syths.builderblock.GUIs;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.syths.builderblock.BuilderBlock;
import com.syths.builderblock.container.ContainerBlockBuilder;
import com.syths.builderblock.tileentity.TileEntityBlockBuilder;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiBlockBuilder extends GuiContainer {
	
	// Resource location for the background image
	private static final ResourceLocation texture = new ResourceLocation(BuilderBlock.modId, "textures/gui/bb_inventory_block_builder_bg.png");
	private TileEntityBlockBuilder tileEntity;

	public GuiBlockBuilder(InventoryPlayer invPlayer, TileEntityBlockBuilder tileEntityBlockBuilder) {
		super(new ContainerBlockBuilder(invPlayer, tileEntityBlockBuilder));
		
		int xSize = 176;
		int ySize = 207;
		
		this.tileEntity = tileEntityBlockBuilder;
	}
	
	// some [x,y] coordinates of graphical elements
	final int COOK_BAR_XPOS = 49;
	final int COOK_BAR_YPOS = 60;
	final int COOK_BAR_ICON_U = 0;   // texture position of white arrow icon
	final int COOK_BAR_ICON_V = 207;
	final int COOK_BAR_WIDTH = 80;
	final int COOK_BAR_HEIGHT = 17;

	final int FLAME_XPOS = 54;
	final int FLAME_YPOS = 80;
	final int FLAME_ICON_U = 176;   // texture position of flame icon
	final int FLAME_ICON_V = 0;
	final int FLAME_WIDTH = 14;
	final int FLAME_HEIGHT = 14;
	final int FLAME_X_SPACING = 18;

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		// Bind image texture
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		
		// Draw the image
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		// Get cook progress
		double cookProgress = tileEntity.fractionOfCookTimeComplete();
		
		// Draw the cook progress bar
		drawTexturedModalRect(guiLeft + COOK_BAR_XPOS, guiTop + COOK_BAR_YPOS, COOK_BAR_ICON_U, COOK_BAR_ICON_V, (int) (cookProgress * COOK_BAR_WIDTH), COOK_BAR_HEIGHT);
		
		// Draw the fuel remaining bar
		double burnRemaining = tileEntity.fractionOfFuelRemaining();
		int yOffset = (int)((1.0 - burnRemaining) * FLAME_HEIGHT);
		drawTexturedModalRect(guiLeft + FLAME_XPOS, guiTop + FLAME_YPOS + yOffset, FLAME_ICON_U, FLAME_ICON_V + yOffset, FLAME_WIDTH, FLAME_HEIGHT - yOffset);		
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		
		final int LABEL_XPOS = 5;
		final int LABEL_YPOS = 5;
		
		fontRendererObj.drawString(tileEntity.getDisplayName().getUnformattedText(), LABEL_XPOS, LABEL_YPOS, Color.DARK_GRAY.getRGB());
		
		List<String> hoveringText = new ArrayList<String>();
		
		// If the mouse is over the progress bar add to hovering text
		if (isInRect(guiLeft + COOK_BAR_XPOS, guiTop + COOK_BAR_YPOS, COOK_BAR_WIDTH, COOK_BAR_HEIGHT, mouseX, mouseY)) {
			hoveringText.add("Progress: ");
			int cookPercentage = (int) (tileEntity.fractionOfCookTimeComplete() * 100);
			hoveringText.add(cookPercentage + "%");
		}
		
		// If the mouse is over burn time indicator add to hovering text
		if (isInRect(guiLeft + FLAME_XPOS, guiTop + FLAME_YPOS, FLAME_WIDTH, FLAME_HEIGHT, mouseX, mouseY)) {
			hoveringText.add("Fuel Time: ");
			hoveringText.add(tileEntity.secondsOfFuelRemaining() + "s");
		}
		
		// If hoveringText is not empty, go ahead and draw it
		if (!hoveringText.isEmpty()) {
			drawHoveringText(hoveringText, mouseX - guiLeft, mouseY - guiTop, fontRendererObj);
		}
	}
	
	// Check if given (x , y) coordinates are within given rectangle
	public static boolean isInRect(int x, int y, int xSize, int ySize, int mouseX, int mouseY) {
		return ((mouseX >= x && mouseX <+ x + xSize) && (mouseY >= y && mouseY <= y + ySize));
	}

}