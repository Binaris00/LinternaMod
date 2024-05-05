package binaris.linterna.mixin;

import binaris.linterna.LinternaMod;
import binaris.linterna.block.LightTimerBlockEntity;
import binaris.linterna.item.LinternaItem;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {
    @Unique
    private int range = 32;
    @Unique
    ClientPlayerEntity player = (ClientPlayerEntity)(Object)this;

    @Inject(method = "tick", at = @At("TAIL"))
    public void LINTERNA$tick(CallbackInfo ci){
        if(player.getMainHandStack().getItem() instanceof LinternaItem && player.getMainHandStack().getOrCreateNbt().getBoolean("active")){
            int lightNumber = range / 5;
            int lightRange = range;

            for(int i = 0; i < lightNumber; ++i) {
                lightRange -= 5;
                this.createLight(player, lightRange);
            }
        }

    }

    @Unique
    private void createLight(ClientPlayerEntity player, int lightRange) {
        World world = player.getEntityWorld();
        BlockEntity blockEntity;

        int x = this.lookingAt(player, lightRange).getX();
        int y = this.lookingAt(player, lightRange).getY();
        int z = this.lookingAt(player, lightRange).getZ();
        boolean createLight = false;

        for(int i = 0; i < 5; ++i) {
            blockEntity = world.getBlockEntity(new BlockPos(x, y, z));
            if (blockEntity instanceof LightTimerBlockEntity) {
                createLight = true;
                break;
            }

            if (!world.isAir(new BlockPos(x, y, z))) {
                int pX = (int) player.getPos().getX();
                int pY = (int) player.getPos().getY();
                int pZ = (int) player.getPos().getZ();
                if (pX > x) {
                    ++x;
                } else if (pX < x) {
                    --x;
                }

                if (pY > y) {
                    ++y;
                } else if (pY < y) {
                    --y;
                }

                if (pZ > z) {
                    ++z;
                } else if (pZ < z) {
                    --z;
                }
            }

            if (world.isAir(new BlockPos(x, y, z))) {
                createLight = true;
                break;
            }
        }

        if (createLight) {
            BlockPos lightPos = new BlockPos(x, y, z);
            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeBlockPos(lightPos);
            ClientPlayNetworking.send(LinternaMod.PACKET_ID, buf);
        }
    }



    @Unique
    private BlockPos lookingAt(ClientPlayerEntity player, int rangeL) {
        Vec3d pos = player.raycast(rangeL, 0.0F, false).getPos();
        return new BlockPos((int) pos.getX(), (int) pos.getY(), (int) pos.getZ());
    }
}
