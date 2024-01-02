package industrialelectricity.client.render.tile;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.joml.AxisAngle4d;
import org.joml.Quaternionf;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import electrodynamics.client.render.tile.AbstractTileRenderer;
import electrodynamics.prefab.utilities.RenderingUtils;
import industrialelectricity.client.ClientRegister;
import industrialelectricity.common.tile.monopole.TileMonopole;
import industrialelectricity.core.utils.IMathUtils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class RenderTileMonopole extends AbstractTileRenderer<TileMonopole> {

	public RenderTileMonopole(BlockEntityRendererProvider.Context pContext) {
		super(pContext);
	}

	@Override
	public void render(@NotNull TileMonopole tile, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight, int packedOverlay) {

		poseStack.pushPose();

		float rotAngleRad = 0;// (float) (tile.getFacing().toYRot() / 180 * Math.PI); // Mth.HALF_PI / 2;

		poseStack.pushPose();

		BakedModel base = getModel(ClientRegister.MODEL_MONOPOLE_BASE);

		poseStack.translate(0.5, -0.5, 0.5);

		RenderingUtils.renderModel(base, tile, RenderType.solid(), poseStack, bufferSource, packedLight, packedOverlay);

		poseStack.popPose();

		poseStack.pushPose();

		BakedModel pole1 = getModel(ClientRegister.MODEL_MONOPOLE_1);

		poseStack.translate(0.5, -0.5, 0.5);

		poseStack.mulPose(new Quaternionf(new AxisAngle4d(rotAngleRad, 0, 1, 0)));

		RenderingUtils.renderModel(pole1, tile, RenderType.solid(), poseStack, bufferSource, packedLight, packedOverlay);

		poseStack.popPose();

		poseStack.pushPose();

		BakedModel pole2 = getModel(ClientRegister.MODEL_MONOPOLE_2);

		poseStack.translate(0.5, 2.5, 0.5);

		poseStack.mulPose(new Quaternionf(new AxisAngle4d(rotAngleRad, 0, 1, 0)));

		RenderingUtils.renderModel(pole2, tile, RenderType.solid(), poseStack, bufferSource, packedLight, packedOverlay);

		poseStack.popPose();

		poseStack.pushPose();

		BakedModel pole3 = getModel(ClientRegister.MODEL_MONOPOLE_3);

		poseStack.translate(0.5, 5.5, 0.5);

		poseStack.mulPose(new Quaternionf(new AxisAngle4d(rotAngleRad, 0, 1, 0)));

		RenderingUtils.renderModel(pole3, tile, RenderType.solid(), poseStack, bufferSource, packedLight, packedOverlay);

		poseStack.popPose();

		poseStack.pushPose();

		BakedModel pole4 = getModel(ClientRegister.MODEL_MONOPOLE_4);

		poseStack.translate(0.5, 8.5, 0.5);

		poseStack.mulPose(new Quaternionf(new AxisAngle4d(rotAngleRad, 0, 1, 0)));

		RenderingUtils.renderModel(pole4, tile, RenderType.solid(), poseStack, bufferSource, packedLight, packedOverlay);

		poseStack.popPose();

		poseStack.pushPose();

		BakedModel pole5 = getModel(ClientRegister.MODEL_MONOPOLE_5);

		poseStack.translate(0.5, 11.5, 0.5);

		poseStack.mulPose(new Quaternionf(new AxisAngle4d(rotAngleRad, 0, 1, 0)));

		RenderingUtils.renderModel(pole5, tile, RenderType.solid(), poseStack, bufferSource, packedLight, packedOverlay);

		poseStack.popPose();

		poseStack.pushPose();

		BakedModel pole6 = getModel(ClientRegister.MODEL_MONOPOLE_6);

		poseStack.translate(0.5, 14.5, 0.5);

		poseStack.mulPose(new Quaternionf(new AxisAngle4d(rotAngleRad, 0, 1, 0)));

		RenderingUtils.renderModel(pole6, tile, RenderType.solid(), poseStack, bufferSource, packedLight, packedOverlay);

		poseStack.popPose();

		poseStack.pushPose();

		BakedModel pole7 = getModel(ClientRegister.MODEL_MONOPOLE_7);

		poseStack.translate(0.5, 17.5, 0.5);

		poseStack.mulPose(new Quaternionf(new AxisAngle4d(rotAngleRad, 0, 1, 0)));

		RenderingUtils.renderModel(pole7, tile, RenderType.solid(), poseStack, bufferSource, packedLight, packedOverlay);

		poseStack.popPose();

		poseStack.pushPose();

		BakedModel pole8 = getModel(ClientRegister.MODEL_MONOPOLE_8);

		poseStack.translate(0.5, 20.5, 0.5);

		poseStack.mulPose(new Quaternionf(new AxisAngle4d(rotAngleRad, 0, 1, 0)));

		RenderingUtils.renderModel(pole8, tile, RenderType.solid(), poseStack, bufferSource, packedLight, packedOverlay);

		poseStack.popPose();

		poseStack.pushPose();

		BakedModel pole9 = getModel(ClientRegister.MODEL_MONOPOLE_9);

		poseStack.translate(0.5, 22.5, 0.5);

		poseStack.mulPose(new Quaternionf(new AxisAngle4d(rotAngleRad, 0, 1, 0)));

		RenderingUtils.renderModel(pole9, tile, RenderType.solid(), poseStack, bufferSource, packedLight, packedOverlay);

		poseStack.popPose();

		BakedModel polearm = getModel(ClientRegister.MODEL_MONOPOLE_ARM);

		poseStack.pushPose();

		poseStack.translate(0.5, 0.5, 0.5);

		poseStack.mulPose(new Quaternionf(new AxisAngle4d(rotAngleRad - Mth.HALF_PI, 0, 1, 0)));

		poseStack.translate(-0.5, -0.5, -0.5);

		poseStack.translate(0.5, 22.5, 0.2);

		RenderingUtils.renderModel(polearm, tile, RenderType.solid(), poseStack, bufferSource, packedLight, packedOverlay);

		poseStack.popPose();

		poseStack.pushPose();

		poseStack.translate(0.5, 0.5, 0.5);

		poseStack.mulPose(new Quaternionf(new AxisAngle4d(rotAngleRad - Mth.HALF_PI, 0, 1, 0)));

		poseStack.translate(-0.5, -0.5, -0.5);

		poseStack.translate(0.5, 20, 0.15);

		RenderingUtils.renderModel(polearm, tile, RenderType.solid(), poseStack, bufferSource, packedLight, packedOverlay);

		poseStack.popPose();

		BakedModel poleinsulator = getModel(ClientRegister.MODEL_MONOPOLE_INSULATOR);

		poseStack.pushPose();

		poseStack.translate(0.5, 0.5, 0.5);

		poseStack.mulPose(new Quaternionf(new AxisAngle4d(rotAngleRad, 0, 1, 0)));

		poseStack.translate(-0.5, -0.5, -0.5);

		poseStack.translate(2.1935, 19, 0.5);

		RenderingUtils.renderModel(poleinsulator, tile, RenderType.solid(), poseStack, bufferSource, packedLight, packedOverlay);

		poseStack.popPose();

		float deltaX = 50;

		float a = 0.1F;

		float b = 2.0F;
		
		float[] color = {0.45F, 0.45F, 0.45F, 1.0F};

		float wireHeight = 0.1F;
		
		float halfDeltaX = deltaX / 2.0F;
		
		float maxY = IMathUtils.cosh(halfDeltaX, 0, a, b);

		List<AABB> aabbs = getWireAABBs(halfDeltaX, maxY, a, b, wireHeight, tile.getBlockPos());

		poseStack.pushPose();

		poseStack.translate(0.5, 0.5, 0.5);

		poseStack.mulPose(new Quaternionf(new AxisAngle4d(rotAngleRad - Mth.HALF_PI, 0, 1, 0)));

		poseStack.translate(-0.5, -0.5, -0.5);

		poseStack.translate(0.5 - deltaX, 20.5 - maxY - 1.875, -1.5 + 0.25 + 0.0375 / 6);

		VertexConsumer consumer = bufferSource.getBuffer(RenderType.solid());

		TextureAtlasSprite white = electrodynamics.client.ClientRegister.CACHED_TEXTUREATLASSPRITES.get(electrodynamics.client.ClientRegister.TEXTURE_WHITE);

		for (AABB aabb : aabbs) {

			RenderingUtils.renderFilledBox(poseStack, consumer, aabb, color[0], color[1], color[2], color[3], white.getU0(), white.getV0(), white.getU1(), white.getV1(), packedLight, packedOverlay);

		}

		poseStack.popPose();

		wireHeight = 0.05F;

		aabbs = getWireAABBs(halfDeltaX, maxY, a, b, wireHeight, tile.getBlockPos());

		poseStack.pushPose();

		poseStack.translate(0.5, 0.5, 0.5);

		poseStack.mulPose(new Quaternionf(new AxisAngle4d(rotAngleRad - Mth.HALF_PI, 0, 1, 0)));

		poseStack.translate(-0.5, -0.5, -0.5);

		poseStack.translate(0.5 - deltaX, 22.5 - maxY - 0.4375, -1.5 + 0.3125 + 0.0375 / 2);

		for (AABB aabb : aabbs) {

			RenderingUtils.renderFilledBox(poseStack, consumer, aabb, color[0], color[1], color[2], color[3], white.getU0(), white.getV0(), white.getU1(), white.getV1(), packedLight, packedOverlay);

		}

		poseStack.popPose();

		poseStack.popPose();

	}

	@Override
	public boolean shouldRender(TileMonopole pBlockEntity, Vec3 pCameraPos) {
		return true;
	}

	public static List<AABB> getWireAABBs(float halfDeltaX, float maxY, float a, float b, float wireHeight, BlockPos startPos) {

		float deltaH = maxY - 1.0F / b;

		List<AABB> aabbs = new ArrayList<>();

		int numIterations = (int) (deltaH / wireHeight);

		float yMaxIter = maxY;
		float yMinIter = maxY;

		float xMinIter = halfDeltaX;
		float xMaxIter = halfDeltaX;

		boolean crossedCenter = false;

		for (int i = 1; i <= numIterations; i++) {

			xMinIter = xMaxIter;
			yMaxIter = yMinIter;

			yMinIter = yMaxIter - wireHeight;
			xMaxIter = IMathUtils.acosh(yMinIter, 0, a, b);

			// can combine bottom AABBs if they intersect; should only be reached in the final iteration
			if (xMaxIter >= halfDeltaX) {

				crossedCenter = true;
				aabbs.add(new AABB(xMinIter, yMaxIter, 0, 2 * halfDeltaX - xMinIter, yMinIter, wireHeight));

				break; // JIC

			} else {
				// right of center
				aabbs.add(new AABB(xMinIter + halfDeltaX, yMaxIter, 0, xMaxIter + halfDeltaX, yMinIter, wireHeight));
				// left of center
				aabbs.add(new AABB(-xMinIter + halfDeltaX, yMinIter, 0, -xMaxIter + halfDeltaX, yMaxIter, wireHeight));
			}

		}

		if (crossedCenter) {
			return aabbs;
		}

		// fill the middle gap if it wasn't

		aabbs.add(new AABB(-xMaxIter + halfDeltaX, yMinIter, 0, xMaxIter + halfDeltaX, yMinIter - wireHeight, wireHeight));

		return aabbs;

	}

}
