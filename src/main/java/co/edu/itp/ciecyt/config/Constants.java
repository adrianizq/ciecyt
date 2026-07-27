package co.edu.itp.ciecyt.config;

/**
 * Application constants.
 */
public final class Constants {

    // Regex for acceptable logins
    public static final String LOGIN_REGEX = "^(?>[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*)|(?>[_.@A-Za-z0-9-]+)$";

    public static final String PATH_SEPARATOR = "/";

    public static final String SYSTEM_ACCOUNT = "system";
    public static final String DEFAULT_LANGUAGE = "es";
    public static final String ANONYMOUS_USER = "anonymoususer";

    public static final Long DEFAULT_ORG_ID =  1L;
    //public static final Integer DEFAULT_NUMBER_LIVES_GAME =  3;

    private Constants() {}
}
