package net.cmr.jurassicrevived.entity.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum ProcompsognathusVariant {
    MALE(0),
    FEMALE(1);

    private static final ProcompsognathusVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(ProcompsognathusVariant::getId)).toArray(ProcompsognathusVariant[]::new);

    private final int id;

    ProcompsognathusVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static ProcompsognathusVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
