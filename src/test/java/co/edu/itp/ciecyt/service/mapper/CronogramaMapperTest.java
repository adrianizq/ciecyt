package co.edu.itp.ciecyt.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CronogramaMapperTest {

    private CronogramaMapper cronogramaMapper;

    @BeforeEach
    public void setUp() {
        cronogramaMapper = new CronogramaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cronogramaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cronogramaMapper.fromId(null)).isNull();
    }
}
