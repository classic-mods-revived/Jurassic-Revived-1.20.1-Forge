package net.cmr.jurassicrevived.entity.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum DiplodocusVariant {
    MALE(0),
    FEMALE(1);

    private static final DiplodocusVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(DiplodocusVariant::getId)).toArray(DiplodocusVariant[]::new);

    private final int id;

    DiplodocusVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static DiplodocusVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
