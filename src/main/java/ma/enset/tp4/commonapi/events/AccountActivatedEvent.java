package ma.enset.tp4.commonapi.events;

import lombok.Getter;
import ma.enset.tp4.commonapi.enums.AccountStatus;

public class AccountActivatedEvent extends BaseEvent<String> {
    @Getter private AccountStatus status;

    public AccountActivatedEvent(String id, AccountStatus status) {
        super(id);

        this.status = status;
    }


}
