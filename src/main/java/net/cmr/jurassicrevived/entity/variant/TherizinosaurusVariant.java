package net.cmr.jurassicrevived.entity.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum TherizinosaurusVariant {
    MALE(0),
    FEMALE(1);

    private static final TherizinosaurusVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(TherizinosaurusVariant::getId)).toArray(TherizinosaurusVariant[]::new);

    private final int id;

    TherizinosaurusVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static TherizinosaurusVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
