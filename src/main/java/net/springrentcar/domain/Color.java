package net.springrentcar.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "colors")
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "color")
    private String color;
}
