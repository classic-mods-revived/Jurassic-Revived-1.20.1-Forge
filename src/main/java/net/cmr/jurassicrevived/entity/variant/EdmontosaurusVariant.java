package net.cmr.jurassicrevived.entity.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum EdmontosaurusVariant {
    MALE(0),
    FEMALE(1);

    private static final EdmontosaurusVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(EdmontosaurusVariant::getId)).toArray(EdmontosaurusVariant[]::new);

    private final int id;

    EdmontosaurusVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static EdmontosaurusVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
