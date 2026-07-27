package co.edu.itp.ciecyt.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CicloPropedeuticoMapperTest {

    private CicloPropedeuticoMapper cicloPropedeuticoMapper;

    @BeforeEach
    public void setUp() {
        cicloPropedeuticoMapper = new CicloPropedeuticoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cicloPropedeuticoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cicloPropedeuticoMapper.fromId(null)).isNull();
    }
}
