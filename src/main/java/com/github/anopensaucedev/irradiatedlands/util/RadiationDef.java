package com.github.anopensaucedev.irradiatedlands.util;

import com.github.anopensaucedev.irradiatedlands.IrradiatedLands;
import net.minecraft.item.Item;

public class RadiationDef {

    public RadiationDef(Item substance, int radiationPerTick){
        this.substance = substance;
        this.radiationPerTick = radiationPerTick;
    }

    public RadiationDef register(){
        return IrradiatedLands.addDef(this);
    }

    public Item substance;

    public int radiationPerTick;

    public int getRadiationPerTick() {
        return radiationPerTick;
    }

    public Item getSubstance() {
        return substance;
    }
}
