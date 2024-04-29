package nl.openminetopia.module.plots.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import nl.openminetopia.utils.MessageUtils;
import nl.openminetopia.utils.WorldGuardUtils;
import org.bukkit.entity.Player;

@CommandAlias("plot|p")
public class PlotClearCommand extends BaseCommand {

    @Subcommand("clear")
    @CommandPermission("openminetopia.plot.clear")
    @Description("Verwijderd alle members en spelers van een plot.")
    public void plotClear(Player player) {
        ProtectedRegion region = WorldGuardUtils.getTopRegion(player.getLocation());

        if (region == null) {
            player.sendMessage(MessageUtils.format("<red>Je staat niet op een geldig plot."));
            return;
        }

        if (region.getFlag(WorldGuardUtils.PLOT_FLAG) == null) {
            player.sendMessage(MessageUtils.format("<red>Dit is geen geldig plot."));
            return;
        }

        region.getOwners().clear();
        region.getMembers().clear();

        player.sendMessage(MessageUtils.format("<dark_aqua>Je hebt alle plot <aqua>owners <dark_aqua>en <aqua>members <dark_aqua>verwijderd."));
    }

}
