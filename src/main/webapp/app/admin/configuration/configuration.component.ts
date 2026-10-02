import { Component, Vue, Inject } from 'vue-property-decorator';
import { mixins } from 'vue-class-component';
import ConfigurationService from './configuration.service';
import { filterBy as filterByCollection, orderBy as orderByCollection } from '@/shared/filter/filter';

@Component({})
export default class JhiConfiguration extends Vue {
  public filterBy(collection: any, search: any, ...keys: string[]): any {
    return filterByCollection(collection, search, ...keys);
  }
  public orderBy(collection: any, ...args: any[]): any[] {
    return orderByCollection(collection, ...args);
  }

  public orderProp = 'prefix';
  public reverse = false;
  public allConfiguration: any = false;
  public configuration: any = false;
  public configKeys: any[] = [];
  public filtered = '';
  @Inject('configurationService') private configurationService: () => ConfigurationService;

  public mounted(): void {
    this.init();
  }

  public init(): void {
    this.configurationService()
      .loadConfiguration()
      .then(res => {
        this.configuration = res;

        for (const config of this.configuration) {
          if (config.properties !== undefined) {
            this.configKeys.push(Object.keys(config.properties));
          }
        }
      });

    this.configurationService()
      .loadEnvConfiguration()
      .then(res => {
        this.allConfiguration = res;
      });
  }

  public changeOrder(prop): void {
    this.orderProp = prop;
    this.reverse = !this.reverse;
  }

  public keys(dict: any): string[] {
    return dict === undefined ? [] : Object.keys(dict);
  }
}
