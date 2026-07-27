package co.edu.itp.ciecyt.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import co.edu.itp.ciecyt.domain.enumeration.EnumGenero;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link co.edu.itp.ciecyt.domain.UserInfo} entity. This class is used
 * in {@link co.edu.itp.ciecyt.web.rest.UserInfoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /user-infos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class UserInfoCriteria implements Serializable, Criteria {
    /**
     * Class for filtering EnumGenre
     */
    public static class EnumGeneroFilter extends Filter<EnumGenero> {

        public EnumGeneroFilter() {
        }

        public EnumGeneroFilter(EnumGeneroFilter filter) {
            super(filter);
        }

        @Override
        public EnumGeneroFilter copy() {
            return new EnumGeneroFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nuip;

    private StringFilter codigoItp;

    private StringFilter celular;

    private StringFilter telefono;

    private EnumGeneroFilter genero;

    //private LocalDateFilter birthDate;

    //private StringFilter movil;

    //private StringFilter nickName;

    //private LongFilter countryId;

     private LongFilter userId;

    public UserInfoCriteria() {
    }

    public UserInfoCriteria(UserInfoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nuip = other.nuip == null ? null : other.nuip.copy();
        this.codigoItp = other.codigoItp == null ? null : other.codigoItp.copy();
        this.celular = other.celular == null ? null : other.celular.copy();
        this.telefono = other.telefono == null ? null : other.telefono.copy();
        this.genero = other.genero == null ? null : other.genero.copy();
        //this.countryId = other.countryId == null ? null : other.countryId.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
    }

    @Override
    public UserInfoCriteria copy() {
        return new UserInfoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNuip() {
        return nuip;
    }

    public void setCity(StringFilter nuip) {
        this.nuip = nuip;
    }

    public EnumGeneroFilter getGenero() {
        return genero;
    }

    public void setGenero(EnumGeneroFilter genero) {
        this.genero = genero;
    }

    public StringFilter getCodigoItp() {
        return codigoItp;
    }

    public void setCodigoItp(StringFilter codigoItp) {
        this.codigoItp = codigoItp;
    }

    public StringFilter getCelular() {
        return celular;
    }

    public void setCelular(StringFilter celular) {
        this.celular = celular;
    }

    public StringFilter getTelefono() {
        return telefono;
    }

    public void setTelefono(StringFilter telefono) {
        this.telefono = telefono;
    }

       public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final UserInfoCriteria that = (UserInfoCriteria) o;
        return
            Objects.equals(id, that.id) &&
                Objects.equals(nuip, that.nuip) &&
                Objects.equals(codigoItp, that.codigoItp) &&
                Objects.equals(genero, that.genero) &&
                Objects.equals(celular, that.celular) &&
                Objects.equals(telefono, that.telefono) &&
                Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            nuip,
            codigoItp,
            genero,
            celular,
            telefono,
            userId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserInfoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (nuip != null ? "nuip=" + nuip + ", " : "") +
            (codigoItp != null ? "codigoItp=" + codigoItp + ", " : "") +
            (genero != null ? "genero=" + genero + ", " : "") +
            (celular != null ? "celular=" + celular + ", " : "") +
            (telefono != null ? "nickName=" + telefono + ", " : "") +
            //(countryId != null ? "countryId=" + countryId + ", " : "") +
            (userId != null ? "userId=" + userId + ", " : "") +
            "}";
    }

}
