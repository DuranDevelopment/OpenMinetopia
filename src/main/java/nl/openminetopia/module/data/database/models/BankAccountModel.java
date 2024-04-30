package nl.openminetopia.module.data.database.models;

import com.craftmend.storm.api.StormModel;
import com.craftmend.storm.api.markers.Column;
import com.craftmend.storm.api.markers.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import nl.openminetopia.api.banking.enums.AccountType;

@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "bankaccounts")
public class BankAccountModel extends StormModel {

    @Column(name = "type")
    private AccountType type;

    @Column(name = "name")
    private String name;

    @Column(name = "balance", defaultValue = "0")
    private Double balance;

    @Column(name = "frozen", defaultValue = "0")
    private Boolean frozen;
}
