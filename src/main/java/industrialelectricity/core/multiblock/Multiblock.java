package industrialelectricity.core.multiblock;

import java.util.List;
import java.util.Map;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import industrialelectricity.References;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DataPackRegistryEvent;

public record Multiblock(Map<Direction, List<MultiblockSlaveNode>> nodes) {

	public static final ResourceKey<Registry<Multiblock>> REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(References.MOD_ID, "multiblock"));

	public static final String FOLDER = "machines/multiblock";

	public static final String MEMBER_FIELD = "members";

	public static final Codec<Multiblock> CODEC = RecordCodecBuilder.create(instance ->

	instance.group(

			Codec.unboundedMap(Direction.CODEC, MultiblockSlaveNode.CODEC.listOf()).fieldOf(MEMBER_FIELD).forGetter(Multiblock::nodes)

	).apply(instance, nodes -> new Multiblock(nodes)));

	public static List<MultiblockSlaveNode> getNodes(Level world, ResourceKey<Multiblock> id, Direction facing) {
		return world.registryAccess().lookupOrThrow(Multiblock.REGISTRY_KEY).getOrThrow(id).get().nodes().get(facing);
	}

	@EventBusSubscriber(modid = References.MOD_ID, bus = Bus.MOD)
	private static final class MultiblockRegistry {

		@SubscribeEvent
		public static void registerMultiblocks(DataPackRegistryEvent.NewRegistry event) {
			event.dataPackRegistry(REGISTRY_KEY, CODEC, CODEC);
		}

	}

}
