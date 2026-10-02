<template>
  <div id="app">
    <ribbon></ribbon>
    <div id="app-header">
      <jhi-navbar></jhi-navbar>
    </div>
    <div class="container-fluid" style="padding: 0 1.5rem;">
      <b-alert
        class="global-alert"
        :show="dismissCountDown"
        dismissible
        :variant="alertType"
        @close="countDownChanged(0)"
        @close-countdown="countDownChanged"
      >
        {{ textoAlerta }}
      </b-alert>
      <div class="jh-card">
        <router-view></router-view>
      </div>
      <b-modal id="login-page" v-model="loginModalVisible" no-footer lazy>
        <template #title>
          <span id="login-title" v-text="$t('login.title')"></span>
        </template>
        <login-form></login-form>
      </b-modal>
      <jhi-footer></jhi-footer>
    </div>
  </div>
</template>

<script lang="ts" src="./app.component.ts">
</script>

<style>
    .btn-switch {
        display: inline-grid;
    }
    .global-alert {
        position: sticky;
        top: 0;
        z-index: 1030;
        margin-top: 0.75rem;
    }
</style>
