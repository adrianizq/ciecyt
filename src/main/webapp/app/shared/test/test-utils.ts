import { mount as vtuMount, shallowMount as vtuShallowMount } from '@vue/test-utils';
import type { VueWrapper } from '@vue/test-utils';

import { setStoreBridge } from '@/shared/store/store-bridge';

export * from '@vue/test-utils';

export type Wrapper<V> = VueWrapper<any> & { vm: V };

interface LocalVueRecorder {
  __rec: {
    plugins: any[];
    components: Record<string, any>;
    directives: Record<string, any>;
    mixins: any[];
  };
  use(plugin: any, options?: any): void;
  component(name: string, definition: any): void;
  directive(name: string, definition: any): void;
  mixin(mixin: any): void;
}

/**
 * Vue 3 no tiene "localVue": las personalizaciones se declaran aqui y luego se
 * vuelcan en `global` al montar.
 */
export function createLocalVue(): LocalVueRecorder {
  const rec = {
    plugins: [] as any[],
    components: {} as Record<string, any>,
    directives: {} as Record<string, any>,
    mixins: [] as any[],
  };
  return {
    __rec: rec,
    use(plugin: any) {
      rec.plugins.push(plugin);
    },
    component(name: string, definition: any) {
      rec.components[name] = definition;
    },
    directive(name: string, definition: any) {
      rec.directives[name] = definition;
    },
    mixin(mixin: any) {
      rec.mixins.push(mixin);
    },
  };
}

function asArray(value: any): any[] {
  if (value === undefined || value === null) return [];
  return Array.isArray(value) ? value : [value];
}

function normalize(options: Record<string, any> | undefined): Record<string, any> {
  if (!options) return {};
  const {
    localVue,
    i18n,
    store,
    router,
    plugins,
    provide,
    stubs,
    mocks,
    components,
    directives,
    mixins,
    config,
    propsData,
    sync,
    ...rest
  } = options;

  const global: Record<string, any> = {};
  const gPlugins: any[] = [...asArray(plugins)];

  const rec = localVue && localVue.__rec;
  if (rec) {
    gPlugins.push(...rec.plugins);
    global.components = { ...rec.components };
    global.directives = { ...rec.directives };
    if (rec.mixins.length) global.mixins = [...rec.mixins];
  }

  if (i18n) gPlugins.push(i18n);
  if (store) {
    gPlugins.push(store);
    setStoreBridge(store);
  }
  if (router) gPlugins.push(router);
  if (gPlugins.length) global.plugins = gPlugins;

  if (provide) global.provide = provide;
  if (stubs) global.stubs = stubs;
  if (mocks) global.mocks = mocks;
  if (components) global.components = { ...global.components, ...components };
  if (directives) global.directives = { ...global.directives, ...directives };
  if (mixins) global.mixins = [...(global.mixins || []), ...asArray(mixins)];
  if (config) global.config = { ...global.config, ...config };

  const normalized: Record<string, any> = { ...rest, global };
  if (propsData !== undefined && normalized.props === undefined) {
    normalized.props = propsData;
  }
  delete normalized.sync;
  return normalized;
}

export function mount<V = any>(component: any, options?: Record<string, any>): Wrapper<V> {
  return vtuMount(component, normalize(options)) as any;
}

export function shallowMount<V = any>(component: any, options?: Record<string, any>): Wrapper<V> {
  return vtuShallowMount(component, normalize(options)) as any;
}
