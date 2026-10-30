<template>
  <b-nav-item-dropdown
    v-if="authenticated"
    right
    no-caret
    class="notif-dropdown"
    menu-class="notif-dropdown-menu"
    @show="cargarNotificaciones"
  >
    <template #button-content>
      <span class="notif-bell" title="Notificaciones" aria-label="Notificaciones">
        <font-awesome-icon icon="bell" />
        <span v-if="notificacionesNoLeidas.length > 0" class="notif-badge">
          {{ notificacionesNoLeidas.length > 9 ? '9+' : notificacionesNoLeidas.length }}
        </span>
      </span>
    </template>
    <div class="notif-header">
      <span>Notificaciones</span>
      <b-button
        v-if="notificacionesNoLeidas.length > 0"
        size="sm"
        variant="link"
        class="notif-mark-all"
        @click.stop="marcarTodasLeidas"
      >
        Marcar todas como leídas
      </b-button>
    </div>
    <div v-if="cargandoNotificaciones && notificacionesNoLeidas.length === 0" class="notif-empty">Cargando...</div>
    <div v-else-if="notificacionesNoLeidas.length === 0" class="notif-empty">No tiene notificaciones pendientes.</div>
    <b-dropdown-item
      v-for="notificacion in notificacionesNoLeidas"
      :key="notificacion.id"
      class="notif-item"
      @click="abrirNotificacion(notificacion)"
    >
      <div class="notif-titulo">{{ notificacion.titulo }}</div>
      <div v-if="notificacion.mensaje" class="notif-mensaje">{{ notificacion.mensaje }}</div>
      <div v-if="notificacion.fechaCreacion" class="notif-fecha">{{ formatearFecha(notificacion.fechaCreacion) }}</div>
    </b-dropdown-item>
  </b-nav-item-dropdown>
</template>

<script lang="ts" src="./notificacion-bell.component.ts"></script>

<style scoped>
.notif-bell {
  position: relative;
  display: inline-flex;
  align-items: center;
  padding: 0.25rem 0.35rem;
  color: #fff;
  font-size: 1.1rem;
  cursor: pointer;
}

.notif-bell:hover,
.notif-bell:focus {
  color: #c4a94d;
}

.notif-badge {
  position: absolute;
  top: -0.15rem;
  right: -0.35rem;
  min-width: 1.05rem;
  height: 1.05rem;
  padding: 0 0.25rem;
  border-radius: 999px;
  background: #c4a94d;
  color: #003366;
  font-size: 0.65rem;
  font-weight: 700;
  line-height: 1.05rem;
  text-align: center;
}

:deep(.notif-dropdown-menu) {
  min-width: 20rem;
  max-width: calc(100vw - 2rem);
  padding: 0;
  overflow: hidden;
}

.notif-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 0.5rem;
  padding: 0.65rem 0.85rem;
  border-bottom: 1px solid #e3e8ef;
  background: #f6f8fb;
  color: #003366;
  font-weight: 600;
}

.notif-mark-all {
  padding: 0;
  font-size: 0.75rem;
  text-decoration: none;
  white-space: nowrap;
}

.notif-empty {
  padding: 1rem 0.85rem;
  color: #6b7280;
  font-size: 0.85rem;
  text-align: center;
}

:deep(.notif-item) {
  display: block;
  padding: 0.65rem 0.85rem;
  border-bottom: 1px solid #eef1f5;
  white-space: normal;
  cursor: pointer;
}

:deep(.notif-item:last-child) {
  border-bottom: 0;
}

.notif-titulo {
  color: #003366;
  font-size: 0.85rem;
  font-weight: 600;
  line-height: 1.25;
}

.notif-mensaje {
  margin-top: 0.2rem;
  color: #374151;
  font-size: 0.78rem;
  line-height: 1.3;
}

.notif-fecha {
  margin-top: 0.25rem;
  color: #9ca3af;
  font-size: 0.7rem;
}
</style>
