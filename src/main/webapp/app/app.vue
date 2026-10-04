<template>
  <div id="app">
    <a class="skip-link visually-hidden-focusable" href="#contenido">Saltar al contenido principal</a>
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
      <main id="contenido" tabindex="-1" class="jh-card">
        <router-view></router-view>
      </main>
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
    .skip-link:focus {
        position: fixed;
        top: 0.5rem;
        left: 0.5rem;
        z-index: 1100;
        padding: 0.5rem 0.75rem;
        background: #003366;
        color: #fff;
        border-radius: 0.25rem;
        text-decoration: none;
    }
    #contenido:focus {
        outline: none;
    }
</style>
