package com.springweb.demo.model;

import lombok.Data;

import java.util.Date;

@Data
public class Spittle {
    private Long id;
    private String message;
    private Date time;
    private Double latitude;
    private Double longtitude;
}
