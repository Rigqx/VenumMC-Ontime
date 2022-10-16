package dk.venummc.ontime;

import dk.venummc.ontime.commands.OntimeCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class Ontime extends JavaPlugin {
    public static Ontime instance;
    public static Ontime getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        getCommand("ontime").setExecutor(new OntimeCommand());
    }
}
