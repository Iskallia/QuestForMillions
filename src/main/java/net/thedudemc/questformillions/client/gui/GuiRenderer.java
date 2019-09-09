package net.thedudemc.questformillions.client.gui;

import java.util.HashMap;
import java.util.LinkedHashMap;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.thedudemc.questformillions.client.QFMClient;

public class GuiRenderer {

	public static GuiRenderer instance;
	int currentPlayerTotalItems = 0;
	boolean isOverlayEnabled = true;
	boolean isNumberVisible = true;
	boolean isLeaderboardVisible = true;
	private LinkedHashMap<String, Integer> players = new LinkedHashMap<String, Integer>();
	Minecraft mc = Minecraft.getMinecraft();

	public GuiRenderer() {
		instance = this;
	}

	@SubscribeEvent
	public void onRender(RenderGameOverlayEvent.Pre event) {
		if (mc.inGameHasFocus) {
			if (isOverlayEnabled) {
				if (event.getType() == ElementType.ALL) {
					int amount = getCurrentPlayerTotalItems();
					if (amount > 0) {
						new GuiOverlay(getCurrentPlayerTotalItems(), players, isNumberVisible, isLeaderboardVisible);
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void onClientTick(ClientTickEvent event) {
		if (mc.inGameHasFocus) {
			if (QFMClient.KEY_TOGGLE_OVERLAY.isPressed()) {
				if (isOverlayEnabled && isNumberVisible && isLeaderboardVisible) {
					isOverlayEnabled = false;
					isNumberVisible = false;
					isLeaderboardVisible = false;
				} else if (isOverlayEnabled && !isNumberVisible) {
					isNumberVisible = true;
				} else if (isNumberVisible && !isLeaderboardVisible) {
					isLeaderboardVisible = true;
				} else if (!isOverlayEnabled && !isNumberVisible) {
					isOverlayEnabled = true;
				}
			}
		}

	}

	public int getCurrentPlayerTotalItems() {
		EntityPlayer player = mc.player;
		if (players.containsKey(player.getName()))
			return players.get(player.getName());
		else
			return 0;
	}

	public void setCurrentPlayerTotalItems(int totalItems) {
		EntityPlayer player = mc.player;
		players.put(player.getName(), totalItems);
	}

	public HashMap<String, Integer> getPlayers() {
		return players;
	}

	public void setPlayers(LinkedHashMap<String, Integer> players) {
		this.players = players;
	}

}
