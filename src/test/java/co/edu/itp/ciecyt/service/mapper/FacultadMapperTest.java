package co.edu.itp.ciecyt.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class FacultadMapperTest {

    private FacultadMapper facultadMapper;

    @BeforeEach
    public void setUp() {
        facultadMapper = new FacultadMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(facultadMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(facultadMapper.fromId(null)).isNull();
    }
}
