package net.cmr.jurassicrevived.entity.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum CompsognathusVariant {
    MALE(0),
    FEMALE(1);

    private static final CompsognathusVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(CompsognathusVariant::getId)).toArray(CompsognathusVariant[]::new);

    private final int id;

    CompsognathusVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static CompsognathusVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
