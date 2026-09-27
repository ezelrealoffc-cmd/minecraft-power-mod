package com.yourmod;

import com.yourmod.event.ClientEvents;
import com.yourmod.event.CommonEvents;
import com.yourmod.item.PowerCoreItem;
import com.yourmod.keybind.ModKeyBindings;
import com.yourmod.ui.PowerHudOverlay;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(YourMod.MODID)
public class YourMod {
    public static final String MODID = "yourmod";
    public static final Logger LOGGER = LogManager.getLogger();

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final RegistryObject<Item> POWER_CORE = ITEMS.register("power_core", () -> new PowerCoreItem(new Item.Properties().stacksTo(1)));

    public YourMod() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        ITEMS.register(modBus);

        modBus.addListener(this::registerKeyBindings);

        MinecraftForge.EVENT_BUS.register(new CommonEvents());
        MinecraftForge.EVENT_BUS.register(new ClientEvents());
        MinecraftForge.EVENT_BUS.register(new PowerHudOverlay());
    }

    private void registerKeyBindings(RegisterKeyMappingsEvent event) {
        ModKeyBindings.register(event);
    }
}
