package bda.tpi.vehiculos.entity;

import bda.tpi.vehiculos.dto.VehiculoDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name="vehiculos")
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "patente")
    private String patente;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_modelo", referencedColumnName = "id")
    private Modelo modelo;

    @JsonIgnore
    @OneToMany(mappedBy = "vehiculo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Posicion> posiciones;

    public VehiculoDTO toDTO() {
        return new VehiculoDTO(
                id,
                patente,
                modelo.getDescripcion(),
                modelo.getMarca().getNombre()
        );
    }

    public Modelo getModelo() {
        return modelo;
    }

    public int getId() {
        return id;
    }

    public String getPatente() {
        return patente;
    }

    public List<Posicion> getPosiciones() {
        return posiciones;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    public void setPosiciones(List<Posicion> posiciones) {
        this.posiciones = posiciones;
    }
}
