package net.cmr.jurassicrevived.entity.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum RugopsVariant {
    MALE(0),
    FEMALE(1);

    private static final RugopsVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(RugopsVariant::getId)).toArray(RugopsVariant[]::new);

    private final int id;

    RugopsVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static RugopsVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
