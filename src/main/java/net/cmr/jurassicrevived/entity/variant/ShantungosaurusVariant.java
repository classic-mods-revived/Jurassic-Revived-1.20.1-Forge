package net.cmr.jurassicrevived.entity.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum ShantungosaurusVariant {
    MALE(0),
    FEMALE(1);

    private static final ShantungosaurusVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(ShantungosaurusVariant::getId)).toArray(ShantungosaurusVariant[]::new);

    private final int id;

    ShantungosaurusVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static ShantungosaurusVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
