package co.edu.itp.ciecyt.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CronogramaCiecytMapperTest {

    private CronogramaCiecytMapper cronogramaCiecytMapper;

    @BeforeEach
    public void setUp() {
        cronogramaCiecytMapper = new CronogramaCiecytMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cronogramaCiecytMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cronogramaCiecytMapper.fromId(null)).isNull();
    }
}
