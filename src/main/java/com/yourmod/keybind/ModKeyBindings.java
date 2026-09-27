package com.yourmod.keybind;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;

public class ModKeyBindings {
    public static final KeyMapping GATHER_POWER = new KeyMapping("key.yourmod.gather_power", InputConstants.KEY_G, "key.categories.yourmod");
    public static final KeyMapping CHARGE_FIST = new KeyMapping("key.yourmod.charge_fist", InputConstants.KEY_Z, "key.categories.yourmod");

    public static void register(RegisterKeyMappingsEvent event) {
        event.register(GATHER_POWER);
        event.register(CHARGE_FIST);
    }
}
