package com.yourmod.event;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.Clone;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.UUID;

public class CommonEvents {
    private static final UUID DAMAGE_BUFF = UUID.fromString("3d5d2d2e-2b0a-4d52-8ec2-7d0d35b62b33");
    private static final UUID HEALTH_BUFF = UUID.fromString("77d8d4ef-0fda-4177-b0cc-7008ea1d3900");
    private static final UUID SPEED_BUFF = UUID.fromString("dbf3b83a-e9de-4f66-9cc2-33d0d52dbb17");

    @SubscribeEvent
    public void onPlayerClone(PlayerEvent.Clone event) {
        Player original = event.getOriginal();
        Player clone = event.getEntity();
        CompoundTag oldData = original.getPersistentData();
        CompoundTag newData = clone.getPersistentData();
        newData.putDouble("respawn_x", oldData.getDouble("respawn_x"));
        newData.putDouble("respawn_y", oldData.getDouble("respawn_y"));
        newData.putDouble("respawn_z", oldData.getDouble("respawn_z"));
        newData.putBoolean("pending_respawn_buff", oldData.getBoolean("pending_respawn_buff"));
    }

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event) {
        if (!(event.getEntity() instanceof LivingEntity target)) {
            return;
        }

        if (!(event.getSource().getEntity() instanceof Player player)) {
            return;
        }

        CompoundTag tag = player.getPersistentData();
        if (tag.getBoolean("charge_fist_active") && tag.getFloat("power_charge") > 0.0F) {
            float power = tag.getFloat("power_charge");
            float extraDamage = 4.0F + (power / 100.0F) * 18.0F;
            event.setAmount(event.getAmount() + extraDamage);

            double dirX = target.getX() - player.getX();
            double dirZ = target.getZ() - player.getZ();
            target.knockback(1.6F + (power / 100.0F) * 2.4F, dirX, dirZ);

            tag.putBoolean("charge_fist_active", false);
            tag.putFloat("power_charge", 0.0F);
            tag.putFloat("charge_hold_time", 0.0F);
        }
    }

    @SubscribeEvent
    public void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        Player player = event.getEntity();
        CompoundTag tag = player.getPersistentData();
        if (!tag.getBoolean("pending_respawn_buff")) {
            return;
        }

        if (tag.contains("respawn_x") && tag.contains("respawn_y") && tag.contains("respawn_z")) {
            player.teleportTo(tag.getDouble("respawn_x"), tag.getDouble("respawn_y"), tag.getDouble("respawn_z"));
        }

        applyRespawnBuff(player);
        tag.putBoolean("pending_respawn_buff", false);
    }

    private void applyRespawnBuff(Player player) {
        player.getAttribute(Attributes.MAX_HEALTH)
                .addPermanentModifier(new AttributeModifier(HEALTH_BUFF, "respawn_health_buff", 1.0D, AttributeModifier.Operation.MULTIPLY_TOTAL));
        player.getAttribute(Attributes.ATTACK_DAMAGE)
                .addPermanentModifier(new AttributeModifier(DAMAGE_BUFF, "respawn_damage_buff", 1.0D, AttributeModifier.Operation.MULTIPLY_TOTAL));
        player.getAttribute(Attributes.MOVEMENT_SPEED)
                .addPermanentModifier(new AttributeModifier(SPEED_BUFF, "respawn_speed_buff", 0.5D, AttributeModifier.Operation.MULTIPLY_TOTAL));

        player.setHealth(player.getMaxHealth());
    }
}
