package bda.tpi.vehiculos.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "modelos")
public class Modelo {

    @Id
    @Column(name = "id")
    private int id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_marca", referencedColumnName = "id")
    private Marca marca;

    @Column(name = "descripcion")
    private String descripcion;

    @JsonIgnore
    @OneToMany(mappedBy = "modelo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Vehiculo> vehiculos;

    public int getId() {
        return id;
    }

    public Marca getMarca() {
        return marca;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public List<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setVehiculos(List<Vehiculo> vehiculos) {
        this.vehiculos = vehiculos;
    }

    @Override
    public String toString() {
        return "Modelo{" +
                "marca=" + marca +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}