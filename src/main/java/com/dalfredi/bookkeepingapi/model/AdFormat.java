package com.dalfredi.bookkeepingapi.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "adformats", schema = "bookkeeping")
public class AdFormat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;


    private String name;
    private String localName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AdFormat adFormat = (AdFormat) o;
        return id != null && Objects.equals(id, adFormat.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}