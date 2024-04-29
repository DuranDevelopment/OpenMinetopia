package nl.openminetopia.api.banking.accounts;

import lombok.AllArgsConstructor;
import lombok.Data;
import nl.openminetopia.api.banking.enums.AccountPermission;
import nl.openminetopia.api.banking.enums.AccountType;

import java.util.Map;
import java.util.UUID;

@Data
@AllArgsConstructor
public class Account {

    private final int id;
    private AccountType accountType;
    private String name;
    private double balance;
    private boolean frozen;

    private Map<UUID, AccountPermission> users;
}