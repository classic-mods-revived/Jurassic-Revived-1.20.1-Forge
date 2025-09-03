package net.jurassicrevived.jurassicrevived.entity.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum AchillobatorVariant {
    MALE(0),
    FEMALE(1);

    private static final AchillobatorVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(AchillobatorVariant::getId)).toArray(AchillobatorVariant[]::new);

    private final int id;

    AchillobatorVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static AchillobatorVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
