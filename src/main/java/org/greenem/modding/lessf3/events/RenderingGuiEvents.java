package org.greenem.modding.lessf3.events;

import org.greenem.modding.lessf3.main.LessF3;
import org.greenem.modding.lessf3.rendering.RenderingCustomF3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static org.greenem.modding.lessf3.general.Values.*;

@Mod.EventBusSubscriber(modid = LessF3.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class RenderingGuiEvents {

    @SubscribeEvent
    public static void onDebugOverlay(RenderGameOverlayEvent.Text e)
    {
        if(lessF3FilterEnabled) {
            RenderingCustomF3.renderUsualLessF3Mode(e);
        }
        else if (onlyXYZEnabled) {
            RenderingCustomF3.renderVeryShortF3Mode(e);
        }
    }
}
