package net.cmr.jurassicrevived.entity.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum StegosaurusVariant {
    MALE(0),
    FEMALE(1);

    private static final StegosaurusVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(StegosaurusVariant::getId)).toArray(StegosaurusVariant[]::new);

    private final int id;

    StegosaurusVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static StegosaurusVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
