package pw._2pi.autogg.autodab.gg;

import com.mrcrayfish.dab.autodab.event.InputEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;

public class GGThread implements Runnable {
    @Override
    public void run() {
        try {
            GameSettings gameSettings = Minecraft.getMinecraft().gameSettings;
            int thirdPersonView = gameSettings.thirdPersonView;
            if (AutoGG.getInstance().isF5())
                gameSettings.thirdPersonView = 2;
            InputEvent.dabLocked = true;
            InputEvent.dabbing = true;
            Thread.sleep(1000L * AutoGG.getInstance().getLength());
            InputEvent.dabbing = false;
            InputEvent.dabLocked = false;
            gameSettings.thirdPersonView = thirdPersonView;
            AutoGG.getInstance().setRunning(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
