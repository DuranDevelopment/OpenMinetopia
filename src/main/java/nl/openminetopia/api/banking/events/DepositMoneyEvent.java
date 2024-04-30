package nl.openminetopia.api.banking.events;

import lombok.Getter;
import nl.openminetopia.api.banking.accounts.Account;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Getter
public class DepositMoneyEvent extends Event {

    private static final HandlerList HANDLER_LIST = new HandlerList();
    private final Player player;
    private final double amount;
    private final Account account;

    /**
     * Constructor for the DepositMoneyEvent
     * @param player The player that is depositing the money
     * @param amount The amount of money that is being deposited
     * @param account The account where the money is being deposited
     *                If the account is null, the money is being deposited to the player's balance (Vault)
     */
    public DepositMoneyEvent(Player player, double amount, Account account) {
        this.player = player;
        this.amount = amount;
        this.account = account;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
