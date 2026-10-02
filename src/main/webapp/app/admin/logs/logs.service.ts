import axios, { AxiosPromise } from 'axios';
import { Vue } from 'vue-facing-decorator';
import { Component } from 'vue-facing-decorator';

@Component
export default class LogsService extends Vue {
  public changeLevel(name: string, configuredLevel: string): AxiosPromise<any> {
    return axios.post('management/loggers/' + name, { configuredLevel });
  }

  public findAll(): AxiosPromise<any> {
    return axios.get('management/loggers');
  }
}
