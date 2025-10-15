package net.cmr.jurassicrevived.entity.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum ApatosaurusVariant {
    MALE(0),
    FEMALE(1);

    private static final ApatosaurusVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(ApatosaurusVariant::getId)).toArray(ApatosaurusVariant[]::new);

    private final int id;

    ApatosaurusVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static ApatosaurusVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
