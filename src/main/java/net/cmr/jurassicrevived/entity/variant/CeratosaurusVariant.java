package net.cmr.jurassicrevived.entity.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum CeratosaurusVariant {
    MALE(0),
    FEMALE(1);

    private static final CeratosaurusVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(CeratosaurusVariant::getId)).toArray(CeratosaurusVariant[]::new);

    private final int id;

    CeratosaurusVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static CeratosaurusVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
