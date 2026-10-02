import path from 'path';
import { defineConfig } from 'vitest/config';
import vue from '@vitejs/plugin-vue2';

export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      vue$: 'vue/dist/vue.esm.js',
      '@': path.resolve(__dirname, 'src/main/webapp/app'),
    },
  },
  test: {
    globals: true,
    environment: 'jsdom',
    include: ['src/test/javascript/spec/**/*.spec.ts'],
    css: false,
    reporters: ['default', 'junit'],
    outputFile: { junit: 'target/test-results/vitest/TESTS-results-vitest.xml' },
  },
});
