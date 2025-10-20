package net.cmr.jurassicrevived.entity.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum GiganotosaurusVariant {
    MALE(0),
    FEMALE(1);

    private static final GiganotosaurusVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(GiganotosaurusVariant::getId)).toArray(GiganotosaurusVariant[]::new);

    private final int id;

    GiganotosaurusVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static GiganotosaurusVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
