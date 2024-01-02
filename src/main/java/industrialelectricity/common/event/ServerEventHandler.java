package industrialelectricity.common.event;

import industrialelectricity.References;
import industrialelectricity.core.multiblock.CommandScanMultiblock;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = References.MOD_ID, bus = Bus.FORGE)
public class ServerEventHandler {
	
	@SubscribeEvent
	public static void registerCommands(RegisterCommandsEvent event) {
		CommandScanMultiblock.register(event.getDispatcher());
	}

}
