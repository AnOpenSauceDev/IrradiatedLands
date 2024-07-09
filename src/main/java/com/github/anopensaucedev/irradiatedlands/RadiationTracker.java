package com.github.anopensaucedev.irradiatedlands;

import com.github.anopensaucedev.irradiatedlands.util.Constants;
import net.minecraft.block.BlockState;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.LightType;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static com.github.anopensaucedev.irradiatedlands.IrradiatedLands.*;

public class RadiationTracker {

    public static Identifier IRRADIATION_DAMAGE_TYPE_ID = new Identifier(Constants.MOD_ID,"irradiation_damage");

    public static RegistryKey<DamageType> IRRADIATION = RegistryKey.of(RegistryKeys.DAMAGE_TYPE,IRRADIATION_DAMAGE_TYPE_ID);

    public static HashMap<PlayerEntity,PlayerRadiationStatus> tracker = new HashMap<>();

    public static void updateRadiationLevels(List<ServerPlayerEntity> playerList){
        for (int i = 0; i < playerList.size(); i++) {
            PlayerEntity player = playerList.get(i);
            if(tracker.get(player) != null){


                calculateRadiation(tracker.get(player));

                if(player.isDead()){
                    tracker.put(player, null);
                }
            }else{
                DebugLogger.Log("Creating new PlayerRadiationStatus for " + player.getName().getLiteralString());
                tracker.put(player,new PlayerRadiationStatus(player));
                calculateRadiation(tracker.get(player));
            }


        }
    }

    public static int DustStormMultiplier = 1;

    public static void calculateRadiation(PlayerRadiationStatus status){

        status.passiveShielding = 0;

        for (int x = 0; x < status.trackedPlayer.getInventory().armor.size(); x++){
            if(status.trackedPlayer.getInventory().armor.get(x).getItem().equals(SUIT_HELMET)){
                status.passiveShielding += 2;
            }
        }


        status.radiationInTick = 0;

        if(status.isImmuneToRadiation){

            return; // don't calculate radiation
        }

        //add background radiation divided by cave shielding
        if(status.trackedPlayer.getWorld().getDimensionKey().getValue().getPath() != "nuclear_wasteland"){
        status.radiationLevel += (Constants.OVERWORLD_BACKGROUND_RADIATION / calculateEnvironmentalShielding(status));
        }else {
            if(status.trackedPlayer.getWorld().isNight()){
                DustStormMultiplier = 5;
            }else {
                DustStormMultiplier = 1;
            }
            int env = ((Constants.WASTELAND_BACKGROUND_RADIATION * DustStormMultiplier) / calculateEnvironmentalShielding(status));
            status.radiationInTick += env;
            status.radiationLevel += env;
        }

        // calculate radiation from the environment (we don't use occlusion because I'm too lazy to implement it in a fast manner)
        BlockPos pos = status.trackedPlayer.getBlockPos();
        int distance = 10;
        int radInTick = 0;
        for(int x = pos.getX() - distance; x <= pos.getX() + distance; x++){
            for(int y = pos.getY() - distance; y <= pos.getY() + distance; y++){
                for(int z = pos.getZ() - distance; z <= pos.getZ() + distance; z++){
                    BlockPos radpos = new BlockPos(x,y,z);
                    BlockState state = status.trackedPlayer.getWorld().getBlockState(radpos);
                    for(int w = 0; w < IrradiatedLands.radiationDefs.size(); w++){ //quad-nested for loop FTW
                        if(IrradiatedLands.radiationDefs.get(w).substance == state.getBlock().asItem()){
                            int rads = IrradiatedLands.radiationDefs.get(w).radiationPerTick;
                            float inverseSquareLawDistance = new Vector3f(radpos.getX(),radpos.getY(),radpos.getZ()).distance(status.trackedPlayer.getPos().toVector3f());
                            int inverseSquareLawRads = (int) Math.floor(rads / inverseSquareLawDistance);
                            status.radiationLevel += inverseSquareLawRads;// crappy Inverse Square Law implementation
                            radInTick += inverseSquareLawRads;
                        }
                    }
                }
            }
        }
        status.radiationInTick += radInTick;


        //subtract radiation from passive shielding equipment
        status.radiationLevel -= (status.passiveShielding * 100);

        if(status.trackedPlayer.hasStatusEffect(SHIELD_EFFECT)){
            status.radiationTolerance = 5; // 5x radiation tolerance
        }else {
            status.radiationTolerance = 1;
        }

        status.radiationLevel -= status.radiationDecayRate; // subtract radiation from body decay rate

        // at the very end, correct any negative radiation and set poisoning level (basically do everything we need to do at the end)
        status.radiationLevel = Math.max(status.radiationLevel,0);
        status.radiationLevel = Math.min(status.radiationLevel,30000); // limit radiation exposure to 10k rads, so they don't die as fast.

        if(status.radiationPoisoningLevel == 4){
            status.trackedPlayer.damage(new DamageSource(status.trackedPlayer.getWorld().getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(DamageTypes.INDIRECT_MAGIC)),2);
            return;
        }

        if(status.radiationLevel > Constants.RADIATION_POISONING_THRESHOLD * status.radiationTolerance){

            if(status.radiationPoisoningLevel == 0){
                status.radiationPoisoningLevel = 1;
            }



            status.ticksSinceRadiationDamage++;
            if(status.ticksSinceRadiationDamage > 300 / status.radiationPoisoningLevel){
                status.timesHitWithRadiation++;
                status.trackedPlayer.damage(new DamageSource(status.trackedPlayer.getWorld().getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(DamageTypes.INDIRECT_MAGIC)),2 * status.radiationPoisoningLevel);
                status.ticksSinceRadiationDamage = 0;
                DebugLogger.Log(status.trackedPlayer.getName().getLiteralString() + " just got a serious case of radiation burns!");
            }

            status.trackedPlayer.addStatusEffect(new StatusEffectInstance(IrradiatedLands.IRRADIATED_EFFECT, 20,0,true,false));



            if(status.timesHitWithRadiation == 5){
                status.radiationPoisoningLevel = 2;
            }
            if(status.timesHitWithRadiation == 6){
                status.radiationPoisoningLevel = 3;
            }

            if(status.timesHitWithRadiation == 9){
                status.radiationPoisoningLevel = 4;
            }

        }


    }

    public static int calculateEnvironmentalShielding(PlayerRadiationStatus status){ // we currently just check for if the player's sky-light level.
        int sl = status.trackedPlayer.getWorld().getLightLevel(LightType.SKY,status.trackedPlayer.getBlockPos().subtract(new Vec3i(0,-1,0)));
            if(sl >= 10){
                return 1;
            }else {
                return 3;
            }
        }


    public static class PlayerRadiationStatus{

        //constructors
        public PlayerRadiationStatus(PlayerEntity player){
            uuid = player.getUuid();
            trackedPlayer = player;
            radiationDecayRate = 150;
            radiationTolerance = 1;
        }


        // radiation core stuff
        public int radiationLevel; // player's cumulative radiation
        public int radiationDecayRate; // radiation loss per tick
        public int radiationPoisoningLevel;
        public int ticksSinceRadiationDamage = 0;
        public int timesHitWithRadiation = 0;
        public int radiationInTick = 0;

        // shielding
        public int passiveShielding = 0; // passive shielding from things like gear. Active Shielding is stuff like effects

        //user stuff
        public UUID uuid;
        public PlayerEntity trackedPlayer;

        // extras
        public boolean isImmuneToRadiation;
        public int radiationTolerance;

    }

}
