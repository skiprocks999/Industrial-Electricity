package industrialelectricity.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import industrialelectricity.References;
import industrialelectricity.client.render.tile.ClientEvents;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.event.ModelEvent.RegisterAdditional;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = References.MOD_ID, bus = Bus.MOD, value = { Dist.CLIENT })
public class ClientRegister {

	private static final String BLOCK_LOC = References.MOD_ID + ":block/";

	public static final ResourceLocation MODEL_MONOPOLE_BASE = new ResourceLocation(BLOCK_LOC + "monopole/monopolebase");
	public static final ResourceLocation MODEL_MONOPOLE_ARM = new ResourceLocation(BLOCK_LOC + "monopole/monopolearm");
	public static final ResourceLocation MODEL_MONOPOLE_INSULATOR = new ResourceLocation(BLOCK_LOC + "monopole/insulator");
	public static final ResourceLocation MODEL_MONOPOLE_1 = new ResourceLocation(BLOCK_LOC + "monopole/monopole1");
	public static final ResourceLocation MODEL_MONOPOLE_2 = new ResourceLocation(BLOCK_LOC + "monopole/monopole2");
	public static final ResourceLocation MODEL_MONOPOLE_3 = new ResourceLocation(BLOCK_LOC + "monopole/monopole3");
	public static final ResourceLocation MODEL_MONOPOLE_4 = new ResourceLocation(BLOCK_LOC + "monopole/monopole4");
	public static final ResourceLocation MODEL_MONOPOLE_5 = new ResourceLocation(BLOCK_LOC + "monopole/monopole5");
	public static final ResourceLocation MODEL_MONOPOLE_6 = new ResourceLocation(BLOCK_LOC + "monopole/monopole6");
	public static final ResourceLocation MODEL_MONOPOLE_7 = new ResourceLocation(BLOCK_LOC + "monopole/monopole7");
	public static final ResourceLocation MODEL_MONOPOLE_8 = new ResourceLocation(BLOCK_LOC + "monopole/monopole8");
	public static final ResourceLocation MODEL_MONOPOLE_9 = new ResourceLocation(BLOCK_LOC + "monopole/monopole9");
	
	public static final ResourceLocation TEXTURE_CONDUCTOR = new ResourceLocation(BLOCK_LOC + "conductor");
	
	public static HashMap<ResourceLocation, TextureAtlasSprite> CACHED_TEXTUREATLASSPRITES = new HashMap<>();
	// for registration purposes only!
	private static final List<ResourceLocation> CUSTOM_TEXTURES = new ArrayList<>();

	public static void setup() {

	}

	@SubscribeEvent
	public static void onModelEvent(RegisterAdditional event) {
		event.register(MODEL_MONOPOLE_BASE);
		event.register(MODEL_MONOPOLE_ARM);
		event.register(MODEL_MONOPOLE_INSULATOR);
		event.register(MODEL_MONOPOLE_1);
		event.register(MODEL_MONOPOLE_2);
		event.register(MODEL_MONOPOLE_3);
		event.register(MODEL_MONOPOLE_4);
		event.register(MODEL_MONOPOLE_5);
		event.register(MODEL_MONOPOLE_6);
		event.register(MODEL_MONOPOLE_7);
		event.register(MODEL_MONOPOLE_8);
		event.register(MODEL_MONOPOLE_9);
	}

	@SubscribeEvent
	public static void registerEntities(EntityRenderersEvent.RegisterRenderers event) {

		//event.registerBlockEntityRenderer(IndustrialElectricityTiles.TILE_MONOPOLE.get(), RenderTileMonopole::new);
		
		ClientEvents.init();
		
	}
	
	static {
		CUSTOM_TEXTURES.add(ClientRegister.TEXTURE_CONDUCTOR);
	}

	@SubscribeEvent
	public static void cacheCustomTextureAtlases(TextureStitchEvent.Post event) {
		if (event.getAtlas().location().equals(TextureAtlas.LOCATION_BLOCKS)) {
			for (ResourceLocation loc : CUSTOM_TEXTURES) {
				ClientRegister.CACHED_TEXTUREATLASSPRITES.put(loc, event.getAtlas().getSprite(loc));
			}
		}
	}

}
