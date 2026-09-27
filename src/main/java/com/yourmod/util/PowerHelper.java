package com.yourmod.util;

import com.yourmod.ModItems;
import net.minecraft.world.entity.player.Player;

public final class PowerHelper {
    private PowerHelper() {
    }

    public static boolean hasPowerItem(Player player) {
        return player.getMainHandItem().is(ModItems.POWER_CORE.get()) || player.getOffhandItem().is(ModItems.POWER_CORE.get());
    }

    public static float getPowerPercent(Player player) {
        return Math.min(100.0F, Math.max(0.0F, player.getPersistentData().getFloat("power_charge"))) / 100.0F;
    }
}
