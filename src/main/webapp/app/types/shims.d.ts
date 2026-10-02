import type { Store } from 'vuex';

declare module 'vue' {
  interface ComponentCustomProperties {
    readonly $store: Store<any>;
  }
}

export {};
