package com.gto.gtocore.api.machine.mana;

import com.gto.gtocore.api.capability.IManaContainer;

import java.util.Set;

public interface IManaMultiblock {

    Set<IManaContainer> getManaContainer();

    boolean isGeneratorMana();
}
