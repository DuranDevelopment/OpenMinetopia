package nl.openminetopia.module.plots.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.kyori.adventure.text.Component;
import nl.openminetopia.utils.MessageUtils;
import nl.openminetopia.utils.WorldGuardUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@CommandAlias("plotinfo|pi")
public class PlotInfoCommand extends BaseCommand {

    @Default
    @Description("Bekijk informatie van een plot.")
    public void plotInfo(Player player) {
        ProtectedRegion region = WorldGuardUtils.getTopRegion(player.getLocation());

        if (region == null) {
            player.sendMessage(MessageUtils.format("<red>Je staat niet op een geldig plot."));
            return;
        }

        if (region.getFlag(WorldGuardUtils.PLOT_FLAG) == null) {
            player.sendMessage(MessageUtils.format("<red>Dit is geen geldig plot."));
            return;
        }

        //TODO: Fix it so the last member and owner don't have the ,
        StringBuilder owners = new StringBuilder();
        region.getOwners().getUniqueIds().forEach(ownerId -> {
            owners.append(Bukkit.getOfflinePlayer(ownerId).getName());
            owners.append(", ");
        });

        StringBuilder members = new StringBuilder();
        region.getMembers().getUniqueIds().forEach(memberId -> {
            members.append(Bukkit.getOfflinePlayer(memberId).getName());
            members.append(", ");
        });

        player.sendMessage(MessageUtils.format("<dark_aqua><st>----------------------------------------------"));
        player.sendMessage(MessageUtils.format("<dark_aqua>Plot informatie voor: <aqua>" + region.getId()));
        player.sendMessage(Component.empty());
        player.sendMessage(MessageUtils.format("<dark_aqua>Owners: <aqua>" + (region.getOwners().size() > 0 ? owners : "Geen.")));
        player.sendMessage(MessageUtils.format("<dark_aqua>Members: <aqua>" + (region.getMembers().size() > 0 ? members : "Geen.")));

        if(region.getFlag(WorldGuardUtils.PLOT_DESCRIPTION) != null) {
            String description = region.getFlag(WorldGuardUtils.PLOT_DESCRIPTION);
            player.sendMessage(MessageUtils.format("<dark_aqua>Beschrijving: <aqua>" + description));
        }

        player.sendMessage(MessageUtils.format("<dark_aqua><st>----------------------------------------------"));
    }

}
