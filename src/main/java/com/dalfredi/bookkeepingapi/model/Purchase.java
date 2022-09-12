package com.dalfredi.bookkeepingapi.model;

import com.dalfredi.bookkeepingapi.model.audit.UserDateAudit;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "purchase", schema = "bookkeeping")
public class Purchase extends UserDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;

    @NotNull
    @Column(name = "date_time")
    LocalDateTime dateTime;

    @NotNull
    @OneToOne
    @JoinColumn(name = "format_id")
    AdFormat format;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    Customer customer;

    @NotNull
    @Min(value = 0L, message = "The value must be positive")
    @Column(name = "price")
    Long price;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "status_id")
    PaymentStatus status;


    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "channel_id" )
    @ToString.Exclude
    Channel channel;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Purchase record = (Purchase) o;
        return id != null && Objects.equals(id, record.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
