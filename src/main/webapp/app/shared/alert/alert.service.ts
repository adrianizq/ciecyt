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
    // Axios envuelve la respuesta real en error.response; cuando la peticion falla antes de
    // llegar al servidor (red caida, CORS, timeout) error.response es undefined.
    const response = error.response || {};
    const data = error.data || response.data || {};
    const detalle = data.message || data.detail || data.title || error.message;
    const status = typeof response.status === 'number' ? response.status : error.status;
    if (typeof status === 'number') {
      if (status === 0) {
        return 'No fue posible comunicarse con el servidor. Verifique su conexión e intente de nuevo.';
      }
      if (status === 401) {
        return 'Su sesión ha expirado. Vuelva a iniciar sesión para continuar.';
      }
      if (status === 403) {
        return 'No tiene permisos para realizar esta acción.';
      }
      if (status === 404) {
        return 'El recurso solicitado ya no está disponible.';
      }
      if (status === 413) {
        return 'El archivo enviado supera el tamaño máximo permitido.';
      }
      if (status === 422 || status === 400) {
        return detalle ? String(detalle) : 'Los datos enviados no son válidos.';
      }
      if (status >= 500) {
        return 'Error del servidor. Intente de nuevo en unos momentos.';
      }
    }
    return detalle ? String(detalle) : 'No fue posible completar la operación. Intente de nuevo.';
  }

  public countDownChanged(dismissCountDown: number) {
    this.store.commit('countDownChanged', dismissCountDown);
  }
}
