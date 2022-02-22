package me.greenem.modding.lessf3.implementations;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractCommandBlockEditScreen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

public class Menu2 extends Screen {
    protected Button doneButton;
    protected Button cancelButton;
    protected EditBox itemEdit;

    public Menu2() {
        //super(TextComponent.EMPTY);
        super(new TextComponent("F3 Filter Settings"));
    }

    @Override
    protected void init() {
        //this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
//        Minecraft.getInstance().getOverlay().;
        doneButton = addRenderableWidget(new Button(this.width / 2 - 4 - 150, this.height / 4 + 120 + 12, 150, 20, CommonComponents.GUI_DONE, (thing) -> {
            onDone();
        }));
        cancelButton = this.addRenderableWidget(new Button(this.width / 2 + 4, this.height / 4 + 120 + 12, 150, 20, CommonComponents.GUI_CANCEL, (p_97687_) -> {
            onClose();
        }));
//        net.minecraft.client.gui.components.
        itemEdit = new EditBox(this.font, this.width / 2 - 150, 50, 300, 40, new TextComponent("Text2")) {

        };
        itemEdit.setMaxLength(32500);
        itemEdit.setResponder(this::onEdited);
        itemEdit.setBordered(true);
        itemEdit.setValue("dsgdfgfd\nsdfgfdgdfghhr\nrgrgr23234");
        addRenderableWidget(itemEdit);
//        this.previousEdit.setEditable(false);
//        this.previousEdit.setValue("-");
//        this.setInitialFocus(this.commandEdit);
//        this.commandEdit.setFocus(true);
    }

//    public void resize(Minecraft p_97677_, int p_97678_, int p_97679_) {
//        String s = this.commandEdit.getValue();
//        this.init(p_97677_, p_97678_, p_97679_);
//        this.commandEdit.setValue(s);
//        this.commandSuggestions.updateCommandInfo();
//    }

    protected void onDone() {

    }

    protected void onEdited(String s) {

    }

    public void render(PoseStack p1, int p2, int p3, float p4) {
        //if (this.minecraft.level != null) { 0x453670 // -1072689136, -804253680
            this.fillGradient(p1, 0, 0, this.width, this.height, -1072689136 , -804253680);
            net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.ScreenEvent.BackgroundDrawnEvent(this, p1));
            super.render(p1, p2, p3, p4);
        //}
    }
}