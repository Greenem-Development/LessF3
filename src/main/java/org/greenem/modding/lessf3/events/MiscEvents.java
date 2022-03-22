package org.greenem.modding.lessf3.events;

import org.greenem.modding.lessf3.main.LessF3;
import org.greenem.modding.lessf3.registration.KeyInit;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static org.greenem.modding.lessf3.general.Values.*;

@Mod.EventBusSubscriber(modid = LessF3.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class MiscEvents {

    @SubscribeEvent
    public static void onEveryTick(TickEvent.ClientTickEvent e) {
        detectBadKeybind();
    }

    public static void detectBadKeybind() {
        if(KeyInit.shortF3.getKey().getValue()==officialF3ButtonCode || KeyInit.veryShortF3.getKey().getValue()==officialF3ButtonCode) {
            if(!warnedAboutBadKeybindAlready) {
                if(Minecraft.getInstance().player != null) {
                    String translatedMessage = I18n.get("less_f3.custom.strings.bad_keybind");
                    Minecraft.getInstance().player.displayClientMessage(new TextComponent(ChatFormatting.RED + translatedMessage), false);
                    warnedAboutBadKeybindAlready = true;
                }
            }
        }
        else {
            warnedAboutBadKeybindAlready = false;
        }
    }
}
