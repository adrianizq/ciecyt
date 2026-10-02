import axios from 'axios';
import type { I18n, VueI18n } from 'vue-i18n';
import { Store } from 'vuex';

export default class TranslationService {
  private store: Store<{}>;
  private i18n: VueI18n;

  constructor(store: Store<{}>, i18n: I18n) {
    this.store = store;
    this.i18n = i18n.global as VueI18n;
  }

  public refreshTranslation(newLanguage: string) {
    let currentLanguage = this.store.getters.currentLanguage;
    currentLanguage = newLanguage ? newLanguage : 'es';
    if (this.i18n && !this.i18n.messages[currentLanguage]) {
      this.i18n.setLocaleMessage(currentLanguage, {});
      axios.get('i18n/' + currentLanguage + '.json').then(res => {
        if (res.data) {
          this.i18n.setLocaleMessage(currentLanguage, res.data);
          this.i18n.locale = currentLanguage;
          this.store.commit('currentLanguage', currentLanguage);
        }
      });
    } else if (this.i18n) {
      this.i18n.locale = currentLanguage;
      this.store.commit('currentLanguage', currentLanguage);
    }
  }
}
