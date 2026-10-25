package com.misaka10843.kuayue_neo21.mixin;

import com.simibubi.create.content.contraptions.MountedStorageManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(MountedStorageManager.class)
public interface MountedStorageAccessor {
    @Invoker("isInitialized") boolean kuayue$isInitialized();
}
