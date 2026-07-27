package co.edu.itp.ciecyt.service.impl;

import co.edu.itp.ciecyt.config.ApplicationProperties;
import co.edu.itp.ciecyt.domain.AdjuntoRetroalimentacion;
import co.edu.itp.ciecyt.repository.AdjuntoRetroalimentacionRepository;
import co.edu.itp.ciecyt.service.AdjuntoRetroalimentacionService;
import co.edu.itp.ciecyt.service.dto.AdjuntoRetroalimentacionDTO;
import co.edu.itp.ciecyt.service.mapper.AdjuntoRetroalimentacionMapper;
import co.edu.itp.ciecyt.service.util.FileUtils;
import co.edu.itp.ciecyt.service.util.MimeTypes;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AdjuntoRetroalimentacion}.
 */
@Service
@Transactional
public class AdjuntoRetroalimentacionServiceImpl implements AdjuntoRetroalimentacionService {

    private final Logger log = LoggerFactory.getLogger(AdjuntoRetroalimentacionServiceImpl.class);

    private final AdjuntoRetroalimentacionRepository adjuntoRetroalimentacionRepository;

    private final AdjuntoRetroalimentacionMapper adjuntoRetroalimentacionMapper;

    private final ApplicationProperties appProperties;

    public AdjuntoRetroalimentacionServiceImpl(
        AdjuntoRetroalimentacionRepository adjuntoRetroalimentacionRepository,
        AdjuntoRetroalimentacionMapper adjuntoRetroalimentacionMapper,
        ApplicationProperties appProperties
    ) {
        this.adjuntoRetroalimentacionRepository = adjuntoRetroalimentacionRepository;
        this.adjuntoRetroalimentacionMapper = adjuntoRetroalimentacionMapper;
        this.appProperties = appProperties;
    }

    @Override
    public AdjuntoRetroalimentacionDTO save(AdjuntoRetroalimentacionDTO adjuntoRetroalimentacionDTO) {
         log.debug("Request to save AdjuntoRetroalimentacion : {}", adjuntoRetroalimentacionDTO);
         AdjuntoRetroalimentacion adjuntoRetroalimentacion = adjuntoRetroalimentacionMapper.toEntity(adjuntoRetroalimentacionDTO);
        adjuntoRetroalimentacion = adjuntoRetroalimentacionRepository.save(adjuntoRetroalimentacion);
        return adjuntoRetroalimentacionMapper.toDto(adjuntoRetroalimentacion);
        /*log.debug("Request to save AdjuntoProyectoFase : {}", adjuntoRetroalimentacionDTO);
        AdjuntoRetroalimentacion adjuntoRetroalimentacion = adjuntoRetroalimentacionMapper.toEntity(adjuntoRetroalimentacionDTO);
        adjuntoRetroalimentacion = adjuntoRetroalimentacionRepository.save(adjuntoRetroalimentacion);

        AdjuntoRetroalimentacionDTO dto = adjuntoRetroalimentacionMapper.toDto(adjuntoRetroalimentacion);

        if (dto.getArchivo() != null) {
            String fileUrl = FileUtils.buildURLImage(
                appProperties.getFilesPath(),
                dto.getFile(),
                //appProperties.getUpload().getFilesRetro()
                appProperties.getUpload().getAdjuntoRetroalimentacion()
            );
            dto.setUrlFile(fileUrl);
        }
        return dto;*/
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AdjuntoRetroalimentacionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AdjuntoRetroalimentacions");
        return adjuntoRetroalimentacionRepository.findAll(pageable).map(adjuntoRetroalimentacionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AdjuntoRetroalimentacionDTO> findOne(Long id) {
        log.debug("Request to get AdjuntoRetroalimentacion : {}", id);
        return adjuntoRetroalimentacionRepository.findById(id).map(adjuntoRetroalimentacionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AdjuntoRetroalimentacion : {}", id);
        adjuntoRetroalimentacionRepository.deleteById(id);
    }

    @Override
    public void attachFile(AdjuntoRetroalimentacionDTO dto, byte[] file, String contentType) {
        AdjuntoRetroalimentacion entity = adjuntoRetroalimentacionMapper.toEntity(dto);
        //Guarda el archivo en el directorio asignado
        //File rootDir = new File("/home/jltovarg/test/dudo");
        try {
            Path rootDir = Paths.get(appProperties.getUpload().getRoot().getDir());
            Path filesDir = rootDir.resolve( appProperties.getUpload().getAdjuntoRetroalimentacion().getDir());
            log.debug("Dir upload: {}, , filesDir: {}, mimetype: {}", rootDir, filesDir, contentType);
            String ext = MimeTypes.getDefaultExt(contentType);
            String nameFile = FileUtils.buildFileName(entity.getId(), ext);

            //FileUtils.writeByteArrayToFile(announcementDir.resolve(nameFile), file );
            Files.write(filesDir.resolve(nameFile), file, StandardOpenOption.CREATE);

            entity.setArchivo(nameFile);
            adjuntoRetroalimentacionRepository.save(entity);
        } catch (Exception e) {
            log.error("Error attah file", e);
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    @Override
    public Resource loadFileAsResource(AdjuntoRetroalimentacionDTO dto) throws Exception {
        try {
            Path rootDir = Paths.get(appProperties.getUpload().getRoot().getDir());
            Path filesDir = rootDir.resolve(appProperties.getUpload().getAdjuntoRetroalimentacion().getDir());
            log.debug("Dir upload: {}, , filesDir: {}, file: {}", rootDir, filesDir, dto.getFile());

            Path filePath = filesDir.resolve(dto.getFile()).normalize();

            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("File not found " + dto.getFile());
            }
        } catch (MalformedURLException ex) {
            throw new FileNotFoundException("File not found " + dto.getFile());
        }
    }

    @Override
    public List<AdjuntoRetroalimentacionDTO> findByAdjuntoRetroalimentacionProyectoIdAndAdjuntoRetroalimentacionFaseIdAndAuthority(
        Long idProyecto,
        Long idFase,
        String authority
    )
        throws Exception {
        List<AdjuntoRetroalimentacion> list = adjuntoRetroalimentacionRepository.findByAdjuntoRetroalimentacionProyectoIdAndAdjuntoRetroalimentacionFaseIdAndAuthority(
            idProyecto,
            idFase,
            authority
        );

        List<AdjuntoRetroalimentacionDTO> listDTO = new ArrayList<>();
        for (AdjuntoRetroalimentacion adjunto : list) {
            listDTO.add(adjuntoRetroalimentacionMapper.toDto(adjunto));
        }
        return listDTO;
    }
}
