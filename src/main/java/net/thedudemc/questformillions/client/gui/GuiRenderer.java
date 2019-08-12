package net.thedudemc.questformillions.client.gui;

import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GuiRenderer {

	public static GuiRenderer instance;
	int totalItems = 0;

	public GuiRenderer() {
		instance = this;
	}

	@SubscribeEvent
	public void onRender(RenderGameOverlayEvent.Pre event) {
		if (event.getType() == ElementType.ALL) {
			new GuiOverlay(getTotalItems());
		}
	}

	public int getTotalItems() {
		return totalItems;
	}

	public void setTotalItems(int totalItems) {
		this.totalItems = totalItems;
	}
}
