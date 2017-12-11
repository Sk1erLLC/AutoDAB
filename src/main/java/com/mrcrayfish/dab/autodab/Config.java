package com.mrcrayfish.dab.autodab;

import net.minecraftforge.common.config.*;
import java.io.*;

public class Config
{
    public static Configuration config;
    public static final String CATEGORY_GENERAL = "general";
    public static boolean showHint;
    
    public static void init(final File file) {
        if (Config.config == null) {
            Config.config = new Configuration(file);
        }
        loadConfig();
        Config.config.save();
    }
    
    public static void loadConfig() {
        Config.showHint = Config.config.getBoolean("show-hint", "general", true, "Hint the key to Dab upon joining a world.");
    }
}
