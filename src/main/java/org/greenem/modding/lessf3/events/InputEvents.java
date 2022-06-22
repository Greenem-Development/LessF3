package org.greenem.modding.lessf3.events;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.greenem.modding.lessf3.general.Values;
import org.greenem.modding.lessf3.main.LessF3;
import org.greenem.modding.lessf3.registration.KeyInit;

import static org.greenem.modding.lessf3.general.Values.*;

@Mod.EventBusSubscriber(modid = LessF3.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class InputEvents {

    @SubscribeEvent
    public static void detectKeyboardButtons(InputEvent.KeyInputEvent e) {
        if(!checkOkConditions()) return;
        isThatShift(e.getKey(), e.getAction());
        System.out.println(e.getAction());
        if(e.getAction()==1) isThatSomeSpecialKey(e.getKey()); // only here?
        if(e.getKey()==officialF3ButtonCode && KeyInit.veryShortF3.getKey().getValue()==officialF3ButtonCode) {
            onlyXYZEnabled = false;
        }
        if(f3IsHeld) {
            System.out.println(isSomeSpecialF3KeyHeld);
        }
//        if(f3IsHeld) {
//            if(onlyXYZEnabled) {
//                Minecraft.getInstance().options.renderDebug = false;
//            }
//            if(lessF3FilterEnabled) {
//                Minecraft.getInstance().options.renderDebug = true;
//            }
//            return;
//        }
        else if(e.getKey()==officialF3ButtonCode && onlyXYZEnabled) {
            if(e.getAction()==1 || e.getAction()==2) {
//                if(!(f3IsHeld && isSomeSpecialF3KeyHeld)) {
                    onlyXYZEnabled = false;
                    Minecraft.getInstance().options.renderDebug = true; // do I need this
//                }
            }
        }
        if(e.getKey()==KeyInit.veryShortF3.getKey().getValue()) {
            if(e.getAction()==0) {
                onVeryShortF3ButtonPressed();
            }
        }
        if(e.getKey()==officialF3ButtonCode && KeyInit.shortF3.getKey().getValue()==officialF3ButtonCode) {
//            if(e.getAction()==1) {
                lessF3FilterEnabled = false;
//            }
        }
        else if(e.getKey()==officialF3ButtonCode && lessF3FilterEnabled) {
            if(e.getAction()==1 || e.getAction()==2) {
//                if(!(f3IsHeld && isSomeSpecialF3KeyHeld)) {
                    lessF3FilterEnabled = false;
                    Minecraft.getInstance().options.renderDebug = true;
//                }
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
        if(!checkOkConditions()) return;
        isThatShift(e.getButton(), e.getAction());
        if(e.getButton()==KeyInit.shortF3.getKey().getValue()){
            if(e.getAction()==0) {
                onLessF3ButtonPressed();
            }
        }
        if(e.getButton()==KeyInit.veryShortF3.getKey().getValue()){
            if(e.getAction()==0) {
                onVeryShortF3ButtonPressed();
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

    // F3 + A + K
    // F3 + K + A
    // F3 + K
    // F3 + A

    private static void isThatSomeSpecialKey(int key) {
        boolean specKey = key==InputConstants.KEY_A || key==InputConstants.KEY_B || key==InputConstants.KEY_C ||
                key==InputConstants.KEY_D || key==InputConstants.KEY_F || key==InputConstants.KEY_G ||
                key==InputConstants.KEY_H || key==InputConstants.KEY_I || key==InputConstants.KEY_L ||
                key==InputConstants.KEY_N || key==InputConstants.KEY_P || key==InputConstants.KEY_Q;
        if(key==InputConstants.KEY_F3) {
            f3IsHeld = true;
            isSomeSpecialF3KeyHeld = false;
            if(f3IsHeld && specKey) {
                isSomeSpecialF3KeyHeld = true;
            }
        }
        else {
            f3IsHeld = false;
            isSomeSpecialF3KeyHeld = false;
        }

        System.out.println(f3IsHeld);
        System.out.println(isSomeSpecialF3KeyHeld);
    }

    private static boolean checkOkConditions() {
        if(Minecraft.getInstance().isPaused()) {
            return false;
        }
        if(Minecraft.getInstance().player==null) {
            return false;
        }
        if(Minecraft.getInstance().screen!=null && Minecraft.getInstance().screen.isPauseScreen()) {
//            System.out.println("pauseScreen");
            return false;
        }
        if(Minecraft.getInstance().player.isDeadOrDying()) {
            return false;
        }
        if(Minecraft.getInstance().screen != null) {
            return false;
        }
        return true;
    }

    public static void onLessF3ButtonPressed() {
        if(!checkOkConditions()) return;
//        if(!Minecraft.getInstance().isLocalServer() && Minecraft.getInstance().getGame().getCurrentSession().) {}
        if(Minecraft.getInstance().options.renderDebug && !lessF3FilterEnabled) { // Normal F3 opened already
            lessF3FilterEnabled = true; // Switch mode to "less f3" without closing the f3
        }
        else if (lessF3FilterEnabled) { // Custom F3 opened already
            Minecraft.getInstance().options.renderDebug = false; // Close F3 and disable F3 (maybe later no)
            lessF3FilterEnabled = false;
        } else if (onlyXYZEnabled) { // Very short custom F3 opened already
            Minecraft.getInstance().options.renderDebug = true; // Close F3 and disable F3 (maybe later no)
            onlyXYZEnabled = false;
            lessF3FilterEnabled = true;
        }
        else { // Nothing is opened already
            lessF3FilterEnabled = true; // Enable "less F3" mode and open F3
            Minecraft.getInstance().options.renderDebug = true;
        }
        if(shiftIsHeld) {
            Minecraft.getInstance().options.renderDebugCharts = !Minecraft.getInstance().options.renderDebugCharts;
        }
    }

    private static void onVeryShortF3ButtonPressed() {
        if(!checkOkConditions()) return;
        if(Minecraft.getInstance().options.renderDebug && !lessF3FilterEnabled) { // Normal F3 opened already
            Minecraft.getInstance().options.renderDebug = false;
            onlyXYZEnabled = true; // Switch mode to "very less f3" with closing the f3 rendering
        }
        else if (lessF3FilterEnabled) { // Custom F3 opened already
            Minecraft.getInstance().options.renderDebug = false; // Close F3 and disable F3 (maybe later no)
            lessF3FilterEnabled = false;
            onlyXYZEnabled = true;
        } else if (onlyXYZEnabled) { // Very short custom F3 opened already
            onlyXYZEnabled = false;
        }
        else { // Nothing is opened already
            onlyXYZEnabled = true; // Enable "less F3" mode and open F3
        }
    }
}
