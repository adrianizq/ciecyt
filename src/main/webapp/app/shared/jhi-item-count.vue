<template>
  <div class="info jhi-item-count">
    <span v-if="i18nEnabled">
      {{ $t('global.item-count', { first, second, total }) }}
    </span>
    <span v-if="!i18nEnabled">
      Showing {{ first }} - {{ second }} of {{ total }} items.
    </span>
  </div>
</template>

<script lang="ts" src="./jhi-item-count.component.ts">
</script>
