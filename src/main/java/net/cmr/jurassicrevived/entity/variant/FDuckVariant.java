package net.cmr.jurassicrevived.entity.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum FDuckVariant {
    MALE(0),
    FEMALE(1);

    private static final FDuckVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(FDuckVariant::getId)).toArray(FDuckVariant[]::new);

    private final int id;

    FDuckVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static FDuckVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
