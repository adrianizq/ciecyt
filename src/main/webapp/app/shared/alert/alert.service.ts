import { Store } from 'vuex';

export default class JhiAlertService {
  [x: string]: any;
  private store: Store<{}>;

  constructor(store: Store<{}>) {
    this.store = store;
    this.store.commit('initAlert');
  }

  public showAlert(alertMessage: any, alertType = 'info') {
    this.store.commit('setAlertType', alertType);
    this.store.commit('setAlertMessage', alertMessage);
  }

  public success(alertMessage: any) {
    this.showAlert(alertMessage, 'success');
  }

  public error(alertMessage: any) {
    this.showAlert(alertMessage, 'danger');
  }

  // Varias paginas llamaban este metodo (JHipster lo tenia en versiones anteriores) y al no
  // existir el error se perdia en la consola en lugar de mostrarse al usuario.
  public showHttpError(component?: any, error?: any) {
    this.showAlert(this.mensajeDeError(error), 'danger');
  }

  private mensajeDeError(error: any): string {
    if (!error) {
      return 'No fue posible completar la operación. Intente de nuevo.';
    }
    const data = error.data;
    const detalle = data && (data.message || data.detail || data.title);
    if (typeof error.status === 'number') {
      if (error.status === 401) {
        return 'Su sesión ha expirado. Vuelva a iniciar sesión para continuar.';
      }
      if (error.status === 403) {
        return 'No tiene permisos para realizar esta acción.';
      }
      if (error.status === 404) {
        return 'El recurso solicitado ya no está disponible.';
      }
      if (error.status >= 500) {
        return 'Error del servidor. Intente de nuevo en unos momentos.';
      }
    }
    return detalle ? String(detalle) : 'No fue posible completar la operación. Intente de nuevo.';
  }

  public countDownChanged(dismissCountDown: number) {
    this.store.commit('countDownChanged', dismissCountDown);
  }
}
