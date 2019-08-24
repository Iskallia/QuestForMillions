package net.thedudemc.questformillions.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.thedudemc.questformillions.client.QFMClient;

public class GuiRenderer {

	public static GuiRenderer instance;
	int totalItems = 0;
	boolean isOverlayEnabled = true;
	boolean isNumberVisible = true;

	public GuiRenderer() {
		instance = this;
	}

	@SubscribeEvent
	public void onRender(RenderGameOverlayEvent.Pre event) {
		if (Minecraft.getMinecraft().inGameHasFocus) {
			if (isOverlayEnabled) {
				if (event.getType() == ElementType.ALL) {
					new GuiOverlay(getTotalItems(), isNumberVisible);
				}
			}
		}
	}

	@SubscribeEvent
	public void onClientTick(ClientTickEvent event) {
		if (Minecraft.getMinecraft().inGameHasFocus) {
			if (QFMClient.KEY_TOGGLE_OVERLAY.isPressed()) {
				if (isOverlayEnabled && isNumberVisible) {
					isOverlayEnabled = false;
					isNumberVisible = false;
				} else if (isOverlayEnabled && !isNumberVisible) {
					isNumberVisible = true;
				} else if (!isOverlayEnabled && !isNumberVisible) {
					isOverlayEnabled = true;
				}
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
