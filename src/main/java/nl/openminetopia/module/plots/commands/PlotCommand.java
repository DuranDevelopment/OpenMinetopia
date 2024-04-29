package nl.openminetopia.module.plots.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import nl.openminetopia.utils.MessageUtils;
import org.bukkit.command.CommandSender;

@CommandAlias("plot|p")
public class PlotCommand extends BaseCommand {

    @Default
    public void helpCommand(CommandSender sender) {
        sender.sendMessage(MessageUtils.format("<dark_aqua>/plot addowner <Speler> <aqua>- Voeg een speler toe als eigenaar."));
        sender.sendMessage(MessageUtils.format("<dark_aqua>/plot removeowner <Speler> <aqua>- Verwijder een speler als eigenaar."));
        sender.sendMessage(MessageUtils.format("<dark_aqua>/plot addmember <Speler> <aqua>- Voeg een speler toe als member."));
        sender.sendMessage(MessageUtils.format("<dark_aqua>/plot removemember <Speler> <aqua>- Verwijder een speler als member."));

        if(sender.hasPermission("openminetopia.plot.*")) {
            sender.sendMessage(MessageUtils.format("<dark_aqua>/plot create <Plot Naam> <aqua>- Maak een plot aan."));
            sender.sendMessage(MessageUtils.format("<dark_aqua>/plot delete <Plot Naam> <aqua>- Verwijder een plot"));
            sender.sendMessage(MessageUtils.format("<dark_aqua>/plot description <Plot Naam> <Beschrijving> <aqua>- Zet een beschrijving van een plot."));
            sender.sendMessage(MessageUtils.format("<dark_aqua>/plot list <Pagina> <Beschrijving> <aqua>- Lijst met plots."));
        }

        sender.sendMessage(MessageUtils.format("<dark_aqua>/plotinfo <aqua>- Bekijk informatie van een plot zoals owners en members."));
    }

}
