package ma.enset.tp4.commands.controllers;

import lombok.AllArgsConstructor;
import ma.enset.tp4.commonapi.commands.CreateAccountCommand;
import ma.enset.tp4.commonapi.commands.CreditAccountCommand;
import ma.enset.tp4.commonapi.commands.DebitAccountCommand;
import ma.enset.tp4.commonapi.dtos.CreateAccountRequestDTO;
import ma.enset.tp4.commonapi.dtos.CreditAccountRequestDTO;
import ma.enset.tp4.commonapi.dtos.DebitAccountRequestDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping(path = "/commands/account")
@AllArgsConstructor
public class AccountCommandController {

    private CommandGateway commandGateway;
    private EventStore eventStore;

    @PostMapping(path="/create")
    public CompletableFuture<String> createAccount(@RequestBody CreateAccountRequestDTO request){
        CompletableFuture<String> commandresponse=commandGateway.send(new CreateAccountCommand(
        UUID.randomUUID().toString(),
        request.getIntialBalance(),
        request.getCurrency()
));
        return commandresponse;
    }
    @GetMapping("/eventStore/{accountId}")
    public Stream eventStore(@PathVariable String accountId){
        return eventStore.readEvents(accountId).asStream();

    }
    @PutMapping(path="/credit")
    public CompletableFuture<String> creditAccount(@RequestBody CreditAccountRequestDTO request){
        CompletableFuture<String> commandresponse=commandGateway.send(new CreditAccountCommand(
                request.getId(),
                request.getAmount(),
                request.getCurrency()
        ));
        return commandresponse;
    }
    @PutMapping(path="/debit")
    public CompletableFuture<String> debitAccount(@RequestBody DebitAccountRequestDTO request){
        CompletableFuture<String> commandresponse=commandGateway.send(new DebitAccountCommand(
                request.getId(),
                request.getAmount(),
                request.getCurrency()
        ));
        return commandresponse;
    }
}
