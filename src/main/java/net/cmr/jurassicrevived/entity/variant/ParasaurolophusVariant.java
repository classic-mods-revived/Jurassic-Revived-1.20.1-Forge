package net.cmr.jurassicrevived.entity.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum ParasaurolophusVariant {
    MALE(0),
    FEMALE(1);

    private static final ParasaurolophusVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(ParasaurolophusVariant::getId)).toArray(ParasaurolophusVariant[]::new);

    private final int id;

    ParasaurolophusVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static ParasaurolophusVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
