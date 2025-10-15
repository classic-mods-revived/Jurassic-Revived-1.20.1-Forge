package net.cmr.jurassicrevived.entity.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum SpinosaurusVariant {
    MALE(0),
    FEMALE(1);

    private static final SpinosaurusVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(SpinosaurusVariant::getId)).toArray(SpinosaurusVariant[]::new);

    private final int id;

    SpinosaurusVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static SpinosaurusVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
