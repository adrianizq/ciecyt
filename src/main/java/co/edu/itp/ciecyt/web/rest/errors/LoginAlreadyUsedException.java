package co.edu.itp.ciecyt.web.rest.errors;

import co.edu.itp.ciecyt.errors.BadRequestAlertException;
import co.edu.itp.ciecyt.errors.ErrorConstants;

public class LoginAlreadyUsedException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public LoginAlreadyUsedException() {
        super(ErrorConstants.LOGIN_ALREADY_USED_TYPE, "Login name already used!", "userManagement", "userexists");
    }
}
