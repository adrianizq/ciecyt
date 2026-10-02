import { Component, Vue, Inject } from 'vue-facing-decorator';
import { mixins } from 'vue-facing-decorator';
import LogsService from './logs.service';
import { filterBy as filterByCollection, orderBy as orderByCollection } from '@/shared/filter/filter';

@Component({})
export default class JhiLogs extends Vue {
  public filterBy(collection: any, search: any, ...keys: string[]): any {
    return filterByCollection(collection, search, ...keys);
  }
  public orderBy(collection: any, ...args: any[]): any[] {
    return orderByCollection(collection, ...args);
  }

  @Inject private logsService: () => LogsService;
  private loggers: any[] = [];
  public filtered = '';
  public orderProp = 'name';
  public reverse = false;

  public mounted(): void {
    this.init();
  }

  public init(): void {
    this.logsService()
      .findAll()
      .then(response => {
        this.extractLoggers(response);
      });
  }

  public updateLevel(name, level): void {
    this.logsService()
      .changeLevel(name, level)
      .then(() => {
        this.init();
      });
  }

  public changeOrder(orderProp): void {
    this.orderProp = orderProp;
    this.reverse = !this.reverse;
  }

  private extractLoggers(response) {
    this.loggers = [];
    if (response.data) {
      for (const key of Object.keys(response.data.loggers)) {
        const logger = response.data.loggers[key];
        this.loggers.push({ name: key, level: logger.effectiveLevel });
      }
    }
  }
}
