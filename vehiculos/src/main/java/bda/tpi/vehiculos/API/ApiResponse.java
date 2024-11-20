package bda.tpi.vehiculos.API;

import java.util.List;

public class ApiResponse {
    private Coordenadas coordenadasAgencia;
    private double radioAdmitidoKm;
    private List<ZonaRestringida> zonasRestringidas;

    @Override
    public String toString() {
        return "ApiResponse{" +
                "coordenadasAgencia=" + coordenadasAgencia +
                ", radioAdmitidoKm=" + radioAdmitidoKm +
                ", zonasRestringidas=" + zonasRestringidas +
                '}';
    }

    public static class Coordenadas {
        private double lat;
        private double lon;

        @Override
        public String toString() {
            return "{" +
                    "lat=" + lat +
                    ", lon=" + lon +
                    '}';
        }

        public double getLat() {
            return lat;
        }

        public double getLon() {
            return lon;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public void setLon(double lon) {
            this.lon = lon;
        }

    }

    public static class ZonaRestringida {
        private Coordenadas noroeste;
        private Coordenadas sureste;

        @Override
        public String toString() {
            return "{" +
                    "noroeste=" + noroeste +
                    ", sureste=" + sureste +
                    '}';
        }

        public Coordenadas getNoroeste() {
            return noroeste;
        }

        public Coordenadas getSureste() {
            return sureste;
        }

        public void setNoroeste(Coordenadas noroeste) {
            this.noroeste = noroeste;
        }

        public void setSureste(Coordenadas sureste) {
            this.sureste = sureste;
        }
    }

    public Coordenadas getCoordenadasAgencia() {
        return coordenadasAgencia;
    }

    public List<ZonaRestringida> getZonasRestringidas() {
        return zonasRestringidas;
    }

    public double getRadioAdmitidoKm() {
        return radioAdmitidoKm;
    }

    public void setCoordenadasAgencia(Coordenadas coordenadasAgencia) {
        this.coordenadasAgencia = coordenadasAgencia;
    }

    public void setRadioAdmitidoKm(double radioAdmitidoKm) {
        this.radioAdmitidoKm = radioAdmitidoKm;
    }

    public void setZonasRestringidas(List<ZonaRestringida> zonasRestringidas) {
        this.zonasRestringidas = zonasRestringidas;
    }

}
