package org.greenem.modding.lessf3.implementations;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.Nullable;

/**
 * THIS WAS AN UNSUCCESSFUL TEST AND WAS NOT USED!
 * **/

public class Menu1 implements MenuProvider {
    public Menu1() {
        
    }

    @Override
    public Component getDisplayName() {
        return new TextComponent("Menu1 title");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int p_39954_, Inventory p_39955_, Player p_39956_) {
        return new AbstractContainerMenu(MenuType.GENERIC_9x5, 2) {

            @Override
            public boolean stillValid(Player p_38874_) {
                return true;
            }
        };
    }
}
