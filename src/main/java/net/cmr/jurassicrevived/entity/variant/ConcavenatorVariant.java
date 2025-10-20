package net.cmr.jurassicrevived.entity.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum ConcavenatorVariant {
    MALE(0),
    FEMALE(1);

    private static final ConcavenatorVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(ConcavenatorVariant::getId)).toArray(ConcavenatorVariant[]::new);

    private final int id;

    ConcavenatorVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static ConcavenatorVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
