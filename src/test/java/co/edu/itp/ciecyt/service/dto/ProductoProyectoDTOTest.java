package co.edu.itp.ciecyt.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class ProductoProyectoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductoProyectoDTO.class);
        ProductoProyectoDTO productoProyectoDTO1 = new ProductoProyectoDTO();
        productoProyectoDTO1.setId(1L);
        ProductoProyectoDTO productoProyectoDTO2 = new ProductoProyectoDTO();
        assertThat(productoProyectoDTO1).isNotEqualTo(productoProyectoDTO2);
        productoProyectoDTO2.setId(productoProyectoDTO1.getId());
        assertThat(productoProyectoDTO1).isEqualTo(productoProyectoDTO2);
        productoProyectoDTO2.setId(2L);
        assertThat(productoProyectoDTO1).isNotEqualTo(productoProyectoDTO2);
        productoProyectoDTO1.setId(null);
        assertThat(productoProyectoDTO1).isNotEqualTo(productoProyectoDTO2);
    }
}
