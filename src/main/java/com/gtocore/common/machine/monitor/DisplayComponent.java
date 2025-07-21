package com.gtocore.common.machine.monitor;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;

public abstract class DisplayComponent implements IDisplayComponent {

    private final ResourceLocation id;

    private DisplayComponent(ResourceLocation id) {
        this.id = id;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public DisplayType getDisplayType() {
        return null;
    }

    public static class Text extends DisplayComponent {

        private FormattedCharSequence text = FormattedCharSequence.EMPTY;

        private Text(ResourceLocation id) {
            super(id);
        }

        @Override
        public Text setInformation(Object... information) {
            if (information.length > 0 && information[0] instanceof FormattedCharSequence text0) {
                this.text = text0;
            } else {
                this.text = FormattedCharSequence.EMPTY; // Default to empty if no valid text provided
            }
            return this;
        }

        @Override
        public FormattedCharSequence getDisplayValue() {
            return text;
        }

        @Override
        public DisplayType getDisplayType() {
            return DisplayType.STYLED_TEXT;
        }

        @Override
        public int getVisualWidth() {
            var font = Minecraft.getInstance().font;
            return font.width(text);
        }

        @Override
        public int getVisualHeight() {
            var font = Minecraft.getInstance().font;
            return font.lineHeight;
        }
    }

    public static Text text(ResourceLocation id) {
        return new Text(id);
    }

    public static Text text(ResourceLocation id, FormattedCharSequence text) {
        return text(id).setInformation(text);
    }
}
