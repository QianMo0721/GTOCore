package com.gto.gtocore.api.data.tag;

public interface ITagPrefix {

    default boolean gtocore$isTagInput() {
        return false;
    }
}
