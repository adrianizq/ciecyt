package co.edu.itp.ciecyt.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class RolMenuMapperTest {

    private RolMenuMapper rolMenuMapper;

    @BeforeEach
    public void setUp() {
        rolMenuMapper = new RolMenuMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(rolMenuMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(rolMenuMapper.fromId(null)).isNull();
    }
}
