package net.cmr.jurassicrevived.entity.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum StyracosaurusVariant {
    MALE(0),
    FEMALE(1);

    private static final StyracosaurusVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(StyracosaurusVariant::getId)).toArray(StyracosaurusVariant[]::new);

    private final int id;

    StyracosaurusVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static StyracosaurusVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
