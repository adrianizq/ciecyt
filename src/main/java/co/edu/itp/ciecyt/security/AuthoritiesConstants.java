package co.edu.itp.ciecyt.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String ASESOR = "ROLE_ASESOR";
    public static final String JURADO = "ROLE_JURADO";
    public static final String DOCENTE = "ROLE_DOCENTE";
    public static final String CIECYT = "ROLE_CIECYT";
    public static final String ESTUDIANTE = "ROLE_ESTUDIANTE";
    public static final String VIABILIDAD = "ROLE_VIABILIDAD";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    private AuthoritiesConstants() {}
}
