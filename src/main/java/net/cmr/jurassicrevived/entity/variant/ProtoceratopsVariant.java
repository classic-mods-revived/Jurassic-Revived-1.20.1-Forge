package net.cmr.jurassicrevived.entity.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum ProtoceratopsVariant {
    MALE(0),
    FEMALE(1);

    private static final ProtoceratopsVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(ProtoceratopsVariant::getId)).toArray(ProtoceratopsVariant[]::new);

    private final int id;

    ProtoceratopsVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static ProtoceratopsVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
