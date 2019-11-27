package net.springrentcar.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Request {
    private Long id;
    private String username;
    private Mark mark;
    private Gearbox gearbox;
    private float volume;
    private Color color;
    private Date startDate;
    private Date endDate;
    private String comment;
    private boolean processed;
}
