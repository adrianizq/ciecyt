import { Component, Inject, Vue } from 'vue-facing-decorator';
import DecanoFacultadService from '@/entities/decano-facultad/decano-facultad.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class CerrarVigenciaDialog extends Vue {
  item: any = null;
  fechaHasta: string = '';
  isSaving = false;
  success = false;
  errorMessage = '';

  @Inject
  private decanoFacultadService: () => DecanoFacultadService;

  @Inject
  private alertService: () => AlertService;

  public open(item: any): void {
    this.item = item;
    this.fechaHasta = new Date().toISOString().slice(0, 10);
    this.success = false;
    this.errorMessage = '';
    this.isSaving = false;
    (this.$refs.modal as any).show();
  }

  public confirm(): void {
    this.errorMessage = '';
    this.success = false;
    if (!this.fechaHasta) {
      this.errorMessage = this.$t('decanoFacultad.cerrar.fechaRequerida').toString();
      return;
    }
    if (!this.item || !this.item.facultad || !this.item.facultad.id) {
      this.errorMessage = this.$t('decanoFacultad.cerrar.facultadNoEncontrada').toString();
      return;
    }
    this.isSaving = true;
    this.decanoFacultadService()
      .cerrarVigencia(this.item.facultad.id, this.fechaHasta)
      .then(() => {
        this.isSaving = false;
        this.success = true;
        this.alertService().success(
          this.$t('decanoFacultad.cerrar.noticeSuccess', {
            login: this.item.user.login,
            fecha: this.fechaHasta,
          }).toString()
        );
        (this.$emit as any)('closed');
        setTimeout(() => {
          (this.$refs.modal as any).hide();
          this.item = null;
        }, 1200);
      })
      .catch(error => {
        this.isSaving = false;
        this.errorMessage = this.alertService().showHttpError(this, error) as string;
      });
  }
}
