package nl.openminetopia;

import co.aikar.commands.MessageType;
import co.aikar.commands.PaperCommandManager;
import lombok.Getter;
import nl.openminetopia.module.ModuleManager;
import nl.openminetopia.module.data.DataModule;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class OpenMinetopia extends JavaPlugin {

    @Getter
    private static OpenMinetopia instance;
    @Getter
    private static ModuleManager moduleManager;
    @Getter
    private static PaperCommandManager commandManager;

    @Override
    public void onEnable() {
        instance = this;
        commandManager = new PaperCommandManager(this);
        moduleManager = new ModuleManager();

        moduleManager.register(new DataModule());

        commandManager.enableUnstableAPI("help");
        commandManager.setFormat(MessageType.HELP, ChatColor.DARK_AQUA);
    }

    @Override
    public void onDisable() {
        moduleManager.disable();
    }
}
