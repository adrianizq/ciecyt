import { Component, Inject, Vue } from 'vue-facing-decorator';
import UserManagementService from '@/admin/user-management/user-management.service';

@Component
export default class ResetPasswordDialog extends Vue {
  @Inject({ from: 'userService' })
  private userManagementService: () => UserManagementService;

  targetLogin = '';
  newPassword = '';
  confirmPassword = '';
  isSaving = false;
  error = false;
  doNotMatch = false;

  open(login: string): void {
    this.resetForm();
    this.targetLogin = login;
    (this.$refs.modal as any).show();
  }

  resetForm(): void {
    this.newPassword = '';
    this.confirmPassword = '';
    this.error = false;
    this.doNotMatch = false;
    this.isSaving = false;
  }

  confirmReset(): void {
    this.error = false;
    if (this.newPassword !== this.confirmPassword) {
      this.doNotMatch = true;
      return;
    }
    if (!this.newPassword || this.newPassword.length < 4) {
      this.error = true;
      return;
    }
    this.doNotMatch = false;
    this.isSaving = true;
    this.userManagementService()
      .resetPassword(this.targetLogin, this.newPassword)
      .then(() => {
        this.isSaving = false;
        (this.$refs.modal as any).hide();
      })
      .catch(() => {
        this.isSaving = false;
        this.error = true;
      });
  }
}
