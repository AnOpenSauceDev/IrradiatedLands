package com.github.anopensaucedev.irradiatedlands.util;

import com.github.anopensaucedev.irradiatedlands.IrradiatedLands;
import com.github.anopensaucedev.irradiatedlands.blocks.blockItems.TrinititeBlockItem;
import net.minecraft.item.Items;

public class DefaultRadiationDefs {

    private static final RadiationDef LAPIS_BLOCK_RADIATION = new RadiationDef(Items.LAPIS_BLOCK,50);

    private static final RadiationDef LAPIS_LAZULI_RADIATION = new RadiationDef(Items.LAPIS_LAZULI,10); // impossible to encounter because we don't check for items

    private static final RadiationDef LAPIS_ORE_RADIATION = new RadiationDef(Items.LAPIS_ORE,10);

    private static final RadiationDef DEEPSLATE_LAPIS_ORE_RADIATION = new RadiationDef(Items.DEEPSLATE_LAPIS_ORE,5);

    private static final RadiationDef TRINITITE_RADIATION = new RadiationDef(IrradiatedLands.TRINITITE_BLOCK_ITEM,4);
    private static final RadiationDef SPENT_NUCLEAR_WASTE_RADIATION = new RadiationDef(IrradiatedLands.SPENT_NUCLEAR_WASTE_BLOCKITEM,200);

    public static void Register(){
        IrradiatedLands.addDef(LAPIS_ORE_RADIATION);
        IrradiatedLands.addDef(SPENT_NUCLEAR_WASTE_RADIATION);
        IrradiatedLands.addDef(LAPIS_LAZULI_RADIATION);
        IrradiatedLands.addDef(DEEPSLATE_LAPIS_ORE_RADIATION);
        IrradiatedLands.addDef(LAPIS_BLOCK_RADIATION);
        IrradiatedLands.addDef(TRINITITE_RADIATION);
    }

}
