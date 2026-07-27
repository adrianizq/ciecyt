package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.CiecytApp;
import co.edu.itp.ciecyt.domain.InformacionPasantia;
import co.edu.itp.ciecyt.repository.InformacionPasantiaRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link InformacionPasantiaResource} REST controller.
 */
@SpringBootTest(classes = CiecytApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class InformacionPasantiaResourceIT {

    private static final Integer DEFAULT_DURACION_HORAS = 1;
    private static final Integer UPDATED_DURACION_HORAS = 2;

    private static final String DEFAULT_DIRECCION = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_LUNES = false;
    private static final Boolean UPDATED_LUNES = true;

    private static final Boolean DEFAULT_MARTES = false;
    private static final Boolean UPDATED_MARTES = true;

    private static final Boolean DEFAULT_MIERCOLES = false;
    private static final Boolean UPDATED_MIERCOLES = true;

    private static final Boolean DEFAULT_JUEVES = false;
    private static final Boolean UPDATED_JUEVES = true;

    private static final Boolean DEFAULT_VIERNES = false;
    private static final Boolean UPDATED_VIERNES = true;

    private static final Boolean DEFAULT_SABADO = false;
    private static final Boolean UPDATED_SABADO = true;

    private static final Boolean DEFAULT_DOMINGO = false;
    private static final Boolean UPDATED_DOMINGO = true;

    private static final String DEFAULT_CONVENIO = "AAAAAAAAAA";
    private static final String UPDATED_CONVENIO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_BONO_ALIMENTICIO = false;
    private static final Boolean UPDATED_BONO_ALIMENTICIO = true;

    private static final Boolean DEFAULT_APOYO_ECONOMICO = false;
    private static final Boolean UPDATED_APOYO_ECONOMICO = true;

    private static final Boolean DEFAULT_AUXILIO_TRANSPORTE = false;
    private static final Boolean UPDATED_AUXILIO_TRANSPORTE = true;

    private static final Boolean DEFAULT_CAPACITACION = false;
    private static final Boolean UPDATED_CAPACITACION = true;

    private static final String DEFAULT_OTRO_APOYO = "AAAAAAAAAA";
    private static final String UPDATED_OTRO_APOYO = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE_EMPRESA = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_EMPRESA = "BBBBBBBBBB";

    private static final String DEFAULT_NIT_EMPRESA = "AAAAAAAAAA";
    private static final String UPDATED_NIT_EMPRESA = "BBBBBBBBBB";

    private static final String DEFAULT_DIRECCION_EMPRESA = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION_EMPRESA = "BBBBBBBBBB";

    private static final String DEFAULT_SECTOR_ECONOMICO_EMPRESA = "AAAAAAAAAA";
    private static final String UPDATED_SECTOR_ECONOMICO_EMPRESA = "BBBBBBBBBB";

    private static final String DEFAULT_REPRESENTANTE_LEGAL_EMPRESA = "AAAAAAAAAA";
    private static final String UPDATED_REPRESENTANTE_LEGAL_EMPRESA = "BBBBBBBBBB";

    private static final String DEFAULT_ASESOR_EMPRESA = "AAAAAAAAAA";
    private static final String UPDATED_ASESOR_EMPRESA = "BBBBBBBBBB";

    private static final String DEFAULT_CARGO_ASESOR_EMPRESA = "AAAAAAAAAA";
    private static final String UPDATED_CARGO_ASESOR_EMPRESA = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_ASESOR_EMPRESA = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_ASESOR_EMPRESA = "BBBBBBBBBB";

    private static final String DEFAULT_MUNICIPIO_EMPRESA = "AAAAAAAAAA";
    private static final String UPDATED_MUNICIPIO_EMPRESA = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO_CONTACTO_EMPRESA = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO_CONTACTO_EMPRESA = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_EMPRESA = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_EMPRESA = "BBBBBBBBBB";

    private static final String DEFAULT_DEPARTAMENTO_EMPRESA = "AAAAAAAAAA";
    private static final String UPDATED_DEPARTAMENTO_EMPRESA = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTIFICACION_REPRESENTANTE_LEGAL = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICACION_REPRESENTANTE_LEGAL = "BBBBBBBBBB";

    private static final String DEFAULT_PROFESION_ASESOR_EMPRESA = "AAAAAAAAAA";
    private static final String UPDATED_PROFESION_ASESOR_EMPRESA = "BBBBBBBBBB";

    private static final String DEFAULT_CELULAR_ASESOR_EMPRESA = "AAAAAAAAAA";
    private static final String UPDATED_CELULAR_ASESOR_EMPRESA = "BBBBBBBBBB";

    @Autowired
    private InformacionPasantiaRepository informacionPasantiaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInformacionPasantiaMockMvc;

    private InformacionPasantia informacionPasantia;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InformacionPasantia createEntity(EntityManager em) {
        InformacionPasantia informacionPasantia = new InformacionPasantia()
            .duracionHoras(DEFAULT_DURACION_HORAS)
            .direccion(DEFAULT_DIRECCION)
            .email(DEFAULT_EMAIL)
            .lunes(DEFAULT_LUNES)
            .martes(DEFAULT_MARTES)
            .miercoles(DEFAULT_MIERCOLES)
            .jueves(DEFAULT_JUEVES)
            .viernes(DEFAULT_VIERNES)
            .sabado(DEFAULT_SABADO)
            .domingo(DEFAULT_DOMINGO)
            .convenio(DEFAULT_CONVENIO)
            .bonoAlimenticio(DEFAULT_BONO_ALIMENTICIO)
            .apoyoEconomico(DEFAULT_APOYO_ECONOMICO)
            .auxilioTransporte(DEFAULT_AUXILIO_TRANSPORTE)
            .capacitacion(DEFAULT_CAPACITACION)
            .otroApoyo(DEFAULT_OTRO_APOYO)
            .nombreEmpresa(DEFAULT_NOMBRE_EMPRESA)
            .nitEmpresa(DEFAULT_NIT_EMPRESA)
            .direccionEmpresa(DEFAULT_DIRECCION_EMPRESA)
            .sectorEconomicoEmpresa(DEFAULT_SECTOR_ECONOMICO_EMPRESA)
            .representanteLegalEmpresa(DEFAULT_REPRESENTANTE_LEGAL_EMPRESA)
            .asesorEmpresa(DEFAULT_ASESOR_EMPRESA)
            .cargoAsesorEmpresa(DEFAULT_CARGO_ASESOR_EMPRESA)
            .emailAsesorEmpresa(DEFAULT_EMAIL_ASESOR_EMPRESA)
            .municipioEmpresa(DEFAULT_MUNICIPIO_EMPRESA)
            .telefonoContactoEmpresa(DEFAULT_TELEFONO_CONTACTO_EMPRESA)
            .emailEmpresa(DEFAULT_EMAIL_EMPRESA)
            .departamentoEmpresa(DEFAULT_DEPARTAMENTO_EMPRESA)
            .identificacionRepresentanteLegal(DEFAULT_IDENTIFICACION_REPRESENTANTE_LEGAL)
            .profesionAsesorEmpresa(DEFAULT_PROFESION_ASESOR_EMPRESA)
            .celularAsesorEmpresa(DEFAULT_CELULAR_ASESOR_EMPRESA);
        return informacionPasantia;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InformacionPasantia createUpdatedEntity(EntityManager em) {
        InformacionPasantia informacionPasantia = new InformacionPasantia()
            .duracionHoras(UPDATED_DURACION_HORAS)
            .direccion(UPDATED_DIRECCION)
            .email(UPDATED_EMAIL)
            .lunes(UPDATED_LUNES)
            .martes(UPDATED_MARTES)
            .miercoles(UPDATED_MIERCOLES)
            .jueves(UPDATED_JUEVES)
            .viernes(UPDATED_VIERNES)
            .sabado(UPDATED_SABADO)
            .domingo(UPDATED_DOMINGO)
            .convenio(UPDATED_CONVENIO)
            .bonoAlimenticio(UPDATED_BONO_ALIMENTICIO)
            .apoyoEconomico(UPDATED_APOYO_ECONOMICO)
            .auxilioTransporte(UPDATED_AUXILIO_TRANSPORTE)
            .capacitacion(UPDATED_CAPACITACION)
            .otroApoyo(UPDATED_OTRO_APOYO)
            .nombreEmpresa(UPDATED_NOMBRE_EMPRESA)
            .nitEmpresa(UPDATED_NIT_EMPRESA)
            .direccionEmpresa(UPDATED_DIRECCION_EMPRESA)
            .sectorEconomicoEmpresa(UPDATED_SECTOR_ECONOMICO_EMPRESA)
            .representanteLegalEmpresa(UPDATED_REPRESENTANTE_LEGAL_EMPRESA)
            .asesorEmpresa(UPDATED_ASESOR_EMPRESA)
            .cargoAsesorEmpresa(UPDATED_CARGO_ASESOR_EMPRESA)
            .emailAsesorEmpresa(UPDATED_EMAIL_ASESOR_EMPRESA)
            .municipioEmpresa(UPDATED_MUNICIPIO_EMPRESA)
            .telefonoContactoEmpresa(UPDATED_TELEFONO_CONTACTO_EMPRESA)
            .emailEmpresa(UPDATED_EMAIL_EMPRESA)
            .departamentoEmpresa(UPDATED_DEPARTAMENTO_EMPRESA)
            .identificacionRepresentanteLegal(UPDATED_IDENTIFICACION_REPRESENTANTE_LEGAL)
            .profesionAsesorEmpresa(UPDATED_PROFESION_ASESOR_EMPRESA)
            .celularAsesorEmpresa(UPDATED_CELULAR_ASESOR_EMPRESA);
        return informacionPasantia;
    }

    @BeforeEach
    public void initTest() {
        informacionPasantia = createEntity(em);
    }

    @Test
    @Transactional
    public void createInformacionPasantia() throws Exception {
        int databaseSizeBeforeCreate = informacionPasantiaRepository.findAll().size();
        // Create the InformacionPasantia
        restInformacionPasantiaMockMvc.perform(post("/api/informacion-pasantias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(informacionPasantia)))
            .andExpect(status().isCreated());

        // Validate the InformacionPasantia in the database
        List<InformacionPasantia> informacionPasantiaList = informacionPasantiaRepository.findAll();
        assertThat(informacionPasantiaList).hasSize(databaseSizeBeforeCreate + 1);
        InformacionPasantia testInformacionPasantia = informacionPasantiaList.get(informacionPasantiaList.size() - 1);
        assertThat(testInformacionPasantia.getDuracionHoras()).isEqualTo(DEFAULT_DURACION_HORAS);
        assertThat(testInformacionPasantia.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
        assertThat(testInformacionPasantia.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testInformacionPasantia.isLunes()).isEqualTo(DEFAULT_LUNES);
        assertThat(testInformacionPasantia.isMartes()).isEqualTo(DEFAULT_MARTES);
        assertThat(testInformacionPasantia.isMiercoles()).isEqualTo(DEFAULT_MIERCOLES);
        assertThat(testInformacionPasantia.isJueves()).isEqualTo(DEFAULT_JUEVES);
        assertThat(testInformacionPasantia.isViernes()).isEqualTo(DEFAULT_VIERNES);
        assertThat(testInformacionPasantia.isSabado()).isEqualTo(DEFAULT_SABADO);
        assertThat(testInformacionPasantia.isDomingo()).isEqualTo(DEFAULT_DOMINGO);
        assertThat(testInformacionPasantia.getConvenio()).isEqualTo(DEFAULT_CONVENIO);
        assertThat(testInformacionPasantia.isBonoAlimenticio()).isEqualTo(DEFAULT_BONO_ALIMENTICIO);
        assertThat(testInformacionPasantia.isApoyoEconomico()).isEqualTo(DEFAULT_APOYO_ECONOMICO);
        assertThat(testInformacionPasantia.isAuxilioTransporte()).isEqualTo(DEFAULT_AUXILIO_TRANSPORTE);
        assertThat(testInformacionPasantia.isCapacitacion()).isEqualTo(DEFAULT_CAPACITACION);
        assertThat(testInformacionPasantia.getOtroApoyo()).isEqualTo(DEFAULT_OTRO_APOYO);
        assertThat(testInformacionPasantia.getNombreEmpresa()).isEqualTo(DEFAULT_NOMBRE_EMPRESA);
        assertThat(testInformacionPasantia.getNitEmpresa()).isEqualTo(DEFAULT_NIT_EMPRESA);
        assertThat(testInformacionPasantia.getDireccionEmpresa()).isEqualTo(DEFAULT_DIRECCION_EMPRESA);
        assertThat(testInformacionPasantia.getSectorEconomicoEmpresa()).isEqualTo(DEFAULT_SECTOR_ECONOMICO_EMPRESA);
        assertThat(testInformacionPasantia.getRepresentanteLegalEmpresa()).isEqualTo(DEFAULT_REPRESENTANTE_LEGAL_EMPRESA);
        assertThat(testInformacionPasantia.getAsesorEmpresa()).isEqualTo(DEFAULT_ASESOR_EMPRESA);
        assertThat(testInformacionPasantia.getCargoAsesorEmpresa()).isEqualTo(DEFAULT_CARGO_ASESOR_EMPRESA);
        assertThat(testInformacionPasantia.getEmailAsesorEmpresa()).isEqualTo(DEFAULT_EMAIL_ASESOR_EMPRESA);
        assertThat(testInformacionPasantia.getMunicipioEmpresa()).isEqualTo(DEFAULT_MUNICIPIO_EMPRESA);
        assertThat(testInformacionPasantia.getTelefonoContactoEmpresa()).isEqualTo(DEFAULT_TELEFONO_CONTACTO_EMPRESA);
        assertThat(testInformacionPasantia.getEmailEmpresa()).isEqualTo(DEFAULT_EMAIL_EMPRESA);
        assertThat(testInformacionPasantia.getDepartamentoEmpresa()).isEqualTo(DEFAULT_DEPARTAMENTO_EMPRESA);
        assertThat(testInformacionPasantia.getIdentificacionRepresentanteLegal()).isEqualTo(DEFAULT_IDENTIFICACION_REPRESENTANTE_LEGAL);
        assertThat(testInformacionPasantia.getProfesionAsesorEmpresa()).isEqualTo(DEFAULT_PROFESION_ASESOR_EMPRESA);
        assertThat(testInformacionPasantia.getCelularAsesorEmpresa()).isEqualTo(DEFAULT_CELULAR_ASESOR_EMPRESA);
    }

    @Test
    @Transactional
    public void createInformacionPasantiaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = informacionPasantiaRepository.findAll().size();

        // Create the InformacionPasantia with an existing ID
        informacionPasantia.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInformacionPasantiaMockMvc.perform(post("/api/informacion-pasantias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(informacionPasantia)))
            .andExpect(status().isBadRequest());

        // Validate the InformacionPasantia in the database
        List<InformacionPasantia> informacionPasantiaList = informacionPasantiaRepository.findAll();
        assertThat(informacionPasantiaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllInformacionPasantias() throws Exception {
        // Initialize the database
        informacionPasantiaRepository.saveAndFlush(informacionPasantia);

        // Get all the informacionPasantiaList
        restInformacionPasantiaMockMvc.perform(get("/api/informacion-pasantias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(informacionPasantia.getId().intValue())))
            .andExpect(jsonPath("$.[*].duracionHoras").value(hasItem(DEFAULT_DURACION_HORAS)))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].lunes").value(hasItem(DEFAULT_LUNES.booleanValue())))
            .andExpect(jsonPath("$.[*].martes").value(hasItem(DEFAULT_MARTES.booleanValue())))
            .andExpect(jsonPath("$.[*].miercoles").value(hasItem(DEFAULT_MIERCOLES.booleanValue())))
            .andExpect(jsonPath("$.[*].jueves").value(hasItem(DEFAULT_JUEVES.booleanValue())))
            .andExpect(jsonPath("$.[*].viernes").value(hasItem(DEFAULT_VIERNES.booleanValue())))
            .andExpect(jsonPath("$.[*].sabado").value(hasItem(DEFAULT_SABADO.booleanValue())))
            .andExpect(jsonPath("$.[*].domingo").value(hasItem(DEFAULT_DOMINGO.booleanValue())))
            .andExpect(jsonPath("$.[*].convenio").value(hasItem(DEFAULT_CONVENIO)))
            .andExpect(jsonPath("$.[*].bonoAlimenticio").value(hasItem(DEFAULT_BONO_ALIMENTICIO.booleanValue())))
            .andExpect(jsonPath("$.[*].apoyoEconomico").value(hasItem(DEFAULT_APOYO_ECONOMICO.booleanValue())))
            .andExpect(jsonPath("$.[*].auxilioTransporte").value(hasItem(DEFAULT_AUXILIO_TRANSPORTE.booleanValue())))
            .andExpect(jsonPath("$.[*].capacitacion").value(hasItem(DEFAULT_CAPACITACION.booleanValue())))
            .andExpect(jsonPath("$.[*].otroApoyo").value(hasItem(DEFAULT_OTRO_APOYO)))
            .andExpect(jsonPath("$.[*].nombreEmpresa").value(hasItem(DEFAULT_NOMBRE_EMPRESA)))
            .andExpect(jsonPath("$.[*].nitEmpresa").value(hasItem(DEFAULT_NIT_EMPRESA)))
            .andExpect(jsonPath("$.[*].direccionEmpresa").value(hasItem(DEFAULT_DIRECCION_EMPRESA)))
            .andExpect(jsonPath("$.[*].sectorEconomicoEmpresa").value(hasItem(DEFAULT_SECTOR_ECONOMICO_EMPRESA)))
            .andExpect(jsonPath("$.[*].representanteLegalEmpresa").value(hasItem(DEFAULT_REPRESENTANTE_LEGAL_EMPRESA)))
            .andExpect(jsonPath("$.[*].asesorEmpresa").value(hasItem(DEFAULT_ASESOR_EMPRESA)))
            .andExpect(jsonPath("$.[*].cargoAsesorEmpresa").value(hasItem(DEFAULT_CARGO_ASESOR_EMPRESA)))
            .andExpect(jsonPath("$.[*].emailAsesorEmpresa").value(hasItem(DEFAULT_EMAIL_ASESOR_EMPRESA)))
            .andExpect(jsonPath("$.[*].municipioEmpresa").value(hasItem(DEFAULT_MUNICIPIO_EMPRESA)))
            .andExpect(jsonPath("$.[*].telefonoContactoEmpresa").value(hasItem(DEFAULT_TELEFONO_CONTACTO_EMPRESA)))
            .andExpect(jsonPath("$.[*].emailEmpresa").value(hasItem(DEFAULT_EMAIL_EMPRESA)))
            .andExpect(jsonPath("$.[*].departamentoEmpresa").value(hasItem(DEFAULT_DEPARTAMENTO_EMPRESA)))
            .andExpect(jsonPath("$.[*].identificacionRepresentanteLegal").value(hasItem(DEFAULT_IDENTIFICACION_REPRESENTANTE_LEGAL)))
            .andExpect(jsonPath("$.[*].profesionAsesorEmpresa").value(hasItem(DEFAULT_PROFESION_ASESOR_EMPRESA)))
            .andExpect(jsonPath("$.[*].celularAsesorEmpresa").value(hasItem(DEFAULT_CELULAR_ASESOR_EMPRESA)));
    }
    
    @Test
    @Transactional
    public void getInformacionPasantia() throws Exception {
        // Initialize the database
        informacionPasantiaRepository.saveAndFlush(informacionPasantia);

        // Get the informacionPasantia
        restInformacionPasantiaMockMvc.perform(get("/api/informacion-pasantias/{id}", informacionPasantia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(informacionPasantia.getId().intValue()))
            .andExpect(jsonPath("$.duracionHoras").value(DEFAULT_DURACION_HORAS))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.lunes").value(DEFAULT_LUNES.booleanValue()))
            .andExpect(jsonPath("$.martes").value(DEFAULT_MARTES.booleanValue()))
            .andExpect(jsonPath("$.miercoles").value(DEFAULT_MIERCOLES.booleanValue()))
            .andExpect(jsonPath("$.jueves").value(DEFAULT_JUEVES.booleanValue()))
            .andExpect(jsonPath("$.viernes").value(DEFAULT_VIERNES.booleanValue()))
            .andExpect(jsonPath("$.sabado").value(DEFAULT_SABADO.booleanValue()))
            .andExpect(jsonPath("$.domingo").value(DEFAULT_DOMINGO.booleanValue()))
            .andExpect(jsonPath("$.convenio").value(DEFAULT_CONVENIO))
            .andExpect(jsonPath("$.bonoAlimenticio").value(DEFAULT_BONO_ALIMENTICIO.booleanValue()))
            .andExpect(jsonPath("$.apoyoEconomico").value(DEFAULT_APOYO_ECONOMICO.booleanValue()))
            .andExpect(jsonPath("$.auxilioTransporte").value(DEFAULT_AUXILIO_TRANSPORTE.booleanValue()))
            .andExpect(jsonPath("$.capacitacion").value(DEFAULT_CAPACITACION.booleanValue()))
            .andExpect(jsonPath("$.otroApoyo").value(DEFAULT_OTRO_APOYO))
            .andExpect(jsonPath("$.nombreEmpresa").value(DEFAULT_NOMBRE_EMPRESA))
            .andExpect(jsonPath("$.nitEmpresa").value(DEFAULT_NIT_EMPRESA))
            .andExpect(jsonPath("$.direccionEmpresa").value(DEFAULT_DIRECCION_EMPRESA))
            .andExpect(jsonPath("$.sectorEconomicoEmpresa").value(DEFAULT_SECTOR_ECONOMICO_EMPRESA))
            .andExpect(jsonPath("$.representanteLegalEmpresa").value(DEFAULT_REPRESENTANTE_LEGAL_EMPRESA))
            .andExpect(jsonPath("$.asesorEmpresa").value(DEFAULT_ASESOR_EMPRESA))
            .andExpect(jsonPath("$.cargoAsesorEmpresa").value(DEFAULT_CARGO_ASESOR_EMPRESA))
            .andExpect(jsonPath("$.emailAsesorEmpresa").value(DEFAULT_EMAIL_ASESOR_EMPRESA))
            .andExpect(jsonPath("$.municipioEmpresa").value(DEFAULT_MUNICIPIO_EMPRESA))
            .andExpect(jsonPath("$.telefonoContactoEmpresa").value(DEFAULT_TELEFONO_CONTACTO_EMPRESA))
            .andExpect(jsonPath("$.emailEmpresa").value(DEFAULT_EMAIL_EMPRESA))
            .andExpect(jsonPath("$.departamentoEmpresa").value(DEFAULT_DEPARTAMENTO_EMPRESA))
            .andExpect(jsonPath("$.identificacionRepresentanteLegal").value(DEFAULT_IDENTIFICACION_REPRESENTANTE_LEGAL))
            .andExpect(jsonPath("$.profesionAsesorEmpresa").value(DEFAULT_PROFESION_ASESOR_EMPRESA))
            .andExpect(jsonPath("$.celularAsesorEmpresa").value(DEFAULT_CELULAR_ASESOR_EMPRESA));
    }
    @Test
    @Transactional
    public void getNonExistingInformacionPasantia() throws Exception {
        // Get the informacionPasantia
        restInformacionPasantiaMockMvc.perform(get("/api/informacion-pasantias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInformacionPasantia() throws Exception {
        // Initialize the database
        informacionPasantiaRepository.saveAndFlush(informacionPasantia);

        int databaseSizeBeforeUpdate = informacionPasantiaRepository.findAll().size();

        // Update the informacionPasantia
        InformacionPasantia updatedInformacionPasantia = informacionPasantiaRepository.findById(informacionPasantia.getId()).get();
        // Disconnect from session so that the updates on updatedInformacionPasantia are not directly saved in db
        em.detach(updatedInformacionPasantia);
        updatedInformacionPasantia
            .duracionHoras(UPDATED_DURACION_HORAS)
            .direccion(UPDATED_DIRECCION)
            .email(UPDATED_EMAIL)
            .lunes(UPDATED_LUNES)
            .martes(UPDATED_MARTES)
            .miercoles(UPDATED_MIERCOLES)
            .jueves(UPDATED_JUEVES)
            .viernes(UPDATED_VIERNES)
            .sabado(UPDATED_SABADO)
            .domingo(UPDATED_DOMINGO)
            .convenio(UPDATED_CONVENIO)
            .bonoAlimenticio(UPDATED_BONO_ALIMENTICIO)
            .apoyoEconomico(UPDATED_APOYO_ECONOMICO)
            .auxilioTransporte(UPDATED_AUXILIO_TRANSPORTE)
            .capacitacion(UPDATED_CAPACITACION)
            .otroApoyo(UPDATED_OTRO_APOYO)
            .nombreEmpresa(UPDATED_NOMBRE_EMPRESA)
            .nitEmpresa(UPDATED_NIT_EMPRESA)
            .direccionEmpresa(UPDATED_DIRECCION_EMPRESA)
            .sectorEconomicoEmpresa(UPDATED_SECTOR_ECONOMICO_EMPRESA)
            .representanteLegalEmpresa(UPDATED_REPRESENTANTE_LEGAL_EMPRESA)
            .asesorEmpresa(UPDATED_ASESOR_EMPRESA)
            .cargoAsesorEmpresa(UPDATED_CARGO_ASESOR_EMPRESA)
            .emailAsesorEmpresa(UPDATED_EMAIL_ASESOR_EMPRESA)
            .municipioEmpresa(UPDATED_MUNICIPIO_EMPRESA)
            .telefonoContactoEmpresa(UPDATED_TELEFONO_CONTACTO_EMPRESA)
            .emailEmpresa(UPDATED_EMAIL_EMPRESA)
            .departamentoEmpresa(UPDATED_DEPARTAMENTO_EMPRESA)
            .identificacionRepresentanteLegal(UPDATED_IDENTIFICACION_REPRESENTANTE_LEGAL)
            .profesionAsesorEmpresa(UPDATED_PROFESION_ASESOR_EMPRESA)
            .celularAsesorEmpresa(UPDATED_CELULAR_ASESOR_EMPRESA);

        restInformacionPasantiaMockMvc.perform(put("/api/informacion-pasantias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedInformacionPasantia)))
            .andExpect(status().isOk());

        // Validate the InformacionPasantia in the database
        List<InformacionPasantia> informacionPasantiaList = informacionPasantiaRepository.findAll();
        assertThat(informacionPasantiaList).hasSize(databaseSizeBeforeUpdate);
        InformacionPasantia testInformacionPasantia = informacionPasantiaList.get(informacionPasantiaList.size() - 1);
        assertThat(testInformacionPasantia.getDuracionHoras()).isEqualTo(UPDATED_DURACION_HORAS);
        assertThat(testInformacionPasantia.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testInformacionPasantia.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testInformacionPasantia.isLunes()).isEqualTo(UPDATED_LUNES);
        assertThat(testInformacionPasantia.isMartes()).isEqualTo(UPDATED_MARTES);
        assertThat(testInformacionPasantia.isMiercoles()).isEqualTo(UPDATED_MIERCOLES);
        assertThat(testInformacionPasantia.isJueves()).isEqualTo(UPDATED_JUEVES);
        assertThat(testInformacionPasantia.isViernes()).isEqualTo(UPDATED_VIERNES);
        assertThat(testInformacionPasantia.isSabado()).isEqualTo(UPDATED_SABADO);
        assertThat(testInformacionPasantia.isDomingo()).isEqualTo(UPDATED_DOMINGO);
        assertThat(testInformacionPasantia.getConvenio()).isEqualTo(UPDATED_CONVENIO);
        assertThat(testInformacionPasantia.isBonoAlimenticio()).isEqualTo(UPDATED_BONO_ALIMENTICIO);
        assertThat(testInformacionPasantia.isApoyoEconomico()).isEqualTo(UPDATED_APOYO_ECONOMICO);
        assertThat(testInformacionPasantia.isAuxilioTransporte()).isEqualTo(UPDATED_AUXILIO_TRANSPORTE);
        assertThat(testInformacionPasantia.isCapacitacion()).isEqualTo(UPDATED_CAPACITACION);
        assertThat(testInformacionPasantia.getOtroApoyo()).isEqualTo(UPDATED_OTRO_APOYO);
        assertThat(testInformacionPasantia.getNombreEmpresa()).isEqualTo(UPDATED_NOMBRE_EMPRESA);
        assertThat(testInformacionPasantia.getNitEmpresa()).isEqualTo(UPDATED_NIT_EMPRESA);
        assertThat(testInformacionPasantia.getDireccionEmpresa()).isEqualTo(UPDATED_DIRECCION_EMPRESA);
        assertThat(testInformacionPasantia.getSectorEconomicoEmpresa()).isEqualTo(UPDATED_SECTOR_ECONOMICO_EMPRESA);
        assertThat(testInformacionPasantia.getRepresentanteLegalEmpresa()).isEqualTo(UPDATED_REPRESENTANTE_LEGAL_EMPRESA);
        assertThat(testInformacionPasantia.getAsesorEmpresa()).isEqualTo(UPDATED_ASESOR_EMPRESA);
        assertThat(testInformacionPasantia.getCargoAsesorEmpresa()).isEqualTo(UPDATED_CARGO_ASESOR_EMPRESA);
        assertThat(testInformacionPasantia.getEmailAsesorEmpresa()).isEqualTo(UPDATED_EMAIL_ASESOR_EMPRESA);
        assertThat(testInformacionPasantia.getMunicipioEmpresa()).isEqualTo(UPDATED_MUNICIPIO_EMPRESA);
        assertThat(testInformacionPasantia.getTelefonoContactoEmpresa()).isEqualTo(UPDATED_TELEFONO_CONTACTO_EMPRESA);
        assertThat(testInformacionPasantia.getEmailEmpresa()).isEqualTo(UPDATED_EMAIL_EMPRESA);
        assertThat(testInformacionPasantia.getDepartamentoEmpresa()).isEqualTo(UPDATED_DEPARTAMENTO_EMPRESA);
        assertThat(testInformacionPasantia.getIdentificacionRepresentanteLegal()).isEqualTo(UPDATED_IDENTIFICACION_REPRESENTANTE_LEGAL);
        assertThat(testInformacionPasantia.getProfesionAsesorEmpresa()).isEqualTo(UPDATED_PROFESION_ASESOR_EMPRESA);
        assertThat(testInformacionPasantia.getCelularAsesorEmpresa()).isEqualTo(UPDATED_CELULAR_ASESOR_EMPRESA);
    }

    @Test
    @Transactional
    public void updateNonExistingInformacionPasantia() throws Exception {
        int databaseSizeBeforeUpdate = informacionPasantiaRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInformacionPasantiaMockMvc.perform(put("/api/informacion-pasantias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(informacionPasantia)))
            .andExpect(status().isBadRequest());

        // Validate the InformacionPasantia in the database
        List<InformacionPasantia> informacionPasantiaList = informacionPasantiaRepository.findAll();
        assertThat(informacionPasantiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInformacionPasantia() throws Exception {
        // Initialize the database
        informacionPasantiaRepository.saveAndFlush(informacionPasantia);

        int databaseSizeBeforeDelete = informacionPasantiaRepository.findAll().size();

        // Delete the informacionPasantia
        restInformacionPasantiaMockMvc.perform(delete("/api/informacion-pasantias/{id}", informacionPasantia.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InformacionPasantia> informacionPasantiaList = informacionPasantiaRepository.findAll();
        assertThat(informacionPasantiaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}


/*
//Integration tests for the {@link InformacionPasantiaResource} REST controller.

@SpringBootTest(classes = CiecytApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class InformacionPasantiaResourceIT {

    private static final Integer DEFAULT_DURACION_HORAS = 1;
    private static final Integer UPDATED_DURACION_HORAS = 2;

    private static final String DEFAULT_DIRECCION = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_LUNES = false;
    private static final Boolean UPDATED_LUNES = true;

    private static final Boolean DEFAULT_MARTES = false;
    private static final Boolean UPDATED_MARTES = true;

    private static final Boolean DEFAULT_MIERCOLES = false;
    private static final Boolean UPDATED_MIERCOLES = true;

    private static final Boolean DEFAULT_JUEVES = false;
    private static final Boolean UPDATED_JUEVES = true;

    private static final Boolean DEFAULT_VIERNES = false;
    private static final Boolean UPDATED_VIERNES = true;

    private static final Boolean DEFAULT_SABADO = false;
    private static final Boolean UPDATED_SABADO = true;

    private static final Boolean DEFAULT_DOMINGO = false;
    private static final Boolean UPDATED_DOMINGO = true;

    private static final String DEFAULT_CONVENIO = "AAAAAAAAAA";
    private static final Integer UPDATED_CONVENIO ="BBBBBBBBBB";

    private static final Boolean DEFAULT_BONO_ALIMENTICIO = false;
    private static final Boolean UPDATED_BONO_ALIMENTICIO = true;

    private static final Boolean DEFAULT_APOYO_ECONOMICO = false;
    private static final Boolean UPDATED_APOYO_ECONOMICO = true;

    private static final Boolean DEFAULT_AUXILIO_TRANSPORTE = false;
    private static final Boolean UPDATED_AUXILIO_TRANSPORTE = true;

    private static final Boolean DEFAULT_CAPACITACION = false;
    private static final Boolean UPDATED_CAPACITACION = true;

    private static final String DEFAULT_OTRO_APOYO = "AAAAAAAAAA";
    private static final String UPDATED_OTRO_APOYO = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE_EMPRESA = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_EMPRESA = "BBBBBBBBBB";

    private static final String DEFAULT_NIT_EMPRESA = "AAAAAAAAAA";
    private static final String UPDATED_NIT_EMPRESA = "BBBBBBBBBB";

    private static final String DEFAULT_DIRECCION_EMPRESA = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION_EMPRESA = "BBBBBBBBBB";

    private static final String DEFAULT_SECTOR_ECONOMICO_EMPRESA = "AAAAAAAAAA";
    private static final String UPDATED_SECTOR_ECONOMICO_EMPRESA = "BBBBBBBBBB";

    private static final String DEFAULT_REPRESENTANTE_LEGAL_EMPRESA = "AAAAAAAAAA";
    private static final String UPDATED_REPRESENTANTE_LEGAL_EMPRESA = "BBBBBBBBBB";

    private static final String DEFAULT_ASESOR_EMPRESA = "AAAAAAAAAA";
    private static final String UPDATED_ASESOR_EMPRESA = "BBBBBBBBBB";

    private static final String DEFAULT_CARGO_ASESOR_EMPRESA = "AAAAAAAAAA";
    private static final String UPDATED_CARGO_ASESOR_EMPRESA = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_ASESOR_EMPRESA = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_ASESOR_EMPRESA = "BBBBBBBBBB";

    private static final String DEFAULT_MUNICIPIO_EMPRESA = "AAAAAAAAAA";
    private static final String UPDATED_MUNICIPIO_EMPRESA = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO_CONTACTO_EMPRESA = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO_CONTACTO_EMPRESA = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_EMPRESA = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_EMPRESA = "BBBBBBBBBB";

    private static final String DEFAULT_DEPARTAMENTO_EMPRESA = "AAAAAAAAAA";
    private static final String UPDATED_DEPARTAMENTO_EMPRESA = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTIFICACION_REPRESENTANTE_LEGAL = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICACION_REPRESENTANTE_LEGAL = "BBBBBBBBBB";

    private static final String DEFAULT_PROFESION_ASESOR_EMPRESA = "AAAAAAAAAA";
    private static final String UPDATED_PROFESION_ASESOR_EMPRESA = "BBBBBBBBBB";

    private static final String DEFAULT_CELULAR_ASESOR_EMPRESA = "AAAAAAAAAA";
    private static final String UPDATED_CELULAR_ASESOR_EMPRESA = "BBBBBBBBBB";

    private static final Long DEFAULT_PROYECTO_ID = 1L;
    private static final Long UPDATED_PROYECTO_ID = 2L;

    @Autowired
    private InformacionPasantiaRepository informacionPasantiaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInformacionPasantiaMockMvc;

    private InformacionPasantia informacionPasantia;

    //
    // Create an entity for this test.
    //
    // This is a static method, as tests for other entities might also need it,
    // if they test an entity which requires the current entity.
     //
    public static InformacionPasantia createEntity(EntityManager em) {
        InformacionPasantia informacionPasantia = new InformacionPasantia()
            .duracionHoras(DEFAULT_DURACION_HORAS)
            .direccion(DEFAULT_DIRECCION)
            .email(DEFAULT_EMAIL)
            .lunes(DEFAULT_LUNES)
            .martes(DEFAULT_MARTES)
            .miercoles(DEFAULT_MIERCOLES)
            .jueves(DEFAULT_JUEVES)
            .viernes(DEFAULT_VIERNES)
            .sabado(DEFAULT_SABADO)
            .domingo(DEFAULT_DOMINGO)
            .convenio(DEFAULT_CONVENIO)
            .bonoAlimenticio(DEFAULT_BONO_ALIMENTICIO)
            .apoyoEconomico(DEFAULT_APOYO_ECONOMICO)
            .auxilioTransporte(DEFAULT_AUXILIO_TRANSPORTE)
            .capacitacion(DEFAULT_CAPACITACION)
            .otroApoyo(DEFAULT_OTRO_APOYO)
            .nombreEmpresa(DEFAULT_NOMBRE_EMPRESA)
            .nitEmpresa(DEFAULT_NIT_EMPRESA)
            .direccionEmpresa(DEFAULT_DIRECCION_EMPRESA)
            .sectorEconomicoEmpresa(DEFAULT_SECTOR_ECONOMICO_EMPRESA)
            .representanteLegalEmpresa(DEFAULT_REPRESENTANTE_LEGAL_EMPRESA)
            .asesorEmpresa(DEFAULT_ASESOR_EMPRESA)
            .cargoAsesorEmpresa(DEFAULT_CARGO_ASESOR_EMPRESA)
            .emailAsesorEmpresa(DEFAULT_EMAIL_ASESOR_EMPRESA)
            .municipioEmpresa(DEFAULT_MUNICIPIO_EMPRESA)
            .telefonoContactoEmpresa(DEFAULT_TELEFONO_CONTACTO_EMPRESA)
            .emailEmpresa(DEFAULT_EMAIL_EMPRESA)
            .departamentoEmpresa(DEFAULT_DEPARTAMENTO_EMPRESA)
            .identificacionRepresentanteLegal(DEFAULT_IDENTIFICACION_REPRESENTANTE_LEGAL)
            .profesionAsesorEmpresa(DEFAULT_PROFESION_ASESOR_EMPRESA)
            .celularAsesorEmpresa(DEFAULT_CELULAR_ASESOR_EMPRESA)
            .proyectoId(DEFAULT_PROYECTO_ID);
        return informacionPasantia;
    }
    //
     // Create an updated entity for this test.
     //
     // This is a static method, as tests for other entities might also need it,
     // if they test an entity which requires the current entity.
     //
    public static InformacionPasantia createUpdatedEntity(EntityManager em) {
        InformacionPasantia informacionPasantia = new InformacionPasantia()
            .duracionHoras(UPDATED_DURACION_HORAS)
            .direccion(UPDATED_DIRECCION)
            .email(UPDATED_EMAIL)
            .lunes(UPDATED_LUNES)
            .martes(UPDATED_MARTES)
            .miercoles(UPDATED_MIERCOLES)
            .jueves(UPDATED_JUEVES)
            .viernes(UPDATED_VIERNES)
            .sabado(UPDATED_SABADO)
            .domingo(UPDATED_DOMINGO)
            .convenio(UPDATED_CONVENIO)
            .bonoAlimenticio(UPDATED_BONO_ALIMENTICIO)
            .apoyoEconomico(UPDATED_APOYO_ECONOMICO)
            .auxilioTransporte(UPDATED_AUXILIO_TRANSPORTE)
            .capacitacion(UPDATED_CAPACITACION)
            .otroApoyo(UPDATED_OTRO_APOYO)
            .nombreEmpresa(UPDATED_NOMBRE_EMPRESA)
            .nitEmpresa(UPDATED_NIT_EMPRESA)
            .direccionEmpresa(UPDATED_DIRECCION_EMPRESA)
            .sectorEconomicoEmpresa(UPDATED_SECTOR_ECONOMICO_EMPRESA)
            .representanteLegalEmpresa(UPDATED_REPRESENTANTE_LEGAL_EMPRESA)
            .asesorEmpresa(UPDATED_ASESOR_EMPRESA)
            .cargoAsesorEmpresa(UPDATED_CARGO_ASESOR_EMPRESA)
            .emailAsesorEmpresa(UPDATED_EMAIL_ASESOR_EMPRESA)
            .municipioEmpresa(UPDATED_MUNICIPIO_EMPRESA)
            .telefonoContactoEmpresa(UPDATED_TELEFONO_CONTACTO_EMPRESA)
            .emailEmpresa(UPDATED_EMAIL_EMPRESA)
            .departamentoEmpresa(UPDATED_DEPARTAMENTO_EMPRESA)
            .identificacionRepresentanteLegal(UPDATED_IDENTIFICACION_REPRESENTANTE_LEGAL)
            .profesionAsesorEmpresa(UPDATED_PROFESION_ASESOR_EMPRESA)
            .celularAsesorEmpresa(UPDATED_CELULAR_ASESOR_EMPRESA)
            .proyectoId(UPDATED_PROYECTO_ID);
        return informacionPasantia;
    }

    @BeforeEach
    public void initTest() {
        informacionPasantia = createEntity(em);
    }

    @Test
    @Transactional
    public void createInformacionPasantia() throws Exception {
        int databaseSizeBeforeCreate = informacionPasantiaRepository.findAll().size();
        // Create the InformacionPasantia
        restInformacionPasantiaMockMvc.perform(post("/api/informacion-pasantias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(informacionPasantia)))
            .andExpect(status().isCreated());

        // Validate the InformacionPasantia in the database
        List<InformacionPasantia> informacionPasantiaList = informacionPasantiaRepository.findAll();
        assertThat(informacionPasantiaList).hasSize(databaseSizeBeforeCreate + 1);
        InformacionPasantia testInformacionPasantia = informacionPasantiaList.get(informacionPasantiaList.size() - 1);
        assertThat(testInformacionPasantia.getDuracionHoras()).isEqualTo(DEFAULT_DURACION_HORAS);
        assertThat(testInformacionPasantia.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
        assertThat(testInformacionPasantia.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testInformacionPasantia.isLunes()).isEqualTo(DEFAULT_LUNES);
        assertThat(testInformacionPasantia.isMartes()).isEqualTo(DEFAULT_MARTES);
        assertThat(testInformacionPasantia.isMiercoles()).isEqualTo(DEFAULT_MIERCOLES);
        assertThat(testInformacionPasantia.isJueves()).isEqualTo(DEFAULT_JUEVES);
        assertThat(testInformacionPasantia.isViernes()).isEqualTo(DEFAULT_VIERNES);
        assertThat(testInformacionPasantia.isSabado()).isEqualTo(DEFAULT_SABADO);
        assertThat(testInformacionPasantia.isDomingo()).isEqualTo(DEFAULT_DOMINGO);
        assertThat(testInformacionPasantia.getConvenio()).isEqualTo(DEFAULT_CONVENIO);
        assertThat(testInformacionPasantia.isBonoAlimenticio()).isEqualTo(DEFAULT_BONO_ALIMENTICIO);
        assertThat(testInformacionPasantia.isApoyoEconomico()).isEqualTo(DEFAULT_APOYO_ECONOMICO);
        assertThat(testInformacionPasantia.isAuxilioTransporte()).isEqualTo(DEFAULT_AUXILIO_TRANSPORTE);
        assertThat(testInformacionPasantia.isCapacitacion()).isEqualTo(DEFAULT_CAPACITACION);
        assertThat(testInformacionPasantia.getOtroApoyo()).isEqualTo(DEFAULT_OTRO_APOYO);
        assertThat(testInformacionPasantia.getNombreEmpresa()).isEqualTo(DEFAULT_NOMBRE_EMPRESA);
        assertThat(testInformacionPasantia.getNitEmpresa()).isEqualTo(DEFAULT_NIT_EMPRESA);
        assertThat(testInformacionPasantia.getDireccionEmpresa()).isEqualTo(DEFAULT_DIRECCION_EMPRESA);
        assertThat(testInformacionPasantia.getSectorEconomicoEmpresa()).isEqualTo(DEFAULT_SECTOR_ECONOMICO_EMPRESA);
        assertThat(testInformacionPasantia.getRepresentanteLegalEmpresa()).isEqualTo(DEFAULT_REPRESENTANTE_LEGAL_EMPRESA);
        assertThat(testInformacionPasantia.getAsesorEmpresa()).isEqualTo(DEFAULT_ASESOR_EMPRESA);
        assertThat(testInformacionPasantia.getCargoAsesorEmpresa()).isEqualTo(DEFAULT_CARGO_ASESOR_EMPRESA);
        assertThat(testInformacionPasantia.getEmailAsesorEmpresa()).isEqualTo(DEFAULT_EMAIL_ASESOR_EMPRESA);
        assertThat(testInformacionPasantia.getMunicipioEmpresa()).isEqualTo(DEFAULT_MUNICIPIO_EMPRESA);
        assertThat(testInformacionPasantia.getTelefonoContactoEmpresa()).isEqualTo(DEFAULT_TELEFONO_CONTACTO_EMPRESA);
        assertThat(testInformacionPasantia.getEmailEmpresa()).isEqualTo(DEFAULT_EMAIL_EMPRESA);
        assertThat(testInformacionPasantia.getDepartamentoEmpresa()).isEqualTo(DEFAULT_DEPARTAMENTO_EMPRESA);
        assertThat(testInformacionPasantia.getIdentificacionRepresentanteLegal()).isEqualTo(DEFAULT_IDENTIFICACION_REPRESENTANTE_LEGAL);
        assertThat(testInformacionPasantia.getProfesionAsesorEmpresa()).isEqualTo(DEFAULT_PROFESION_ASESOR_EMPRESA);
        assertThat(testInformacionPasantia.getCelularAsesorEmpresa()).isEqualTo(DEFAULT_CELULAR_ASESOR_EMPRESA);
        assertThat(testInformacionPasantia.getProyectoId()).isEqualTo(DEFAULT_PROYECTO_ID);
    }

    @Test
    @Transactional
    public void createInformacionPasantiaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = informacionPasantiaRepository.findAll().size();

        // Create the InformacionPasantia with an existing ID
        informacionPasantia.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInformacionPasantiaMockMvc.perform(post("/api/informacion-pasantias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(informacionPasantia)))
            .andExpect(status().isBadRequest());

        // Validate the InformacionPasantia in the database
        List<InformacionPasantia> informacionPasantiaList = informacionPasantiaRepository.findAll();
        assertThat(informacionPasantiaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllInformacionPasantias() throws Exception {
        // Initialize the database
        informacionPasantiaRepository.saveAndFlush(informacionPasantia);

        // Get all the informacionPasantiaList
        restInformacionPasantiaMockMvc.perform(get("/api/informacion-pasantias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(informacionPasantia.getId().intValue())))
            .andExpect(jsonPath("$.[*].duracionHoras").value(hasItem(DEFAULT_DURACION_HORAS)))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].lunes").value(hasItem(DEFAULT_LUNES.booleanValue())))
            .andExpect(jsonPath("$.[*].martes").value(hasItem(DEFAULT_MARTES.booleanValue())))
            .andExpect(jsonPath("$.[*].miercoles").value(hasItem(DEFAULT_MIERCOLES.booleanValue())))
            .andExpect(jsonPath("$.[*].jueves").value(hasItem(DEFAULT_JUEVES.booleanValue())))
            .andExpect(jsonPath("$.[*].viernes").value(hasItem(DEFAULT_VIERNES.booleanValue())))
            .andExpect(jsonPath("$.[*].sabado").value(hasItem(DEFAULT_SABADO.booleanValue())))
            .andExpect(jsonPath("$.[*].domingo").value(hasItem(DEFAULT_DOMINGO.booleanValue())))
            .andExpect(jsonPath("$.[*].convenio").value(hasItem(DEFAULT_CONVENIO)))
            .andExpect(jsonPath("$.[*].bonoAlimenticio").value(hasItem(DEFAULT_BONO_ALIMENTICIO.booleanValue())))
            .andExpect(jsonPath("$.[*].apoyoEconomico").value(hasItem(DEFAULT_APOYO_ECONOMICO.booleanValue())))
            .andExpect(jsonPath("$.[*].auxilioTransporte").value(hasItem(DEFAULT_AUXILIO_TRANSPORTE.booleanValue())))
            .andExpect(jsonPath("$.[*].capacitacion").value(hasItem(DEFAULT_CAPACITACION.booleanValue())))
            .andExpect(jsonPath("$.[*].otroApoyo").value(hasItem(DEFAULT_OTRO_APOYO)))
            .andExpect(jsonPath("$.[*].nombreEmpresa").value(hasItem(DEFAULT_NOMBRE_EMPRESA)))
            .andExpect(jsonPath("$.[*].nitEmpresa").value(hasItem(DEFAULT_NIT_EMPRESA)))
            .andExpect(jsonPath("$.[*].direccionEmpresa").value(hasItem(DEFAULT_DIRECCION_EMPRESA)))
            .andExpect(jsonPath("$.[*].sectorEconomicoEmpresa").value(hasItem(DEFAULT_SECTOR_ECONOMICO_EMPRESA)))
            .andExpect(jsonPath("$.[*].representanteLegalEmpresa").value(hasItem(DEFAULT_REPRESENTANTE_LEGAL_EMPRESA)))
            .andExpect(jsonPath("$.[*].asesorEmpresa").value(hasItem(DEFAULT_ASESOR_EMPRESA)))
            .andExpect(jsonPath("$.[*].cargoAsesorEmpresa").value(hasItem(DEFAULT_CARGO_ASESOR_EMPRESA)))
            .andExpect(jsonPath("$.[*].emailAsesorEmpresa").value(hasItem(DEFAULT_EMAIL_ASESOR_EMPRESA)))
            .andExpect(jsonPath("$.[*].municipioEmpresa").value(hasItem(DEFAULT_MUNICIPIO_EMPRESA)))
            .andExpect(jsonPath("$.[*].telefonoContactoEmpresa").value(hasItem(DEFAULT_TELEFONO_CONTACTO_EMPRESA)))
            .andExpect(jsonPath("$.[*].emailEmpresa").value(hasItem(DEFAULT_EMAIL_EMPRESA)))
            .andExpect(jsonPath("$.[*].departamentoEmpresa").value(hasItem(DEFAULT_DEPARTAMENTO_EMPRESA)))
            .andExpect(jsonPath("$.[*].identificacionRepresentanteLegal").value(hasItem(DEFAULT_IDENTIFICACION_REPRESENTANTE_LEGAL)))
            .andExpect(jsonPath("$.[*].profesionAsesorEmpresa").value(hasItem(DEFAULT_PROFESION_ASESOR_EMPRESA)))
            .andExpect(jsonPath("$.[*].celularAsesorEmpresa").value(hasItem(DEFAULT_CELULAR_ASESOR_EMPRESA)))
            .andExpect(jsonPath("$.[*].proyectoId").value(hasItem(DEFAULT_PROYECTO_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getInformacionPasantia() throws Exception {
        // Initialize the database
        informacionPasantiaRepository.saveAndFlush(informacionPasantia);

        // Get the informacionPasantia
        restInformacionPasantiaMockMvc.perform(get("/api/informacion-pasantias/{id}", informacionPasantia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(informacionPasantia.getId().intValue()))
            .andExpect(jsonPath("$.duracionHoras").value(DEFAULT_DURACION_HORAS))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.lunes").value(DEFAULT_LUNES.booleanValue()))
            .andExpect(jsonPath("$.martes").value(DEFAULT_MARTES.booleanValue()))
            .andExpect(jsonPath("$.miercoles").value(DEFAULT_MIERCOLES.booleanValue()))
            .andExpect(jsonPath("$.jueves").value(DEFAULT_JUEVES.booleanValue()))
            .andExpect(jsonPath("$.viernes").value(DEFAULT_VIERNES.booleanValue()))
            .andExpect(jsonPath("$.sabado").value(DEFAULT_SABADO.booleanValue()))
            .andExpect(jsonPath("$.domingo").value(DEFAULT_DOMINGO.booleanValue()))
            .andExpect(jsonPath("$.convenio").value(DEFAULT_CONVENIO))
            .andExpect(jsonPath("$.bonoAlimenticio").value(DEFAULT_BONO_ALIMENTICIO.booleanValue()))
            .andExpect(jsonPath("$.apoyoEconomico").value(DEFAULT_APOYO_ECONOMICO.booleanValue()))
            .andExpect(jsonPath("$.auxilioTransporte").value(DEFAULT_AUXILIO_TRANSPORTE.booleanValue()))
            .andExpect(jsonPath("$.capacitacion").value(DEFAULT_CAPACITACION.booleanValue()))
            .andExpect(jsonPath("$.otroApoyo").value(DEFAULT_OTRO_APOYO))
            .andExpect(jsonPath("$.nombreEmpresa").value(DEFAULT_NOMBRE_EMPRESA))
            .andExpect(jsonPath("$.nitEmpresa").value(DEFAULT_NIT_EMPRESA))
            .andExpect(jsonPath("$.direccionEmpresa").value(DEFAULT_DIRECCION_EMPRESA))
            .andExpect(jsonPath("$.sectorEconomicoEmpresa").value(DEFAULT_SECTOR_ECONOMICO_EMPRESA))
            .andExpect(jsonPath("$.representanteLegalEmpresa").value(DEFAULT_REPRESENTANTE_LEGAL_EMPRESA))
            .andExpect(jsonPath("$.asesorEmpresa").value(DEFAULT_ASESOR_EMPRESA))
            .andExpect(jsonPath("$.cargoAsesorEmpresa").value(DEFAULT_CARGO_ASESOR_EMPRESA))
            .andExpect(jsonPath("$.emailAsesorEmpresa").value(DEFAULT_EMAIL_ASESOR_EMPRESA))
            .andExpect(jsonPath("$.municipioEmpresa").value(DEFAULT_MUNICIPIO_EMPRESA))
            .andExpect(jsonPath("$.telefonoContactoEmpresa").value(DEFAULT_TELEFONO_CONTACTO_EMPRESA))
            .andExpect(jsonPath("$.emailEmpresa").value(DEFAULT_EMAIL_EMPRESA))
            .andExpect(jsonPath("$.departamentoEmpresa").value(DEFAULT_DEPARTAMENTO_EMPRESA))
            .andExpect(jsonPath("$.identificacionRepresentanteLegal").value(DEFAULT_IDENTIFICACION_REPRESENTANTE_LEGAL))
            .andExpect(jsonPath("$.profesionAsesorEmpresa").value(DEFAULT_PROFESION_ASESOR_EMPRESA))
            .andExpect(jsonPath("$.celularAsesorEmpresa").value(DEFAULT_CELULAR_ASESOR_EMPRESA))
            .andExpect(jsonPath("$.proyectoId").value(DEFAULT_PROYECTO_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingInformacionPasantia() throws Exception {
        // Get the informacionPasantia
        restInformacionPasantiaMockMvc.perform(get("/api/informacion-pasantias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInformacionPasantia() throws Exception {
        // Initialize the database
        informacionPasantiaRepository.saveAndFlush(informacionPasantia);

        int databaseSizeBeforeUpdate = informacionPasantiaRepository.findAll().size();

        // Update the informacionPasantia
        InformacionPasantia updatedInformacionPasantia = informacionPasantiaRepository.findById(informacionPasantia.getId()).get();
        // Disconnect from session so that the updates on updatedInformacionPasantia are not directly saved in db
        em.detach(updatedInformacionPasantia);
        updatedInformacionPasantia
            .duracionHoras(UPDATED_DURACION_HORAS)
            .direccion(UPDATED_DIRECCION)
            .email(UPDATED_EMAIL)
            .lunes(UPDATED_LUNES)
            .martes(UPDATED_MARTES)
            .miercoles(UPDATED_MIERCOLES)
            .jueves(UPDATED_JUEVES)
            .viernes(UPDATED_VIERNES)
            .sabado(UPDATED_SABADO)
            .domingo(UPDATED_DOMINGO)
            .convenio(UPDATED_CONVENIO)
            .bonoAlimenticio(UPDATED_BONO_ALIMENTICIO)
            .apoyoEconomico(UPDATED_APOYO_ECONOMICO)
            .auxilioTransporte(UPDATED_AUXILIO_TRANSPORTE)
            .capacitacion(UPDATED_CAPACITACION)
            .otroApoyo(UPDATED_OTRO_APOYO)
            .nombreEmpresa(UPDATED_NOMBRE_EMPRESA)
            .nitEmpresa(UPDATED_NIT_EMPRESA)
            .direccionEmpresa(UPDATED_DIRECCION_EMPRESA)
            .sectorEconomicoEmpresa(UPDATED_SECTOR_ECONOMICO_EMPRESA)
            .representanteLegalEmpresa(UPDATED_REPRESENTANTE_LEGAL_EMPRESA)
            .asesorEmpresa(UPDATED_ASESOR_EMPRESA)
            .cargoAsesorEmpresa(UPDATED_CARGO_ASESOR_EMPRESA)
            .emailAsesorEmpresa(UPDATED_EMAIL_ASESOR_EMPRESA)
            .municipioEmpresa(UPDATED_MUNICIPIO_EMPRESA)
            .telefonoContactoEmpresa(UPDATED_TELEFONO_CONTACTO_EMPRESA)
            .emailEmpresa(UPDATED_EMAIL_EMPRESA)
            .departamentoEmpresa(UPDATED_DEPARTAMENTO_EMPRESA)
            .identificacionRepresentanteLegal(UPDATED_IDENTIFICACION_REPRESENTANTE_LEGAL)
            .profesionAsesorEmpresa(UPDATED_PROFESION_ASESOR_EMPRESA)
            .celularAsesorEmpresa(UPDATED_CELULAR_ASESOR_EMPRESA)
            .proyectoId(UPDATED_PROYECTO_ID);

        restInformacionPasantiaMockMvc.perform(put("/api/informacion-pasantias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedInformacionPasantia)))
            .andExpect(status().isOk());

        // Validate the InformacionPasantia in the database
        List<InformacionPasantia> informacionPasantiaList = informacionPasantiaRepository.findAll();
        assertThat(informacionPasantiaList).hasSize(databaseSizeBeforeUpdate);
        InformacionPasantia testInformacionPasantia = informacionPasantiaList.get(informacionPasantiaList.size() - 1);
        assertThat(testInformacionPasantia.getDuracionHoras()).isEqualTo(UPDATED_DURACION_HORAS);
        assertThat(testInformacionPasantia.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testInformacionPasantia.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testInformacionPasantia.isLunes()).isEqualTo(UPDATED_LUNES);
        assertThat(testInformacionPasantia.isMartes()).isEqualTo(UPDATED_MARTES);
        assertThat(testInformacionPasantia.isMiercoles()).isEqualTo(UPDATED_MIERCOLES);
        assertThat(testInformacionPasantia.isJueves()).isEqualTo(UPDATED_JUEVES);
        assertThat(testInformacionPasantia.isViernes()).isEqualTo(UPDATED_VIERNES);
        assertThat(testInformacionPasantia.isSabado()).isEqualTo(UPDATED_SABADO);
        assertThat(testInformacionPasantia.isDomingo()).isEqualTo(UPDATED_DOMINGO);
        assertThat(testInformacionPasantia.getConvenio()).isEqualTo(UPDATED_CONVENIO);
        assertThat(testInformacionPasantia.isBonoAlimenticio()).isEqualTo(UPDATED_BONO_ALIMENTICIO);
        assertThat(testInformacionPasantia.isApoyoEconomico()).isEqualTo(UPDATED_APOYO_ECONOMICO);
        assertThat(testInformacionPasantia.isAuxilioTransporte()).isEqualTo(UPDATED_AUXILIO_TRANSPORTE);
        assertThat(testInformacionPasantia.isCapacitacion()).isEqualTo(UPDATED_CAPACITACION);
        assertThat(testInformacionPasantia.getOtroApoyo()).isEqualTo(UPDATED_OTRO_APOYO);
        assertThat(testInformacionPasantia.getNombreEmpresa()).isEqualTo(UPDATED_NOMBRE_EMPRESA);
        assertThat(testInformacionPasantia.getNitEmpresa()).isEqualTo(UPDATED_NIT_EMPRESA);
        assertThat(testInformacionPasantia.getDireccionEmpresa()).isEqualTo(UPDATED_DIRECCION_EMPRESA);
        assertThat(testInformacionPasantia.getSectorEconomicoEmpresa()).isEqualTo(UPDATED_SECTOR_ECONOMICO_EMPRESA);
        assertThat(testInformacionPasantia.getRepresentanteLegalEmpresa()).isEqualTo(UPDATED_REPRESENTANTE_LEGAL_EMPRESA);
        assertThat(testInformacionPasantia.getAsesorEmpresa()).isEqualTo(UPDATED_ASESOR_EMPRESA);
        assertThat(testInformacionPasantia.getCargoAsesorEmpresa()).isEqualTo(UPDATED_CARGO_ASESOR_EMPRESA);
        assertThat(testInformacionPasantia.getEmailAsesorEmpresa()).isEqualTo(UPDATED_EMAIL_ASESOR_EMPRESA);
        assertThat(testInformacionPasantia.getMunicipioEmpresa()).isEqualTo(UPDATED_MUNICIPIO_EMPRESA);
        assertThat(testInformacionPasantia.getTelefonoContactoEmpresa()).isEqualTo(UPDATED_TELEFONO_CONTACTO_EMPRESA);
        assertThat(testInformacionPasantia.getEmailEmpresa()).isEqualTo(UPDATED_EMAIL_EMPRESA);
        assertThat(testInformacionPasantia.getDepartamentoEmpresa()).isEqualTo(UPDATED_DEPARTAMENTO_EMPRESA);
        assertThat(testInformacionPasantia.getIdentificacionRepresentanteLegal()).isEqualTo(UPDATED_IDENTIFICACION_REPRESENTANTE_LEGAL);
        assertThat(testInformacionPasantia.getProfesionAsesorEmpresa()).isEqualTo(UPDATED_PROFESION_ASESOR_EMPRESA);
        assertThat(testInformacionPasantia.getCelularAsesorEmpresa()).isEqualTo(UPDATED_CELULAR_ASESOR_EMPRESA);
        assertThat(testInformacionPasantia.getProyectoId()).isEqualTo(UPDATED_PROYECTO_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingInformacionPasantia() throws Exception {
        int databaseSizeBeforeUpdate = informacionPasantiaRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInformacionPasantiaMockMvc.perform(put("/api/informacion-pasantias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(informacionPasantia)))
            .andExpect(status().isBadRequest());

        // Validate the InformacionPasantia in the database
        List<InformacionPasantia> informacionPasantiaList = informacionPasantiaRepository.findAll();
        assertThat(informacionPasantiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInformacionPasantia() throws Exception {
        // Initialize the database
        informacionPasantiaRepository.saveAndFlush(informacionPasantia);

        int databaseSizeBeforeDelete = informacionPasantiaRepository.findAll().size();

        // Delete the informacionPasantia
        restInformacionPasantiaMockMvc.perform(delete("/api/informacion-pasantias/{id}", informacionPasantia.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InformacionPasantia> informacionPasantiaList = informacionPasantiaRepository.findAll();
        assertThat(informacionPasantiaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
*/