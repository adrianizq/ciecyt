package co.edu.itp.ciecyt.service;

import co.edu.itp.ciecyt.domain.AsesorExterno;
import co.edu.itp.ciecyt.domain.Facultad;
import co.edu.itp.ciecyt.repository.AsesorExternoRepository;
import co.edu.itp.ciecyt.repository.FacultadRepository;
import co.edu.itp.ciecyt.security.SecurityUtils;
import co.edu.itp.ciecyt.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Registro de profesionales externos para cubrir una asesoria o un jurado cuando la lista
 * habilitada de la facultad no tiene disponibilidad.
 *
 * <p>El paragrafo 1 del articulo 8 y el paragrafo 1 del articulo 9 del Acuerdo 25 exigen dos
 * cosas: que no exista vinculo laboral y que el CIECYT verifique la idoneidad academica. La
 * persona externa se registra como dato (nunca se le crea una cuenta de usuario institucional,
 * que es justamente la clase de vinculo que el reglamento quiere evitar), y en el momento en que
 * el CIECYT constata la idoneidad queda marcada como verificada; solo las verificadas pueden
 * designarse.
 */
@Service
public class AsesorExternoService {

    private final Logger log = LoggerFactory.getLogger(AsesorExternoService.class);

    private final AsesorExternoRepository asesorExternoRepository;
    private final FacultadRepository facultadRepository;

    public AsesorExternoService(
        AsesorExternoRepository asesorExternoRepository,
        FacultadRepository facultadRepository
    ) {
        this.asesorExternoRepository = asesorExternoRepository;
        this.facultadRepository = facultadRepository;
    }

    @Transactional(readOnly = true)
    public Optional<AsesorExterno> findOne(Long id) {
        return asesorExternoRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<AsesorExterno> listarPorFacultad(Long facultadId) {
        return asesorExternoRepository.findByFacultadId(facultadId);
    }

    @Transactional(readOnly = true)
    public List<AsesorExterno> listarVerificadosPorFacultadYRol(Long facultadId, String rol) {
        return asesorExternoRepository.findVerificadosPorFacultadYRol(facultadId, normalizarRol(rol));
    }

    /**
     * Registra al profesional externo. Aun no esta verificado: la idoneidad la constata el CIECYT
     * despues, con el metodo verificar.
     */
    @Transactional
    public AsesorExterno registrar(AsesorExterno datos, Long facultadId) {
        validarDatos(datos);
        Facultad facultad = facultadRepository.findById(facultadId)
            .orElseThrow(() -> new BadRequestAlertException("La facultad indicada no existe", "asesorExterno", "facultadInexistente"));

        if (asesorExternoRepository.findPorDocumentoEnFacultad(facultadId, datos.getNumeroDocumento()).isPresent()) {
            throw new BadRequestAlertException("Ya esta registrada una persona externa con ese documento en la facultad", "asesorExterno", "yaExiste");
        }

        AsesorExterno externo = new AsesorExterno();
        externo.setNombres(datos.getNombres());
        externo.setApellidos(datos.getApellidos());
        externo.setNumeroDocumento(datos.getNumeroDocumento());
        externo.setCorreoElectronico(datos.getCorreoElectronico());
        externo.setTelefono(datos.getTelefono());
        externo.setTitulos(datos.getTitulos());
        externo.setInstitucionOrigen(datos.getInstitucionOrigen());
        externo.setRol(datos.getRol());
        externo.setFuenteVerificacion(datos.getFuenteVerificacion());
        externo.setObservaciones(datos.getObservaciones());
        externo.setIdoneidadVerificada(Boolean.FALSE);
        externo.setFacultad(facultad);
        return asesorExternoRepository.save(externo);
    }

    /**
     * Ajusta los datos de la persona externa. Si ya estaba verificada y se tocan sus datos, la
     * verificacion sigue valida: la idoneidad es de la persona, no del texto de sus datos. El
     * cambio de rol (de asesor a jurado o al reves) si resetea la verificacion de idoneidad,
     * porque la idoneidad se constata para el cargo.
     */
    @Transactional
    public AsesorExterno actualizar(Long id, AsesorExterno datos) {
        validarDatos(datos);
        AsesorExterno actual = asesorExternoRepository.findById(id)
            .orElseThrow(() -> new BadRequestAlertException("La persona externa no existe", "asesorExterno", "noExiste"));

        String rolAnterior = actual.getRol();
        actual.setNombres(datos.getNombres());
        actual.setApellidos(datos.getApellidos());
        actual.setNumeroDocumento(datos.getNumeroDocumento());
        actual.setCorreoElectronico(datos.getCorreoElectronico());
        actual.setTelefono(datos.getTelefono());
        actual.setTitulos(datos.getTitulos());
        actual.setInstitucionOrigen(datos.getInstitucionOrigen());
        actual.setRol(datos.getRol());
        actual.setFuenteVerificacion(datos.getFuenteVerificacion());
        actual.setObservaciones(datos.getObservaciones());
        if (rolAnterior != null && !rolAnterior.equals(actual.getRol())) {
            actual.setIdoneidadVerificada(Boolean.FALSE);
            actual.setFechaVerificacionIdoneidad(null);
            actual.setVerificadorLogin(null);
        }
        return asesorExternoRepository.save(actual);
    }

    /**
     * El CIECYT constata la idoneidad academica de la persona externa. Solo despues de este paso
     * se puede designar en un proyecto.
     */
    @Transactional
    public AsesorExterno verificar(Long id) {
        AsesorExterno externo = asesorExternoRepository.findById(id)
            .orElseThrow(() -> new BadRequestAlertException("La persona externa no existe", "asesorExterno", "noExiste"));
        externo.setIdoneidadVerificada(Boolean.TRUE);
        externo.setFechaVerificacionIdoneidad(LocalDate.now());
        externo.setVerificadorLogin(SecurityUtils.getCurrentUserLogin().orElse("desconocido"));
        return asesorExternoRepository.save(externo);
    }

    private void validarDatos(AsesorExterno datos) {
        if (datos == null) {
            throw new BadRequestAlertException("Faltan los datos de la persona externa", "asesorExterno", "datosVacios");
        }
        if (datos.getNombres() == null || datos.getNombres().trim().isEmpty()) {
            throw new BadRequestAlertException("Indique los nombres", "asesorExterno", "nombresVacios");
        }
        if (datos.getApellidos() == null || datos.getApellidos().trim().isEmpty()) {
            throw new BadRequestAlertException("Indique los apellidos", "asesorExterno", "apellidosVacios");
        }
        if (datos.getNumeroDocumento() == null || datos.getNumeroDocumento().trim().isEmpty()) {
            throw new BadRequestAlertException("Indique el numero de documento", "asesorExterno", "documentoVacio");
        }
        String rol = normalizarRol(datos.getRol());
        if (!AsesorExterno.ROL_ASESOR.equals(rol) && !AsesorExterno.ROL_JURADO.equals(rol)) {
            throw new BadRequestAlertException("El rol debe ser ASESOR o JURADO", "asesorExterno", "rolInvalido");
        }
        datos.setRol(rol);
    }

    private String normalizarRol(String rol) {
        return rol == null || rol.trim().isEmpty() ? null : rol.trim().toUpperCase();
    }
}