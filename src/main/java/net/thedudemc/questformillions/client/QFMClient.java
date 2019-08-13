package net.thedudemc.questformillions.client;

import javax.annotation.Nullable;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.thedudemc.questformillions.QuestForMillions;
import net.thedudemc.questformillions.client.render.RenderPedestal;
import net.thedudemc.questformillions.common.init.QFMBlocks;
import net.thedudemc.questformillions.common.tileentity.TilePedestal;

@Mod.EventBusSubscriber(modid = QuestForMillions.MODID, value = Side.CLIENT)
public class QFMClient {

	public static KeyBinding KEY_TOGGLE_OVERLAY = new KeyBinding("key.questformillions.desc", Keyboard.KEY_F8, "key.questformillions.category");

	@SubscribeEvent
	static void onRegisterModels(final ModelRegistryEvent context) {
		registerItemModel(Item.getItemFromBlock(QFMBlocks.PEDESTAL), 0, "inventory");
		registerItemModel(Item.getItemFromBlock(QFMBlocks.TREASURE), 0, "inventory");
	}

	private static void registerItemModel(final Item item, final int meta, final String variant) {
		if (Items.AIR == item) {
			throw new IllegalStateException("Empty item");
		}

		@Nullable
		final ResourceLocation name = item.getRegistryName();

		if (name == null) {
			throw new IllegalStateException("Missing registry name");
		}

		if (variant.isEmpty()) {
			throw new IllegalStateException("Empty variant string");
		}

		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(name, variant));
	}

	public static void registerRenderers() {
		ClientRegistry.bindTileEntitySpecialRenderer(TilePedestal.class, new RenderPedestal());
	}

	public static void registerKeybinds() {

		ClientRegistry.registerKeyBinding(KEY_TOGGLE_OVERLAY);
	}
}
