package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.service.ProyectoPredictService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for managing {@link co.edu.itp.ciecyt.domain.Proyecto}.
 */
@RestController
@RequestMapping("/api")
public class ProyectoPredictResource {

    private final Logger log = LoggerFactory.getLogger(ProyectoPredictResource.class);

    private static final String ENTITY_NAME = "proyecto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProyectoPredictService proyectoPredictService;

    public ProyectoPredictResource(ProyectoPredictService proyectoPredictService) {
        this.proyectoPredictService = proyectoPredictService;
    }

    @GetMapping("/proyectopredict/{tipo}")
    public ResponseEntity<?> getPredictProyecto(@PathVariable Long tipo) throws Exception {
        log.debug("REST request to get Proyecto : {}");
        try {
            List<String> estadisticas = proyectoPredictService.predicePlay(tipo);
            return new ResponseEntity<>(estadisticas, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
