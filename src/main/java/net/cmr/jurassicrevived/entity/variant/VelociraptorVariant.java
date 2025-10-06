package net.cmr.jurassicrevived.entity.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum VelociraptorVariant {
    MALE(0),
    FEMALE(1);

    private static final VelociraptorVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(VelociraptorVariant::getId)).toArray(VelociraptorVariant[]::new);

    private final int id;

    VelociraptorVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static VelociraptorVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
