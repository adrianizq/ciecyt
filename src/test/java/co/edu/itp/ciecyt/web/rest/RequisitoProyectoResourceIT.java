package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.CiecytApp;
import co.edu.itp.ciecyt.domain.Modalidad;
import co.edu.itp.ciecyt.domain.Proyecto;
import co.edu.itp.ciecyt.domain.RequisitoProyecto;
import co.edu.itp.ciecyt.domain.enumeration.EnumEstadoRequisito;
import co.edu.itp.ciecyt.repository.ModalidadRepository;
import co.edu.itp.ciecyt.repository.ProyectoRepository;
import co.edu.itp.ciecyt.repository.RequisitoProyectoRepository;
import co.edu.itp.ciecyt.security.AuthoritiesConstants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for the {@link RequisitoProyectoResource} REST controller.
 */
@SpringBootTest(classes = CiecytApp.class)
@AutoConfigureMockMvc
@WithMockUser(authorities = AuthoritiesConstants.ADMIN)
public class RequisitoProyectoResourceIT {

    @Autowired
    private MockMvc restRequisitoProyectoMockMvc;

    @Autowired
    private ProyectoRepository proyectoRepository;

    @Autowired
    private ModalidadRepository modalidadRepository;

    @Autowired
    private RequisitoProyectoRepository requisitoProyectoRepository;

    private Proyecto proyectoDeModalidad(Long modalidadId) {
        Modalidad modalidad = modalidadRepository.findById(modalidadId)
            .orElseThrow(() -> new AssertionError("No existe la modalidad " + modalidadId));
        Proyecto proyecto = new Proyecto()
            .titulo("Proyecto de prueba modalidad " + modalidadId)
            .proyectoModalidad(modalidad);
        return proyectoRepository.saveAndFlush(proyecto);
    }

    private void generar(Proyecto proyecto) throws Exception {
        restRequisitoProyectoMockMvc
            .perform(post("/api/requisito-proyectos/generar/proyecto/{id}", proyecto.getId()))
            .andExpect(status().isOk());
    }

    private RequisitoProyecto requisitoDe(Proyecto proyecto, String codigo) {
        return requisitoProyectoRepository.findByRequisitoProyectoProyectoId(proyecto.getId()).stream()
            .filter(
                requisito ->
                    requisito.getRequisitoProyectoRequisito() != null
                        && codigo.equals(requisito.getRequisitoProyectoRequisito().getCodigo())
            )
            .findFirst()
            .orElseThrow(() -> new AssertionError("No se genero el requisito " + codigo));
    }

    @Test
    @Transactional
    public void getAllRequisitoProyectos() throws Exception {
        restRequisitoProyectoMockMvc
            .perform(get("/api/requisito-proyectos").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    /**
     * Generar los requisitos de una tesis trae la lista del Acuerdo 025 y deja fuera los
     * requisitos legacy del Acuerdo 29, que quedaron inactivos.
     */
    @Test
    @Transactional
    public void generarRequisitosDeTesisIncluyeLaSemillaDelAcuerdo25() throws Exception {
        Proyecto proyecto = proyectoDeModalidad(9004L);

        restRequisitoProyectoMockMvc
            .perform(post("/api/requisito-proyectos/generar/proyecto/{id}", proyecto.getId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[*].requisitoProyectoRequisitoCodigo", hasItem("REQ_CERT_EST_ACTIVO")))
            .andExpect(jsonPath("$[*].requisitoProyectoRequisitoCodigo", hasItem("REQ_AVANCE_75")))
            .andExpect(jsonPath("$[*].requisitoProyectoRequisitoCodigo", not(hasItem("REQ_FORM_INSCRIPCION"))));
    }

    /**
     * El avance del 75% no aplica a Diplomado ni a Especializacion: el catalogo lo siembra con
     * modalidad NULL y quien lo filtra al generar es el servicio.
     */
    @Test
    @Transactional
    public void generarRequisitosDeDiplomadoExcluyeElAvance75() throws Exception {
        Proyecto proyecto = proyectoDeModalidad(9005L);

        restRequisitoProyectoMockMvc
            .perform(post("/api/requisito-proyectos/generar/proyecto/{id}", proyecto.getId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[*].requisitoProyectoRequisitoCodigo", hasItem("REQ_RECIBO_PAGO")))
            .andExpect(jsonPath("$[*].requisitoProyectoRequisitoCodigo", not(hasItem("REQ_AVANCE_75"))));
    }

    @Test
    @Transactional
    public void getRequisitosDelProyecto() throws Exception {
        Proyecto proyecto = proyectoDeModalidad(9001L);
        generar(proyecto);

        restRequisitoProyectoMockMvc
            .perform(get("/api/requisito-proyectos/proyecto/{id}", proyecto.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[*].requisitoProyectoRequisitoCodigo", hasItem("REQ_AVANCE_75")));
    }

    /**
     * El requisito de tipo CAMPO no recibe archivo sino el dato numerico del avance, y ese dato
     * se guarda en la observacion porque no hay columna propia para el.
     */
    @Test
    @Transactional
    public void entregarRequisitoDeCampoGuardaElDatoNumerico() throws Exception {
        Proyecto proyecto = proyectoDeModalidad(9001L);
        generar(proyecto);
        RequisitoProyecto avance = requisitoDe(proyecto, "REQ_AVANCE_75");

        restRequisitoProyectoMockMvc
            .perform(
                post("/api/requisito-proyectos/{id}/entregar", avance.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"observacion\":\"78.5\"}")
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.estado").value(EnumEstadoRequisito.ENTREGADO.name()))
            .andExpect(jsonPath("$.observacion").value("78.5"));

        RequisitoProyecto guardado = requisitoProyectoRepository.findById(avance.getId()).orElse(null);
        assertThat(guardado).isNotNull();
        assertThat(guardado.getEstado()).isEqualTo(EnumEstadoRequisito.ENTREGADO);
        assertThat(guardado.getObservacion()).isEqualTo("78.5");
    }

    @Test
    @Transactional
    public void entregarRequisitoDeCampoRechazaUnDatoNoNumerico() throws Exception {
        Proyecto proyecto = proyectoDeModalidad(9001L);
        generar(proyecto);
        RequisitoProyecto avance = requisitoDe(proyecto, "REQ_AVANCE_75");

        restRequisitoProyectoMockMvc
            .perform(
                post("/api/requisito-proyectos/{id}/entregar", avance.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"observacion\":\"ochenta\"}")
            )
            .andExpect(status().isBadRequest());
    }

    /**
     * Cuando el CIECYT aprueba sin dejar comentario propio no se borra el dato que trajo el
     * estudiante: es el unico registro del avance que se acaba de revisar.
     */
    @Test
    @Transactional
    public void validarSinComentarioConservaElDatoDelEstudiante() throws Exception {
        Proyecto proyecto = proyectoDeModalidad(9004L);
        generar(proyecto);
        RequisitoProyecto avance = requisitoDe(proyecto, "REQ_AVANCE_75");

        restRequisitoProyectoMockMvc
            .perform(
                post("/api/requisito-proyectos/{id}/entregar", avance.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"observacion\":\"81\"}")
            )
            .andExpect(status().isOk());

        restRequisitoProyectoMockMvc
            .perform(
                post("/api/requisito-proyectos/{id}/validar", avance.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"aprobado\":true}")
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.estado").value(EnumEstadoRequisito.APROBADO.name()))
            .andExpect(jsonPath("$.observacion").value("81"));
    }

    @Test
    @Transactional
    public void rechazarRequisitoGuardaLaObservacionDelCiecyt() throws Exception {
        Proyecto proyecto = proyectoDeModalidad(9004L);
        generar(proyecto);
        RequisitoProyecto certificado = requisitoDe(proyecto, "REQ_CERT_EST_ACTIVO");

        restRequisitoProyectoMockMvc
            .perform(
                post("/api/requisito-proyectos/{id}/validar", certificado.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"aprobado\":false,\"observacion\":\"El certificado no esta legible\"}")
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.estado").value(EnumEstadoRequisito.RECHAZADO.name()))
            .andExpect(jsonPath("$.observacion").value("El certificado no esta legible"));
    }

    /**
     * Un requisito inexistente no da informacion de ningun proyecto, asi que la autorizacion
     * falla antes de que se plantee el 404.
     */
    @Test
    @Transactional
    public void getRequisitoProyectoInexistenteRespondeProhibido() throws Exception {
        restRequisitoProyectoMockMvc
            .perform(get("/api/requisito-proyectos/{id}", Long.MAX_VALUE).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isForbidden());
    }
}
