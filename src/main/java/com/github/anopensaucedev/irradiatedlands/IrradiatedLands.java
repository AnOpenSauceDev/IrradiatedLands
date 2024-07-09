package com.github.anopensaucedev.irradiatedlands;

import com.github.anopensaucedev.irradiatedlands.blocks.RedButton;
import com.github.anopensaucedev.irradiatedlands.blocks.SpentNuclearWaste;
import com.github.anopensaucedev.irradiatedlands.blocks.blockItems.RedButtonItem;
import com.github.anopensaucedev.irradiatedlands.blocks.blockItems.SpentNuclearWasteBlockItem;
import com.github.anopensaucedev.irradiatedlands.items.CreeperCola;
import com.github.anopensaucedev.irradiatedlands.items.Dosimeter;
import com.github.anopensaucedev.irradiatedlands.items.RadStim;
import com.github.anopensaucedev.irradiatedlands.items.WeightyBoy;
import com.github.anopensaucedev.irradiatedlands.items.armor.SuitArmorMaterial;
import com.github.anopensaucedev.irradiatedlands.items.caps.Bottlecap;
import com.github.anopensaucedev.irradiatedlands.items.caps.StarBottlecap;
import com.github.anopensaucedev.irradiatedlands.util.Constants;
import com.github.anopensaucedev.irradiatedlands.effects.IrradiatedStatusEffect;
import com.github.anopensaucedev.irradiatedlands.effects.RadiationShieldingEffect;
import com.github.anopensaucedev.irradiatedlands.listener.ServerTickListener;
import com.github.anopensaucedev.irradiatedlands.util.DefaultRadiationDefs;
import com.github.anopensaucedev.irradiatedlands.util.RadiationDef;
import com.github.anopensaucedev.irradiatedlands.util.Sounds;
import com.github.anopensaucedev.libmcdevfabric.Debug;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSetType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import java.util.ArrayList;
import java.util.List;


public class IrradiatedLands implements ModInitializer {

    public static final StatusEffect SHIELD_EFFECT = new RadiationShieldingEffect(StatusEffectCategory.BENEFICIAL, 0x550000);
    public static final StatusEffect IRRADIATED_EFFECT = new IrradiatedStatusEffect(StatusEffectCategory.HARMFUL,0x550000);

    public static List<RadiationDef> radiationDefs = new ArrayList<>();

    public static Debug DebugLogger = new Debug("Irradiated Lands Debug Logger");

    public static final Block TRINITITE = new Block(FabricBlockSettings.create().hardness(2));
    public static final Item TRINITITE_BLOCK_ITEM = new BlockItem(TRINITITE,new Item.Settings());
    public static final Item RADIATION_STIM = new RadStim(new Item.Settings());
    public static final Item DOSIMETER = new Dosimeter(new Item.Settings());
    public static final Item BOTTLECAP = new Bottlecap(new Item.Settings());
    public static final Item STAR_BOTTLECAP = new StarBottlecap(new Item.Settings().rarity(Rarity.EPIC));
    public static final Item CREEPER_COLA = new CreeperCola(new Item.Settings());

    public static final ArmorMaterial SUIT_MATERIAL = new SuitArmorMaterial();
    public static final Item SUIT_HELMET = new ArmorItem(SUIT_MATERIAL, ArmorItem.Type.HELMET, new Item.Settings());
    public static final Block SPENT_NUCLEAR_WASTE = new SpentNuclearWaste(FabricBlockSettings.create());
    public static final BlockItem SPENT_NUCLEAR_WASTE_BLOCKITEM = new SpentNuclearWasteBlockItem(SPENT_NUCLEAR_WASTE,new Item.Settings());
    public static final DefaultParticleType DUST_CLOUD = FabricParticleTypes.simple();

    public static final Block RED_BUTTON = new RedButton(BlockSetType.IRON,20,FabricBlockSettings.create());
    public static final BlockItem RED_BUTTON_ITEM = new RedButtonItem(RED_BUTTON, new Item.Settings());
    public static final Item WEIGHTY_BOY = new WeightyBoy(new Item.Settings());
    @Override
    public void onInitialize() {
        ServerTickEvents.END_SERVER_TICK.register(new ServerTickListener());
        RegisterEffects();
        RegisterMisc(); // small one-off things
        DefaultRadiationDefs.Register();
        RegisterBlocks();
        RegisterItems();
        Sounds.RegisterSounds();
    }

    public static void RegisterItems(){
        Registry.register(Registries.ITEM,new Identifier(Constants.MOD_ID,"red_button"),RED_BUTTON_ITEM);
        Registry.register(Registries.ITEM,new Identifier(Constants.MOD_ID,"suit_helmet"),SUIT_HELMET);
        Registry.register(Registries.ITEM,new Identifier(Constants.MOD_ID,"creeper_cola"),CREEPER_COLA);
        Registry.register(Registries.ITEM,new Identifier(Constants.MOD_ID,"star_bottlecap"),STAR_BOTTLECAP);
        Registry.register(Registries.ITEM,new Identifier(Constants.MOD_ID,"bottlecap"),BOTTLECAP);
        Registry.register(Registries.ITEM,new Identifier(Constants.MOD_ID,"radiation_stim"),RADIATION_STIM);
        Registry.register(Registries.ITEM,new Identifier(Constants.MOD_ID,"trinitite"),TRINITITE_BLOCK_ITEM);
        Registry.register(Registries.ITEM,new Identifier(Constants.MOD_ID,"dosimeter"),DOSIMETER);
        Registry.register(Registries.ITEM,new Identifier(Constants.MOD_ID,"spent_nuclear_waste"),SPENT_NUCLEAR_WASTE_BLOCKITEM);
        Registry.register(Registries.ITEM,new Identifier(Constants.MOD_ID,"weighty_boy"),WEIGHTY_BOY);
    }

    public static void RegisterBlocks(){
        Registry.register(Registries.BLOCK,new Identifier(Constants.MOD_ID,"trinitite"),TRINITITE);
        Registry.register(Registries.BLOCK,new Identifier(Constants.MOD_ID,"spent_nuclear_waste"),SPENT_NUCLEAR_WASTE);
        Registry.register(Registries.BLOCK,new Identifier(Constants.MOD_ID,"red_button"),RED_BUTTON);
    }

    public static RadiationDef addDef(RadiationDef def){
        radiationDefs.add(def);
        return def;
    }

    public static void removeDef(RadiationDef instance){
        radiationDefs.remove(instance);
    }


    public void RegisterMisc(){
        Registry.register(Registries.PARTICLE_TYPE,new Identifier(Constants.MOD_ID,"dust_cloud"),DUST_CLOUD);
    }


    public void RegisterEffects(){
        Registry.register(Registries.STATUS_EFFECT, new Identifier(Constants.MOD_ID,"radiation_shielding"),SHIELD_EFFECT); // fun fact: the Fabric API is REQUIRED to register effects. I learned this the hard way.
        Registry.register(Registries.STATUS_EFFECT, new Identifier(Constants.MOD_ID,"irradiated"),IRRADIATED_EFFECT); // irradiated is more of a visual indicator than an actual effect.
    }

}