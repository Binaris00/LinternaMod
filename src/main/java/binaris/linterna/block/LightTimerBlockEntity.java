package binaris.linterna.block;

import binaris.linterna.LinternaMod;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LightTimerBlockEntity extends BlockEntity {
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
        ticksExisted = nbt.getInt("ticksExisted");
        super.readNbt(nbt);
    }

    public static void tick(World world, BlockPos pos, BlockState state, LightTimerBlockEntity lightTimerBlockEntity) {
        ++lightTimerBlockEntity.ticksExisted;
        if (lightTimerBlockEntity.ticksExisted > 15) {
            world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
            world.removeBlockEntity(pos);
        }


    }
}
