package com.github.anopensaucedev.irradiatedlands.items;

import com.github.anopensaucedev.irradiatedlands.IrradiatedLands;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.concurrent.ThreadLocalRandom;

//have to keep this copyright-safe
public class CreeperCola extends Item {
    public CreeperCola(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(!user.isDead()){
                user.playSound(SoundEvents.ENTITY_WITCH_DRINK,1,0.75f);
                user.getStackInHand(hand).decrement(1);
                if(ThreadLocalRandom.current().nextInt(10) == 5){
                    user.giveItemStack(new ItemStack(IrradiatedLands.STAR_BOTTLECAP));
                }else {
                    user.giveItemStack(new ItemStack(IrradiatedLands.BOTTLECAP));
                }

                if(!world.isClient){
                    user.getHungerManager().add(3,3);
                }

                return TypedActionResult.success(user.getStackInHand(hand),true);
        }
        return TypedActionResult.pass(user.getStackInHand(hand));
    }

}
