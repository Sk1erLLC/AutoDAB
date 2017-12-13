package club.sk1er.mods.autodab;

import com.mrcrayfish.dab.autodab.MrCrayfishDabMod;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import pw._2pi.autogg.autodab.gg.AutoGG;

import java.io.IOException;

/**
 * Created by Mitchell Katz on 9/24/2017.
 */

@Mod(modid = "autodab", version = "1.1", clientSideOnly = true, acceptedMinecraftVersions = "[1.8.9]")
public class AutoDAB {

    private Sk1erMod sk1erMod;

    @Mod.EventHandler
    public void init(final FMLPreInitializationEvent event) throws IOException {
        sk1erMod = new Sk1erMod("autodab","1.1","Auto DAB");
        sk1erMod.checkStatus();
        new AutoGG(event.getSuggestedConfigurationFile());
        new MrCrayfishDabMod();
    }
}
