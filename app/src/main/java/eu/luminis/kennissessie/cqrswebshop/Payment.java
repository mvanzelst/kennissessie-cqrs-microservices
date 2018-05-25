package eu.luminis.kennissessie.cqrswebshop;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.factory.annotation.Autowired;

import eu.luminis.kennissessie.cqrswebshop.api.command.CreatePaymentCommand;
import eu.luminis.kennissessie.cqrswebshop.api.command.DepositAmountCommand;
import eu.luminis.kennissessie.cqrswebshop.api.event.AmountDepositedEvent;
import eu.luminis.kennissessie.cqrswebshop.api.event.PaymentCreatedEvent;
import eu.luminis.kennissessie.cqrswebshop.api.event.PaymentSuccessfulEvent;

@Aggregate
@Entity
public class Payment {

    @Autowired
    private transient EventBus eventBus;

    @Id
    @AggregateIdentifier
    private UUID id;

    private boolean isPayed;

    private long amount;

    // JPA happy :)
    protected Payment(){

    }

    @CommandHandler
    public Payment(CreatePaymentCommand createPaymentCommand){
        apply(new PaymentCreatedEvent(createPaymentCommand));
    }

    @CommandHandler
    public void handle(DepositAmountCommand depositAmountCommand){
        if(isPayed){
            throw new IllegalStateException("Amount already payed");
        }

        if(amount - depositAmountCommand.getAmount() < 0){
            throw new IllegalStateException("Deposit is to large for standing amount");
        }

        boolean paymentSuccessful = amount - depositAmountCommand.getAmount() == 0;
        apply(new AmountDepositedEvent(id, depositAmountCommand.getAmount()));

        if(paymentSuccessful){
            apply(new PaymentSuccessfulEvent(id));
        }
    }

    @EventSourcingHandler
    public void handle(AmountDepositedEvent amountDepositedEvent){
        amount -= amountDepositedEvent.getAmount();
    }

    @EventSourcingHandler
    public void handle(PaymentSuccessfulEvent paymentSuccessfulEvent){
        isPayed = true;
    }

    @EventSourcingHandler
    public void handle(PaymentCreatedEvent paymentCreatedEvent){
        id = paymentCreatedEvent.getPaymentId();
        amount = paymentCreatedEvent.getAmount();
        isPayed = false;
    }

}
