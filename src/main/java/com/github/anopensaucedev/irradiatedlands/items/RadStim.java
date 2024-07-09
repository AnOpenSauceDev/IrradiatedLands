package com.github.anopensaucedev.irradiatedlands.items;

import com.github.anopensaucedev.irradiatedlands.IrradiatedLands;
import com.github.anopensaucedev.irradiatedlands.effects.IrradiatedStatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class RadStim extends Item {
    public RadStim(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(!user.isDead()){
            if(!user.hasStatusEffect(IrradiatedLands.SHIELD_EFFECT)){
                user.playSound(SoundEvents.ENTITY_SPLASH_POTION_BREAK,1,0.75f);
                user.addStatusEffect(new StatusEffectInstance(IrradiatedLands.SHIELD_EFFECT,240*20,1));
                user.getStackInHand(hand).decrement(1);
                return TypedActionResult.success(user.getStackInHand(hand),true);
            }
        }
        return TypedActionResult.pass(user.getStackInHand(hand));
    }

}
