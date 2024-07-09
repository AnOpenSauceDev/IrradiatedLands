package com.github.anopensaucedev.irradiatedlands.util;


import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

import static com.github.anopensaucedev.irradiatedlands.IrradiatedLands.DebugLogger;

public class Sounds {

    public static final Identifier GEIGER_CLICK_LONG = new Identifier(Constants.MOD_ID,"geiger");
    public static final SoundEvent GEIGER_CLICK_LONG_EVENT = SoundEvent.of(GEIGER_CLICK_LONG);

    public static void RegisterSounds(){
        DebugLogger.Log("registering sound stuff at: " + GEIGER_CLICK_LONG.toString());
        Registry.register(Registries.SOUND_EVENT,GEIGER_CLICK_LONG,GEIGER_CLICK_LONG_EVENT);
    }

}
