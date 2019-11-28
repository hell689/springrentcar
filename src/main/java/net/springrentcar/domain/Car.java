package net.springrentcar.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "mark", nullable = false)
    private Mark mark;
    @ManyToOne
    @JoinColumn(name = "gearbox", nullable = false)
    private Gearbox gearbox;
    private float volume;
    @ManyToOne
    @JoinColumn(name = "color", nullable = false)
    private Color color;

}
