package club.sk1er.mods;

import com.mrcrayfish.dab.autodab.MrCrayfishDabMod;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import pw._2pi.autogg.autodab.gg.AutoGG;

import java.io.IOException;

/**
 * Created by Mitchell Katz on 9/24/2017.
 */

@Mod(modid = "autodab", version = "1.0.0", clientSideOnly = true, acceptedMinecraftVersions = "[1.8.9]")
public class AutoDAB {


    @Mod.EventHandler
    public void init(final FMLPreInitializationEvent event) throws IOException {
        new AutoGG(event.getSuggestedConfigurationFile());
        new MrCrayfishDabMod();
    }
}
