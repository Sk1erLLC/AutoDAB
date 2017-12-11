package com.mrcrayfish.dab.autodab.event;

import com.mrcrayfish.dab.autodab.MrCrayfishDabMod;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class InputEvent {
    public static final int MAX_DABBING_HELD = 8;
    public static boolean dabbing;
    public static boolean printed;
    public static int dabbingHeld;
    public static int prevDabbingHeld;
    public static boolean dabLocked = false;
    public static float firstPersonPartialTicks;

    static {
        InputEvent.dabbing = false;
        InputEvent.printed = false;
        InputEvent.dabbingHeld = 0;
        InputEvent.prevDabbingHeld = 0;
    }

    @SubscribeEvent
    public void onKeyInput(final net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent event) {
        if (dabLocked)
            return;
        if (MrCrayfishDabMod.dab.isKeyDown()) {
            InputEvent.dabbing = true;
        } else {
            InputEvent.dabbing = false;
        }
    }

    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        InputEvent.prevDabbingHeld = InputEvent.dabbingHeld;
        if (InputEvent.dabbing && InputEvent.dabbingHeld < 8) {
            ++InputEvent.dabbingHeld;
        } else if (!InputEvent.dabbing && InputEvent.dabbingHeld > 0) {
            --InputEvent.dabbingHeld;
        }
    }

    @SubscribeEvent
    public void onRender(final RenderHandEvent event) {
        InputEvent.firstPersonPartialTicks = event.partialTicks;
    }
}
