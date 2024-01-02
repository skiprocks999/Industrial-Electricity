package industrialelectricity.datagen.client;

import electrodynamics.datagen.client.ElectrodynamicsBlockStateProvider;
import industrialelectricity.References;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;

public class IndustialElectricityBlockStateProvider extends ElectrodynamicsBlockStateProvider {

	public IndustialElectricityBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
		super(output, exFileHelper, References.MOD_ID);
	}
	
	@Override
	protected void registerStatesAndModels() {
		
	}

}
