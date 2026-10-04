<template>
  <b-modal
    ref="modal"
    id="resetPasswordModal"
    :title="$t('userManagement.resetPassword.title', { login: targetLogin })"
    :ok-title="$t('userManagement.resetPassword.confirm')"
    :cancel-title="$t('entity.action.cancel')"
    :busy="isSaving"
    @ok.prevent="confirmReset"
    @cancel="resetForm"
    @close="resetForm"
  >
    <div data-cy="reset-password-modal-body">
      <p v-html="$t('userManagement.resetPassword.body', { login: targetLogin })"></p>

      <div class="mb-3 form-group">
        <label class="form-control-label" for="newPassword" v-text="$t('global.form[\'newpassword.label\']')"></label>
        <input
          type="password"
          class="form-control"
          id="newPassword"
          name="newPassword"
          data-cy="reset-password-new"
          minlength="4"
          maxlength="100"
          v-model="newPassword"
        />
      </div>

      <div class="mb-3 form-group">
        <label class="form-control-label" for="confirmPassword" v-text="$t('global.form[\'confirmpassword.label\']')"></label>
        <input
          type="password"
          class="form-control"
          id="newPasswordConfirm"
          name="newPasswordConfirm"
          data-cy="reset-password-confirm"
          minlength="4"
          maxlength="100"
          v-model="confirmPassword"
        />
      </div>

      <div class="alert alert-warning" role="alert" v-if="doNotMatch" v-text="$t('global.messages.error.dontmatch')"></div>
      <div class="alert alert-danger" role="alert" v-if="error" v-text="$t('userManagement.resetPassword.error')"></div>
    </div>
  </b-modal>
</template>

<script lang="ts" src="./reset-password-dialog.component.ts"></script>
