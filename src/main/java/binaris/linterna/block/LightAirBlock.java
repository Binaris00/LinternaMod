package binaris.linterna.block;

import binaris.linterna.LinternaMod;
import binaris.linterna.item.LinternaItem;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class LightAirBlock extends AirBlock implements BlockEntityProvider {
    public LightAirBlock() {
        super(FabricBlockSettings.copyOf(Blocks.AIR).luminance(15));
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new LightTimerBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return type == LinternaMod.LIGHT_TIMER_BLOCK_ENTITY ? (world1, pos, state1, blockEntity) -> LightTimerBlockEntity.tick(world1, pos, state1, (LightTimerBlockEntity) blockEntity) : null;
    }
}
