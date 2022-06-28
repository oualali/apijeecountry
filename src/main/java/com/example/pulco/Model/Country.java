package com.example.pulco.Model;

import javax.persistence.*;
import java.io.Serializable;



@Entity
@Table(name = "countries")
public class Country implements Serializable {
    @Id
    @GeneratedValue( strategy= GenerationType.IDENTITY )
    private int id;
    private String pays, type;

    public Country() {
    }

    public Country(String pays, String type) {
        this.pays = pays;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getpays() {
        return this.pays;
    }

    public void setPays(String name) {
        this.pays = name;
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
                ", name='" + pays + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
