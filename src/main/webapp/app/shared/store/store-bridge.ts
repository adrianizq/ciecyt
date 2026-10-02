import { Base } from 'vue-facing-decorator';
import type { Store } from 'vuex';

/**
 * vue-facing-decorator construye la muestra de `data()` con `new cons()`, lejos de
 * cualquier instancia montada, de modo que los inicializadores de campo que leen
 * `this.$store` no ven `app.config.globalProperties`. Este puente expone la ultima
 * tienda creada/montada para que esa muestra pueda leerla; la instancia real sigue
 * resolviendo `$store` a traves de las propiedades globales de la app.
 */
let bridgeStore: Store<any> | null = null;

export function setStoreBridge(store: Store<any> | null): void {
  bridgeStore = store;
}

Object.defineProperty(Base.prototype, '$store', {
  configurable: true,
  get(): any {
    return bridgeStore ?? { getters: {} };
  },
});
