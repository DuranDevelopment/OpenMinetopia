package nl.openminetopia.utils;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.StringFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import lombok.experimental.UtilityClass;
import org.bukkit.Location;

import java.util.Comparator;

@UtilityClass
public class WorldGuardUtils {

    public static StateFlag PLOT_FLAG = new StateFlag("openmt-plot", true);
    public static StringFlag PLOT_DESCRIPTION = new StringFlag("openmt-description");

    public void loadFlags() {
        FlagRegistry registry = WorldGuard.getInstance().getFlagRegistry();
        try {
            registry.register(PLOT_FLAG);
            registry.register(PLOT_DESCRIPTION);
        } catch (FlagConflictException e) {
            PLOT_FLAG = (StateFlag) registry.get("openmt-plot");
            PLOT_DESCRIPTION = (StringFlag) registry.get("openmt-description");
        }

    }

    public ProtectedRegion getTopRegion(Location location) {
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager manager = container.get(BukkitAdapter.adapt(location.getWorld()));
        if(manager == null) return null;
        ApplicableRegionSet regionSet = manager.getApplicableRegions(BukkitAdapter.asBlockVector(location));

        return regionSet.getRegions().stream()
                .max(Comparator.comparingInt(ProtectedRegion::getPriority))
                .orElse(null);
    }

}
