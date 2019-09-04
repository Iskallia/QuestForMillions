package net.thedudemc.questformillions.common.storage;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.thedudemc.questformillions.QuestForMillions;
import net.thedudemc.questformillions.common.storage.million.MillionProvider;

public class CapabilityHandler {

	public static final ResourceLocation MILLION_CAP = new ResourceLocation(QuestForMillions.MODID, "players");

	@SubscribeEvent
	public void attachToWorld(AttachCapabilitiesEvent<World> event) {
		if (!event.getObject().isRemote)
			event.addCapability(MILLION_CAP, new MillionProvider());
	}

}
