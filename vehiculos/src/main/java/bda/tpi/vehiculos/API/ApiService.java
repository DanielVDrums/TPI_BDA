package bda.tpi.vehiculos.API;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiService {

    private final RestTemplate restTemplate = new RestTemplate();

    public ApiResponse obtenerJSON() {
        String url = "https://labsys.frc.utn.edu.ar/apps-disponibilizadas/backend/api/v1/configuracion/";
        ApiResponse response = restTemplate.getForObject(url, ApiResponse.class);

        //sout para ver si el objeto se creo con exito en base al JSON obtenido
        System.out.println("Respuesta obtenida: " + response);
        return response;
    }

}
