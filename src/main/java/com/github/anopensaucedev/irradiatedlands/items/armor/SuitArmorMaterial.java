package com.github.anopensaucedev.irradiatedlands.items.armor;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public class SuitArmorMaterial implements ArmorMaterial {

    private static final int[] BASE_DURABILITY = new int[] {20, 30, 20, 20};
    private static final int[] PROTECTION_VALUES = new int[] {5, 2, 3, 1};

    @Override
    public int getDurability(ArmorItem.Type type) {
        return BASE_DURABILITY[type.getEquipmentSlot().getEntitySlotId()] * 1;
    }

    @Override
    public int getProtection(ArmorItem.Type type) {
        return PROTECTION_VALUES[type.getEquipmentSlot().getEntitySlotId()];
    }

    @Override
    public int getEnchantability() {
        return 5;
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.BLOCK_BEACON_ACTIVATE;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(Items.IRON_INGOT);
    }

    @Override
    public String getName() {
        return "suitarmor";
    }

    @Override
    public float getToughness() {
        return 3;
    }

    @Override
    public float getKnockbackResistance() {
        return 2;
    }
}
