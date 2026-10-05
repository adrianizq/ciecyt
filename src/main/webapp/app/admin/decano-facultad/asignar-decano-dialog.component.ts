import { Component, Inject, Vue } from 'vue-facing-decorator';
import DecanoFacultadService from '@/entities/decano-facultad/decano-facultad.service';
import AlertService from '@/shared/alert/alert.service';
import axios from 'axios';

@Component
export default class AsignarDecanoDialog extends Vue {
  form: any = null;
  facultades: any[] = [];
  isSaving = false;
  success = false;
  errorMessage = '';
  resolvedUser: any = null;
  loginError = '';
  private loginLookupTimer: any = null;

  @Inject
  private decanoFacultadService: () => DecanoFacultadService;

  @Inject
  private alertService: () => AlertService;

  public get formValid(): boolean {
    return !!this.form && !!this.form.userId && !!this.form.facultadId && !!this.form.fechaDesde && !this.loginError;
  }

  public open(): void {
    this.reset();
    this.loadFacultades();
    (this.$refs.modal as any).show();
  }

  public reset(): void {
    this.form = {
      userLogin: '',
      userId: null,
      facultadId: null,
      cargo: 'Decano',
      esDelegado: false,
      fechaDesde: new Date().toISOString().slice(0, 10),
      actoResolucion: '',
      observaciones: '',
    };
    this.facultades = [];
    this.success = false;
    this.errorMessage = '';
    this.isSaving = false;
    this.resolvedUser = null;
    this.loginError = '';
  }

  public userName(user: any): string {
    if (!user) {
      return '';
    }
    return [user.firstName, user.lastName]
      .filter((p: any) => !!p)
      .join(' ')
      .trim();
  }

  public onUserLoginInput(): void {
    this.resolvedUser = null;
    this.loginError = '';
    this.form.userId = null;
    if (this.loginLookupTimer) {
      clearTimeout(this.loginLookupTimer);
    }
    const login = (this.form.userLogin || '').trim();
    if (!login) {
      return;
    }
    this.loginLookupTimer = setTimeout(() => this.lookupUser(login), 300);
  }

  private lookupUser(login: string): void {
    axios
      .get(`api/users/${encodeURIComponent(login)}`)
      .then(res => {
        const user = res.data || null;
        if (!user || !user.id) {
          this.loginError = this.$t('decanoFacultad.asignar.loginNotFound').toString();
          return;
        }
        if (!(user.authorities || []).includes('ROLE_DECANO')) {
          this.loginError = this.$t('decanoFacultad.asignar.loginMissingDecanoRole').toString();
          this.resolvedUser = user;
          return;
        }
        this.resolvedUser = user;
        this.form.userId = user.id;
      })
      .catch(() => {
        this.loginError = this.$t('decanoFacultad.asignar.loginNotFound').toString();
      });
  }

  private loadFacultades(): void {
    axios
      .get('api/facultads?sort=facultad,asc')
      .then(res => {
        this.facultades = (res.data || []).slice().sort((a: any, b: any) => a.facultad.localeCompare(b.facultad));
      })
      .catch(() => {
        // fallback: usar la lista ya cacheada que la pagina trae en su loadAll si existiera; aqui
        // mostramos el error para que el admin sepa que la consulta fallo.
        this.errorMessage = this.$t('decanoFacultad.asignar.facultadesLoadError').toString();
      });
  }

  public confirm(): void {
    this.errorMessage = '';
    this.success = false;
    if (!this.formValid) {
      this.errorMessage = this.$t('decanoFacultad.asignar.invalidForm').toString();
      return;
    }
    this.isSaving = true;

    const payload = {
      id: null,
      facultad: { id: this.form.facultadId },
      user: { id: this.form.userId },
      cargo: this.form.cargo || 'Decano',
      esDelegado: !!this.form.esDelegado,
      fechaDesde: this.form.fechaDesde,
      fechaHasta: null,
      actoResolucion: this.form.actoResolucion || null,
      observaciones: this.form.observaciones || null,
    };

    this.decanoFacultadService()
      .create(payload)
      .then(res => {
        this.isSaving = false;
        this.success = true;
        this.alertService().success(
          this.$t('decanoFacultad.asignar.noticeSuccess', {
            login: this.form.userLogin,
            facultad: this.facultadNombre(this.form.facultadId),
          }).toString()
        );
        (this.$emit as any)('saved', res.data);
        setTimeout(() => {
          (this.$refs.modal as any).hide();
          this.reset();
        }, 1500);
      })
      .catch(error => {
        this.isSaving = false;
        this.errorMessage = this.alertService().showHttpError(this, error);
      });
  }

  private facultadNombre(id: number): string {
    const f = this.facultades.find(x => x.id === id);
    return f ? f.facultad : String(id);
  }
}
