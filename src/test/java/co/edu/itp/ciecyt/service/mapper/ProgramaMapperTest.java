package co.edu.itp.ciecyt.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ProgramaMapperTest {

    private ProgramaMapper programaMapper;

    @BeforeEach
    public void setUp() {
        programaMapper = new ProgramaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(programaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(programaMapper.fromId(null)).isNull();
    }
}
