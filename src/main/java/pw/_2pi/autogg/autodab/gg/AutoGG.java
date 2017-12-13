package pw._2pi.autogg.autodab.gg;

import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommand;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import pw._2pi.autogg.autodab.util.AutoGGThreadFactory;
import pw._2pi.autogg.autodab.util.ConfigUtil;
import pw._2pi.autogg.autodab.util.GetTriggers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class AutoGG {
    public static final ExecutorService THREAD_POOL;
    private static AutoGG instance;

    static {
        THREAD_POOL = Executors.newCachedThreadPool(new AutoGGThreadFactory());
    }

    private final Minecraft mc;
    private boolean onHypixel;
    private boolean toggle;
    private int length;
    private List<String> triggers;
    private ConfigUtil util;
    private boolean running;
    private boolean chroma = true;
    private boolean f5 = true;

    public AutoGG(File config) {
        this.mc = Minecraft.getMinecraft();
        this.onHypixel = false;
        this.toggle = true;
        this.length = 3;
        this.running = false;
        AutoGG.instance = this;
        util = new ConfigUtil(config);
        MinecraftForge.EVENT_BUS.register((Object) this);
        MinecraftForge.EVENT_BUS.register((Object) new GGListener());
        ClientCommandHandler.instance.registerCommand((ICommand) new GGCommand());
        AutoGG.THREAD_POOL.submit(new GetTriggers());

    }

    public static AutoGG getInstance() {
        return AutoGG.instance;
    }

    public ConfigUtil getUtil() {
        return util;
    }

    public boolean isF5() {
        return f5;
    }

    public void setF5(boolean f5) {
        this.f5 = f5;
    }

    public void toggleChroma(boolean newState) {
        this.chroma = newState;
    }

    public boolean isChroma() {
        return chroma;
    }

    @SubscribeEvent
    public void playerLoggedIn(final FMLNetworkEvent.ClientConnectedToServerEvent event) {
        this.onHypixel = (!this.mc.isSingleplayer() && event.manager.getRemoteAddress().toString().toLowerCase().contains("hypixel.net"));
    }

    @SubscribeEvent
    public void playerLoggedOut(final FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        this.onHypixel = false;
    }

    public boolean isHypixel() {
        return this.onHypixel;
    }

    public List getTriggers() {
        return this.triggers;
    }

    public void setTriggers(final ArrayList triggers) {
        this.triggers = triggers;
    }

    public boolean isToggled() {
        return this.toggle;
    }

    public void setToggled(boolean toggle) {
        this.toggle = toggle;
    }

    public void setToggled() {
        this.toggle = !this.toggle;
    }

    public int getLength() {
        return this.length;
    }

    public void setLength(final int delay) {
        this.length = delay;
    }

    public Minecraft getMinecraft() {
        return this.mc;
    }

    public boolean isRunning() {
        return this.running;
    }

    public void setRunning(final boolean running) {
        this.running = running;
    }
}
