<template>
  <div class="home">
    <!-- Header -->
    <div class="ciecyt-header text-center">
      <h1>CIECYT-ITP</h1>
      <div class="accent-line mx-auto"></div>
      <h2>Sistema de Gestión de Trabajos de Grado</h2>
    </div>

    <!-- Authenticated: Dashboard -->
    <div v-if="authenticated">
      <div class="row mb-4">
        <div class="col-12">
          <div class="d-flex align-items-center justify-content-between">
            <div>
              <h4 class="mb-0" style="color:#1a2332; font-weight:600;">Modalidades de Grado</h4>
              <p class="text-muted mb-0" style="font-size:0.9rem;">Seleccione una modalidad para iniciar o continuar su propuesta</p>
            </div>
            <div class="text-right">
              <small class="text-muted">Bienvenido,</small><br>
              <strong style="color:#003366;">{{ username }}</strong>
            </div>
          </div>
        </div>
      </div>

      <!-- Modality Cards Grid -->
      <div class="row">
        <div class="col-md-4 col-lg-4 mb-4" v-for="modalidad in modalidades" :key="modalidad.id">
          <div class="card modalidad-card" @click="navegarModalidad(modalidad)">
            <div class="card-body">
              <div class="card-icon" :style="{ backgroundColor: modalidad.color }">
                <font-awesome-icon :icon="modalidad.icono" />
              </div>
              <h5 class="card-title">{{ modalidad.nombre }}</h5>
              <p class="card-text">{{ modalidad.descripcion }}</p>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Not authenticated -->
    <div v-if="!authenticated" class="text-center mt-5">
      <div class="alert alert-warning d-inline-block" style="border-radius:0.75rem;">
        <span v-text="$t('global.messages.info.authenticated.prefix')">If you want to </span>
        <a class="alert-link" v-on:click="openLogin()" v-text="$t('global.messages.info.authenticated.link')">sign in</a>
        <span v-html="$t('global.messages.info.authenticated.suffix')"></span>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./home.component.ts">
</script>
