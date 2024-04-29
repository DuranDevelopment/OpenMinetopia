package nl.openminetopia.module.data.database.models;

import com.craftmend.storm.api.StormModel;
import com.craftmend.storm.api.markers.Column;
import com.craftmend.storm.api.markers.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "bankaccounts")
public class BankAccountModel extends StormModel {

    @Column(name = "type")
    private String type;

    @Column(name = "name")
    private String name;

    @Column(name = "balance", defaultValue = "0")
    private Integer balance;

    @Column(name = "frozen", defaultValue = "0")
    private Boolean frozen;
}
