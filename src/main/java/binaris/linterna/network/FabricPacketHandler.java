package binaris.linterna.network;

import binaris.linterna.LinternaMod;
import binaris.linterna.block.LightAirBlock;
import binaris.linterna.block.LightTimerBlockEntity;
import binaris.linterna.item.LinternaItem;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class FabricPacketHandler {
    public static void init(){
        ServerPlayNetworking.registerGlobalReceiver(LinternaMod.PACKET_ID, (server, player, handler, buf, responseSender) -> {
            BlockPos pos = buf.readBlockPos();

            server.execute(() ->
            {
                World world = player.getEntityWorld();

                if(world.getBlockEntity(pos) instanceof LightTimerBlockEntity lightTimerBlockEntity){
                    lightTimerBlockEntity.ticksExisted = 0;
                } else if (world.getBlockState(pos).getBlock() != LinternaMod.LIGHT_AIR_BLOCK) {
                    world.setBlockState(pos, LinternaMod.LIGHT_AIR_BLOCK.getDefaultState());
                }
            });
        });
    }
}
