package co.edu.itp.ciecyt.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class FasesMapperTest {

    private FasesMapper fasesMapper;

    @BeforeEach
    public void setUp() {
        fasesMapper = new FasesMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(fasesMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(fasesMapper.fromId(null)).isNull();
    }
}
