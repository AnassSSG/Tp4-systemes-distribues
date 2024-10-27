package ma.enset.tp4.commands.aggregates;

import ma.enset.tp4.commonapi.commands.CreateAccountCommand;
import ma.enset.tp4.commonapi.commands.CreditAccountCommand;
import ma.enset.tp4.commonapi.commands.DebitAccountCommand;
import ma.enset.tp4.commonapi.enums.AccountStatus;
import ma.enset.tp4.commonapi.events.AccountActivatedEvent;
import ma.enset.tp4.commonapi.events.AccountCreatedEvent;
import ma.enset.tp4.commonapi.events.AccountCreditedEvent;
import ma.enset.tp4.commonapi.events.AccountDebitedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class AccountAggregate {
    @AggregateIdentifier
    private String accountId;
    private double balance;
    private String currency;
    private AccountStatus status;

    public AccountAggregate() {
    }
    @CommandHandler
    public AccountAggregate(CreateAccountCommand createAccountCommand) {
if(createAccountCommand.getIntialBalance()<0) throw new RuntimeException("impossible");
        AggregateLifecycle.apply(new AccountCreatedEvent(
                createAccountCommand.getId(),
                createAccountCommand.getIntialBalance(),
                createAccountCommand.getCurrency(),
                AccountStatus.CREATED
        ));
    }
    @EventSourcingHandler
    public void on(AccountCreatedEvent event) {
        this.accountId=event.getId();
        this.balance=event.getInitialBalance();
        this.currency=event.getCurrency();
        this.status=AccountStatus.CREATED;
        AggregateLifecycle.apply(new AccountActivatedEvent(
                event.getId(),
                AccountStatus.ACTIVATED
        ));
    }
    @EventSourcingHandler
    public void on(AccountActivatedEvent event) {
        this.status=event.getStatus();
    }
    @CommandHandler
    public void handle(CreditAccountCommand command) {
        if(command.getAmount()<0) throw new RuntimeException("impossible");
        AggregateLifecycle.apply(new AccountCreditedEvent(
command.getId(),
command.getAmount(),
                command.getCurrency()
        ));
    }
    @EventSourcingHandler
    public void on(AccountCreditedEvent event){
        this.balance += event.getAmount();
    }
    @CommandHandler
    public void handle(DebitAccountCommand command) {
        if(command.getAmount()<0) throw new RuntimeException("impossible");
        if(this.balance<command.getAmount()) throw new RuntimeException("Balance not sufficient exception =>" + balance);
        AggregateLifecycle.apply(new AccountDebitedEvent(
                command.getId(),
                command.getAmount(),
                command.getCurrency()
        ));
    }
    @EventSourcingHandler
    public void on(AccountDebitedEvent event){
        this.balance -= event.getAmount();
    }

}
