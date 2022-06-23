package org.greenem.modding.lessf3.registration;

import com.mojang.blaze3d.platform.InputConstants;
import org.greenem.modding.lessf3.main.LessF3;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.ClientRegistry;

public class KeyInit {
    public static KeyMapping usualShortF3;
    public static KeyMapping veryShortF3;

    public static void init() {
        usualShortF3 = registerKey("short_f3", InputConstants.UNKNOWN.getValue(), KeyMapping.CATEGORY_INTERFACE);
        veryShortF3 = registerKey("very_short_f3", InputConstants.UNKNOWN.getValue(), KeyMapping.CATEGORY_INTERFACE);
    }

    public static KeyMapping registerKey(String name, int keyCode, String category) {
        final var key = new KeyMapping("key." + LessF3.MODID + "." + name, keyCode, category);
        ClientRegistry.registerKeyBinding(key);
        return key;
    }
}
