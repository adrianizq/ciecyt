import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IRolMenu } from '@/shared/model/rol-menu.model';

const baseApiUrl = 'api/rol-menus';

export default class RolMenuService {
  public fromRol(rol: string): Promise<IRolMenu[]> {
    return new Promise<IRolMenu[]>(resolve => {
      const paginationQuery = {
        page: 0,
        size: 100,
        sort: ['id,asc'],
      };

      this.retrieve(paginationQuery).then(res => {
        const rolMenu: IRolMenu[] = res.data;

        const response: IRolMenu[] = [];

        rolMenu.map(rm => {
          if (rm.authName === rol) {
            response[rm.rolMenuMenuId] = rm;
          }
        });

        resolve(response);
      });
    });
  }

  public find(id: number): Promise<IRolMenu> {
    return new Promise<IRolMenu>(resolve => {
      axios.get(`${baseApiUrl}/${id}`).then(function (res) {
        resolve(res.data);
      });
    });
  }

  public retrieve(paginationQuery?: any): Promise<any> {
    return new Promise<any>(resolve => {
      axios.get(baseApiUrl + `?${buildPaginationQueryOpts(paginationQuery)}`).then(function (res) {
        resolve(res);
      });
    });
  }

  public delete(id: number): Promise<any> {
    return new Promise<any>(resolve => {
      axios.delete(`${baseApiUrl}/${id}`).then(function (res) {
        resolve(res);
      });
    });
  }

  public create(entity: IRolMenu): Promise<IRolMenu> {
    return new Promise<IRolMenu>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function (res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IRolMenu): Promise<IRolMenu> {
    return new Promise<IRolMenu>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function (res) {
        resolve(res.data);
      });
    });
  }

  public batchUpdate(entities: IRolMenu[]): Promise<boolean> {
    return new Promise<boolean>(async resolve => {
      console.log(entities);
      for (const entity of entities) {
        if (entity.id) {
          await this.update(entity);
        } else {
          await this.create(entity);
        }
      }

      resolve(true);
    });
  }
}
