package co.edu.itp.ciecyt.service.util;

import co.edu.itp.ciecyt.config.ApplicationProperties.DirPath;
import co.edu.itp.ciecyt.config.Constants;
import java.util.Optional;

//import liquibase.util.file.FilenameUtils;
import org.apache.commons.io.FilenameUtils;

public class FileUtils {

    private static liquibase.util.file.FilenameUtils FilenameUtils;

    public static Optional<String> getExtensionByStringHandling(String filename) {
        return Optional.ofNullable(filename)
            .filter(f -> f.contains("."))
            .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }

    public static String getExtensionByApacheCommonLib(String filename) {
        return FilenameUtils.getExtension(filename);
    }

    public static String buildFileName(Long id, String extension ){
        StringBuilder sb = new StringBuilder();
        sb.append(id).append( FilenameUtils.EXTENSION_SEPARATOR_STR).append(extension);
        return sb.toString();

    }

    public static String buildURLImage(String urlImage, String idImage, DirPath path ){
        StringBuilder sb = new StringBuilder();
        sb.append( urlImage );
        sb.append(Constants.PATH_SEPARATOR);
        sb.append( path.getDir() );
        sb.append(Constants.PATH_SEPARATOR);
        sb.append(idImage);
        return sb.toString();
    }
}
