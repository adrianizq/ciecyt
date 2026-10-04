package co.edu.itp.ciecyt.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO para que un administrador del sistema resetee la contrasena de
 * cualquier usuario sin necesidad de conocer la contrasena actual
 * (a diferencia de {@link PasswordChangeDTO}, que requiere current + new).
 *
 * Se usa en POST /api/admin/users/{login}/reset-password. Solo accesible
 * para ROLE_ADMIN (Authorization es via @PreAuthorize y la regla global
 * /api/** en SecurityConfiguration).
 */
public class AdminPasswordResetDTO {

    @NotBlank
    @Size(min = 4, max = 100)
    private String newPassword;

    public AdminPasswordResetDTO() {
        // Empty constructor needed for Jackson.
    }

    public AdminPasswordResetDTO(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
