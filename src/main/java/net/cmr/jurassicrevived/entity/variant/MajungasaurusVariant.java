package net.cmr.jurassicrevived.entity.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum MajungasaurusVariant {
    MALE(0),
    FEMALE(1);

    private static final MajungasaurusVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(MajungasaurusVariant::getId)).toArray(MajungasaurusVariant[]::new);

    private final int id;

    MajungasaurusVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static MajungasaurusVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
