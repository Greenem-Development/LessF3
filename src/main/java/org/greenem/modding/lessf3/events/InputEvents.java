package org.greenem.modding.lessf3.events;

import org.greenem.modding.lessf3.main.LessF3;
import org.greenem.modding.lessf3.registration.KeyInit;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static org.greenem.modding.lessf3.general.Values.*;

@Mod.EventBusSubscriber(modid = LessF3.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class InputEvents {

    @SubscribeEvent
    public static void detectKeyboardButtons(InputEvent.KeyInputEvent e) {
        if(e.getKey()==officialF3ButtonCode && KeyInit.shortF3.getKey().getValue()==officialF3ButtonCode) {
            lessF3FilterEnabled = false;
        }
        else if(e.getKey()==officialF3ButtonCode && lessF3FilterEnabled) {
            if(e.getAction()==0) {
                lessF3FilterEnabled = false;
                Minecraft.getInstance().options.renderDebug = true;
            }
        }
        else if(e.getKey()==KeyInit.shortF3.getKey().getValue()) {
            if(e.getAction()==0) {
                onLessF3ButtonPressed();
            }
        }
    }

    @SubscribeEvent
    public static void detectMouseButtons(InputEvent.MouseInputEvent e) {
        if(e.getButton()==KeyInit.shortF3.getKey().getValue()){
            if(e.getAction()==0) {
                onLessF3ButtonPressed();
            }
        }
    }

    public static void onLessF3ButtonPressed() {
        if(Minecraft.getInstance().isPaused()) {
            return;
        }
        if(Minecraft.getInstance().options.renderDebug && !lessF3FilterEnabled) { // Normal F3 opened already
            lessF3FilterEnabled = true; // Switch mode to "less f3" without closing the f3
        }
        else if (lessF3FilterEnabled) { // Custom F3 opened already
            Minecraft.getInstance().options.renderDebug = false; // Close F3 and disable F3 (maybe later no)
            lessF3FilterEnabled = false;
        }
        else { // Nothing is opened already
            lessF3FilterEnabled = true; // Enable "less F3" mode and open F3
            Minecraft.getInstance().options.renderDebug = true;
        }
    }
}
