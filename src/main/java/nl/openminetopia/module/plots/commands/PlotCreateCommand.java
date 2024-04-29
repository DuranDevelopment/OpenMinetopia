package nl.openminetopia.module.plots.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.BukkitPlayer;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import nl.openminetopia.api.plots.events.PlotCreateEvent;
import nl.openminetopia.utils.MessageUtils;
import nl.openminetopia.utils.WorldGuardUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

@CommandAlias("plot|p")
public class PlotCreateCommand extends BaseCommand {

    @Subcommand("create")
    @CommandPermission("openminetopia.plot.create")
    public void plotCreate(Player player, String name, boolean topToDown) {
        BukkitPlayer bukkitPlayer = BukkitAdapter.adapt(player);
        World bukkitWorld = player.getWorld();

        try {
            Region region = WorldEdit.getInstance().getSessionManager().get(bukkitPlayer).getSelection(bukkitPlayer.getWorld());
            BlockVector3 max = region.getMaximumPoint();
            BlockVector3 min = region.getMinimumPoint();

            if (topToDown) {
                max = region.getMaximumPoint().withY(bukkitWorld.getMaxHeight());
                min = region.getMinimumPoint().withY(bukkitWorld.getMinHeight());
            }

            ProtectedRegion wgRegion = new ProtectedCuboidRegion(name, min, max);
            wgRegion.setFlag(WorldGuardUtils.PLOT_FLAG, StateFlag.State.ALLOW);

            RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
            RegionManager manager = container.get(region.getWorld());

            if (manager == null) {
                player.sendMessage(MessageUtils.format("<red>Er ging iets mis met het aanmaken van je plot."));
                return;
            }

            manager.addRegion(wgRegion);

            PlotCreateEvent event = new PlotCreateEvent(player, wgRegion);
            Bukkit.getPluginManager().callEvent(event);

            player.sendMessage(MessageUtils.format("<dark_aqua>Je hebt het plot <aqua>" + wgRegion.getId() + " <dark_aqua>aangemaakt."));
        } catch (IncompleteRegionException e) {
            player.sendMessage(MessageUtils.format("<red>Je hebt nog geen selectie gemaakt, maak er een met de WorldEdit wand via //wand."));
        }

    }

}
