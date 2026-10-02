<template>
  <input v-bind="$attrs" type="date" :value="modelValue" @input="onInput" />
</template>

<script lang="ts">
import { Component, Emit, Prop, Vue } from 'vue-facing-decorator';

@Component({ inheritAttrs: false })
export default class FormDatepicker extends Vue {
  @Prop() modelValue: string;

  @Emit('update:modelValue')
  onInput(event: Event): string {
    return (event.target as HTMLInputElement).value;
  }
}
</script>
