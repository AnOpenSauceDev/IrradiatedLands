package com.github.anopensaucedev.irradiatedlands;

import com.github.anopensaucedev.irradiatedlands.blocks.Constants;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class RadiationManager {

    public static Identifier IRRADIATION_DAMAGE_TYPE_ID = new Identifier(Constants.MOD_ID,"irradiation_damage");

    public static RegistryKey<DamageType> IRRADIATION = RegistryKey.of(RegistryKeys.DAMAGE_TYPE,IRRADIATION_DAMAGE_TYPE_ID);

    

}
