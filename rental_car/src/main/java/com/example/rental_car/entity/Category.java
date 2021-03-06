package com.example.rental_car.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCategory")
    private int id;

    @Column(name = "typology")
    private String typology;

    @OneToMany(mappedBy="category", fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    private List<Vehicle> vehicles;

    public Category() {}

    public Category(String typology) {
        this.typology = typology;
    }

    public Category (int id, String typology) {
        this.id = id;
        this.typology = typology;
    }

    public Category(int id, String typology, List<Vehicle> vehicles) {
        this.id = id;
        this.typology = typology;
        this.vehicles = vehicles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypology() {
        return typology;
    }

    public void setTypology(String typology) {
        this.typology = typology;
    }

    public List<Vehicle> getVehicles() { return vehicles; }

    public void setVehicles(List<Vehicle> vehicles) { this.vehicles = vehicles; }

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    @Override
    public String toString() {
        return "Category [id=" + id + ", typology=" + typology + "]";
    }
}
