import fs from 'fs';
import path from 'path';
import { fileURLToPath } from 'url';
import { defineConfig } from 'vitest/config';
import type { Plugin } from 'vitest/config';
import vue from '@vitejs/plugin-vue';

const here = path.dirname(fileURLToPath(import.meta.url));
const webapp = path.resolve(here, 'src/main/webapp');
const outDir = path.resolve(here, 'target/classes/static');

const staticPaths = ['content', 'favicon.ico', 'manifest.webapp', 'robots.txt'];
const languages = ['es', 'en'];

const proxyPaths = [
  '/api',
  '/services',
  '/management',
  '/swagger-resources',
  '/v2/api-docs',
  '/v3/api-docs',
  '/swagger-ui',
  '/h2-console',
  '/auth',
];

function walk(abs: string, rel: string, out: Array<{ rel: string; abs: string }>): void {
  if (fs.statSync(abs).isDirectory()) {
    for (const entry of fs.readdirSync(abs)) {
      walk(path.join(abs, entry), `${rel}/${entry}`, out);
    }
    return;
  }
  out.push({ rel, abs });
}

// vue-i18n 9 interpola con {nombre}; los mensajes de JHipster llegan con {{nombre}}.
function vueI18nInterpolation(value: unknown): unknown {
  if (typeof value === 'string') {
    return value.replace(/\{\{\s*([A-Za-z_$][\w$]*)\s*\}\}/g, '{$1}');
  }
  if (Array.isArray(value)) {
    return value.map(vueI18nInterpolation);
  }
  if (value && typeof value === 'object') {
    return Object.fromEntries(Object.entries(value as Record<string, unknown>).map(([k, v]) => [k, vueI18nInterpolation(v)]));
  }
  return value;
}

function deepMerge(target: Record<string, unknown>, source: Record<string, unknown>): Record<string, unknown> {
  for (const key of Object.keys(source)) {
    const sv = source[key];
    const tv = target[key];
    if (sv && typeof sv === 'object' && !Array.isArray(sv) && tv && typeof tv === 'object' && !Array.isArray(tv)) {
      target[key] = deepMerge({ ...(tv as Record<string, unknown>) }, sv as Record<string, unknown>);
    } else {
      target[key] = sv;
    }
  }
  return target;
}

function mergeLanguage(lang: string): string {
  const dir = path.join(webapp, 'i18n', lang);
  const merged: Record<string, unknown> = {};
  for (const file of fs
    .readdirSync(dir)
    .filter(name => name.endsWith('.json'))
    .sort()) {
    const parsed = JSON.parse(fs.readFileSync(path.join(dir, file), 'utf8')) as Record<string, unknown>;
    deepMerge(merged, parsed);
  }
  return JSON.stringify(vueI18nInterpolation(merged), null, 2);
}

function envDefine(): Plugin {
  let mode = 'development';
  let replacements: Array<[string, string]> = [];
  return {
    name: 'ciecyt:env-define',
    enforce: 'pre',
    configResolved(config) {
      mode = config.mode;
      const nodeEnv = mode === 'production' ? 'production' : mode;
      replacements = [
        ['process.env.NODE_ENV', JSON.stringify(nodeEnv)],
        ['process.env.SERVER_API_URL', JSON.stringify('')],
        ['process.env.BUILD_TIMESTAMP', JSON.stringify(String(Date.now()))],
        ['process.env.VERSION', JSON.stringify(process.env.APP_VERSION || 'UNKNOWN')],
      ];
    },
    transform(code, id) {
      if (mode === 'test' || process.env.VITEST) return null;
      const file = id.split('?')[0];
      if (!file.startsWith(webapp) || file.includes('node_modules')) return null;
      if (!code.includes('process.env.')) return null;
      let out = code;
      for (const [key, value] of replacements) {
        out = out.split(key).join(value);
      }
      return out === code ? null : { code: out, map: null };
    },
  };
}

function staticAssets(): Plugin {
  return {
    name: 'ciecyt:static-assets',
    apply: 'build',
    generateBundle() {
      const files: Array<{ rel: string; abs: string }> = [];
      for (const asset of staticPaths) {
        walk(path.join(webapp, asset), asset, files);
      }
      for (const { rel, abs } of files) {
        this.emitFile({ type: 'asset', fileName: rel, source: fs.readFileSync(abs) });
      }
      for (const lang of languages) {
        this.emitFile({ type: 'asset', fileName: `i18n/${lang}.json`, source: mergeLanguage(lang) });
      }
    },
  };
}

function languageBundles(): Plugin {
  return {
    name: 'ciecyt:language-bundles',
    configureServer(server) {
      server.middlewares.use((req, res, next) => {
        const match = /^\/i18n\/(es|en)\.json/.exec(req.url || '');
        if (!match) {
          next();
          return;
        }
        res.setHeader('Content-Type', 'application/json');
        res.end(mergeLanguage(match[1]));
      });
    },
  };
}

export default defineConfig(({ mode }) => ({
  root: webapp,
  publicDir: false,
  plugins: [vue(), envDefine(), languageBundles(), staticAssets()],
  define: {
    'process.env.NODE_ENV': JSON.stringify(mode === 'production' ? 'production' : mode),
    'process.env.SERVER_API_URL': JSON.stringify(''),
    'process.env.BUILD_TIMESTAMP': JSON.stringify(String(Date.now())),
    'process.env.VERSION': JSON.stringify(process.env.APP_VERSION || 'UNKNOWN'),
  },
  resolve: {
    extensions: ['.ts', '.js', '.vue', '.json', '.mjs'],
    alias: {
      vue$: 'vue/dist/vue.esm-bundler.js',
      '@': path.resolve(webapp, 'app'),
    },
  },
  css: {
    preprocessorOptions: {
      scss: {
        quietDeps: true,
        silenceDeprecations: ['import', 'global-builtin', 'color-functions', 'slash-div'],
      },
    },
  },
  server: {
    port: 9000,
    proxy: Object.fromEntries(proxyPaths.map(proxyPath => [proxyPath, { target: 'http://127.0.0.1:8080', changeOrigin: false }])),
  },
  build: {
    outDir,
    emptyOutDir: true,
    sourcemap: true,
  },
  test: {
    globals: true,
    environment: 'jsdom',
    include: [path.resolve(here, 'src/test/javascript/spec/**/*.spec.ts')],
    css: false,
    reporters: ['default', 'junit'],
    outputFile: { junit: path.resolve(here, 'target/test-results/vitest/TESTS-results-vitest.xml') },
  },
}));
