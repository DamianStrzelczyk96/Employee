package com.springboot.h2.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Car {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @NonNull
    public Employee employees;
    @NotEmpty
    private String name;
    @NotEmpty
    private String model;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ToString.Exclude
    private LocalDate registrationDate =LocalDate.now();

}
