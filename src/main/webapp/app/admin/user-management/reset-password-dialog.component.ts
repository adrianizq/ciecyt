import { Component, Inject, Vue } from 'vue-facing-decorator';
import UserManagementService from '@/admin/user-management/user-management.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class ResetPasswordDialog extends Vue {
  @Inject({ from: 'userService' })
  private userManagementService: () => UserManagementService;

  @Inject
  private alertService: () => AlertService;

  targetLogin = '';
  newPassword = '';
  confirmPassword = '';
  isSaving = false;
  success = false;
  errorMessage = '';
  doNotMatch = false;

  open(login: string): void {
    this.resetForm();
    this.targetLogin = login;
    (this.$refs.modal as any).show();
  }

  resetForm(): void {
    this.newPassword = '';
    this.confirmPassword = '';
    this.success = false;
    this.errorMessage = '';
    this.doNotMatch = false;
    this.isSaving = false;
  }

  confirmReset(): void {
    this.success = false;
    this.errorMessage = '';

    if (this.newPassword !== this.confirmPassword) {
      this.doNotMatch = true;
      return;
    }
    if (!this.newPassword || this.newPassword.length < 4) {
      this.errorMessage = this.$t('userManagement.resetPassword.shortPassword').toString();
      return;
    }
    this.doNotMatch = false;
    this.isSaving = true;

    this.userManagementService()
      .resetPassword(this.targetLogin, this.newPassword)
      .then(() => {
        this.isSaving = false;
        this.success = true;
        this.alertService().success(this.$t('userManagement.resetPassword.noticeSuccess', { login: this.targetLogin }).toString());
      })
      .catch(error => {
        this.isSaving = false;
        this.errorMessage = this.$t('userManagement.resetPassword.errorWithDetail', {
          detail: this.extractDetail(error),
        }).toString();
        this.alertService().error(this.errorMessage);
      });
  }

  private extractDetail(error: any): string {
    if (!error) {
      return 'unknown';
    }
    const data = error.response?.data ?? error.data ?? {};
    return data.message || data.detail || data.title || error.message || 'unknown';
  }
}
