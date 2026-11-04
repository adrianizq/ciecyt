package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.domain.Festivo;
import co.edu.itp.ciecyt.errors.BadRequestAlertException;
import co.edu.itp.ciecyt.repository.FestivoRepository;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * Catalogo de dias no habiles.
 */
@RestController
@RequestMapping("/api")
public class FestivoResource {

    private static final String ENTITY_NAME = "festivo";
    private static final DateTimeFormatter FORMATO_ISO = DateTimeFormatter.ISO_LOCAL_DATE;
    private static final DateTimeFormatter FORMATO_DIA_MES_ANIO = DateTimeFormatter.ofPattern("d/M/yyyy");

    private final FestivoRepository festivoRepository;

    public FestivoResource(FestivoRepository festivoRepository) {
        this.festivoRepository = festivoRepository;
    }

    @GetMapping("/festivos")
    public List<Festivo> getAll() {
        return festivoRepository.findAllByOrderByFechaAsc();
    }

    /**
     * Plantilla CSV para carga masiva. Se entrega con BOM para que Excel respete las tildes.
     */
    @GetMapping("/festivos/plantilla")
    @PreAuthorize("hasAnyRole('ADMIN', 'CIECYT')")
    public ResponseEntity<byte[]> plantilla() {
        String contenido = "\uFEFFfecha,descripcion,activo\r\n2026-01-01,Año Nuevo,true\r\n";
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"plantilla-festivos.csv\"")
            .contentType(MediaType.parseMediaType("text/csv; charset=UTF-8"))
            .body(contenido.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Importa festivos desde un CSV. Formato: fecha,descripcion,activo.
     *
     * <p>Si una fecha ya existe se actualiza su descripcion y estado; las filas con errores se
     * informan por numero de linea sin detener el resto de la carga.
     */
    @PostMapping("/festivos/importar")
    @PreAuthorize("hasAnyRole('ADMIN', 'CIECYT')")
    @Transactional
    public ResponseEntity<ImportacionFestivosResultado> importar(@RequestBody ImportacionFestivosSolicitud solicitud) {
        String texto = solicitud == null || solicitud.getContenido() == null ? "" : solicitud.getContenido();
        if (texto.trim().isEmpty()) {
            throw new BadRequestAlertException("Adjunte un archivo CSV", ENTITY_NAME, "archivovacio");
        }
        if (texto.startsWith("\uFEFF")) {
            texto = texto.substring(1);
        }

        ImportacionFestivosResultado resultado = new ImportacionFestivosResultado();
        List<Festivo> aGuardar = new ArrayList<>();
        Map<LocalDate, Festivo> existentes = festivoRepository
            .findAll()
            .stream()
            .collect(Collectors.toMap(Festivo::getFecha, Function.identity(), (primero, segundo) -> primero));

        String[] lineas = texto.split("\\r?\\n", -1);
        boolean primeraLinea = true;
        char separador = ',';

        for (int i = 0; i < lineas.length; i++) {
            String linea = lineas[i].trim();
            if (linea.isEmpty() || linea.startsWith("#")) {
                continue;
            }

            if (primeraLinea) {
                separador = linea.contains(";") && !linea.contains(",") ? ';' : ',';
                primeraLinea = false;
                if (esCabecera(linea, separador)) {
                    continue;
                }
            }

            resultado.total++;
            List<String> campos = parsearLinea(linea, separador);
            if (campos.size() < 2) {
                resultado.agregarError(i + 1, "Se esperaban las columnas fecha,descripcion,activo");
                continue;
            }

            LocalDate fecha;
            try {
                fecha = parsearFecha(campos.get(0));
            } catch (DateTimeParseException ex) {
                resultado.agregarError(i + 1, "Fecha invalida: use AAAA-MM-DD o DD/MM/AAAA");
                continue;
            }

            String descripcion = campos.get(1).trim();
            if (descripcion.isEmpty()) {
                resultado.agregarError(i + 1, "La descripcion es obligatoria");
                continue;
            }
            if (descripcion.length() > 255) {
                resultado.agregarError(i + 1, "La descripcion supera 255 caracteres");
                continue;
            }

            boolean activo;
            try {
                activo = parsearActivo(campos.size() > 2 ? campos.get(2) : "");
            } catch (IllegalArgumentException ex) {
                resultado.agregarError(i + 1, "Valor de activo invalido: use true/false o si/no");
                continue;
            }

            Festivo festivo = existentes.get(fecha);
            if (festivo == null) {
                festivo = new Festivo();
                festivo.setFecha(fecha);
                existentes.put(fecha, festivo);
                resultado.creados++;
            } else {
                resultado.actualizados++;
            }
            festivo.setDescripcion(descripcion);
            festivo.setActivo(activo);
            if (!aGuardar.contains(festivo)) {
                aGuardar.add(festivo);
            }
        }

        festivoRepository.saveAll(aGuardar);
        return ResponseEntity.ok(resultado);
    }

    @PostMapping("/festivos")
    @PreAuthorize("hasAnyRole('ADMIN', 'CIECYT')")
    public ResponseEntity<Festivo> create(@RequestBody Festivo festivo) throws URISyntaxException {
        if (festivo.getId() != null) {
            throw new BadRequestAlertException("Un festivo nuevo no puede tener id", ENTITY_NAME, "idexists");
        }
        validar(festivo);
        Festivo saved = festivoRepository.save(festivo);
        return ResponseEntity.created(new URI("/api/festivos/" + saved.getId())).body(saved);
    }

    @PutMapping("/festivos")
    @PreAuthorize("hasAnyRole('ADMIN', 'CIECYT')")
    public ResponseEntity<Festivo> update(@RequestBody Festivo festivo) {
        if (festivo.getId() == null) {
            throw new BadRequestAlertException("Festivo sin id", ENTITY_NAME, "idnull");
        }
        validar(festivo);
        return ResponseEntity.ok(festivoRepository.save(festivo));
    }

    @DeleteMapping("/festivos/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CIECYT')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        festivoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private void validar(Festivo festivo) {
        if (festivo.getFecha() == null) {
            throw new BadRequestAlertException("Indique la fecha del festivo", ENTITY_NAME, "fecharequerida");
        }
        if (festivo.getDescripcion() == null || festivo.getDescripcion().trim().isEmpty()) {
            throw new BadRequestAlertException("Indique la descripcion del festivo", ENTITY_NAME, "descripcionrequerida");
        }
    }

    private boolean esCabecera(String linea, char separador) {
        List<String> campos = parsearLinea(linea, separador);
        return (
            campos.size() >= 2 &&
            campos.get(0).toLowerCase(Locale.ROOT).contains("fecha") &&
            campos.get(1).toLowerCase(Locale.ROOT).contains("descripcion")
        );
    }

    private LocalDate parsearFecha(String valor) {
        String fecha = valor == null ? "" : valor.trim();
        try {
            return LocalDate.parse(fecha, FORMATO_ISO);
        } catch (DateTimeParseException ex) {
            return LocalDate.parse(fecha, FORMATO_DIA_MES_ANIO);
        }
    }

    private boolean parsearActivo(String valor) {
        String activo = valor == null ? "" : valor.trim().toLowerCase(Locale.ROOT);
        if (activo.isEmpty() || activo.equals("true") || activo.equals("1") || activo.equals("si") || activo.equals("sí") || activo.equals("s")) {
            return true;
        }
        if (activo.equals("false") || activo.equals("0") || activo.equals("no") || activo.equals("n")) {
            return false;
        }
        throw new IllegalArgumentException("Activo invalido");
    }

    /**
     * Parser CSV minimo: admite comas o punto y coma y campos entre comillas dobles.
     */
    private List<String> parsearLinea(String linea, char separador) {
        List<String> campos = new ArrayList<>();
        StringBuilder actual = new StringBuilder();
        boolean entreComillas = false;

        for (int i = 0; i < linea.length(); i++) {
            char caracter = linea.charAt(i);
            if (caracter == '"') {
                if (entreComillas && i + 1 < linea.length() && linea.charAt(i + 1) == '"') {
                    actual.append('"');
                    i++;
                } else {
                    entreComillas = !entreComillas;
                }
            } else if (caracter == separador && !entreComillas) {
                campos.add(actual.toString().trim());
                actual.setLength(0);
            } else {
                actual.append(caracter);
            }
        }
        campos.add(actual.toString().trim());
        return campos;
    }

    public static class ImportacionFestivosSolicitud {

        private String contenido;

        public String getContenido() {
            return contenido;
        }

        public void setContenido(String contenido) {
            this.contenido = contenido;
        }
    }

    public static class ImportacionFestivosResultado {

        private int total;
        private int creados;
        private int actualizados;
        private final List<ErrorImportacion> errores = new ArrayList<>();

        public int getTotal() {
            return total;
        }

        public int getCreados() {
            return creados;
        }

        public int getActualizados() {
            return actualizados;
        }

        public List<ErrorImportacion> getErrores() {
            return errores;
        }

        public void agregarError(int linea, String mensaje) {
            errores.add(new ErrorImportacion(linea, mensaje));
        }
    }

    public static class ErrorImportacion {

        private final int linea;
        private final String mensaje;

        public ErrorImportacion(int linea, String mensaje) {
            this.linea = linea;
            this.mensaje = mensaje;
        }

        public int getLinea() {
            return linea;
        }

        public String getMensaje() {
            return mensaje;
        }
    }
}

