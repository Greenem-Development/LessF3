package org.greenem.modding.lessf3.registration;

import com.mojang.blaze3d.platform.InputConstants;
import org.greenem.modding.lessf3.main.LessF3;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.ClientRegistry;

public class KeyInit {
//    private KeyInit() {
//
//    }

    public static KeyMapping shortF3;
    public static KeyMapping testMenu;

    public static void init() {
        shortF3 = registerKey("short_f3", InputConstants.UNKNOWN.getValue(), KeyMapping.CATEGORY_INTERFACE);
        testMenu = registerKey("test_menu", InputConstants.KEY_N, KeyMapping.CATEGORY_INTERFACE);
    }

    public static KeyMapping registerKey(String name, int keyCode, String category) {
        final var key = new KeyMapping("key." + LessF3.MODID + "." + name, keyCode, category);
        ClientRegistry.registerKeyBinding(key);
        return key;
    }
}
