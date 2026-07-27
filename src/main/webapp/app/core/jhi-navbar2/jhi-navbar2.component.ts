import { Component, Inject, Vue } from 'vue-property-decorator';

import MenuService from '@/entities/menu/menu.service';
import { IMenu, MenuBar } from '@/shared/model/menu.model';

@Component
export default class JhiNavbar2 extends Vue {
  @Inject('menuService')
  private menuService: () => MenuService;

  ///public version = VERSION ? 'v' + VERSION : '';
  public version = 'Software para la Gestión de los Trabajos de Grado';
  private currentLanguage = this.$store.getters.currentLanguage;
  private languages: any = this.$store.getters.languages;
  public menus2: MenuBar[] = [];

  created() {
    this.menuService()
      .all()
      .then(res => {
        this.menus2 = res;
      });
  }

  public subIsActive(input) {
    const paths = Array.isArray(input) ? input : [input];
    return paths.some(path => {
      return this.$route.path.indexOf(path) === 0; // current path starts with this path string
    });
  }

  public isUrl(url: string): boolean {
    if (url.indexOf('()') === -1) {
      return true;
    }

    return false;
  }

  public actionMenu(callback: string): void {
    callback = callback.replace('()', '');
    this[callback]();
  }

  public isActiveLanguage(key: string): boolean {
    return key === this.$store.getters.currentLanguage;
  }

  public get authenticated(): boolean {
    return this.$store.getters.authenticated;
  }
}
