package nl.openminetopia.module.plots.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import nl.openminetopia.api.plots.events.PlotCreateEvent;
import nl.openminetopia.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@CommandAlias("plot|p")
public class PlotDeleteCommand extends BaseCommand {

    @Subcommand("delete")
    @CommandPermission("openminetopia.plot.delete")
    public void deletePlot(Player player, String name) {
        World world = BukkitAdapter.adapt(player.getWorld());

        RegionContainer regionContainer = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager regionManager = regionContainer.get(world);

        if (regionManager == null) {
            player.sendMessage(MessageUtils.format("<red>Kon geen regio's ophalen voor deze wereld."));
            return;
        }

        ProtectedRegion region = regionManager.getRegion(name);

        if (region == null) {
            player.sendMessage(MessageUtils.format("<red>Geen region gevonden met de naam " + name + "."));
            return;
        }

        PlotCreateEvent event = new PlotCreateEvent(player, region);
        Bukkit.getPluginManager().callEvent(event);

        player.sendMessage(MessageUtils.format("<dark_aqua>Plot met de naam <aqua>" + region.getId() + " <dark_aqua>verwijderd."));
        regionManager.removeRegion(region.getId());
    }


}
