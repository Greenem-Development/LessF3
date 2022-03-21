package org.greenem.modding.lessf3.events;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.player.KeyboardInput;
import org.greenem.modding.lessf3.extenders.ExtendedBookEditInput;
import org.greenem.modding.lessf3.general.Values;
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
        isThatShift(e.getKey(), e.getAction());
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

        /*if(e.getAction()==0) {
            if (e.getKey() == KeyInit.testMenu.getKey().getValue()) {
//                Minecraft.getInstance().setScreen(new Menu2());
//                Minecraft.getInstance().player.openMenu(new Menu2());
                Minecraft.getInstance().setScreen(new ExtendedBookEditInput());
                System.out.println("opened Menu1");
            }
        }*/
    }

    @SubscribeEvent
    public static void detectMouseButtons(InputEvent.MouseInputEvent e) {
        isThatShift(e.getButton(), e.getAction());
        if(e.getButton()==KeyInit.shortF3.getKey().getValue()){
            if(e.getAction()==0) {
                onLessF3ButtonPressed();
            }
        }
    }

    private static void isThatShift(int key, int action) {
        if(key==InputConstants.KEY_LSHIFT || key==InputConstants.KEY_RSHIFT) {
            if(action==1) {
                shiftIsHeld = true;
            }
            if(action==0) {
                shiftIsHeld = false;
            }
        }
    }

    public static void onLessF3ButtonPressed() {
        if(Minecraft.getInstance().isPaused()) {
            return;
        }
        if(Minecraft.getInstance().player==null) {
            return;
        }
        if(Minecraft.getInstance().screen!=null && Minecraft.getInstance().screen.isPauseScreen()) {
//            System.out.println("pauseScreen");
            return;
        }
//        if(!Minecraft.getInstance().isLocalServer() && Minecraft.getInstance().getGame().getCurrentSession().) {}
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
        if(shiftIsHeld) {
            Minecraft.getInstance().options.renderDebugCharts = !Minecraft.getInstance().options.renderDebugCharts;
        }
    }
}
