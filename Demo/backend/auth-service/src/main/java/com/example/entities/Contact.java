package com.example.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "contacts", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "contact_id"})
})
@Data
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "contact_id")
    private Long contactId;
}
