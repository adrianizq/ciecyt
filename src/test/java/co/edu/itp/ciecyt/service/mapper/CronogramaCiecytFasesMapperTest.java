package co.edu.itp.ciecyt.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CronogramaCiecytFasesMapperTest {

    private CronogramaCiecytFasesMapper cronogramaCiecytFasesMapper;

    @BeforeEach
    public void setUp() {
        cronogramaCiecytFasesMapper = new CronogramaCiecytFasesMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cronogramaCiecytFasesMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cronogramaCiecytFasesMapper.fromId(null)).isNull();
    }
}
