import { Component, Inject, Vue } from 'vue-facing-decorator';
import { mixins } from 'vue-facing-decorator';
import DecanoFacultadService from '@/entities/decano-facultad/decano-facultad.service';
import { formatDate as formatDateValue } from '@/shared/date/filters';
import AlertService from '@/shared/alert/alert.service';
import AsignarDecanoDialog from './asignar-decano-dialog.vue';
import CerrarVigenciaDialog from './cerrar-vigencia-dialog.vue';

@Component({
  components: {
    AsignarDecanoDialog,
    CerrarVigenciaDialog,
  },
})
export default class DecanoFacultadPage extends Vue {
  public asignaciones: any[] = [];
  public removeId: any = null;
  public isSaving = false;
  public error: any = '';
  public dismissCountDown = 0;
  public alertType: string;
  public alertMessage: any;

  @Inject
  private alertService: () => AlertService;

  @Inject
  private decanoFacultadService: () => DecanoFacultadService;

  public formatDate(value: any): string {
    return formatDateValue(value);
  }

  public formatUserName(user: any): string {
    if (!user) {
      return '';
    }
    const parts = [user.firstName, user.lastName].filter(part => !!part);
    return parts.join(' ').trim();
  }

  public mounted(): void {
    this.loadAll();
  }

  public loadAll(): void {
    this.decanoFacultadService()
      .retrieve()
      .then(res => {
        this.asignaciones = (res.data || []).slice().sort(this.compareParaVista);
      })
      .catch(error => {
        this.alertService().showHttpError(this, error);
      });
  }

  private compareParaVista(a: any, b: any): number {
    // Vigentes primero (fechaHasta nula), luego por facultad, luego por fecha desde desc.
    const aVigente = !a.fechaHasta ? 0 : 1;
    const bVigente = !b.fechaHasta ? 0 : 1;
    if (aVigente !== bVigente) {
      return aVigente - bVigente;
    }
    const fa = (a.facultad && a.facultad.facultad) || '';
    const fb = (b.facultad && b.facultad.facultad) || '';
    if (fa !== fb) {
      return fa.localeCompare(fb);
    }
    return (b.fechaDesde || '').localeCompare(a.fechaDesde || '');
  }

  public prepareRemove(item: any): void {
    this.removeId = item;
    (this.$refs.removeDecanoFacultad as any).show();
  }

  public deleteDecanoFacultad(): void {
    this.isSaving = true;
    this.decanoFacultadService()
      .delete(this.removeId.id)
      .then(() => {
        this.alertService().success(this.$t('decanoFacultad.deleted', { login: this.userLogin(this.removeId) }).toString());
        this.removeId = null;
        (this.$refs.removeDecanoFacultad as any).hide();
        this.loadAll();
      })
      .catch(error => {
        this.alertService().showHttpError(this, error);
      })
      .finally(() => {
        this.isSaving = false;
      });
  }

  public openAsignarDialog(): void {
    (this.$refs.asignarDialog as any).open();
  }

  public openCerrarDialog(item: any): void {
    (this.$refs.cerrarDialog as any).open(item);
  }

  public closeDialog(): void {
    this.removeId = null;
    (this.$refs.removeDecanoFacultad as any).hide();
  }

  public countDownChanged(dismissCountDown: number): void {
    this.alertService().countDownChanged(dismissCountDown);
    this.dismissCountDown = dismissCountDown;
  }

  private userLogin(item: any): string {
    return item && item.user ? item.user.login : '';
  }
}
