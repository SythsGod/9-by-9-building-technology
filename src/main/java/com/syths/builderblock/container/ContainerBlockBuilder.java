package com.syths.builderblock.container;

import com.syths.builderblock.tileentity.TileEntityBlockBuilder;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerBlockBuilder extends Container {
	
	private TileEntityBlockBuilder tileEntityBlockBuilder; // Stores the instance of the tile entity for later use
	private int [] cachedFields; // Stores cached values, used by the server to update ONLY the client side tile entity when values have changed
	
	// All the player slots (0 - 35) so 36 slots
	private final int HOTBAR_SLOT_COUNT = 9;
	private final int PLAYER_INVENTORY_ROW_COUNT = 3;
	private final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
	private final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
	private final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
	
	// All the tile entity slots
	public final int FUEL_SLOTS_COUNT = 1;
	public final int BLOCK_SLOTS_COUNT = 3;
	public final int BLOCKBUILDER_SLOTS_COUNT = FUEL_SLOTS_COUNT + BLOCK_SLOTS_COUNT;
	
	// Set starting index for each set of slots (for easier use)
	private final int VANILLA_FIRST_SLOT_INDEX = 0;
	private final int FIRST_FUEL_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;
	private final int FIRST_BLOCK_SLOT_INDEX = FIRST_FUEL_SLOT_INDEX + FUEL_SLOTS_COUNT;
	
	
	
	public ContainerBlockBuilder(InventoryPlayer invPlayer, TileEntityBlockBuilder tileEntityBlockBuilder) {
		this.tileEntityBlockBuilder = tileEntityBlockBuilder;
		
		final int SLOT_X_SPACING = 18;
		final int SLOT_Y_SPACING = 18;
		final int HOTBAR_XPOS = 8;
		final int HOTBAR_YPOS = 183;
		
		// Add hotbar to the GUI
		for (int x = 0; x < HOTBAR_SLOT_COUNT; x++) {
			int slotNumber = x;
			addSlotToContainer(new Slot(invPlayer, slotNumber, HOTBAR_XPOS + SLOT_X_SPACING *x, HOTBAR_YPOS));
		}
		
		final int PLAYER_INVENTORY_XPOS = 8;
		final int PLAYER_INVENTORY_YPOS = 125;
		
		// Add rest of player inventory to GUI
		for (int y = 0; y < PLAYER_INVENTORY_ROW_COUNT; y++) {
			for (int x = 0; x < PLAYER_INVENTORY_COLUMN_COUNT; x++ ) {
				int slotNumber = HOTBAR_SLOT_COUNT + y * PLAYER_INVENTORY_COLUMN_COUNT + x;
				int xpos = PLAYER_INVENTORY_XPOS + x * SLOT_X_SPACING;
				int ypos = PLAYER_INVENTORY_YPOS + y * SLOT_Y_SPACING;
				addSlotToContainer(new Slot(invPlayer, slotNumber, xpos, ypos));
			}
		}
		
		final int FUEL_SLOTS_XPOS = 53;
		final int FUEL_SLOTS_YPOS = 96;
		
		// Add fuel slots for tile entity
		addSlotToContainer(new SlotFuel(tileEntityBlockBuilder, FIRST_FUEL_SLOT_INDEX, FUEL_SLOTS_XPOS, FUEL_SLOTS_YPOS));
		
		final int INPUT_SLOTS_XPOS = 26;
		final int INPUT_SLOTS_YPOS = 24;
		
		// Add block input slots
		for (int y = 0; y < BLOCK_SLOTS_COUNT; y++) {
			int slotNumber = y + FIRST_BLOCK_SLOT_INDEX;
			addSlotToContainer(new Slot(tileEntityBlockBuilder, slotNumber, INPUT_SLOTS_XPOS, INPUT_SLOTS_YPOS + SLOT_Y_SPACING * y));
		}
		
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {

		return tileEntityBlockBuilder.isUseableByPlayer(playerIn);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int sourceSlotIndex) {
		
		Slot sourceSlot = (Slot)inventorySlots.get(sourceSlotIndex);
		
		if (sourceSlot == null || !sourceSlot.getHasStack()) {
			return null;
		}
		
		ItemStack sourceStack = sourceSlot.getStack();
		ItemStack copyOfSourceStack = sourceStack.copy();
		
		// Check if the sourceStack is in a vanilla container slot (player inventory)
		if (sourceSlotIndex >= VANILLA_FIRST_SLOT_INDEX && sourceSlotIndex < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
			// This is a vanilla slot so move --> Tile Entity
//			if (TileEntityBlockBuilder.getSmeltingResultForItem(sourceStack) != null) {
				if(!mergeItemStack(sourceStack, FIRST_FUEL_SLOT_INDEX, FIRST_FUEL_SLOT_INDEX + FUEL_SLOTS_COUNT, false)) {
					return null;
				} else if (TileEntityBlockBuilder.getItemBurnTime(sourceStack) > 0) {
					if(!mergeItemStack(sourceStack, FIRST_FUEL_SLOT_INDEX, FIRST_FUEL_SLOT_INDEX + FUEL_SLOTS_COUNT, true)) {
						return null;
					}
					
				} else {
					return null;
//				}
			}
		} else if (sourceSlotIndex >= FIRST_FUEL_SLOT_INDEX && sourceSlotIndex < FIRST_FUEL_SLOT_INDEX + BLOCKBUILDER_SLOTS_COUNT) {
			// This is a slot from our Tile Entity so merge the itemstack --> player inventory
			if(!mergeItemStack(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
				return null;
			}
		} else {
			System.err.print("Invalid slotIndex: " + sourceSlotIndex);
			return null;
		}
		
		// If stack size == 0 (the entire stack was moved) set slot contents to null
		if(sourceStack.stackSize == 0) {
			sourceSlot.putStack(null);
		} else {
			sourceSlot.onSlotChanged();
		}
		
		sourceSlot.onPickupFromSlot(playerIn, sourceStack);
		return sourceStack;
	}

	
	// Syncronize changed to the client
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
		boolean allFieldsHaveChanged = false;
		boolean fieldHasChanged[] = new boolean[tileEntityBlockBuilder.getFieldCount()];
		
		if(cachedFields == null) {
			cachedFields = new int[tileEntityBlockBuilder.getFieldCount()];
			allFieldsHaveChanged = true;
		}
		
		for (int i = 0; i < cachedFields.length; ++i) {
			if (allFieldsHaveChanged || cachedFields[i] != tileEntityBlockBuilder.getField(i)) {
				cachedFields[i] = tileEntityBlockBuilder.getField(i);
				fieldHasChanged[i] = true;
			}
		}
		
		// Go through list of listeners (players using this inventory) and update them if necessary
		for (IContainerListener listener : this.listeners) {
			for (int fieldID = 0; fieldID < tileEntityBlockBuilder.getFieldCount(); ++fieldID) {
				if (fieldHasChanged[fieldID]) {}
				// These ints as parameters are truncated to shorts
				listener.sendProgressBarUpdate(this, fieldID, cachedFields[fieldID]);
			}
		}		
	}
	
	// Called when progress bar update is received
	@SideOnly(Side.CLIENT)
	@Override
	public void updateProgressBar(int id, int data) {
		tileEntityBlockBuilder.setField(id, data);
	}
	
	// SlotFuel is a slot for fuel items
	public class SlotFuel extends Slot {
		public SlotFuel(IInventory inventoryIn, int index, int xPos, int yPos) {
			super(inventoryIn, index, xPos, yPos);
		}
		
		// If this functions returns false the item isn't able to be inserted in the slot
		@Override
		public boolean isItemValid(ItemStack stack) {
			return TileEntityBlockBuilder.isItemValidForFuelSlot(stack);
		}
	}
	
	// SlotBuilderBlocks is a slot for building blocks
	public class SlotBuilderBlocks extends Slot {
		public SlotBuilderBlocks(IInventory inventoryIn, int index, int xPos, int yPos) {
			super(inventoryIn, index, xPos, yPos);
		}
		
		@Override
		public boolean isItemValid(ItemStack stack) {
			return TileEntityBlockBuilder.isItemValidForBuilding(stack);
		}
	}
}
