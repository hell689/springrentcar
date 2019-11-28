package net.springrentcar.domain;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "rents")
public class Rent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "request", nullable = false)
    private Request request;
    @ManyToOne
    @JoinColumn(name = "car", nullable = false)
    private Car car;
    @Type(type = "date")
    @Column(name = "start_date")
    private Date startDate;
    @Type(type = "date")
    @Column(name = "end_date")
    private Date endDate;
}
