<template>
  <div class="ciecyt-file-upload">
    <label :for="inputId" class="form-label visually-hidden">
      {{ label }}
    </label>
    <input
      :id="inputId"
      type="file"
      class="form-control"
      :accept="accept"
      :aria-describedby="error ? errorId : (helpId || undefined)"
      :aria-invalid="!!error"
      :disabled="disabled"
      @change="onChange"
    />
    <small v-if="help && !error" :id="helpId" class="form-text text-muted">
      {{ help }}
    </small>
    <div v-if="error" :id="errorId" class="invalid-feedback d-block" role="alert">
      {{ error }}
    </div>
    <div v-else-if="file" class="file-selected mt-1">
      <small class="text-muted">
        {{ file.name }} ({{ formattedSize }})
      </small>
      <button
        v-if="clearable"
        type="button"
        class="btn btn-link btn-sm p-0 ms-2"
        :aria-label="`Quitar ${file.name}`"
        @click="clear"
      >
        Quitar
      </button>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue, Prop } from 'vue-facing-decorator';

const MAX_BYTES_DEFAULT = 20 * 1024 * 1024; // 20 MB

@Component
export default class FileUpload extends Vue {
  @Prop({ default: 'Adjuntar archivo' })
  label: string;
  @Prop({ default: '*/*' })
  accept: string;
  @Prop({ default: MAX_BYTES_DEFAULT })
  maxBytes: number;
  @Prop({ default: false })
  disabled: boolean;
  @Prop({ default: false })
  clearable: boolean;
  @Prop({ default: '' })
  help: string;

  file: File | null = null;
  error: string = '';

  get inputId(): string {
    return 'file-upload-' + this._uid;
  }
  get errorId(): string {
    return 'file-upload-error-' + this._uid;
  }
  get helpId(): string {
    return 'file-upload-help-' + this._uid;
  }
  get formattedSize(): string {
    if (!this.file) return '';
    const bytes = this.file.size;
    if (bytes < 1024) return bytes + ' B';
    if (bytes < 1024 * 1024) return (bytes / 1024).toFixed(1) + ' KB';
    return (bytes / (1024 * 1024)).toFixed(1) + ' MB';
  }

  onChange(event: Event): void {
    const target = event.target as HTMLInputElement;
    const file = target.files && target.files[0] ? target.files[0] : null;
    this.error = '';
    if (!file) {
      this.file = null;
      this.$emit('change', null);
      return;
    }
    if (this.accept && this.accept !== '*/*') {
      const allowed = this.accept.split(',').map(s => s.trim().toLowerCase());
      const ext = '.' + file.name.split('.').pop().toLowerCase();
      const mime = (file.type || '').toLowerCase();
      const matches = allowed.some(pattern => {
        if (pattern.startsWith('.')) return ext === pattern;
        if (pattern.endsWith('/*')) return mime.startsWith(pattern.replace('/*', '/'));
        return mime === pattern;
      });
      if (!matches) {
        this.error = `Tipo de archivo no permitido. Solo se aceptan: ${this.accept}.`;
        target.value = '';
        this.file = null;
        this.$emit('change', null, this.error);
        return;
      }
    }
    if (this.maxBytes && file.size > this.maxBytes) {
      const maxMb = (this.maxBytes / (1024 * 1024)).toFixed(0);
      this.error = `El archivo es demasiado grande. Tamaño máximo permitido: ${maxMb} MB.`;
      target.value = '';
      this.file = null;
      this.$emit('change', null, this.error);
      return;
    }
    this.file = file;
    this.$emit('change', file);
  }

  clear(): void {
    this.file = null;
    this.error = '';
    const input = document.getElementById(this.inputId) as HTMLInputElement | null;
    if (input) input.value = '';
    this.$emit('change', null);
  }
}
</script>

<style scoped>
.ciecyt-file-upload {
  display: inline-block;
  width: 100%;
}
.file-selected {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}
</style>
