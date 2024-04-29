package nl.openminetopia.module.plots.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import nl.openminetopia.utils.MessageUtils;
import nl.openminetopia.utils.WorldGuardUtils;
import org.bukkit.entity.Player;

@CommandAlias("plot|p")
public class PlotDescriptionCommand extends BaseCommand {

    @Subcommand("description")
    @CommandPermission("openminetopia.plot.description")
    public void plotDescription(Player player, String description) {
        ProtectedRegion region = WorldGuardUtils.getTopRegion(player.getLocation());

        if (region == null) {
            player.sendMessage(MessageUtils.format("<red>Je staat niet op een geldig plot."));
            return;
        }

        if (region.getFlag(WorldGuardUtils.PLOT_FLAG) == null) {
            player.sendMessage(MessageUtils.format("<red>Dit is geen geldig plot."));
            return;
        }

        if (description.isBlank() || description.equalsIgnoreCase("remove") || description.equalsIgnoreCase("delete") || description.equalsIgnoreCase("null")) {
            region.setFlag(WorldGuardUtils.PLOT_DESCRIPTION, null);
            player.sendMessage(MessageUtils.format("<dark_aqua>Plot description van <aqua>" + region.getId() + " <dark_aqua>verwijderd."));
            return;
        }

        region.setFlag(WorldGuardUtils.PLOT_DESCRIPTION, description);
        player.sendMessage(MessageUtils.format("<dark_aqua>Plot description van <aqua>" + region.getId() + " <dark_aqua>veranderd naar <aqua>" + description + "<dark_aqua>."));
    }

}
