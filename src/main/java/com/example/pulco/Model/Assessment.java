package com.example.pulco.Model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "assessments")
public class Assessment implements Serializable {
    @Id
    @GeneratedValue( strategy= GenerationType.IDENTITY )
    private int id;
    private String name, type;

    public Assessment() {
    }

    public Assessment(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Assessment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
