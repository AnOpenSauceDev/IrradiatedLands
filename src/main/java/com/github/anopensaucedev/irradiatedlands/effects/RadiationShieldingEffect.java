package com.github.anopensaucedev.irradiatedlands.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;

public class RadiationShieldingEffect extends StatusEffect {
    public RadiationShieldingEffect(StatusEffectCategory type, int colour) {
        super(type,colour);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        return;
    }



}
