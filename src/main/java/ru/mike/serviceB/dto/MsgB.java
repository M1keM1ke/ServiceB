package ru.mike.serviceB.dto;

import lombok.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MsgB {
    private String txt;
    private Date createdDt;
    private double currentTemp;
}
