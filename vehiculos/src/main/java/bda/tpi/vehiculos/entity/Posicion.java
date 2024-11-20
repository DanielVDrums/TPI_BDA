package bda.tpi.vehiculos.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Table(name = "posiciones")
public class Posicion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_vehiculo", referencedColumnName = "id")
    private Vehiculo vehiculo;

    @Column(name = "fecha_hora")
    private Date fecha_hora;

    @Column(name = "latitud")
    private Double latitud;

    @Column(name = "longitud")
    private Double longitud;

    public Posicion( Vehiculo vehiculo,Date fecha_hora, Double latitud, Double longitud) {
        this.vehiculo = vehiculo;
        this.fecha_hora = fecha_hora;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public Posicion(){

    }

    public Date getFechaHora() {
        return fecha_hora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fecha_hora = fechaHora;
    }
}
