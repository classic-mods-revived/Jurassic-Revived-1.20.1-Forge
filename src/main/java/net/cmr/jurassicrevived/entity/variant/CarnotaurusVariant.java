package net.cmr.jurassicrevived.entity.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum CarnotaurusVariant {
    MALE(0),
    FEMALE(1);

    private static final CarnotaurusVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(CarnotaurusVariant::getId)).toArray(CarnotaurusVariant[]::new);

    private final int id;

    CarnotaurusVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static CarnotaurusVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
