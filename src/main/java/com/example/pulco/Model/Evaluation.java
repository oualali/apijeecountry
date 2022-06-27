package com.example.pulco.Model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "evaluations")
public class Evaluation implements Serializable {
    @Id
    @GeneratedValue( strategy= GenerationType.IDENTITY )
    private int id;
    private String value;

    public Evaluation() {
        this.date = LocalDateTime.now();
    }

    public Evaluation(String value) {
        this.value = value;
        this.date = LocalDateTime.now();
    }

    private LocalDateTime date;

    public void setValue(String value) {
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public LocalDateTime getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Evaluation{" +
                "id=" + id +
                ", value='" + value + '\'' +
                ", date=" + date +
                '}';
    }
}
