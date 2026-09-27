package com.yourmod.ui;

import com.yourmod.util.PowerHelper;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@OnlyIn(Dist.CLIENT)
public class PowerHudOverlay {
    @SubscribeEvent
    public void renderPowerBar(RenderGuiEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) {
            return;
        }

        if (!PowerHelper.hasPowerItem(mc.player)) {
            return;
        }

        int screenW = mc.getWindow().getGuiScaledWidth();
        int x = screenW - 150;
        int y = 30;
        int width = 110;
        int height = 12;

        float percent = PowerHelper.getPowerPercent(mc.player);
        int fillWidth = (int) (width * percent);

        event.getGuiGraphics().fill(x, y, x + width, y + height, 0x66000000);
        event.getGuiGraphics().fill(x, y, x + fillWidth, y + height, 0xFF7CFC00);
        event.getGuiGraphics().drawString(mc.font, "Power", x, y - 12, 0xFFFFFFFF);
    }
}
