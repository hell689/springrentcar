package net.springrentcar.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "marks")
public class Mark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "mark")
    private String mark;
}
