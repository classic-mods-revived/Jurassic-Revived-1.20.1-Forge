package net.cmr.jurassicrevived.entity.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum BaryonyxVariant {
    MALE(0),
    FEMALE(1);

    private static final BaryonyxVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(BaryonyxVariant::getId)).toArray(BaryonyxVariant[]::new);

    private final int id;

    BaryonyxVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static BaryonyxVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
