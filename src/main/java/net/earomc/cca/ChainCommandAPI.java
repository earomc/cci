package net.earomc.cca;

import org.bukkit.plugin.java.JavaPlugin;

public class ChainCommandAPI extends JavaPlugin {

    public static void registerCommand(CommandComponent commandComponent, JavaPlugin plugin) {
        plugin.getCommand(commandComponent.getKey()).setExecutor(commandComponent);
    }

}
