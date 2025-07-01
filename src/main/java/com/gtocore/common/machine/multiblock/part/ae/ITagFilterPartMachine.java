package com.gtocore.common.machine.multiblock.part.ae;

import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.gui.fancy.IFancyConfigurator;

import net.minecraft.network.chat.Component;

import com.lowdragmc.lowdraglib.gui.texture.IGuiTexture;
import com.lowdragmc.lowdraglib.gui.widget.LabelWidget;
import com.lowdragmc.lowdraglib.gui.widget.TextFieldWidget;
import com.lowdragmc.lowdraglib.gui.widget.Widget;
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup;

interface ITagFilterPartMachine {

    String getTagWhite();

    String getTagBlack();

    void setTagWhite(String tagWhite);

    void setTagBlack(String tagBlack);

    class FilterIFancyConfigurator implements IFancyConfigurator {

        private final ITagFilterPartMachine machine;

        FilterIFancyConfigurator(ITagFilterPartMachine machine) {
            this.machine = machine;
        }

        @Override
        public Component getTitle() {
            return Component.translatable("gtocore.machine.tag_filter.tag_config_title");
        }

        @Override
        public IGuiTexture getIcon() {
            return GuiTextures.BUTTON_BLACKLIST.getSubTexture(0, 0, 20, 20);
        }

        @Override
        public Widget createConfigurator() {
            return new WidgetGroup(0, 0, 132, 100)
                    .addWidget(new LabelWidget(9, 4,
                            () -> "gui.enderio.filter.whitelist"))
                    .addWidget(new TextFieldWidget(9, 16, 114, 16,
                            machine::getTagWhite,
                            machine::setTagWhite))
                    .addWidget(new LabelWidget(9, 36,
                            () -> "gui.enderio.filter.blacklist"))
                    .addWidget(new TextFieldWidget(9, 48, 114, 16,
                            machine::getTagBlack,
                            machine::setTagBlack))
                    .addWidget(new LabelWidget(0, 68,
                            () -> "gtocore.machine.tag_filter.tooltip.0"))
                    .addWidget(new LabelWidget(0, 84,
                            () -> "gtocore.machine.tag_filter.tooltip.1"));
        }
    }
}
