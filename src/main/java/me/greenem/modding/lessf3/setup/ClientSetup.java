package me.greenem.modding.lessf3.setup;

import me.greenem.modding.lessf3.registration.KeyInit;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientSetup {

    public static void init(FMLClientSetupEvent e) {
        KeyInit.init();
    }

}
