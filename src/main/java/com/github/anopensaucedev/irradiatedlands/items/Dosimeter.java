package com.github.anopensaucedev.irradiatedlands.items;

import com.github.anopensaucedev.irradiatedlands.IrradiatedLands;
import com.github.anopensaucedev.irradiatedlands.RadiationTracker;
import com.github.anopensaucedev.irradiatedlands.util.Sounds;
import com.github.anopensaucedev.libmcdevfabric.Debug;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.concurrent.ThreadLocalRandom;

// not *exactly* accurate, but good enough
public class Dosimeter extends Item {
    public Dosimeter(Settings settings) {
        super(settings);
    }


    int radiationTicks = 0;
    int rollTicks = 35;

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {


        if(!world.isClient && entity.isPlayer()){
            PlayerEntity player = (PlayerEntity) entity;
            if(RadiationTracker.tracker.get(player) != null){
            RadiationTracker.PlayerRadiationStatus status = RadiationTracker.tracker.get(player);

                radiationTicks++;
                if(radiationTicks > rollTicks && status.radiationLevel > status.radiationDecayRate){
                    rollTicks = ThreadLocalRandom.current().nextInt(40,85) / (1 + (status.radiationInTick / 50));
                    entity.getWorld().playSound(null,entity.getBlockPos(),Sounds.GEIGER_CLICK_LONG_EVENT, SoundCategory.AMBIENT,1, ThreadLocalRandom.current().nextFloat(0.90f,1.05f));
                    radiationTicks = 0;
                }

            float Rads = status.radiationInTick;
            if(status.radiationPoisoningLevel == 0){
                player.sendMessage(Text.of(Rads / 10 + " Rads/t, No Radiation Poisoning"),true);
            }
            if(status.radiationPoisoningLevel == 1){
                player.sendMessage(Text.of(Rads / 10 + " Rads/t, Mild Radiation Poisoning"),true);
            }
            if(status.radiationPoisoningLevel == 2){
                player.sendMessage(Text.of(Rads / 10 + " Rads/t, Severe Radiation Poisoning"),true);
            }
            if(status.radiationPoisoningLevel == 3){
                player.sendMessage(Text.of(Rads / 10 + " Rads/t, Extreme Radiation Poisoning"),true);
            }
            if(status.radiationPoisoningLevel == 4){
                player.sendMessage(Text.of(Rads / 10 + " Rads/t, Fatal Radiation Poisoning."),true);
            }
        }
        }
    }

}
