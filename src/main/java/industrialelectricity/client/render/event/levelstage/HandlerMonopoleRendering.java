package industrialelectricity.client.render.event.levelstage;

import java.util.HashMap;
import java.util.List;

import org.joml.AxisAngle4d;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;

import electrodynamics.Electrodynamics;
import electrodynamics.client.render.event.levelstage.AbstractLevelStageHandler;
import electrodynamics.prefab.utilities.RenderingUtils;
import electrodynamics.prefab.utilities.math.Color;
import industrialelectricity.client.ClientRegister;
import industrialelectricity.common.tile.monopole.MonopoleType;
import industrialelectricity.common.tile.monopole.TileMonopole;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RenderLevelStageEvent.Stage;

public class HandlerMonopoleRendering extends AbstractLevelStageHandler {

	public static final HandlerMonopoleRendering INSTANCE = new HandlerMonopoleRendering();

	private final HashMap<BlockPos, MonopoleData> monopolesToRender = new HashMap<>();

	private static final Color CABLE_COLOR = new Color(115, 115, 115, 256);

	@Override
	public boolean shouldRender(Stage stage) {
		return stage == Stage.AFTER_TRANSLUCENT_BLOCKS;
	}

	@Override
	public void clear() {
		monopolesToRender.clear();
	}

	@Override
	public void render(Camera camera, Frustum frustum, LevelRenderer renderer, PoseStack stack, Matrix4f projectionMatrix, Minecraft minecraft, int renderTick, float partialTick) {

		if (monopolesToRender.isEmpty()) {
			return;
		}

		int renderDistance = (int) (minecraft.gameRenderer.getRenderDistance() * 16.0F + (TileMonopole.MAX_TENSION_SPAN / 2.0));

		MultiBufferSource.BufferSource buffer = minecraft.renderBuffers().bufferSource();
		Vec3 camPos = camera.getPosition();

		TextureAtlasSprite white = electrodynamics.client.ClientRegister.CACHED_TEXTUREATLASSPRITES.get(electrodynamics.client.ClientRegister.TEXTURE_WHITE);

		float u0White = white.getU0();
		float u1White = white.getU1();
		float v0White = white.getV0();
		float v1White = white.getV1();

		stack.pushPose();
		stack.translate(-camPos.x, -camPos.y, -camPos.z);

		double minX = camPos.x - renderDistance;
		double maxX = camPos.x + renderDistance;

		double minZ = camPos.z - renderDistance;
		double maxZ = camPos.z + renderDistance;

		BakedModel base = getModel(minecraft, ClientRegister.MODEL_MONOPOLE_BASE);
		BakedModel pole1 = getModel(minecraft, ClientRegister.MODEL_MONOPOLE_1);
		BakedModel pole2 = getModel(minecraft, ClientRegister.MODEL_MONOPOLE_2);
		BakedModel pole3 = getModel(minecraft, ClientRegister.MODEL_MONOPOLE_3);
		BakedModel pole4 = getModel(minecraft, ClientRegister.MODEL_MONOPOLE_4);
		BakedModel pole5 = getModel(minecraft, ClientRegister.MODEL_MONOPOLE_5);
		BakedModel pole6 = getModel(minecraft, ClientRegister.MODEL_MONOPOLE_6);
		BakedModel pole7 = getModel(minecraft, ClientRegister.MODEL_MONOPOLE_7);
		BakedModel pole8 = getModel(minecraft, ClientRegister.MODEL_MONOPOLE_8);
		BakedModel pole9 = getModel(minecraft, ClientRegister.MODEL_MONOPOLE_9);
		BakedModel polearm = getModel(minecraft, ClientRegister.MODEL_MONOPOLE_ARM);
		BakedModel poleinsulator = getModel(minecraft, ClientRegister.MODEL_MONOPOLE_INSULATOR);

		VertexConsumer consumer = buffer.getBuffer(RenderType.solid());
		
		

		monopolesToRender.forEach((pos, data) -> {
			
			// filter out poles that are outside of render distance
			if (pos.getX() > maxX || pos.getX() < minX || pos.getZ() > maxZ || pos.getX() < minZ || !data.renderConductor) {
				return;
			}

			switch (data.type) {

			case TANGENT:

				if (!data.renderConductor) {

					return;

				}

				stack.pushPose();

				if (minecraft.getEntityRenderDispatcher().shouldRenderHitBoxes()) {

					data.sheildWireAABBs.forEach(pair -> {

						RenderingUtils.renderFilledBox(stack, consumer, pair.getSecond(), 1.0F, 0, 0, 1.0F, u0White, v0White, u1White, v1White, LevelRenderer.getLightColor(minecraft.level, pos), OverlayTexture.NO_OVERLAY);

					});

					data.mainConductorAABBs.forEach(pair -> {

						RenderingUtils.renderFilledBox(stack, consumer, pair.getSecond(), 1.0F, 0, 0, 1.0F, u0White, v0White, u1White, v1White, LevelRenderer.getLightColor(minecraft.level, pos), OverlayTexture.NO_OVERLAY);

					});

				} else {

					stack.translate(0.5 + pos.getX(), 0.5 + pos.getY(), 0.5 + pos.getZ());

					stack.mulPose(new Quaternionf(new AxisAngle4d(data.conductorAngle, 0, 1, 0)));

					stack.translate(-0.5 - pos.getX(), -0.5 - pos.getY(), -0.5 - pos.getZ());

					stack.pushPose();

					data.mainConductorAABBs.forEach(pair -> {

						if (frustum.isVisible(pair.getSecond())) {

							RenderingUtils.renderFilledBox(stack, consumer, pair.getFirst(), CABLE_COLOR.rFloat(), CABLE_COLOR.gFloat(), CABLE_COLOR.bFloat(), CABLE_COLOR.aFloat(), u0White, v0White, u1White, v1White, LevelRenderer.getLightColor(minecraft.level, pos), OverlayTexture.NO_OVERLAY);

						}

					});

					data.sheildWireAABBs.forEach(pair -> {

						if (frustum.isVisible(pair.getSecond())) {

							RenderingUtils.renderFilledBox(stack, consumer, pair.getFirst(), CABLE_COLOR.rFloat(), CABLE_COLOR.gFloat(), CABLE_COLOR.bFloat(), CABLE_COLOR.aFloat(), u0White, v0White, u1White, v1White, LevelRenderer.getLightColor(minecraft.level, pos), OverlayTexture.NO_OVERLAY);

						}

					});

					stack.popPose();

				}

				stack.popPose();

				break;

			case ANGLE:

				break;

			case DEADEND:

				break;

			}

		});

		buffer.endBatch(RenderType.solid());

		monopolesToRender.forEach((pos, data) -> {

			// filter out poles that are outside of render distance
			if (pos.getX() > maxX || pos.getX() < minX || pos.getZ() > maxZ || pos.getX() < minZ) {
				return;
			}

			Quaternionf poleRot = new Quaternionf(new AxisAngle4d(data.rotAngle(), 0, 1, 0));

			switch (data.type) {

			case TANGENT:

				stack.pushPose();

				stack.translate(pos.getX(), pos.getY(), pos.getZ());

				stack.pushPose();

				if (frustum.isVisible(data.poleAABBs.get(0))) { // AABBs are for rendering bounding box
					stack.pushPose();

					stack.translate(0.5, -0.5, 0.5);

					RenderingUtils.renderModel(base, null, RenderType.solid(), stack, buffer, LevelRenderer.getLightColor(minecraft.level, pos), OverlayTexture.NO_OVERLAY);

					stack.popPose();
				}

				stack.translate(0.5, 0.5, 0.5);

				stack.mulPose(poleRot);

				stack.translate(-0.5, -0.5, -0.5);

				if (frustum.isVisible(data.poleAABBs.get(1))) {
					stack.pushPose();

					stack.translate(0.5, -0.5, 0.5);

					RenderingUtils.renderModel(pole1, null, RenderType.solid(), stack, buffer, LevelRenderer.getLightColor(minecraft.level, pos), OverlayTexture.NO_OVERLAY);

					stack.popPose();
				}

				if (frustum.isVisible(data.poleAABBs.get(2))) {
					stack.pushPose();

					stack.translate(0.5, 2.5, 0.5);

					RenderingUtils.renderModel(pole2, null, RenderType.solid(), stack, buffer, LevelRenderer.getLightColor(minecraft.level, pos), OverlayTexture.NO_OVERLAY);

					stack.popPose();
				}

				if (frustum.isVisible(data.poleAABBs.get(3))) {
					stack.pushPose();

					stack.translate(0.5, 5.5, 0.5);

					RenderingUtils.renderModel(pole3, null, RenderType.solid(), stack, buffer, LevelRenderer.getLightColor(minecraft.level, pos), OverlayTexture.NO_OVERLAY);

					stack.popPose();
				}

				if (frustum.isVisible(data.poleAABBs.get(4))) {
					stack.pushPose();

					stack.translate(0.5, 8.5, 0.5);

					RenderingUtils.renderModel(pole4, null, RenderType.solid(), stack, buffer, LevelRenderer.getLightColor(minecraft.level, pos), OverlayTexture.NO_OVERLAY);

					stack.popPose();
				}

				if (frustum.isVisible(data.poleAABBs.get(5))) {
					stack.pushPose();

					stack.translate(0.5, 11.5, 0.5);

					RenderingUtils.renderModel(pole5, null, RenderType.solid(), stack, buffer, LevelRenderer.getLightColor(minecraft.level, pos), OverlayTexture.NO_OVERLAY);

					stack.popPose();
				}

				if (frustum.isVisible(data.poleAABBs.get(6))) {
					stack.pushPose();

					stack.translate(0.5, 14.5, 0.5);

					RenderingUtils.renderModel(pole6, null, RenderType.solid(), stack, buffer, LevelRenderer.getLightColor(minecraft.level, pos), OverlayTexture.NO_OVERLAY);

					stack.popPose();
				}

				if (frustum.isVisible(data.poleAABBs.get(7))) {
					stack.pushPose();

					stack.translate(0.5, 17.5, 0.5);

					RenderingUtils.renderModel(pole7, null, RenderType.solid(), stack, buffer, LevelRenderer.getLightColor(minecraft.level, pos), OverlayTexture.NO_OVERLAY);

					stack.popPose();
				}

				if (frustum.isVisible(data.poleAABBs.get(8))) {
					stack.pushPose();

					stack.translate(0.5, 20.5, 0.5);

					RenderingUtils.renderModel(pole8, null, RenderType.solid(), stack, buffer, LevelRenderer.getLightColor(minecraft.level, pos), OverlayTexture.NO_OVERLAY);

					stack.popPose();
				}

				if (frustum.isVisible(data.poleAABBs.get(9))) {
					stack.pushPose();

					stack.translate(0.5, 22.5, 0.5);

					RenderingUtils.renderModel(pole9, null, RenderType.solid(), stack, buffer, LevelRenderer.getLightColor(minecraft.level, pos), OverlayTexture.NO_OVERLAY);

					stack.popPose();
				}

				stack.popPose();

				if (data.renderArms) {

					stack.pushPose();

					stack.translate(0.5, 0.5, 0.5);

					stack.mulPose(new Quaternionf(new AxisAngle4d(data.rotAngle(), 0, 1, 0)));

					stack.translate(-0.5, -0.5, -0.5);

					if (frustum.isVisible(data.poleAABBs.get(10))) {
						stack.pushPose();

						stack.translate(0.5, 22.5, 0.2);

						RenderingUtils.renderModel(polearm, null, RenderType.solid(), stack, buffer, LevelRenderer.getLightColor(minecraft.level, pos), OverlayTexture.NO_OVERLAY);

						stack.popPose();

					}

					if (frustum.isVisible(data.poleAABBs.get(11))) {
						stack.pushPose();

						stack.translate(0.5, 20, 0.15);

						RenderingUtils.renderModel(polearm, null, RenderType.solid(), stack, buffer, LevelRenderer.getLightColor(minecraft.level, pos), OverlayTexture.NO_OVERLAY);

						stack.popPose();
					}

					stack.popPose();

					if (frustum.isVisible(data.poleAABBs.get(12))) {

						stack.pushPose();

						stack.translate(0.5, 0.5, 0.5);

						stack.mulPose(new Quaternionf(new AxisAngle4d(data.armOnLeft ? data.rotAngle : -data.rotAngle, 0, 1, 0)));

						stack.translate(-0.5, -0.5, -0.5);

						stack.translate(0.5, 19, data.armOnLeft ? -1.1935 : 2.1935);

						RenderingUtils.renderModel(poleinsulator, null, RenderType.solid(), stack, buffer, LevelRenderer.getLightColor(minecraft.level, pos), OverlayTexture.NO_OVERLAY);

						stack.popPose();

					}

				}

				stack.popPose();

				break;

			case ANGLE:

				break;

			case DEADEND:

				break;

			}

		});

	}

	private BakedModel getModel(Minecraft minecraft, ResourceLocation loc) {
		return minecraft.getModelManager().getModel(loc);
	}

	public static void addRenderData(BlockPos pos, MonopoleData data) {
		INSTANCE.monopolesToRender.put(pos, data);
	}

	public static void removeRenderData(BlockPos pos) {
		INSTANCE.monopolesToRender.remove(pos);
	}

	public static record MonopoleData(MonopoleType type, float rotAngle, boolean renderArms, boolean armOnLeft, List<AABB> poleAABBs, float conductorAngle, boolean renderConductor, List<Pair<AABB, AABB>> mainConductorAABBs, List<Pair<AABB, AABB>> sheildWireAABBs) {

	}

}
