import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IMenu, MenuBar, MenuChildren } from '@/shared/model/menu.model';

const baseApiUrl = 'api/menus';

export default class MenuService {
  public all(): Promise<MenuBar[]> {
    return new Promise<MenuBar[]>(resolve => {
      const paginationQuery = {
        page: 0,
        size: 100,
        sort: ['id,asc'],
      };
      this.retrieveNoPage().then(res => {
        const menus: IMenu[] = res.data;
        const parent: MenuBar[] = [];
        menus.map(menu => {
          if (!menu.menuPadreId) {
            delete menu.menuPadreNombre;
            const children: MenuChildren[] = [];
            parent.push({
              ...menu,
            });
          }
        });
        parent.map(par => {
          par.children = [];
          menus.map(menu => {
            if (menu.menuPadreId === par.id) {
              par.children.push(menu);
            }
          });
        });
        resolve(parent);
      });
    });
  }
  //////////////////
  public allRoles(rol?: any): Promise<MenuBar[]> {
    return new Promise<MenuBar[]>(resolve => {
      this.retrieveMenusRoles(rol).then(res => {
        const menus: IMenu[] = res.data;
        const parent: MenuBar[] = [];
        menus.map(menu => {
          if (!menu.menuPadreId) {
            delete menu.menuPadreNombre;
            const children: MenuChildren[] = [];
            parent.push({
              ...menu,
            });
          }
        });
        parent.map(par => {
          par.children = [];
          menus.map(menu => {
            if (menu.menuPadreId === par.id) {
              par.children.push(menu);
            }
          });
        });
        resolve(parent);
      });
    });
  }

  public find(id: number): Promise<IMenu> {
    return new Promise<IMenu>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${id}`)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }

  public retrieve(paginationQuery?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrl + `?${buildPaginationQueryOpts(paginationQuery)}`)
        .then(function (res) {
          resolve(res);
        })
        .catch(reject);
    });
  }
  public retrieveNoPage(): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(`api/menus-no-page`)
        .then(function (res) {
          resolve(res);
        })
        .catch(reject);
    });
  }

  public retrieveMenusRol(rol?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(`api/menus-rol/${rol}`)
        .then(function (res) {
          resolve(res);
        })
        .catch(reject);
    });
  }
  //retorna los menus con los roles que se le pasa en rol
  //estos roles estan separados con espacios
  //en MenuRes
  public retrieveMenusRoles(rol?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(`api/menus-roles/${rol}`)
        .then(function (res) {
          resolve(res);
        })
        .catch(reject);
    });
  }

  public delete(id: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .delete(`${baseApiUrl}/${id}`)
        .then(function (res) {
          resolve(res);
        })
        .catch(reject);
    });
  }

  public create(entity: IMenu): Promise<IMenu> {
    return new Promise<IMenu>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}`, entity)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }

  public update(entity: IMenu): Promise<IMenu> {
    return new Promise<IMenu>((resolve, reject) => {
      axios
        .put(`${baseApiUrl}`, entity)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }
}
