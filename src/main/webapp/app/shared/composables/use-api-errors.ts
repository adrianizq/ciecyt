import { inject } from 'vue';
import AlertService from '@/shared/alert/alert.service';

/**
 * Composable que centraliza como se muestran los errores de los servicios
 * al usuario final. Antes del Sprint 4 cada pagina repetia .catch() vacios
 * y `.catch(err => alertService().error('Error: ' + ...))` con texto tecnico.
 *
 * Uso (en setup() o composable interno):
 *   const { showHttpError, handle } = useApiErrors();
 *   service.delete(id)
 *     .then(() => ...)
 *     .catch(handle);            // equivalente a .catch(err => showHttpError(err))
 *
 *   async function guardar() {
 *     try { await service.save(); }
 *     catch (e) { showHttpError(e); }
 *   }
 *
 * showHttpError() reutiliza JhiAlertService.mensajeDeError para mapear 401,
 * 403, 404, 413, 422/400, 5xx y errores de red a mensajes en lenguaje natural.
 */
export function useApiErrors() {
  const alertService: AlertService | undefined = inject<AlertService>('alertService');

  function showHttpError(error: any): void {
    if (!alertService) {
      console.warn('useApiErrors: AlertService no inyectado; mostrando error por consola:', error);
      return;
    }
    try {
      alertService.showHttpError(null, error);
    } catch (serviceError) {
      console.warn('No se pudo mostrar el error al usuario:', serviceError, 'origen:', error);
    }
  }

  function handle(error: any): void {
    showHttpError(error);
  }

  return { showHttpError, handle };
}

/**
 * Envoltorio para promesas que normalmente no harian nada al fallar pero
 * deben avisar al usuario. Antes del Sprint 4 varios archivos usaban
 * .catch(() => {}) tragando errores silenciosamente.
 */
export async function safe(operation: Promise<unknown>, onError?: (err: any) => void): Promise<void> {
  try {
    await operation;
  } catch (err) {
    if (onError) {
      onError(err);
    } else {
      useApiErrors().showHttpError(err);
    }
  }
}
