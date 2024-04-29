package nl.openminetopia.module.plots.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.annotation.Syntax;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import com.destroystokyo.paper.profile.PlayerProfile;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import nl.openminetopia.utils.MessageUtils;
import nl.openminetopia.utils.WorldGuardUtils;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

@CommandAlias("plot|p")
public class PlotMembersCommand extends BaseCommand {

    @Subcommand("addmember")
    @Description("Voegt een speler toe aan een plot.")
    @Syntax("<speler>")
    public void addPlotMember(Player player, OnlinePlayer onlinePlayer) {
        ProtectedRegion region = WorldGuardUtils.getTopRegion(player.getLocation());
        Player target = onlinePlayer.getPlayer();

        if (region == null) {
            player.sendMessage(MessageUtils.format("<red>Je staat niet op een geldig plot."));
            return;
        }

        if (region.getFlag(WorldGuardUtils.PLOT_FLAG) == null) {
            player.sendMessage(MessageUtils.format("<red>Dit is geen geldig plot."));
            return;
        }

        if (!region.getOwners().contains(player.getUniqueId())) {
            player.sendMessage(MessageUtils.format("<red>Je bent geen eigenaar van dit plot."));
            return;
        }

        if (region.getMembers().contains(target.getUniqueId())) {
            player.sendMessage(MessageUtils.format("<red>" + target.getName() + " is al een member van dit plot."));
            return;
        }

        region.getMembers().addPlayer(target.getUniqueId());
        player.sendMessage(MessageUtils.format("<dark_aqua>Je hebt <aqua>" + target.getName() + " <dark_aqua>toegevoegd aan het plot."));
    }

    @Subcommand("removemember")
    @Description("Verwijderd een speler van een plot.")
    @Syntax("<speler>")
    public void removePlotMember(Player player, OfflinePlayer offlinePlayer) {
        ProtectedRegion region = WorldGuardUtils.getTopRegion(player.getLocation());
        PlayerProfile profile = offlinePlayer.getPlayerProfile();

        if (region == null) {
            player.sendMessage(MessageUtils.format("<red>Je staat niet op een geldig plot."));
            return;
        }

        if (region.getFlag(WorldGuardUtils.PLOT_FLAG) == null) {
            player.sendMessage(MessageUtils.format("<red>Dit is geen geldig plot."));
            return;
        }

        if (!region.getOwners().contains(player.getUniqueId())) {
            player.sendMessage(MessageUtils.format("<red>Je bent geen eigenaar van dit plot."));
            return;
        }

        if (!region.getMembers().contains(profile.getId())) {
            player.sendMessage(MessageUtils.format("<red>" + profile.getName() + " is geen member van dit plot."));
            return;
        }

        region.getMembers().removePlayer(profile.getId());
        player.sendMessage(MessageUtils.format("<dark_aqua>Je hebt <aqua>" + profile.getName() + " <dark_aqua>verwijderd van dit plot."));
    }

}