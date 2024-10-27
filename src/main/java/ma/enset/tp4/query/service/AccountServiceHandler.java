package ma.enset.tp4.query.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.enset.tp4.commonapi.dtos.OperationType;
import ma.enset.tp4.commonapi.events.AccountActivatedEvent;
import ma.enset.tp4.commonapi.events.AccountCreatedEvent;
import ma.enset.tp4.commonapi.events.AccountCreditedEvent;
import ma.enset.tp4.commonapi.events.AccountDebitedEvent;
import ma.enset.tp4.commonapi.queries.GetAccountByIdQuery;
import ma.enset.tp4.commonapi.queries.GetAllAccountsQuery;
import ma.enset.tp4.query.entities.Account;
import ma.enset.tp4.query.entities.Operation;
import ma.enset.tp4.query.repositories.AccountRepository;
import ma.enset.tp4.query.repositories.OperationRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j

public class AccountServiceHandler {
    private AccountRepository accountRepository;
    private OperationRepository operationRepository;
    @EventHandler
    public void on(AccountCreatedEvent event){
        log.info("AccountCreatedEvent received");
        accountRepository.save(new Account(
                event.getId(),
                event.getInitialBalance(),
                event.getStatus(),
                event.getCurrency(),
                null


        ));

    }
    @EventHandler
    public void on(AccountActivatedEvent event){
        log.info("AccountAdditional info received");
        Account account=accountRepository.findById(event.getId()).get();
        account.setStatus(event.getStatus());
        accountRepository.save(account);

    }
    @EventHandler
    public void on(AccountDebitedEvent event){
        log.info("AccountDebitedEvent received");
        Account account=accountRepository.findById(event.getId()).get();
        Operation operation = new Operation();
        operation.setAmount(event.getAmount());
        operation.setDate(new Date());
        operation.setType(OperationType.DEBIT);
        operation.setAccount(account);
        operationRepository.save(operation);
        account.setBalance(account.getBalance()-event.getAmount());
        accountRepository.save(account);


    }
    @EventHandler
    public void on(AccountCreditedEvent event){
        log.info("AccountCreditedEvent received");
        Account account=accountRepository.findById(event.getId()).get();
        Operation operation = new Operation();
        operation.setAmount(event.getAmount());
        operation.setDate(new Date());
        operation.setType(OperationType.CREDIT);
        operation.setAccount(account);
        operationRepository.save(operation);
        account.setBalance(account.getBalance()+event.getAmount());


    }
    @QueryHandler
    public List<Account> on(GetAllAccountsQuery query){
        return accountRepository.findAll();
    }
    @QueryHandler
    public Account on(GetAccountByIdQuery query){
        return accountRepository.findById(query.getId()).get();
    }


}
