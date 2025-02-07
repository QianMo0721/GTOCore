package com.gto.gtocore.api.capability;

public interface IManaContainer {

    int getMaxMana();

    int getCurrentMana();

    void setCurrentMana(int mana);

    default int getMaxConsumption() {
        return getMaxMana();
    }

    default boolean addMana(int amount) {
        int mana = getCurrentMana() + amount;
        if (amount > getMaxConsumption() || mana > getMaxMana()) {
            return false;
        }
        setCurrentMana(mana);
        return true;
    }

    default boolean removeMana(int amount) {
        int mana = getCurrentMana() - amount;
        if (amount > getMaxConsumption() || mana < 0) {
            return false;
        }
        setCurrentMana(mana);
        return true;
    }
}
