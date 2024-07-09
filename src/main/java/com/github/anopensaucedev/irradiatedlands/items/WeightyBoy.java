package com.github.anopensaucedev.irradiatedlands.items;

import com.github.anopensaucedev.libmcdevfabric.MCDEVMathUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.joml.Quaternionf;
import org.joml.Vector3f;

// cursed version of the fatman
public class WeightyBoy extends Item {
    public WeightyBoy(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
       if(!world.isClient){
           TntEntity mininuke = new TntEntity(world,user.getX(),user.getY(),user.getZ(),user);
           Vec3d velocity = user.getRotationVector().multiply(1,3,5).normalize();
           mininuke.setVelocity(velocity);
            world.spawnEntity(mininuke);
            return TypedActionResult.pass(user.getStackInHand(hand));
       }
       return TypedActionResult.pass(user.getStackInHand(hand));
    }




}
