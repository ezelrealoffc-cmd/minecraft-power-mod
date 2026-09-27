package com.yourmod.item;

import com.yourmod.ModItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class PowerCoreItem extends Item {
    public PowerCoreItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide) {
            player.getPersistentData().putDouble("death_x", player.getX());
            player.getPersistentData().putDouble("death_y", player.getY());
            player.getPersistentData().putDouble("death_z", player.getZ());
            player.getPersistentData().putBoolean("pending_respawn_buff", true);

            level.addParticle(ParticleTypes.SOUL, player.getX(), player.getY() + 1.2D, player.getZ(), 0.0D, 0.2D, 0.0D);
            level.addParticle(ParticleTypes.ENCHANT, player.getX(), player.getY() + 1.2D, player.getZ(), 0.0D, 0.2D, 0.0D);

            player.hurt(player.damageSources().generic(), Float.MAX_VALUE);
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }
}
