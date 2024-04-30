package nl.openminetopia.module.banking.menus;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import nl.openminetopia.api.banking.accounts.Account;
import nl.openminetopia.api.banking.enums.AccountPermission;
import nl.openminetopia.api.banking.manager.BankingManager;
import nl.openminetopia.module.banking.BankingModule;
import nl.openminetopia.utils.GUIHolder;
import nl.openminetopia.utils.ItemBuilder;
import nl.openminetopia.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class AccountContentMenu extends GUIHolder {


    private final Player player;
    private final Account account;
    private final double balance;
    private final String name;

    public AccountContentMenu(Player player, Account account) {
        this.player = player;
        this.account = account;

        if (account == null) {
            this.balance = BankingModule.getEcon().getBalance(player);
            this.inventory = Bukkit.createInventory(this, 9 * 6, MessageUtils.format("<dark_aqua>Totale waarde: <aqua>" + balance));
            this.name = player.getName();
            fillInventory(balance);
            return;
        }

        this.name = account.getName();
        balance = account.getBalance();
        this.inventory = Bukkit.createInventory(this, 9 * 6, MessageUtils.format("<dark_aqua>Totale waarde: <aqua>" + balance));
        fillInventory(balance);
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        event.setCancelled(true);

        if (event.getCurrentItem() == null) return;
        if (event.getCurrentItem().getType().equals(Material.AIR)) return;

        if (account != null && account.isFrozen()) {
            player.sendMessage(MessageUtils.format("<red>Deze rekening is bevroren."));
            return;
        }

        ItemStack item = event.getCurrentItem();

        // Menu Item
        if (NBTEditor.contains(item, "openmt_menu_banknote")) {
            double singularValue = Double.parseDouble(NBTEditor.getString(item, "openmt_menu_banknote"));
            double value = Double.parseDouble(NBTEditor.getString(item, "openmt_menu_banknote")) * item.getAmount();

            if (balance < value) {
                player.sendMessage(MessageUtils.format("<red>Er is onvoldoende saldo op deze rekening."));
                return;
            }

            if (player.getInventory().firstEmpty() == -1) {
                player.sendMessage(MessageUtils.format("<red>Full!"));
                return;
            }
            player.getInventory().addItem(moneyItem(item.getType(), singularValue, item.getAmount()));

            // check if player has withdraw permissions:
            if (account != null) {
                AccountPermission accountPermission = BankingManager.getInstance().getAccountPermission(account, player.getUniqueId());
                if (accountPermission != AccountPermission.WITHDRAW && accountPermission != AccountPermission.ADMIN) {
                    player.sendMessage(MessageUtils.format("<red>Je hebt geen toestemming om geld op te nemen van deze rekening"));
                    return;
                }
            }

            setBalance(account, balance - value);
            player.sendMessage(MessageUtils.format("<dark_aqua>Je hebt <aqua>€" + value + " <dark_aqua>opgenomen van de rekening <aqua>" + name));
            new AccountContentMenu(player, account).open(player);
            return;
        }

        if (NBTEditor.contains(item, "openmt_banking_note")) {
            // check if player has deposit permissions:
            if (account != null) {
                AccountPermission accountPermission = BankingManager.getInstance().getAccountPermission(account, player.getUniqueId());
                if (accountPermission != AccountPermission.DEPOSIT && accountPermission != AccountPermission.ADMIN) {
                    player.sendMessage(MessageUtils.format("<red>Je hebt geen toestemming om geld te storten op deze rekening"));
                    return;
                }
            }

            if (event.getClick().isLeftClick()) {
                double value = Double.parseDouble(NBTEditor.getString(item, "openmt_banking_note")) * item.getAmount();
                item.setAmount(0);

                player.sendMessage(MessageUtils.format("<dark_aqua>Je hebt <aqua>€" + value + " <dark_aqua>gestort naar de rekening <aqua>" + name));
                setBalance(account, balance + value);
                new AccountContentMenu(player, account).open(player);
                return;
            }

            double value = Double.parseDouble(NBTEditor.getString(item, "openmt_banking_note"));

            setBalance(account, balance + value);
            item.setAmount(item.getAmount() - 1);
            player.sendMessage(MessageUtils.format("<dark_aqua>Je hebt <aqua>€" + value + " <dark_aqua>gestort naar de rekening <aqua>" + name));
            new AccountContentMenu(player, account).open(player);
        }
    }


    private void setBalance(Account account, double value) {
        if (account == null) {
            BankingModule.getEcon().withdrawPlayer(player, balance);
            BankingModule.getEcon().depositPlayer(player, value);
            return;
        }
        account.setBalance(value);
    }

    private ItemStack moneyMenuItem(Material material, double value) {
        return new ItemBuilder(material)
                .setName(MessageUtils.format("<dark_aqua>€" + value))
                .addLoreLine("<gray>Klik om op te nemen.")
                .setNBT("openmt_menu_banknote", String.valueOf(value))
                .toItemStack();
    }

    private ItemStack moneyItem(Material material, double value, int amount) {
        return new ItemBuilder(material, amount)
                .setName(MessageUtils.format("<dark_aqua>€" + value))
                .addLoreLine("<aqua>Officieel Minetopia bankbiljet.")
                .addLoreLine("<aqua>Eigendom van de Centrale Bank.")
                .setNBT("openmt_banking_note", String.valueOf(value))
                .toItemStack();
    }

    private void fillInventory(double balance) {
        if (balance >= 2304 * 500) balance = 2304 * 500;

        while (balance >= 500 * 64) {
            ItemStack bankNotes = moneyMenuItem(Material.GHAST_TEAR, 500);
            bankNotes.setAmount(64);
            this.inventory.addItem(bankNotes);
            balance -= 500 * 64;
        }

        while (balance >= 500) {
            this.inventory.addItem(moneyMenuItem(Material.GHAST_TEAR, 500));
            balance -= 500;
        }

        while (balance >= 200) {
            this.inventory.addItem(moneyMenuItem(Material.DIAMOND, 200));
            balance -= 200;
        }

        while (balance >= 100) {
            this.inventory.addItem(moneyMenuItem(Material.REDSTONE, 100));
            balance -= 100;
        }

        while (balance >= 50) {
            this.inventory.addItem(moneyMenuItem(Material.EMERALD, 50));
            balance -= 50;
        }

        while (balance >= 20) {
            this.inventory.addItem(moneyMenuItem(Material.COAL, 20));
            balance -= 20;
        }

        while (balance >= 10) {
            this.inventory.addItem(moneyMenuItem(Material.IRON_INGOT, 10));
            balance -= 10;
        }

        while (balance >= 5) {
            this.inventory.addItem(moneyMenuItem(Material.QUARTZ, 5));
            balance -= 5;
        }

        while (balance >= 1) {
            this.inventory.addItem(moneyMenuItem(Material.GOLD_INGOT, 1));
            balance -= 1;
        }

        while (balance >= 0.10) {
            this.inventory.addItem(moneyMenuItem(Material.GOLD_NUGGET, 0.10));
            balance -= 0.10;
        }

        for (int i = 36; i < 45; i++) {
            this.inventory.setItem(i, new ItemBuilder(Material.PURPLE_STAINED_GLASS_PANE).setName(MessageUtils.format(" ")).toItemStack());
        }

        this.inventory.setItem(45, moneyMenuItem(Material.GOLD_NUGGET, 0.10));
        this.inventory.setItem(46, moneyMenuItem(Material.GOLD_INGOT, 1));
        this.inventory.setItem(47, moneyMenuItem(Material.QUARTZ, 5));
        this.inventory.setItem(48, moneyMenuItem(Material.IRON_INGOT, 10));
        this.inventory.setItem(49, moneyMenuItem(Material.COAL, 20));
        this.inventory.setItem(50, moneyMenuItem(Material.EMERALD, 50));
        this.inventory.setItem(51, moneyMenuItem(Material.REDSTONE, 100));
        this.inventory.setItem(52, moneyMenuItem(Material.DIAMOND, 200));
        this.inventory.setItem(53, moneyMenuItem(Material.GHAST_TEAR, 500));
    }
}
