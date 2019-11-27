package net.springrentcar.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "gearboxes")
public class Gearbox {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "gearbox")
    private String gearbox;
}
