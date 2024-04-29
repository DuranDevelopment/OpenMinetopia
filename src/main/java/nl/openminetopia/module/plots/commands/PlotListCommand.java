package nl.openminetopia.module.plots.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Optional;
import co.aikar.commands.annotation.Subcommand;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import nl.openminetopia.utils.MessageUtils;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@CommandAlias("plot|p")
public class PlotListCommand extends BaseCommand {

    @Subcommand("list")
    public void listCommand(Player player, @Optional Integer page) {
        World world = BukkitAdapter.adapt(player.getWorld());

        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager manager = container.get(world);

        if (manager == null) {
            player.sendMessage(MessageUtils.format("<red>Er ging iets mis met het ophalen van de regio's."));
            return;
        }

        Collection<String> regionNames = manager.getRegions().keySet();
        regionNames.remove("__global__");

        int pageSize = 15;
        int totalPages = (int) Math.ceil(regionNames.size() / (double) pageSize);

        if(page == null || page < 1 || page > totalPages) {
            page = 1;
        }

        int startIndex = (page - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, regionNames.size());

        List<String> regionList = new ArrayList<>(regionNames);
        for(int i = startIndex; i < endIndex; i++) {
            player.sendMessage(MessageUtils.format("<dark_aqua> - <aqua>" + regionList.get(i)));
        }

        player.sendMessage(MessageUtils.format("<dark_aqua>Pagina <aqua>" + page + "<dark_aqua>/<aqua>" + totalPages + "<dark_aqua>. Totaal: <aqua>" + regionNames.size() + " <dark_aqua>regio's."));
    }

}
