package industrialelectricity.common.tile.monopole;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.mojang.datafixers.util.Pair;

import electrodynamics.Electrodynamics;
import electrodynamics.common.tile.machines.quarry.TileQuarry;
import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyType;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import industrialelectricity.client.render.event.levelstage.HandlerMonopoleRendering;
import industrialelectricity.core.utils.IMathUtils;
import industrialelectricity.registries.IndustrialElectricityTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class TileMonopole extends GenericTile {

	public static final int MAX_TENSION_SPAN = 150;

	public static final int MAX_HEIGHT_CHANGE = 40;

	public static final float MAIN_CONDUCTOR_SIZE = 0.1F;
	public static final float SHIELD_CONDUCTOR_SIZE = 0.05F;

	public static final double RUN_TO_RISE_RATIO = 5.0D / Math.sqrt(11.0D);

	public final Property<Integer> monopoleType = property(new Property<>(PropertyType.Integer, "monopoletype", 0));
	public final Property<Float> monopoleRot = property(new Property<>(PropertyType.Float, "monopolerot", 0.0F));
	public final Property<Float> monopoleConductorRot = property(new Property<>(PropertyType.Float, "monopoleconductorrot", 0.0F));
	public final Property<Float> distanceBetweenPoles = property(new Property<>(PropertyType.Float, "distancebetweenpoles", 0.0F));
	public final Property<Boolean> renderMonopoleArms = property(new Property<>(PropertyType.Boolean, "rendermonopolearms", false));
	public final Property<Boolean> armsOnLeft = property(new Property<>(PropertyType.Boolean, "armsonleft", false));
	public final Property<Boolean> renderMonopoleConductor = property(new Property<>(PropertyType.Boolean, "rendermonopoleconductor", false));

	public final Property<BlockPos> incomingTowerPos = property(new Property<>(PropertyType.BlockPos, "incomingtowerpos", TileQuarry.OUT_OF_REACH));
	public final Property<BlockPos> outgoingTowerPos = property(new Property<>(PropertyType.BlockPos, "outgoingtowerpos", TileQuarry.OUT_OF_REACH));

	public TileMonopole(BlockPos worldPos, BlockState blockState) {
		super(IndustrialElectricityTiles.TILE_MONOPOLE.get(), worldPos, blockState);
		addComponent(new ComponentTickable(this).tickClient(this::tickClient));
		addComponent(new ComponentPacketHandler(this));
	}

	private int iterI;
	private int iterJ;

	public void tickClient(ComponentTickable tickable) {
		

		if (tickable.getTicks() % 1 == 0) {

			iterI = iterI % MAX_TENSION_SPAN;

			if (iterI == 0) {

				iterJ = iterJ % MAX_HEIGHT_CHANGE;

				iterJ++;

			}

			if (iterJ < (int) Math.floor(iterI / RUN_TO_RISE_RATIO)) {
				// Electrodynamics.LOGGER.info("i : " + iterI + ", j : " + iterJ);
				//addRenderData(iterI, iterJ);
			}

			iterI++;

		}
	}

	@Override
	public void onLoad() {
		super.onLoad();

		if (level.isClientSide) {
			addRenderData(50, 30);
		}

	}

	private void addRenderData(int posX, int posY) {
		switch (getMonopoleType()) {

		// NOTE run must be 5/sqrt(11) times greater than rise

		case TANGENT:

			if (posY == 0) {
				outgoingTowerPos.set(getBlockPos().relative(Direction.WEST, posX));
			} else {
				outgoingTowerPos.set(getBlockPos().relative(Direction.WEST, posX).relative(Direction.UP, posY));
			}

			float heightDifference = outgoingTowerPos.get().getY() - getBlockPos().getY();

			// https://mathhelpforum.com/t/catenary-cable-different-heights.96398/

			monopoleRot.set(0);
			monopoleConductorRot.set(0);

			renderMonopoleArms.set(true);
			renderMonopoleConductor.set(true);
			armsOnLeft.set(true);

			BlockPos to = outgoingTowerPos.get();

			float horizontalDistance = Mth.sqrt(Mth.square(to.getX() - getBlockPos().getX()) + Mth.square(to.getZ() - getBlockPos().getZ()));
			float verticalDistance = Math.abs(heightDifference);

			float signOfDiff = heightDifference == 0 ? 1 : -Math.signum(heightDifference);

			float cableLength = horizontalDistance * getTension(horizontalDistance, verticalDistance);

			float guessA = Mth.sqrt(Mth.sqrt(Mth.square(cableLength) - Mth.square(verticalDistance)));

			float a = IMathUtils.newtIterForA(guessA, horizontalDistance, verticalDistance, cableLength);

			//Electrodynamics.LOGGER.info("i : " + iterI + ", j : " + iterJ + ", is nan : " + (a != a) + ", ten : " + getTension(horizontalDistance, verticalDistance));

			float cons = (float) Math.log((cableLength + verticalDistance) / (cableLength - verticalDistance)) * a;

			float x1 = 0.5F * (cons - horizontalDistance);
			float x2 = 0.5F * (cons + horizontalDistance);

			float y1 = IMathUtils.cosh(x1, 0, a, a);
			float y2 = IMathUtils.cosh(x2, 0, a, a);

			float heightOff = signOfDiff < 0 ? Math.min(y1, y2) : Math.max(y1, y2);

			float xOffsetMain = 0.5F;// 0.5F - horizontalDistance;
			float yOffsetMain = 20.5F - heightOff - 1.875F;
			float zOffsetMain = -1.5F + 0.25F + 0.0375F / 6F;

			float xOffsetShield = 0.5F; // - deltaX;
			float yOffsetSheied = 22.5F - heightOff - 0.4375F;
			float zOffsetShield = -1.5F + 0.3125F + 0.0375F / 2F;

			MonopoleClientBuffer.postRenderData(getBlockPos(), getMonopoleType(), monopoleRot.get(), renderMonopoleArms.get(), armsOnLeft.get(), getMonopoleAABBs(), monopoleConductorRot.get(), renderMonopoleConductor.get(), getWireAABBsUnequalHeight(signOfDiff, horizontalDistance, xOffsetMain, yOffsetMain, zOffsetMain, x1, x2, y1, y2, a, a, MAIN_CONDUCTOR_SIZE),
					getWireAABBsUnequalHeight(signOfDiff, horizontalDistance, xOffsetShield, yOffsetSheied, zOffsetShield, x1, x2, y1, y2, a, a, SHIELD_CONDUCTOR_SIZE));

			break;

		case ANGLE:

			break;

		case DEADEND:

			break;

		}
	}

	public MonopoleType getMonopoleType() {
		return MonopoleType.values()[monopoleType.get()];
	}

	@Override
	public void onBlockDestroyed() {
		HandlerMonopoleRendering.removeRenderData(getBlockPos());
	}

	@Override
	public void onChunkUnloaded() {
		HandlerMonopoleRendering.removeRenderData(getBlockPos());
	}

	public List<Pair<AABB, AABB>> getWireAABBsUnequalHeight(float sign, float horDistance, float xOffset, float yOffset, float zOffset, float x1, float x2, float y1, float y2, float a, float b, float wireHeight) {

		List<AABB> aabbs = new ArrayList<>();

		// Left side

		float distanceFromCenter = sign < 0 ? x1 : -x2;

		float deltaHLeft = y1 - IMathUtils.cosh(0, 0, a, b);

		int numIterationsLeft = (int) (deltaHLeft / wireHeight);

		float yMaxIterLeft = y1;
		float yMinIterLeft = y1;

		float xMinIterLeft = x1;
		float xMaxIterLeft = x1;

		float deltaX;

		int xIter;

		for (int i = 0; i < numIterationsLeft; i++) {

			xMinIterLeft = xMaxIterLeft;
			yMaxIterLeft = yMinIterLeft;

			yMinIterLeft = yMaxIterLeft - wireHeight;
			xMaxIterLeft = -IMathUtils.acosh(yMinIterLeft, 0, a, b);

			if (xMaxIterLeft > 0) {

				break;

			}

			deltaX = xMaxIterLeft - xMinIterLeft;

			if (deltaX <= 1.0F) {
				aabbs.add(new AABB(sign * xMinIterLeft + distanceFromCenter, yMinIterLeft, 0, sign * xMaxIterLeft + distanceFromCenter, yMaxIterLeft, wireHeight));
				continue;
			}

			xIter = (int) deltaX;

			for (int j = 0; j < xIter; j++) {

				aabbs.add(new AABB(sign * (xMinIterLeft + j) + distanceFromCenter, yMinIterLeft, 0, sign * (xMinIterLeft + j + 1) + distanceFromCenter, yMaxIterLeft, wireHeight));

			}

			aabbs.add(new AABB(sign * (xMinIterLeft + xIter) + distanceFromCenter, yMinIterLeft, 0, sign * xMaxIterLeft + distanceFromCenter, yMaxIterLeft, wireHeight));

		}

		// Right side

		float deltaHRight = y2 - b;

		int numIterationsRight = (int) (deltaHRight / wireHeight);

		float yMaxIterRight = y2;
		float yMinIterRight = y2;

		float xMinIterRight = x2;
		float xMaxIterRight = x2;

		for (int i = 0; i < numIterationsRight; i++) {

			xMinIterRight = xMaxIterRight;
			yMaxIterRight = yMinIterRight;

			yMinIterRight = yMaxIterRight - wireHeight;
			xMaxIterRight = IMathUtils.acosh(yMinIterRight, 0, a, b);

			if (xMaxIterRight < 0) {

				break;

			}

			deltaX = xMinIterRight - xMaxIterRight;

			if (deltaX <= 1.0F) {

				aabbs.add(new AABB(sign * xMaxIterRight + distanceFromCenter, yMinIterRight, 0, sign * xMinIterRight + distanceFromCenter, yMaxIterRight, wireHeight));

				continue;

			}

			xIter = (int) deltaX;

			for (int j = 0; j < xIter; j++) {

				aabbs.add(new AABB(sign * (xMaxIterRight + j) + distanceFromCenter, yMinIterRight, 0, sign * (xMaxIterRight + j + 1) + distanceFromCenter, yMaxIterRight, wireHeight));

			}

			aabbs.add(new AABB(sign * (xMaxIterRight + xIter) + distanceFromCenter, yMinIterRight, 0, sign * xMinIterRight + distanceFromCenter, yMaxIterRight, wireHeight));

		}

		// Fill in gap in center

		float yMaxGap = yMaxIterLeft > yMaxIterRight ? yMaxIterLeft : yMaxIterRight;

		deltaX = xMaxIterRight - xMaxIterLeft;

		xIter = (int) deltaX;

		for (int j = 0; j < xIter; j++) {

			aabbs.add(new AABB(sign * (xMaxIterLeft + j) + distanceFromCenter, yMaxGap - wireHeight, 0, sign * (xMaxIterLeft + j + 1) + distanceFromCenter, yMaxGap - 2.0F * wireHeight, wireHeight));

		}

		aabbs.add(new AABB(sign * (xMaxIterLeft + xIter) + distanceFromCenter, yMaxGap - wireHeight, 0, sign * xMaxIterRight + distanceFromCenter, yMaxGap - 2.0F * wireHeight, wireHeight));

		switch (getMonopoleType()) {

		case TANGENT:

			List<Pair<AABB, AABB>> list = new ArrayList<>();

			float cosineAngle = (float) Math.cos(monopoleConductorRot.get());
			float sineAngle = (float) Math.sin(monopoleConductorRot.get());

			float xLength, xCenter;

			for (AABB aabb : aabbs) {
				xLength = (float) (aabb.maxX - aabb.minX);
				xCenter = (float) (aabb.minX + (xLength / 2.0F));

				list.add(Pair.of(aabb.move(xOffset, yOffset, zOffset).move(getBlockPos()), aabb.move(xOffset, yOffset, zOffset).move(getBlockPos()).move(-xCenter + cosineAngle * (xCenter) - sineAngle * xOffset, 0, -sineAngle * (xCenter)).inflate(0.5)));
			}

			return list;

		case ANGLE:

		case DEADEND:

		}

		return Collections.emptyList();

	}

	private float getTension(float horizontalDistance, float verticalDistance) {

		float vOverH = verticalDistance / horizontalDistance;

		// return 1.0025F;

		if (vOverH <= 0.03F) {
			return 1.0025F;
		}
		
		int divisions = 1000;
		
		float deltaY = (1.2F - 1.0025F) / ((float) divisions);
		
		float maxVOverH = (float) (1.0F / RUN_TO_RISE_RATIO);
		
		float deltaX = (maxVOverH) / ((float) divisions);
		
		float addon = 0;
		
		if(vOverH >= 0.55 && vOverH <= 0.6) {
			addon = 0.027F;
		}
		
		if(vOverH >= 0.5 && vOverH <= 0.55) {
			addon = 0.02F;
		}
		
		if(vOverH > 0.6 && vOverH < 0.615) {
			addon = 0.04F;
		}
		
		if(vOverH >= 0.615 && vOverH < 0.625) {
			addon = 0.042F;
		}
		
		if(vOverH >= 0.625 && vOverH < 0.639) {
			addon = 0.046F;
		}
		
		if(vOverH >= 0.639 && vOverH < 0.7) {
			addon = 0.049F;
		}
		
		if(vOverH > 0.1 && vOverH < 0.2) {
			addon = -0.02F;
		}
		
		if(vOverH >= 0.2 && vOverH < 0.3) {
			addon = -0.01F;
		}
		
		if(vOverH > 0.05 && vOverH <= 0.1) {
			addon = -0.015F;
		}

		if(vOverH > 0.03 && vOverH < 0.05) {
			addon = -0.01F;
		}
		
		for (int i = 1; i <= divisions; i++) {

			if (vOverH < i * deltaX) {
				
				return 1.0025F + deltaY * i + addon;
			}

		}

		return 1.2F;

	}

	public List<AABB> getMonopoleAABBs() {

		switch (getMonopoleType()) {

		case TANGENT:

			List<AABB> aabbs = new ArrayList<>();

			aabbs.add(new AABB(-1, -1, -2, 1, 0, 1).inflate(1).move(getBlockPos()));
			aabbs.add(new AABB(0, 0, 0, 1, 1, 1).inflate(1).move(getBlockPos()));
			aabbs.add(new AABB(0, 1, 0, 1, 4, 1).inflate(1).move(getBlockPos()));
			aabbs.add(new AABB(0, 4, 0, 1, 7, 1).inflate(1).move(getBlockPos()));
			aabbs.add(new AABB(0, 7, 0, 1, 10, 1).inflate(1).move(getBlockPos()));
			aabbs.add(new AABB(0, 10, 0, 1, 13, 1).inflate(1).move(getBlockPos()));
			aabbs.add(new AABB(0, 13, 0, 1, 17, 1).inflate(1).move(getBlockPos()));
			aabbs.add(new AABB(0, 17, 0, 1, 20, 1).inflate(1).move(getBlockPos()));
			aabbs.add(new AABB(0, 20, 0, 1, 23, 1).inflate(1).move(getBlockPos()));
			aabbs.add(new AABB(0, 23, 0, 1, 24, 1).inflate(1).move(getBlockPos()));
			aabbs.add(new AABB(-5, 22, -5, 5, 23, 5).inflate(1).move(getBlockPos()));
			aabbs.add(new AABB(-5, 19.5, -5, 5, 20.5, 5).inflate(1).move(getBlockPos()));
			aabbs.add(new AABB(-3, 17, -3, 3, 19, 3).inflate(1).move(getBlockPos()));

			return aabbs;

		case ANGLE:
			return Collections.emptyList();

		case DEADEND:
			return Collections.emptyList();

		}

		return Collections.emptyList();

	}

}
