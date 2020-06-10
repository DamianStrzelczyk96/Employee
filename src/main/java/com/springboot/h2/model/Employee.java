package com.springboot.h2.model;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @EqualsAndHashCode.Exclude
    @NotEmpty
    private String lastName;
    @EqualsAndHashCode.Exclude
    @NotEmpty
    private String firstName;
    @EqualsAndHashCode.Exclude
    @NotEmpty
    private String address;
    @EqualsAndHashCode.Exclude
    @NotEmpty
    private String city;
    @EqualsAndHashCode.Exclude
    @NotNull
    private double salary;

    @EqualsAndHashCode.Exclude
    @Max(value = 99)
    @Min(value = 18)
    private double age;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @EqualsAndHashCode.Exclude
    @NotNull
    private LocalDate startJobDate =LocalDate.now();
    @EqualsAndHashCode.Exclude
    private int benefit;

    @EqualsAndHashCode.Exclude
    private String email;

    @OneToMany(mappedBy = "employees",cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Car> cars;
}