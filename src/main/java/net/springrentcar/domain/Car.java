package net.springrentcar.domain;

import lombok.Data;

@Data
public class Car {
    private Long id;
    private Mark mark;
    private Gearbox gearbox;
    private float volume;
    private Color color;

}
