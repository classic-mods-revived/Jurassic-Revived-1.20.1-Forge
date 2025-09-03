package net.jurassicrevived.jurassicrevived.entity.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum BrachiosaurusVariant {
    MALE(0),
    FEMALE(1);

    private static final BrachiosaurusVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(BrachiosaurusVariant::getId)).toArray(BrachiosaurusVariant[]::new);

    private final int id;

    BrachiosaurusVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static BrachiosaurusVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
