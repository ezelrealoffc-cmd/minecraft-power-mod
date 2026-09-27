package com.yourmod;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, YourMod.MODID);

    public static final RegistryObject<Item> POWER_CORE = ITEMS.register("power_core", () ->
            new net.minecraft.world.item.Item(new Item.Properties().stacksTo(1))
    );
}
