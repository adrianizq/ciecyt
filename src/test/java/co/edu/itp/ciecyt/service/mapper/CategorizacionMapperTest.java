package co.edu.itp.ciecyt.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CategorizacionMapperTest {

    private CategorizacionMapper categorizacionMapper;

    @BeforeEach
    public void setUp() {
        categorizacionMapper = new CategorizacionMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(categorizacionMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(categorizacionMapper.fromId(null)).isNull();
    }
}
