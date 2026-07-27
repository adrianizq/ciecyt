package co.edu.itp.ciecyt.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ResultadosEsperadosMapperTest {

    private ResultadosEsperadosMapper resultadosEsperadosMapper;

    @BeforeEach
    public void setUp() {
        resultadosEsperadosMapper = new ResultadosEsperadosMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(resultadosEsperadosMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(resultadosEsperadosMapper.fromId(null)).isNull();
    }
}
