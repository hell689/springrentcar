package net.springrentcar.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Rent {
    private Long id;
    private Request request;
    private Car car;
    private Date startDate;
    private Date endDate;
}
