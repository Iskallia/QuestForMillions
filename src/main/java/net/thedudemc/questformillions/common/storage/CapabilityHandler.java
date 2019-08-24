package net.thedudemc.questformillions.common.storage;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.thedudemc.questformillions.QuestForMillions;

public class CapabilityHandler {

	public static final ResourceLocation MILLION_CAP = new ResourceLocation(QuestForMillions.MODID, "million");

	@SubscribeEvent
	public void attachCapability(AttachCapabilitiesEvent<Entity> event) {
		if (!(event.getObject() instanceof EntityPlayer))
			return;
		event.addCapability(MILLION_CAP, new MillionProvider());
	}

}
