package industrialelectricity.common.tile.monopole;

import java.util.List;

import com.mojang.datafixers.util.Pair;

import industrialelectricity.client.render.event.levelstage.HandlerMonopoleRendering;
import industrialelectricity.client.render.event.levelstage.HandlerMonopoleRendering.MonopoleData;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;

public class MonopoleClientBuffer {
	
	public static void postRenderData(BlockPos pos, MonopoleType type, float rotAngle, boolean renderArms, boolean armOnLeft, List<AABB> poleAABBs, float conductorAngle, boolean renderConductor, List<Pair<AABB, AABB>> mainConductorAABBs, List<Pair<AABB, AABB>> sheildWireAABBs) {
		HandlerMonopoleRendering.addRenderData(pos, new MonopoleData(type, rotAngle, renderArms, armOnLeft, poleAABBs, conductorAngle, renderConductor, mainConductorAABBs, sheildWireAABBs));
	}

}
