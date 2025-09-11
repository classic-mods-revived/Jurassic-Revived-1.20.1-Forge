package net.jurassicrevived.jurassicrevived.util;

public final class FenceUpdateGuard {
    private static final ThreadLocal<Boolean> GUARD = ThreadLocal.withInitial(() -> false);

    public static boolean begin() {
        if (Boolean.TRUE.equals(GUARD.get())) return false;
        GUARD.set(true);
        return true;
    }

    public static void end() {
        GUARD.set(false);
    }

    private FenceUpdateGuard() {}
}
