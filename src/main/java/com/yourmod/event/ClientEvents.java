package com.yourmod.event;

import com.yourmod.ModItems;
import com.yourmod.keybind.ModKeyBindings;
import com.yourmod.util.PowerHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@OnlyIn(Dist.CLIENT)
public class ClientEvents {
    @SubscribeEvent
    public void onKeyInput(InputEvent.Key event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }

        if (ModKeyBindings.CHARGE_FIST.consumeClick()) {
            if (PowerHelper.hasPowerItem(player)) {
                player.getPersistentData().putBoolean("charge_fist_active", true);
            }
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }

        if (!PowerHelper.hasPowerItem(player)) {
            player.getPersistentData().putFloat("power_charge", 0.0F);
            player.getPersistentData().putFloat("charge_hold_time", 0.0F);
            return;
        }

        float dt = 1.0F / 20.0F;
        if (ModKeyBindings.GATHER_POWER.isDown()) {
            float holdTime = Math.min(3.0F, player.getPersistentData().getFloat("charge_hold_time") + dt);
            float power = (holdTime / 3.0F) * 100.0F;
            player.getPersistentData().putFloat("charge_hold_time", holdTime);
            player.getPersistentData().putFloat("power_charge", power);
        }
    }
}
