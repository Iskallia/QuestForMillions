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
	private float lastAngle = 0;
	private float currentAngle = 180;
	private long lastTime = 0;

	@Override
	public void renderTileEntityFast(TilePedestal te, double x, double y, double z, float partialTicks, int destroyStage, float partial, BufferBuilder buffer) {
		// entity.prevPosX+(entity.posY-entity.prevPosY)*ClassWithPartialTicks.partialTicks;
		float rotation = lastAngle + (currentAngle - lastAngle) * partialTicks;
		ItemStack itemStack = new ItemStack(Items.DIAMOND);

		if (itemStack.isEmpty()) {
			return;
		}
		GlStateManager.disableLighting();
		RenderHelper.enableStandardItemLighting();
		System.out.println(rotation);

		GlStateManager.pushMatrix();
		GlStateManager.translate(x + 0.5f, y + 0.5f, z + 0.5f);
		GlStateManager.rotate(rotation, 0.0f, 1.0f, 0);
		lastAngle = rotation;
		if (currentAngle >= 360) {
			System.out.println(System.currentTimeMillis() - lastTime);
			lastTime = System.currentTimeMillis();
			currentAngle = 1;
		}

		Minecraft.getMinecraft().getRenderItem().renderItem(itemStack, ItemCameraTransforms.TransformType.GROUND);

		GlStateManager.popMatrix();

		RenderHelper.disableStandardItemLighting();
		GlStateManager.enableLighting();
	}
}