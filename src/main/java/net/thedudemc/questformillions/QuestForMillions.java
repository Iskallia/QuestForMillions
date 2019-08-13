package net.thedudemc.questformillions;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.thedudemc.questformillions.client.QFMClient;
import net.thedudemc.questformillions.client.gui.GuiRenderer;
import net.thedudemc.questformillions.common.network.TotalItemsPacket;
import net.thedudemc.questformillions.common.network.TotalItemsPacketHandler;
import net.thedudemc.questformillions.common.util.Config;

@Mod(modid = QuestForMillions.MODID, useMetadata = true)
public class QuestForMillions {

	public static final String MODID = "questformillions";
	public static final SimpleNetworkWrapper PACKET = NetworkRegistry.INSTANCE.newSimpleChannel("questformillions_packets");

	@Instance
	public static QuestForMillions instance;

	@EventHandler
	public static void PreInit(FMLPreInitializationEvent event) {
		Config.init(event.getSuggestedConfigurationFile());
		PACKET.registerMessage(TotalItemsPacketHandler.class, TotalItemsPacket.class, 0, Side.CLIENT);
		MinecraftForge.EVENT_BUS.register(new GuiRenderer());

	}

	@EventHandler
	public static void init(FMLInitializationEvent event) {

	}

	@EventHandler
	public static void PostInit(FMLPostInitializationEvent event) {
		if (event.getSide() == Side.CLIENT) {
			QFMClient.registerRenderers();
		}
	}

	public static final String getTranslationKey(String name) {
		return QuestForMillions.MODID + "." + name;
	}
}
