package net.cmr.jurassicrevived.entity.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum GuanlongVariant {
    MALE(0),
    FEMALE(1);

    private static final GuanlongVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(GuanlongVariant::getId)).toArray(GuanlongVariant[]::new);

    private final int id;

    GuanlongVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static GuanlongVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
