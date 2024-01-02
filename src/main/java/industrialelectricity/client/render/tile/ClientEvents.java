package industrialelectricity.client.render.tile;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.client.guidebook.ScreenGuidebook;
import electrodynamics.client.render.event.levelstage.AbstractLevelStageHandler;
import industrialelectricity.client.render.event.levelstage.HandlerMonopoleRendering;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ClientEvents {

	private static final List<AbstractLevelStageHandler> LEVEL_STAGE_RENDER_HANDLERS = new ArrayList<>();
	
	public static void init() {
		
		LEVEL_STAGE_RENDER_HANDLERS.add(HandlerMonopoleRendering.INSTANCE);
		
	}
	
	@SubscribeEvent
	public static void handleRenderEvents(RenderLevelStageEvent event) {
		LEVEL_STAGE_RENDER_HANDLERS.forEach(handler -> {
			if (handler.shouldRender(event.getStage())) {
				handler.render(event.getCamera(), event.getFrustum(), event.getLevelRenderer(), event.getPoseStack(), event.getProjectionMatrix(), Minecraft.getInstance(), event.getRenderTick(), event.getPartialTick());
			}
		});
	}

	@SubscribeEvent
	public static void wipeRenderHashes(ClientPlayerNetworkEvent.LoggingOut event) {
		ScreenGuidebook.setInitNotHappened();
		Player player = event.getPlayer();
		if (player != null) {
			LEVEL_STAGE_RENDER_HANDLERS.forEach(AbstractLevelStageHandler::clear);
		}
	}
	
}
