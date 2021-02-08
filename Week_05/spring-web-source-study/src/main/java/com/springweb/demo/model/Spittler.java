package com.springweb.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Spittler {
    private Long id;
    private String message;
    private Date time;
    private Double latitude;
    private Double longtitude;

    @Override
    public boolean equals(Object o) {
     return  EqualsBuilder.reflectionEquals(this, o,  "id", "time");
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, "id", "time");
    }
}
