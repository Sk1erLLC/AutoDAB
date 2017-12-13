package pw._2pi.autogg.autodab.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import pw._2pi.autogg.autodab.gg.AutoGG;

import java.io.*;

public class ConfigUtil {

    private File configFile;

    public ConfigUtil(File configFile) {
        this.configFile = configFile;
        load();
    }

    public void load() {
        if (configFile.exists()) {
            try {
                FileReader fr = new FileReader(configFile);
                BufferedReader br = new BufferedReader(fr);
                StringBuilder builder = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    builder.append(line);
                }
                String s = builder.toString();
                JsonObject asJsonObject = new JsonParser().parse(s).getAsJsonObject();
                AutoGG.getInstance().setLength(asJsonObject.get("length").getAsInt());
                AutoGG.getInstance().setF5(asJsonObject.get("f5").getAsBoolean());
                AutoGG.getInstance().setToggled(asJsonObject.get("toggled").getAsBoolean());
                AutoGG.getInstance().toggleChroma(asJsonObject.get("chroma").getAsBoolean());

            } catch (Exception e) {

            }
        } else {

        }
    }

    public void save() {
        try {
            configFile.createNewFile();
            FileWriter fw = new FileWriter(configFile);
            BufferedWriter bw = new BufferedWriter(fw);
            JsonObject object = new JsonObject();
            object.addProperty("f5", AutoGG.getInstance().isF5());
            object.addProperty("length", AutoGG.getInstance().getLength());
            object.addProperty("chroma", AutoGG.getInstance().isChroma());

            object.addProperty("toggled", AutoGG.getInstance().isToggled());
            bw.write(object.toString());
            bw.close();
            fw.close();
        } catch (Exception e) {

        }
    }
}
