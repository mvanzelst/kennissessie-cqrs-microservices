package eu.luminis.kennissessie.cqrswebshop.api.event;

import java.util.Objects;
import java.util.UUID;

public class AmountDepositedEvent {

    private final UUID id;
    private final long amount;

    public AmountDepositedEvent(UUID id, long amount) {

        this.id = id;
        this.amount = amount;
    }

    public long getAmount() {
        return amount;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        AmountDepositedEvent that = (AmountDepositedEvent) o;
        return amount == that.amount && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, amount);
    }
}
