<template>
  <b-modal
    ref="modal"
    id="resetPasswordModal"
    :title="$t('userManagement.resetPassword.title', { login: targetLogin })"
    :ok-title="isSaving
      ? $t('userManagement.resetPassword.saving')
      : success
        ? $t('userManagement.resetPassword.close')
        : $t('userManagement.resetPassword.confirm')"
    :cancel-title="success ? null : $t('entity.action.cancel')"
    :ok-disabled="isSaving || success"
    :busy="isSaving"
    :ok-only="success"
    @ok.prevent="confirmReset"
  >
    <div data-cy="reset-password-modal-body">
      <p v-html="$t('userManagement.resetPassword.body', { login: targetLogin })"></p>

      <template v-if="!success">
        <div class="mb-3 form-group">
          <label
            class="form-control-label"
            for="newPassword"
            v-text="$t('global.form[\'newpassword.label\']')"
          ></label>
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
          <label
            class="form-control-label"
            for="newPasswordConfirm"
            v-text="$t('global.form[\'confirmpassword.label\']')"
          ></label>
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

        <div class="alert alert-warning" role="alert" v-if="doNotMatch" data-cy="reset-password-mismatch" v-text="$t('global.messages.error.dontmatch')"></div>
      </template>

      <div class="alert alert-danger" role="alert" v-if="errorMessage" data-cy="reset-password-error">{{ errorMessage }}</div>
      <div class="alert alert-success" role="alert" v-if="success" data-cy="reset-password-success" v-html="$t('userManagement.resetPassword.success', { login: targetLogin })"></div>
    </div>
  </b-modal>
</template>

<script lang="ts" src="./reset-password-dialog.component.ts"></script>
