package com.mrcrayfish.dab.autodab;

import api.player.model.ModelPlayerAPI;
import com.mrcrayfish.dab.autodab.client.model.entity.ModelPlayerOverride;
import com.mrcrayfish.dab.autodab.event.InputEvent;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class MrCrayfishDabMod {

    public static KeyBinding dab;

    public MrCrayfishDabMod() {
        MinecraftForge.EVENT_BUS.register((Object) new InputEvent());
        ModelPlayerAPI.register("cdabm", (Class) ModelPlayerOverride.class);
        ClientRegistry.registerKeyBinding(MrCrayfishDabMod.dab = new KeyBinding("Dab", 19, "Dab Mod"));
    }

    @Mod.EventHandler
    public void preInit(final FMLPreInitializationEvent event) {
        Config.init(event.getSuggestedConfigurationFile());
    }

}
