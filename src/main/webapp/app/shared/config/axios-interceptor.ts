import axios from 'axios';

import { SERVER_API_URL } from '@/constants';

// 2 minutos: tiempo suficiente para subir adjuntos grandes sin dejar al usuario
// esperando los ~17 minutos que tomaba antes (1.000.000 ms).
const TIMEOUT = 120000;
const onRequestSuccess = config => {
  const token = localStorage.getItem('jhi-authenticationToken') || sessionStorage.getItem('jhi-authenticationToken');
  if (token) {
    if (!config.headers) {
      config.headers = {};
    }
    config.headers.Authorization = `Bearer ${token}`;
  }
  config.timeout = TIMEOUT;
  config.url = `${SERVER_API_URL}${config.url}`;
  return config;
};
const setupAxiosInterceptors = onUnauthenticated => {
  const onResponseError = err => {
    // err.response no existe cuando la peticion falla antes de llegar al servidor
    // (red caida, CORS, timeout): en ese caso status queda en 0 y no debe romper aqui.
    const status = err && err.response ? err.response.status : 0;
    if (status === 403 || status === 401) {
      onUnauthenticated(status, err);
    }
    return Promise.reject(err);
  };
  if (axios.interceptors) {
    axios.interceptors.request.use(onRequestSuccess);
    axios.interceptors.response.use(res => res, onResponseError);
  }
};

export { onRequestSuccess, setupAxiosInterceptors };
