package net.thedudemc.questformillions;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
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
import net.thedudemc.questformillions.common.event.EventStorm;
import net.thedudemc.questformillions.common.network.TotalItemsPacket;
import net.thedudemc.questformillions.common.network.TotalItemsPacketHandler;
import net.thedudemc.questformillions.common.storage.CapabilityHandler;
import net.thedudemc.questformillions.common.storage.million.IMillion;
import net.thedudemc.questformillions.common.storage.million.Million;
import net.thedudemc.questformillions.common.storage.million.MillionsStorage;
import net.thedudemc.questformillions.common.util.Config;

@Mod(modid = QuestForMillions.MODID, useMetadata = true, dependencies = "required-after:ftblib")
public class QuestForMillions {

	public static final String MODID = "questformillions";
	public static final SimpleNetworkWrapper PACKET = NetworkRegistry.INSTANCE.newSimpleChannel("qfm_packets");
	public static Item item = null;

	@Instance
	public static QuestForMillions instance;

	@EventHandler
	public static void PreInit(FMLPreInitializationEvent event) {
		Config.init(event.getSuggestedConfigurationFile());
		PACKET.registerMessage(TotalItemsPacketHandler.class, TotalItemsPacket.class, 0, Side.CLIENT);
		MinecraftForge.EVENT_BUS.register(new GuiRenderer());
		MinecraftForge.EVENT_BUS.register(new CapabilityHandler());
		MinecraftForge.EVENT_BUS.register(new EventStorm());
		CapabilityManager.INSTANCE.register(IMillion.class, new MillionsStorage(), Million::new);

	}

	@EventHandler
	public static void init(FMLInitializationEvent event) {
		if (event.getSide() == Side.CLIENT) {
			QFMClient.registerKeybinds();
		}
	}

	@EventHandler
	public static void PostInit(FMLPostInitializationEvent event) {
		if (event.getSide() == Side.CLIENT) {
			QFMClient.registerRenderers();
		}
		item = Config.pedestal_enableCustomItem ? Item.getByNameOrId(Config.pedestal_customItem) : Items.DIAMOND;
	}

	public static final String getTranslationKey(String name) {
		return QuestForMillions.MODID + "." + name;
	}
}
