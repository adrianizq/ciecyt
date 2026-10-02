import js from '@eslint/js';
import globals from 'globals';
import tseslint from 'typescript-eslint';
import vue from 'eslint-plugin-vue';
import prettier from 'eslint-config-prettier';

export default tseslint.config(
  { ignores: ['node_modules/**', 'target/**', 'record_academico/**', 'src/main/webapp/content/**', 'src/main/webapp/i18n/**'] },
  js.configs.recommended,
  ...tseslint.configs.recommended,
  ...vue.configs['flat/vue2-recommended'],
  {
    files: ['**/*.vue'],
    languageOptions: { parserOptions: { parser: tseslint.parser } },
  },
  {
    languageOptions: {
      globals: { ...globals.browser, ...globals.node, ...globals.mocha },
    },
    rules: {
      // estilo: ya lo lleva prettier y reescribir plantillas solo genera ruido
      'vue/attributes-order': 'off',
      'vue/v-on-style': 'off',
      'vue/v-bind-style': 'off',
      'vue/first-attribute-linebreak': 'off',
      'vue/component-definition-name-casing': 'off',
      'vue/one-component-per-file': 'off',
      'vue/attribute-hyphenation': 'off',
      'vue/this-in-template': 'off',
      'vue/order-in-components': 'off',
      'vue/no-template-shadow': 'off',
      'vue/no-v-html': 'off',
      // el patrón de i18n de JHipster deja el texto inglés de respaldo dentro
      // del elemento para que v-text/v-html lo sobrescriba al montar
      'vue/no-child-content': 'off',
      'vue/no-v-text-v-html-on-component': 'off',
      'vue/multi-word-component-names': 'off',
      '@typescript-eslint/no-explicit-any': 'off',
      '@typescript-eslint/no-wrapper-object-types': 'off',
      '@typescript-eslint/no-empty-object-type': 'off',
      'no-undef': 'off',
      'no-empty': ['error', { allowEmptyCatch: true }],
      '@typescript-eslint/no-unused-vars': ['warn', { args: 'none', caughtErrors: 'none', varsIgnorePattern: '^_' }],
    },
  },
  prettier
);
