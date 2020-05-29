package net.earomc.cca;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.*;

public class CommandComponent implements CommandExecutor {

    private final HashMap<String, CommandComponent> stringSubCommandHashMap;
    private final String key;
    private CommandExecutor commandExecutor;
    private ArrayList<CommandComponent> subCommands;

    public CommandComponent(String key) {
        this.key = key;
        this.stringSubCommandHashMap = new HashMap<>();
    }

    public CommandComponent() {
        this(null);
    }

    public CommandComponent(String key, CommandExecutor commandExecutor) {
        this(key);
        this.commandExecutor = commandExecutor;
    }

    public CommandComponent addSubCommand(CommandComponent commandComponent) {
        if (commandComponent == this) {
            throw new IllegalArgumentException("Can't make command component a sub command of itself!");
        }
        stringSubCommandHashMap.put(commandComponent.getKey(), commandComponent);
        return this;
    }

    public Set<String> getSubCommandKeys() {
        return stringSubCommandHashMap.keySet();
    }
    public List<CommandComponent> getSubCommands() {
        if (subCommands != null) return subCommands;
        subCommands = new ArrayList<>();
        stringSubCommandHashMap.forEach((k, c) -> subCommands.add(c));
        return subCommands;
    }

    public HashMap<String, CommandComponent> getSubCommandsMap() {
        return stringSubCommandHashMap;
    }

    public String getKey() {
        return key;
    }

    @Override
    @SuppressWarnings("ManualArrayCopy")
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (commandExecutor != null) {
            commandExecutor.onCommand(commandSender, command, label, args);
        }
        if (args.length > 0) {
            String[] newArgs = new String[args.length - 1];
            if (newArgs.length > 0) {
                for (int i = 0; i < newArgs.length; i++) {
                    newArgs[i] = args[i + 1];
                }
            }
            CommandComponent subCommand = stringSubCommandHashMap.get(args[0]);
            if (subCommand != null) {
                return subCommand.onCommand(commandSender, command, label, newArgs);
            }

        }
        return true;
    }
}
