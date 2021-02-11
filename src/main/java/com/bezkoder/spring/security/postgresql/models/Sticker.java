package com.bezkoder.spring.security.postgresql.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sticker")
public class Sticker extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String client_id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Product> products;

    private String name;

    private String phone;

    private String address;
    private String comment;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status=Status.not_opened;

}
