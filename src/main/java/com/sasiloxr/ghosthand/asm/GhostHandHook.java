package com.sasiloxr.ghosthand.asm;

import com.google.common.collect.Lists;
import com.sasiloxr.ghosthand.GhostHandMod;
import com.sasiloxr.ghosthand.utils.Utils;
import net.minecraft.entity.Entity;

import java.util.Iterator;
import java.util.List;

public class GhostHandHook {
    public static GhostHandHook INSTANCE;

    public GhostHandHook() {
        INSTANCE = this;
    }

    public List<Entity> handlerList(List<Entity> list) {
        if (!GhostHandMod.enabled) {
            return list;
        }
        if (GhostHandMod.legit) {
            return list;
        }
        Iterator<Entity> iterator = list.iterator();
        while (iterator.hasNext()) {
            Entity entity1 = iterator.next();
            if (Utils.isTeamMate(entity1)) {
                iterator.remove();
            }
        }
        return list;
    }
}
