package org.greenem.modding.lessf3.events;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.greenem.modding.lessf3.main.LessF3;
import org.greenem.modding.lessf3.registration.KeyInit;

import static org.greenem.modding.lessf3.general.Values.*;

@Mod.EventBusSubscriber(modid = LessF3.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class InputEvents {

    @SubscribeEvent
    public static void detectKeyboardButtons(InputEvent.KeyInputEvent e) {
        if(!checkOkConditions()) return;
        detectShift(e.getKey(), e.getAction());
        if(!detectSpecialKeys(e.getKey(), e.getAction())) return;

        boolean overlappingUsualModeKey = KeyInit.veryShortF3.getKey().getValue()==officialF3ButtonCode;
        boolean overlappingShorterModeKey = KeyInit.veryShortF3.getKey().getValue()==officialF3ButtonCode;
        boolean currentlyPressedF3 = e.getKey()==officialF3ButtonCode;
        boolean currentlyPressedUsualLessF3 = e.getKey()==KeyInit.usualShortF3.getKey().getValue();
        boolean currentlyPressedVeryLessF3 = e.getKey()==KeyInit.veryShortF3.getKey().getValue();

        if(currentlyPressedF3){
            if(overlappingUsualModeKey || overlappingShorterModeKey) {
                shorterLessF3Enabled = false;
            }
            else if(usualLessF3Enabled || shorterLessF3Enabled) {
                if (e.getAction() == 0) {
                    usualLessF3Enabled = false;
                    shorterLessF3Enabled = false;
                    Minecraft.getInstance().options.renderDebug = true; // do I need this?
                }
            }
        }
        else if (e.getAction() == 0) {
            if(currentlyPressedUsualLessF3) onUsualLessF3ButtonPressed();
            else if(currentlyPressedVeryLessF3) onVeryShortF3ButtonPressed();
        }
    }

    @SubscribeEvent
    public static void detectMouseButtons(InputEvent.MouseInputEvent e) {
        if(!checkOkConditions()) return;
        detectShift(e.getButton(), e.getAction());

        boolean currentlyPressedUsualLessF3 = e.getButton()==KeyInit.usualShortF3.getKey().getValue();
        boolean currentlyPressedVeryLessF3 = e.getButton()==KeyInit.veryShortF3.getKey().getValue();

        if(e.getAction()==0) {
            if(currentlyPressedUsualLessF3) onUsualLessF3ButtonPressed();
            else if(currentlyPressedVeryLessF3) onVeryShortF3ButtonPressed();
        }
    }

    private static void detectShift(int key, int action) {
        if(key==InputConstants.KEY_LSHIFT || key==InputConstants.KEY_RSHIFT) {
            if(action==1) {
                shiftIsHeld = true;
            }
            if(action==0) {
                shiftIsHeld = false;
            }
        }
    }

    private static boolean detectSpecialKeys(int key, int action) { // action: 1 - just pressed, 2 - long enough, 0 - stopped holding
        boolean f3 = key==InputConstants.KEY_F3;
        boolean spec = key==InputConstants.KEY_A || key==InputConstants.KEY_B || key==InputConstants.KEY_C ||
                key==InputConstants.KEY_D || key==InputConstants.KEY_F || key==InputConstants.KEY_G ||
                key==InputConstants.KEY_H || key==InputConstants.KEY_I || key==InputConstants.KEY_L ||
                key==InputConstants.KEY_N || key==InputConstants.KEY_P || key==InputConstants.KEY_Q;
        if(f3 && action==1) {
            if(f3SpecKeysStage==0) f3SpecKeysStage = 1;
        }
        else if(spec && action==1 && f3SpecKeysStage==1) {
            f3SpecKeysStage = 2;
        }
        else if(f3 && action==0) {
            if(f3SpecKeysStage==2) {
                f3SpecKeysStage = 0;
                return false;
            } else {
                f3SpecKeysStage = 0;
            }
        }
        return true;
    }

    public static void onUsualLessF3ButtonPressed() {
        if(!checkOkConditions()) return;
        Options opt = Minecraft.getInstance().options;

        if(opt.renderDebug && !usualLessF3Enabled) { // Normal F3 opened already
            usualLessF3Enabled = true; // Switch mode to "less f3" without closing the f3
        }
        else if (usualLessF3Enabled) { // Custom F3 opened already
            opt.renderDebug = false; // Close F3 and disable F3 (maybe later no)
            usualLessF3Enabled = false;
        } else if (shorterLessF3Enabled) { // Very short custom F3 opened already
            opt.renderDebug = true; // Close F3 and disable F3 (maybe later no)
            shorterLessF3Enabled = false;
            usualLessF3Enabled = true;
        }
        else { // Nothing is opened already
            usualLessF3Enabled = true; // Enable "less F3" mode and open F3
            opt.renderDebug = true;
        }
        if(shiftIsHeld) {
            opt.renderDebugCharts = !opt.renderDebugCharts;
        }
    }

    private static void onVeryShortF3ButtonPressed() {
        if(!checkOkConditions()) return;
        Options opt = Minecraft.getInstance().options;

        if(opt.renderDebug && !usualLessF3Enabled) { // Normal F3 opened already
            opt.renderDebug = false;
            shorterLessF3Enabled = true; // Switch mode to "very less f3" with closing the f3 rendering
        }
        else if (usualLessF3Enabled) { // Custom F3 opened already
            opt.renderDebug = false; // Close F3 and disable F3 (maybe later no)
            usualLessF3Enabled = false;
            shorterLessF3Enabled = true;
        } else if (shorterLessF3Enabled) { // Very short custom F3 opened already
            shorterLessF3Enabled = false;
        }
        else { // Nothing is opened already
            shorterLessF3Enabled = true; // Enable "less F3" mode and open F3
        }
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
//        if(!Minecraft.getInstance().isLocalServer() && Minecraft.getInstance().getGame().getCurrentSession().) {}
    }
}
