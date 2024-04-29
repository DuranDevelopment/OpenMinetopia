package nl.openminetopia.module.plots;

import nl.openminetopia.module.Module;
import nl.openminetopia.module.plots.commands.*;

public class PlotModule extends Module {

    @Override
    public void enable() {
        registerCommand(new PlotInfoCommand());

        registerCommand(new PlotMembersCommand());
        registerCommand(new PlotOwnersCommand());
        registerCommand(new PlotClearCommand());
        registerCommand(new PlotCreateCommand());
        registerCommand(new PlotDeleteCommand());
        registerCommand(new PlotDescriptionCommand());
        registerCommand(new PlotListCommand());
    }

    @Override
    public void disable() {

    }
}
