package com.gtocore.common.machine.multiblock.part.ae.widget.slot;

import net.minecraftforge.items.IItemHandlerModifiable;

public class AEPatternViewSlotWidget extends com.gregtechceu.gtceu.integration.ae2.gui.widget.slot.AEPatternViewSlotWidget {

    private final Runnable clicked;

    public AEPatternViewSlotWidget(IItemHandlerModifiable itemHandler, int slotIndex, int xPosition, int yPosition, Runnable clicked) {
        super(itemHandler, slotIndex, xPosition, yPosition);
        this.clicked = clicked;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (slotReference != null && gui != null && button == 2 && isMouseOverElement(mouseX, mouseY)) {
            clicked.run();
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
}
