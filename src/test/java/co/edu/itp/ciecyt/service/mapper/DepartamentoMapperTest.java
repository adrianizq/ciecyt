package co.edu.itp.ciecyt.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DepartamentoMapperTest {

    private DepartamentoMapper departamentoMapper;

    @BeforeEach
    public void setUp() {
        departamentoMapper = new DepartamentoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(departamentoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(departamentoMapper.fromId(null)).isNull();
    }
}
