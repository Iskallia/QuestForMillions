package net.thedudemc.questformillions.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.animation.FastTESR;
import net.thedudemc.questformillions.QuestForMillions;
import net.thedudemc.questformillions.common.tileentity.TilePedestal;

public class RenderPedestal extends FastTESR<TilePedestal> {

	private float currentTick = 0;

	@Override
	public void renderTileEntityFast(TilePedestal te, double x, double y, double z, float partialTicks, int destroyStage, float partial, BufferBuilder buffer) {
		ItemStack itemStack = new ItemStack(QuestForMillions.item);

		if (itemStack.isEmpty()) {
			return;
		}
		currentTick = Minecraft.getMinecraft().player.ticksExisted;

		float rotation = currentTick + partialTicks;

		GlStateManager.disableLighting();
		RenderHelper.enableStandardItemLighting();

		GlStateManager.pushMatrix();
		GlStateManager.translate(x + 0.5f, y + 0.5f, z + 0.5f);
		GlStateManager.rotate(rotation, 0.0f, 1.0f, 0);

		Minecraft.getMinecraft().getRenderItem().renderItem(itemStack, ItemCameraTransforms.TransformType.GROUND);

		GlStateManager.popMatrix();

		RenderHelper.disableStandardItemLighting();
		GlStateManager.enableLighting();

	}
}