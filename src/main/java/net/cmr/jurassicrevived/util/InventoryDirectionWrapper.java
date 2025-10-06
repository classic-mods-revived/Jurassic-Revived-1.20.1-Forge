package net.cmr.jurassicrevived.util;

import net.minecraft.core.Direction;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandlerModifiable;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class InventoryDirectionWrapper {
    public Map<Direction, LazyOptional<WrappedHandler>> directionsMap;

    public InventoryDirectionWrapper(IItemHandlerModifiable handler, InventoryDirectionEntry... entries) {
        directionsMap = new HashMap<>();

        // Group all entries by direction so each direction can expose multiple slots
        Map<Direction, List<InventoryDirectionEntry>> grouped = new HashMap<>();
        for (var x : entries) {
            grouped.computeIfAbsent(x.direction, d -> new ArrayList<>()).add(x);
        }

        // Build a single WrappedHandler per direction that knows all slots for that direction
        for (var e : grouped.entrySet()) {
            Direction dir = e.getKey();
            List<InventoryDirectionEntry> dirEntries = e.getValue();

            directionsMap.put(dir, LazyOptional.of(() -> new WrappedHandler(
                    handler,
                    // extract: allow any slot listed for this direction
                    (i) -> {
                        for (var de : dirEntries) {
                            if (de.slotIndex == i) return true;
                        }
                        return false;
                    },
                    // insert: allow only slots listed for this direction with canInsert = true
                    (i, s) -> {
                        for (var de : dirEntries) {
                            if (de.slotIndex == i && de.canInsert) return true;
                        }
                        return false;
                    }
            )));
        }
    }
}