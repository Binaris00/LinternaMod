package binaris.linterna.block;

import binaris.linterna.LinternaMod;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Block entity para {@link binaris.linterna.block.LightAirBlock LightTimerBlock}
 * Se encarga de eliminar el bloque de aire despues de un tiempo determinado
 */
public class LightTimerBlockEntity extends BlockEntity {
    /* Ticks que ha existido el bloque */
    public int ticksExisted = 0;
    public LightTimerBlockEntity(BlockPos pos, BlockState state) {
        super(LinternaMod.LIGHT_TIMER_BLOCK_ENTITY, pos, state);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        nbt.putInt("ticksExisted", ticksExisted);
        super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        ticksExisted = nbt.getInt("ticksExisted");
    }

    public static void tick(World world, BlockPos pos, BlockState state, LightTimerBlockEntity lightTimerBlockEntity) {
        if(world.isClient()) return;

        ++lightTimerBlockEntity.ticksExisted;

        // Pequeño test para ver los bloques generados...
        //        if(world.getBlockState(pos).getBlock() == LinternaMod.LIGHT_AIR_BLOCK) {
        //            world.addParticle(ParticleTypes.FLAME, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0.0D, 0.0D, 0.0D);
        //        }

        if (lightTimerBlockEntity.ticksExisted > 4) {
            world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
            world.removeBlockEntity(pos);
        }


    }
}
