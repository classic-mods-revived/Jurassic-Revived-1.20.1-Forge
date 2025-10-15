package net.cmr.jurassicrevived.entity.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum IndominusRexVariant {
    MALE(0),
    FEMALE(1);

    private static final IndominusRexVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(IndominusRexVariant::getId)).toArray(IndominusRexVariant[]::new);

    private final int id;

    IndominusRexVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static IndominusRexVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
