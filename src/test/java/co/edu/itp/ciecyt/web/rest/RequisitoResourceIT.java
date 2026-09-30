package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.CiecytApp;
import co.edu.itp.ciecyt.domain.Requisito;
import co.edu.itp.ciecyt.domain.enumeration.EnumEstadoProyecto;
import co.edu.itp.ciecyt.domain.enumeration.TipoRequisito;
import co.edu.itp.ciecyt.repository.RequisitoRepository;
import co.edu.itp.ciecyt.security.AuthoritiesConstants;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for the {@link RequisitoResource} REST controller.
 */
@SpringBootTest(classes = CiecytApp.class)
@AutoConfigureMockMvc
@WithMockUser(authorities = AuthoritiesConstants.ADMIN)
public class RequisitoResourceIT {

    /** Requisitos sembrados por el Acuerdo 025, art. 5, par. 1 (changeset 20260929000001). */
    private static final String[] CODIGOS_ACUERDO_25 = {
        "REQ_CERT_EST_ACTIVO",
        "REQ_RECIBO_PAGO",
        "REQ_RECORD_ACADEMICO",
        "REQ_FORMATO_INSCRIPCION",
        "REQ_PROPUESTA",
        "REQ_ASISTENCIA_SUSTENTACIONES",
        "REQ_AVANCE_75",
    };

    @Autowired
    private MockMvc restRequisitoMockMvc;

    @Autowired
    private RequisitoRepository requisitoRepository;

    @Test
    @Transactional
    public void getAllRequisitos() throws Exception {
        restRequisitoMockMvc
            .perform(get("/api/requisitos").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    /**
     * La semilla del Acuerdo 025 tiene que estar activa y con el avance del 75% como campo
     * aplicable a todas las modalidades; la exclusion de Diplomado y Especializacion se resuelve
     * al generar los requisitos del proyecto, no en la fila del catalogo.
     */
    @Test
    @Transactional
    public void laSemillaDelAcuerdo25EstaActiva() throws Exception {
        restRequisitoMockMvc
            .perform(get("/api/requisitos/activos").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        List<String> codigos = requisitoRepository.findAllByActivoTrueOrderByNombreAsc().stream()
            .map(Requisito::getCodigo)
            .collect(Collectors.toList());

        for (String codigo : CODIGOS_ACUERDO_25) {
            assertThat(codigos).contains(codigo);
        }

        Requisito avance = requisitoRepository.findAll().stream()
            .filter(requisito -> "REQ_AVANCE_75".equals(requisito.getCodigo()))
            .findFirst()
            .orElseThrow(() -> new AssertionError("REQ_AVANCE_75 no existe en el catalogo"));

        assertThat(avance.getTipo()).isEqualTo(TipoRequisito.CAMPO);
        assertThat(avance.getRequisitoModalidad()).isNull();
        assertThat(avance.getObligatorio()).isTrue();
        assertThat(avance.getRequisitoEstado()).isEqualTo(EnumEstadoProyecto.EN_VALIDACION_DOCUMENTAL);
    }

    /**
     * Los requisitos que habia antes para tesis (Acuerdo 29) quedan inactivos para que no se
     * mezclen con la lista de verificacion del Acuerdo 025.
     */
    @Test
    @Transactional
    public void losRequisitosLegacyDelAcuerdo29QuedaronInactivos() {
        List<Requisito> legacy = requisitoRepository.findAll().stream()
            .filter(requisito -> requisito.getId() != null && requisito.getId() >= 95000 && requisito.getId() <= 95006)
            .collect(Collectors.toList());

        assertThat(legacy).hasSize(7);
        legacy.forEach(requisito -> assertThat(requisito.getActivo()).isFalse());
    }

    @Test
    @Transactional
    public void getRequisito() throws Exception {
        Requisito requisito = requisitoRepository.findAll().stream()
            .filter(candidato -> candidato.getId() != null && candidato.getId() >= 95100 && candidato.getId() <= 95106)
            .findFirst()
            .orElseThrow(() -> new AssertionError("Falta la semilla del Acuerdo 025"));

        restRequisitoMockMvc
            .perform(get("/api/requisitos/{id}", requisito.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.codigo").value(requisito.getCodigo()));
    }

    @Test
    @Transactional
    public void getNonExistingRequisito() throws Exception {
        restRequisitoMockMvc
            .perform(get("/api/requisitos/{id}", Long.MAX_VALUE).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void createRequisito() throws Exception {
        int databaseSizeBeforeCreate = requisitoRepository.findAll().size();

        restRequisitoMockMvc
            .perform(
                post("/api/requisitos")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        "{\"codigo\":\"REQ_TEST_IT\",\"nombre\":\"Requisito de prueba\","
                            + "\"tipo\":\"DOCUMENTO\",\"obligatorio\":true,\"activo\":true}"
                    )
            )
            .andExpect(status().isCreated());

        assertThat(requisitoRepository.findAll()).hasSize(databaseSizeBeforeCreate + 1);
    }

    /**
     * El catalogo de requisitos lo administra un rol concreto: un usuario comun no lo puede
     * crear ni borrar.
     */
    @Test
    @Transactional
    @WithMockUser(authorities = AuthoritiesConstants.USER)
    public void createRequisitoSinRolAdminEstaProhibido() throws Exception {
        restRequisitoMockMvc
            .perform(
                post("/api/requisitos")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"codigo\":\"REQ_SIN_PERMISO\",\"nombre\":\"No deberia crearse\",\"tipo\":\"DOCUMENTO\"}")
            )
            .andExpect(status().isForbidden());
    }

    @Test
    @Transactional
    @WithMockUser(authorities = AuthoritiesConstants.USER)
    public void deleteRequisitoSinRolAdminEstaProhibido() throws Exception {
        restRequisitoMockMvc
            .perform(delete("/api/requisitos/{id}", 95100L).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isForbidden());
    }
}
