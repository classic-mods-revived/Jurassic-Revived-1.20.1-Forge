package net.cmr.jurassicrevived.entity.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum AlbertosaurusVariant {
    MALE(0),
    FEMALE(1);

    private static final AlbertosaurusVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(AlbertosaurusVariant::getId)).toArray(AlbertosaurusVariant[]::new);

    private final int id;

    AlbertosaurusVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static AlbertosaurusVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
