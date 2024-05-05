package binaris.linterna.network;

import binaris.linterna.LinternaMod;
import binaris.linterna.block.LightTimerBlockEntity;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class FabricPacketHandler {
    public static void init(){
        ServerPlayNetworking.registerGlobalReceiver(LinternaMod.PACKET_ID, (server, player, handler, buf, responseSender) -> {
            BlockPos pos = buf.readBlockPos();
            World world = player.getEntityWorld();

            server.execute(() ->
            {
                if(world.getBlockEntity(pos) instanceof LightTimerBlockEntity lightTimerBlockEntity
                        && lightTimerBlockEntity.ticksExisted > 0 && world.getBlockState(pos).getBlock() == LinternaMod.LIGHT_AIR_BLOCK){

                    lightTimerBlockEntity.ticksExisted = 0;
                } else {
                    world.setBlockState(pos, LinternaMod.LIGHT_AIR_BLOCK.getDefaultState());
                }
            });
        });
    }
}
