package net.thedudemc.questformillions.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.thedudemc.questformillions.QuestForMillions;

public class GuiOverlay extends Gui {
	int width = 0;
	int height = 0;
	int centerX = 0;
	int centerY = 0;
	private Minecraft mc;
	int totalItems = 0;
	boolean isNumberVisible;

	public GuiOverlay(int totalItems, boolean isNumberVisible) {
		this.totalItems = totalItems;
		this.isNumberVisible = isNumberVisible;

		drawBar();
	}

	private void drawBar() {
		if (this.getTotalItems() > 0) {
			this.mc = Minecraft.getMinecraft();
			this.mc.getTextureManager().bindTexture(new ResourceLocation(QuestForMillions.MODID, "textures/gui/main.png"));
			ScaledResolution sr = new ScaledResolution(this.mc);
			this.width = sr.getScaledWidth();
			this.height = sr.getScaledHeight();
			this.centerX = this.width / 2;
			this.centerY = this.height / 2;
			int startY = 10;
			int startX = 10;

			// draw progress bar background
			drawTexturedModalRect(startX + 18, startY - 3, 0, 0, 182, 22);
			// draw colored progress bar
			drawTexturedModalRect(startX + 18 + 3, startY, 0, 22, (int) ((((float) 176) * ((float) this.totalItems / 1000000f))), 16);
			// draw diamond icon
			this.mc.getRenderItem().renderItemIntoGUI(new ItemStack(Items.DIAMOND), 10, 10);
			if (isNumberVisible) {
				drawCenteredString(this.mc.fontRenderer, String.valueOf(this.totalItems > 1000000 ? 1000000 : this.totalItems) + "/1000000", startX + 111, startY + 5, 0x000000);
				drawCenteredString(this.mc.fontRenderer, String.valueOf(this.totalItems > 1000000 ? 1000000 : this.totalItems) + "/1000000", startX + 111, startY + 4, 0xFFFFFF);
			}
		}
	}

	public int getTotalItems() {
		return totalItems;
	}

	public void setTotalItems(int totalItems) {
		this.totalItems = totalItems;
	}
}
