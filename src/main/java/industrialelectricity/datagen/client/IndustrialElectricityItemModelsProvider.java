package industrialelectricity.datagen.client;

import electrodynamics.datagen.client.ElectrodynamicsItemModelsProvider;
import industrialelectricity.References;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;

public class IndustrialElectricityItemModelsProvider extends ElectrodynamicsItemModelsProvider {

	public IndustrialElectricityItemModelsProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
		super(output, existingFileHelper, References.MOD_ID);
	}
	
	@Override
	protected void registerModels() {
		
	}

}
