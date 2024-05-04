package binaris.linterna;

import binaris.linterna.block.LightAirBlock;
import binaris.linterna.block.LightTimerBlockEntity;
import binaris.linterna.item.LinternaItem;
import binaris.linterna.network.FabricPacketHandler;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LinternaMod implements ModInitializer {
	public static final String MOD_ID = "linterna-mod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final Identifier PACKET_ID = new Identifier(MOD_ID, "light_timer_packet");

	// =================== Registries ===================
	public static Item LINTERNA_ITEM = new LinternaItem();
	public static Block LIGHT_AIR_BLOCK = new LightAirBlock();

	public static final BlockEntityType<LightTimerBlockEntity> LIGHT_TIMER_BLOCK_ENTITY = Registry.register(
			Registries.BLOCK_ENTITY_TYPE,
			new Identifier(MOD_ID, "linterna_block_entity"),
			FabricBlockEntityTypeBuilder.create(LightTimerBlockEntity::new, LIGHT_AIR_BLOCK).build()
	);


	@Override
	public void onInitialize() {
		LOGGER.info("Iniciando LinternaItem Mod!");
		LOGGER.info("Mod hecho como prueba para Eufonia");
		FabricPacketHandler.init();
		Registry.register(Registries.ITEM, new Identifier(MOD_ID, "linterna"), LINTERNA_ITEM);
		Registry.register(Registries.BLOCK, new Identifier(MOD_ID, "light_air"), LIGHT_AIR_BLOCK);
	}
}