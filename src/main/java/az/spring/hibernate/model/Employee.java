package az.spring.hibernate.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table (name = "employee")
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", length = 25)
    private String name;

    @Column(length = 25)
    private String surname;

    private int age;
    private double salary;

}
