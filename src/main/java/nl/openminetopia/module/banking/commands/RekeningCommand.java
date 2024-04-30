package nl.openminetopia.module.banking.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import nl.openminetopia.utils.MessageUtils;
import org.bukkit.command.CommandSender;

@CommandAlias("rekening|ohrekening|spaarrekening|bankaccount")
public class RekeningCommand extends BaseCommand {


    @Default
    @CommandPermission("openminetopia.bankaccount")
    public void helpCommand(CommandSender sender) {
        sender.sendMessage(MessageUtils.format("<dark_aqua>/rekening create <Spaar/Bedrijf/Overheid> <aqua>- Maak een bankrekening."));
        sender.sendMessage(MessageUtils.format("<dark_aqua>/rekening delete <ID> <aqua>- Verwijder een bankrekening."));
        sender.sendMessage(MessageUtils.format("<dark_aqua>/rekening adduser <ID> <Speler> [Opnemen/Storten/Beide] <aqua>- Voeg een gebruiker toe aan een bankrekening."));
        sender.sendMessage(MessageUtils.format("<dark_aqua>/rekening removeuser <ID> <Speler> <aqua>- Verwijder een gebruiker van een bankrekening."));
        sender.sendMessage(MessageUtils.format("<dark_aqua>/rekening setname <ID> <NieuweNaam> <aqua>- Hernoem een bankrekening."));
        sender.sendMessage(MessageUtils.format("<dark_aqua>/rekening freeze <ID> <aqua>- Bevries een bankrekening of geef deze vrij."));
        sender.sendMessage(MessageUtils.format("<dark_aqua>/rekening give <ID> <Amount> <aqua>- Voeg geld toe aan het saldo van een rekening."));
        sender.sendMessage(MessageUtils.format("<dark_aqua>/rekening list <Spaar/Bedrijf/Overheid> [Pagina] <aqua>- Krijg een lijst van bankrekeningen."));
        sender.sendMessage(MessageUtils.format("<dark_aqua>/rekening plist <Speler> <aqua>- Krijg een lijst van bankrekeningen."));
        sender.sendMessage(MessageUtils.format("<dark_aqua>/rekening info <ID> <aqua>- Krijg informatie over een bankrekening te zien."));
    }
}
