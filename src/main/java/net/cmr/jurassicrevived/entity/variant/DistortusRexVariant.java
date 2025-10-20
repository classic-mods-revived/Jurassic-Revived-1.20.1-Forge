package net.cmr.jurassicrevived.entity.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum DistortusRexVariant {
    MALE(0),
    FEMALE(1);

    private static final DistortusRexVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(DistortusRexVariant::getId)).toArray(DistortusRexVariant[]::new);

    private final int id;

    DistortusRexVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static DistortusRexVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
