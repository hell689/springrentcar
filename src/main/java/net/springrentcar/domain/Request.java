package net.springrentcar.domain;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "requests")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String username;
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
    @Type(type = "date")
    @Column(name = "start_date")
    private Date startDate;
    @Type(type = "date")
    @Column(name = "end_date")
    private Date endDate;
    private String comment;
    private boolean processed;
}
