package net.cmr.jurassicrevived.entity.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum DeinonychusVariant {
    MALE(0),
    FEMALE(1);

    private static final DeinonychusVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(DeinonychusVariant::getId)).toArray(DeinonychusVariant[]::new);

    private final int id;

    DeinonychusVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static DeinonychusVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
