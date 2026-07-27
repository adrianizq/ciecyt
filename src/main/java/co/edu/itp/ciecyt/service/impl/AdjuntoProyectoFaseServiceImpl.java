package co.edu.itp.ciecyt.service.impl;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import co.edu.itp.ciecyt.domain.AdjuntoRetroalimentacion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.itp.ciecyt.config.ApplicationProperties;
import co.edu.itp.ciecyt.domain.AdjuntoProyectoFase;
import co.edu.itp.ciecyt.repository.AdjuntoProyectoFaseRepository;
import co.edu.itp.ciecyt.service.AdjuntoProyectoFaseService;
import co.edu.itp.ciecyt.service.dto.AdjuntoProyectoFaseDTO;
import co.edu.itp.ciecyt.service.mapper.AdjuntoProyectoFaseMapper;
import co.edu.itp.ciecyt.service.util.FileUtils;
import co.edu.itp.ciecyt.service.util.MimeTypes;

/**
 * Service Implementation for managing {@link AdjuntoProyectoFase}.
 */
@Service
@Transactional
public class AdjuntoProyectoFaseServiceImpl implements AdjuntoProyectoFaseService {

    private final Logger log = LoggerFactory.getLogger(AdjuntoProyectoFaseServiceImpl.class);

    private final AdjuntoProyectoFaseRepository adjuntoProyectoFaseRepository;

    private final AdjuntoProyectoFaseMapper adjuntoProyectoFaseMapper;

    private final ApplicationProperties appProperties;

    public AdjuntoProyectoFaseServiceImpl(AdjuntoProyectoFaseRepository adjuntoProyectoFaseRepository,
                                          AdjuntoProyectoFaseMapper adjuntoProyectoFaseMapper, ApplicationProperties appProperties) {
        this.adjuntoProyectoFaseRepository = adjuntoProyectoFaseRepository;
        this.adjuntoProyectoFaseMapper = adjuntoProyectoFaseMapper;
        this.appProperties = appProperties;
    }

    @Override
    public AdjuntoProyectoFaseDTO save(AdjuntoProyectoFaseDTO adjuntoProyectoFaseDTO) {
        log.debug("Request to save AdjuntoProyectoFase : {}", adjuntoProyectoFaseDTO);

        AdjuntoProyectoFase adjuntoProyectoFase = adjuntoProyectoFaseMapper.toEntity(adjuntoProyectoFaseDTO);
        adjuntoProyectoFase = adjuntoProyectoFaseRepository.save(adjuntoProyectoFase);
        return adjuntoProyectoFaseMapper.toDto(adjuntoProyectoFase);

        /*AdjuntoProyectoFase adjuntoProyectoFase = adjuntoProyectoFaseMapper.toEntity(adjuntoProyectoFaseDTO);
        adjuntoProyectoFase = adjuntoProyectoFaseRepository.save(adjuntoProyectoFase);


        AdjuntoProyectoFaseDTO dto  = adjuntoProyectoFaseMapper.toDto(adjuntoProyectoFase);

        if (dto.getArchivo() != null) {
            String fileUrl = FileUtils.buildURLImage(
                appProperties.getFilesPath(),
                dto.getFile(),
                appProperties.getUpload().getFiles()
            );
            dto.setUrlFile(fileUrl);
        }
        return dto;

         */
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AdjuntoProyectoFaseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AdjuntoProyectoFases");
        return adjuntoProyectoFaseRepository.findAll(pageable).map(adjuntoProyectoFaseMapper::toDto);

        /*log.debug("Request to get all AdjuntoProyectoFases");
        return adjuntoProyectoFaseRepository.findAll(pageable)
            .map(adjuntoProyectoFaseMapper::toDto).map(dto -> {
                    if (dto.getArchivo() != null) {
                        String urlFile = FileUtils.buildURLImage(
                            appProperties.getFilesPath(),
                            dto.getFile(),
                            appProperties.getUpload().getFiles()
                        );
                        dto.setUrlFile(urlFile);
                    }
                    return dto;
                }
            );

         */
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<AdjuntoProyectoFaseDTO> findOne(Long id) {
        log.debug("Request to get AdjuntoProyectoFase : {}", id);

        return adjuntoProyectoFaseRepository.findById(id).map(adjuntoProyectoFaseMapper::toDto);

   /*     return adjuntoProyectoFaseRepository.findById(id)
            .map(adjuntoProyectoFaseMapper::toDto).map(dto -> {
                    if (dto.getArchivo() != null) {
                        String urlFile = FileUtils.buildURLImage(
                            appProperties.getFilesPath(),
                            dto.getFile(),
                            appProperties.getUpload().getFiles()
                        );
                        dto.setUrlFile(urlFile);
                    }
                    return dto;
                }
            );*/
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AdjuntoProyectoFase : {}", id);
        adjuntoProyectoFaseRepository.deleteById(id);
    }


    /**
     * Ajunta un arhivo de imagen a la organizacion
     *
     * @param dto the organization dto
     * @param file the byte file image upload
     * @param contentType the contenttype file image
     *
     */
    @Override
    public void attachFile(AdjuntoProyectoFaseDTO dto, byte[] file, String contentType) {
        AdjuntoProyectoFase entity = adjuntoProyectoFaseMapper.toEntity(dto);
        //Guarda el archivo en el directorio asignado
        //File rootDir = new File("/home/jltovarg/test/dudo");
        try {
            Path rootDir = Paths.get(appProperties.getUpload().getRoot().getDir());
            Path filesDir = rootDir.resolve(appProperties.getUpload().getAdjuntoProyectoFase().getDir());
            log.debug("Dir upload: {}, , filesDir: {}, mimetype: {}", rootDir, filesDir, contentType);
            String ext = MimeTypes.getDefaultExt(contentType);
            String nameFile = FileUtils.buildFileName(entity.getId(), ext);

            //FileUtils.writeByteArrayToFile(announcementDir.resolve(nameFile), file );
            Files.write(filesDir.resolve(nameFile), file, StandardOpenOption.CREATE);

            entity.setArchivo(nameFile);
            adjuntoProyectoFaseRepository.save(entity);
        } catch (Exception e) {
            log.error("Error attah file", e);
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }
    /**
     * Carga un archivo del directorio base
     * @param dto
     * @return
     * @throws Exception
     */
    @Override
    public Resource loadFileAsResource(AdjuntoProyectoFaseDTO dto) throws Exception {

        try {

            Path rootDir = Paths.get(appProperties.getUpload().getRoot().getDir());
            Path filesDir = rootDir.resolve(appProperties.getUpload().getAdjuntoProyectoFase().getDir());
            log.debug("Dir upload: {}, , filesDir: {}, file: {}", rootDir, filesDir, dto.getFile());

            Path filePath = filesDir.resolve(dto.getFile()).normalize();

            Resource resource = new UrlResource(filePath.toUri());

            if(resource.exists()) {

                return resource;

            } else {

                throw new FileNotFoundException("File not found " + dto.getFile());

            }

        } catch (MalformedURLException ex) {

            throw new FileNotFoundException("File not found " + dto.getFile());

        }

    }

    @Override
    @Transactional(readOnly = true)
    public List<AdjuntoProyectoFaseDTO> findByProyectoFaseProyectoIdAndAdjuntoProyectoFaseFaseId(Long idProyecto, Long idFase)
        throws Exception {
        List<AdjuntoProyectoFase> list = adjuntoProyectoFaseRepository.findByProyectoFaseProyectoIdAndAdjuntoProyectoFaseFaseId(
            idProyecto,
            idFase
        );
        List<AdjuntoProyectoFaseDTO> listDTO = new ArrayList<>();
        for (AdjuntoProyectoFase adjunto : list) {
            listDTO.add(adjuntoProyectoFaseMapper.toDto(adjunto));
        }
        return listDTO;
    }
}
