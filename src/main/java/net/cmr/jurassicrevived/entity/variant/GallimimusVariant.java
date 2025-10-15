package net.cmr.jurassicrevived.entity.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum GallimimusVariant {
    MALE(0),
    FEMALE(1);

    private static final GallimimusVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(GallimimusVariant::getId)).toArray(GallimimusVariant[]::new);

    private final int id;

    GallimimusVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static GallimimusVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
