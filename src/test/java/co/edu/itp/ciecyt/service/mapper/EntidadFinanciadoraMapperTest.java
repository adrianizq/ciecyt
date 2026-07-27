package co.edu.itp.ciecyt.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class EntidadFinanciadoraMapperTest {

    private EntidadFinanciadoraMapper entidadFinanciadoraMapper;

    @BeforeEach
    public void setUp() {
        entidadFinanciadoraMapper = new EntidadFinanciadoraMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(entidadFinanciadoraMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(entidadFinanciadoraMapper.fromId(null)).isNull();
    }
}
