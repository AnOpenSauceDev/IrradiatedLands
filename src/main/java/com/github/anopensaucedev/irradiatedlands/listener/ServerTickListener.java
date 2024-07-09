package com.github.anopensaucedev.irradiatedlands.listener;

import com.github.anopensaucedev.irradiatedlands.IrradiatedLands;
import com.github.anopensaucedev.irradiatedlands.RadiationTracker;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;

import java.util.UUID;

public class ServerTickListener implements ServerTickEvents.EndTick{

    static MinecraftServer serverinst;

    @Override
    public void onEndTick(MinecraftServer server) {
        serverinst = server;
        RadiationTracker.updateRadiationLevels(server.getPlayerManager().getPlayerList());
    }

    public static PlayerEntity UUIDtoPlayer(UUID uuid){
        return serverinst.getPlayerManager().getPlayer(uuid);
    }
    
}
