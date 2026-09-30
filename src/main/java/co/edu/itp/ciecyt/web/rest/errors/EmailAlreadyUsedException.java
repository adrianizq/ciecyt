package co.edu.itp.ciecyt.web.rest.errors;

import co.edu.itp.ciecyt.errors.BadRequestAlertException;
import co.edu.itp.ciecyt.errors.ErrorConstants;

public class EmailAlreadyUsedException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public EmailAlreadyUsedException() {
        super(ErrorConstants.EMAIL_ALREADY_USED_TYPE, "Email is already in use!", "userManagement", "emailexists");
    }
}
