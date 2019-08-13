package net.thedudemc.questformillions.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.animation.FastTESR;
import net.thedudemc.questformillions.common.tileentity.TilePedestal;

public class RenderPedestal extends FastTESR<TilePedestal> {
	private float angle = 0;

	@Override
	public void renderTileEntityFast(TilePedestal te, double x, double y, double z, float partialTicks, int destroyStage, float partial, BufferBuilder buffer) {
		// IItemHandler itemHandler =
		// te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

		ItemStack itemStack = new ItemStack(Items.DIAMOND);

		if (itemStack.isEmpty()) {
			return;
		}
		GlStateManager.disableLighting();
		RenderHelper.enableStandardItemLighting();
		// System.out.println(partialTicks);

		GlStateManager.pushMatrix();
		GlStateManager.translate(x + 0.5f, y + 0.5f, z + 0.5f);
		GlStateManager.rotate(angle, 0.0f, 1.0f, 0);
		System.out.println("Times partial: " + (angle + 1f * partialTicks / 100));
		System.out.println("Basic: " + angle);
		angle = angle + 1f;

		if (angle == 361) {
			angle = 0;
		}
		Minecraft.getMinecraft().getRenderItem().renderItem(itemStack, ItemCameraTransforms.TransformType.GROUND);

		GlStateManager.popMatrix();

		RenderHelper.disableStandardItemLighting();
		GlStateManager.enableLighting();
	}
}