package nl.openminetopia.api.banking.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;

@Getter
@AllArgsConstructor
public enum AccountType {
    PRIVATE("<gold>Privérekening", Material.GOLD_BLOCK),
    COMPANY("<aqua>Bedrijfsrekening", Material.DIAMOND_BLOCK),
    SAVINGS("<red>Spaarrekening", Material.REDSTONE_BLOCK),
    GOVERNMENT("<green>Overheidsrekening", Material.EMERALD_BLOCK),;

    private final String name;
    private final Material material;
}