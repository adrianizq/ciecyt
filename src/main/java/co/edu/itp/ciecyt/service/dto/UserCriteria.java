package co.edu.itp.ciecyt.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link co.edu.itp.ciecyt.domain.User} entity. This class is used
 * in {@link co.edu.itp.ciecyt.web.rest.UserResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /Users?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class UserCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter login;

    private StringFilter firstName;

    private StringFilter lastName;

    private StringFilter email;

    private BooleanFilter activated;

    //private BooleanFilter premium;

    //private LongFilter organizationId;

    public UserCriteria() {
    }

    public UserCriteria(UserCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.login = other.login == null ? null : other.login.copy();
        this.firstName = other.firstName == null ? null : other.firstName.copy();
        this.lastName = other.lastName == null ? null : other.lastName.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.activated = other.activated == null ? null : other.activated.copy();
      //  this.premium = other.premium == null ? null : other.premium.copy();
      //  this.organizationId = other.organizationId == null ? null : other.organizationId.copy();
    }

    @Override
    public UserCriteria copy() {
        return new UserCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }


    public BooleanFilter getActivated() {
        return activated;
    }

    public void setActivated(BooleanFilter activated) {
        this.activated = activated;
    }


    public StringFilter getLogin() {
        return this.login;
    }

    public void setLogin(StringFilter login) {
        this.login = login;
    }

    public StringFilter getFirstName() {
        return this.firstName;
    }

    public void setFirstName(StringFilter firstName) {
        this.firstName = firstName;
    }

    public StringFilter getLastName() {
        return this.lastName;
    }

    public void setLastName(StringFilter lastName) {
        this.lastName = lastName;
    }

    public StringFilter getEmail() {
        return this.email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }
/*
    public BooleanFilter getPremium() {
        return this.premium;
    }

    public void setPremium(BooleanFilter premium) {
        this.premium = premium;
    }


    public LongFilter getOrganizationId() {
        return this.organizationId;
    }

    public void setOrganizationId(LongFilter organizationId) {
        this.organizationId = organizationId;
    }



 */

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final UserCriteria that = (UserCriteria) o;
        return
            Objects.equals(id, that.id) &&
                Objects.equals(login, that.login) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(email, that.email) &&
                Objects.equals(activated, that.activated);
//                Objects.equals(organizationId, that.organizationId) &&
//                Objects.equals(premium, that.premium);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            login,
            firstName,
            lastName,
            email,
            activated
  //          premium,
  //          organizationId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (login != null ? "login=" + login + ", " : "") +
            (firstName != null ? "firstName=" + firstName + ", " : "") +
            (lastName != null ? "lastName=" + lastName + ", " : "") +
            (email != null ? "email=" + email + ", " : "") +
            (activated != null ? "activated=" + activated + ", " : "") +
            //(premium != null ? "premium=" + premium + ", " : "") +
            //(organizationId != null ? "organizationId=" + organizationId + ", " : "") +
            "}";
    }

}
