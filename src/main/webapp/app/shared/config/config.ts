import Vuex from 'vuex';
import VueI18n from 'vue-i18n';
import JhiFormatter from './formatter';
import { setupAxiosInterceptors } from '@/shared/config/axios-interceptor';

import { library } from '@fortawesome/fontawesome-svg-core';
import { faSort } from '@fortawesome/free-solid-svg-icons/faSort';
import { faEye } from '@fortawesome/free-solid-svg-icons/faEye';
import { faSync } from '@fortawesome/free-solid-svg-icons/faSync';
import { faBan } from '@fortawesome/free-solid-svg-icons/faBan';
import { faTrash } from '@fortawesome/free-solid-svg-icons/faTrash';
import { faArrowLeft } from '@fortawesome/free-solid-svg-icons/faArrowLeft';
import { faSave } from '@fortawesome/free-solid-svg-icons/faSave';
import { faPlus } from '@fortawesome/free-solid-svg-icons/faPlus';
import { faPencilAlt } from '@fortawesome/free-solid-svg-icons/faPencilAlt';
import { faUser } from '@fortawesome/free-solid-svg-icons/faUser';
import { faHdd } from '@fortawesome/free-solid-svg-icons/faHdd';
import { faTachometerAlt } from '@fortawesome/free-solid-svg-icons/faTachometerAlt';
import { faHeart } from '@fortawesome/free-solid-svg-icons/faHeart';
import { faList } from '@fortawesome/free-solid-svg-icons/faList';
import { faTasks } from '@fortawesome/free-solid-svg-icons/faTasks';
import { faBook } from '@fortawesome/free-solid-svg-icons/faBook';
import { faLock } from '@fortawesome/free-solid-svg-icons/faLock';
import { faSignInAlt } from '@fortawesome/free-solid-svg-icons/faSignInAlt';
import { faSignOutAlt } from '@fortawesome/free-solid-svg-icons/faSignOutAlt';
import { faThList } from '@fortawesome/free-solid-svg-icons/faThList';
import { faUserPlus } from '@fortawesome/free-solid-svg-icons/faUserPlus';
import { faWrench } from '@fortawesome/free-solid-svg-icons/faWrench';
import { faAsterisk } from '@fortawesome/free-solid-svg-icons/faAsterisk';
import { faFlag } from '@fortawesome/free-solid-svg-icons/faFlag';
import { faBell } from '@fortawesome/free-solid-svg-icons/faBell';
import { faHome } from '@fortawesome/free-solid-svg-icons/faHome';
import { faTimesCircle } from '@fortawesome/free-solid-svg-icons/faTimesCircle';
import { faSearch } from '@fortawesome/free-solid-svg-icons/faSearch';
import { faRoad } from '@fortawesome/free-solid-svg-icons/faRoad';
import { faCloud } from '@fortawesome/free-solid-svg-icons/faCloud';
import { faBars } from '@fortawesome/free-solid-svg-icons/faBars';
import { faTimes } from '@fortawesome/free-solid-svg-icons/faTimes';
import { faPaperclip } from '@fortawesome/free-solid-svg-icons/faPaperclip';
import { faFileAlt } from '@fortawesome/free-solid-svg-icons/faFileAlt';
import { faDownload } from '@fortawesome/free-solid-svg-icons/faDownload';
import { faCommentDots } from '@fortawesome/free-solid-svg-icons/faCommentDots';
import { faPaperPlane } from '@fortawesome/free-solid-svg-icons/faPaperPlane';
import { faCalendarAlt } from '@fortawesome/free-solid-svg-icons/faCalendarAlt';
import { faUndo } from '@fortawesome/free-solid-svg-icons/faUndo';
import { faCheckCircle } from '@fortawesome/free-solid-svg-icons/faCheckCircle';
import { faInfoCircle } from '@fortawesome/free-solid-svg-icons/faInfoCircle';
import { faClipboardList } from '@fortawesome/free-solid-svg-icons/faClipboardList';
import { faArrowRight } from '@fortawesome/free-solid-svg-icons/faArrowRight';
import { faEnvelope } from '@fortawesome/free-solid-svg-icons/faEnvelope';

import VueCookie from 'vue-cookie';
import Vuelidate from 'vuelidate';
import Vue2Filters from 'vue2-filters';

import * as filters from '@/shared/date/filters';

export function initVueApp(vue) {
  vue.use(VueCookie);
  vue.use(Vuelidate);
  vue.use(Vue2Filters);
  setupAxiosInterceptors(() => console.log('Unauthorized!'));
  filters.initFilters();
}

export function initFortAwesome(vue) {
  library.add(
    faSort,
    faEye,
    faSync,
    faBan,
    faTrash,
    faArrowLeft,
    faSave,
    faPlus,
    faPencilAlt,
    faUser,
    faTachometerAlt,
    faHeart,
    faList,
    faTasks,
    faBook,
    faHdd,
    faLock,
    faSignInAlt,
    faSignOutAlt,
    faWrench,
    faThList,
    faUserPlus,
    faAsterisk,
    faFlag,
    faBell,
    faHome,
    faRoad,
    faCloud,
    faTimesCircle,
    faSearch,
    faBars,
    faTimes,
    faPaperclip,
    faFileAlt,
    faDownload,
    faCommentDots,
    faPaperPlane,
    faCalendarAlt,
    faUndo,
    faCheckCircle,
    faInfoCircle,
    faClipboardList,
    faArrowRight,
    faEnvelope
  );
}

export function initI18N(vue) {
  vue.use(VueI18n);
  return new VueI18n({
    silentTranslationWarn: true,
    formatter: new JhiFormatter(),
  });
}

export function initVueXStore(vue) {
  vue.use(Vuex);
  return new Vuex.Store({
    state: {
      dismissSecs: 0,
      dismissCountDown: 0,
      alertType: '',
      alertMessage: {},
      logon: false,
      userIdentity: null,
      authenticated: false,
      ribbonOnProfiles: '',
      activeProfiles: '',
      currentLanguage: localStorage.getItem('currentLanguage') || 'es',
      languages: {
        en: { name: 'English' },
        es: { name: 'Español' },
        // jhipster-needle-i18n-language-key-pipe - JHipster will add/remove languages in this object
      },
      /*Menu Lateral Tesis*/
      menu_lateral: [
        {
          title: 'Información General',
          description: 'Ingrese aquí los datos generales de la propuesta',
          to: '/propuesta/informacion-general',
        },
        {
          title: 'Integrantes',
          description: 'En esta sección ingrese los integrantes de la propuesta Nota: Cada modalidad tiene un maximo de integrantes',
          to: '/propuesta/integrantes',
        },
        {
          title: 'Certificado de Estudiante Activo',
          description: 'Expedido por Registro y Control',
          to: '/propuesta/documento/certificado-estudiante',
        },
        {
          title: 'Recibo de Pago de la Opción de Grado',
          description: 'Validado por Tesorería',
          to: '/propuesta/documento/recibo-pago',
        },
        {
          title: 'Récord Académico',
          description: 'Expedido por Registro y Control',
          to: '/propuesta/documento/record-academico',
        },
        {
          title: 'Formato de Inscripción de la Opción de Grado',
          description: 'Formato de inscripción de la opción de grado',
          to: '/propuesta/documento/formato-inscripcion',
        },
        {
          title: 'Adjuntar Propuesta',
          description: 'Envíe el archivo con su propuesta',
          to: '/propuesta/adjuntar_propuesta',
        },
        {
          title: 'Enviar Propuesta',
          description: 'En esta sección enviará su propuesta al Ciecyt',
          to: '/propuesta/enviar_propuesta',
        },
        {
          title: 'Retroalimentación Asesor',
          description: 'Encuentre las correcciones del Asesor',
          to: '/propuesta/retroalimentacion',
        },
        {
          title: 'Retroalimentación Jurados',
          description: 'Encuentre las correcciones de los Jurados',
          to: '/propuesta/retroalimentacion-viabilidad',
        },
      ],

      menu_lateral_pasantia: [
        {
          title: 'Informacion General',
          description: 'Ingrese aquí los datos generales de la propuesta de pasantía',
          to: '/propuesta-pasantia/informacion-general-pasantia',
        },
        {
          title: 'Integrantes',
          description: 'En esta sección ingrese los integrantes de la propuesta Nota: Cada modalidad tiene un maximo de integrantes',
          to: '/propuesta-pasantia/integrantes-pasantia',
        },
        {
          title: 'Información de la Empresa',
          description: 'En este apartado diligencie: La información de la Empresa donde realizará su pasantia',
          to: '/propuesta-pasantia/informacion-empresa',
        },
        {
          title: 'Adjuntar Propuesta',
          description: 'Envíe el archivo con su propuesta',
          to: '/propuesta-pasantia/adjuntar-propuesta-pasantia',
        },
        {
          title: 'Enviar Propuesta',
          description: 'En esta sección enviará su propuesta al Ciecyt',
          to: '/propuesta-pasantia/enviar-propuesta-pasantia',
        },
        {
          title: 'Retroalimentación Asesor',
          description: 'Encuentre las correcciones del Asesor',
          to: '/propuesta-pasantia/retroalimentacion',
        },

        {
          title: 'Retroalimentación Jurados',
          description: 'Encuentre las correcciones de los Jurados',
          to: '/propuesta-pasantia/retroalimentacion-viabilidad',
        },
      ],
      /*Menu Lateral Diplomado*/
      menu_lateral_diplomado: [
        {
          title: 'Informacion General',
          description: 'Ingrese aquí los datos generales de la propuesta',
          to: '/propuesta-diplomado/informacion-general-diplomado',
        },
        {
          title: 'Integrante(s)',
          description: 'En esta sección ingrese los integrantes de la propuesta Nota: Cada modalidad tiene un maximo de integrantes',
          to: '/propuesta-diplomado/integrantes-diplomado',
        },

        {
          title: 'Adjuntar Propuesta',
          description: 'Envíe el archivo con su propuesta',
          to: '/propuesta-diplomado/adjuntar-propuesta-diplomado',
        },
        {
          title: 'Enviar Propuesta',
          description: 'En esta sección enviará su propuesta al Ciecyt',
          to: '/propuesta-diplomado/enviar-propuesta-diplomado',
        },
        {
          title: 'Retroalimentación Asesor',
          description: 'Encuentre las correcciones del Asesor',
          to: '/propuesta-diplomado/retroalimentacion',
        },
        {
          title: 'Retroalimentación Jurados',
          description: 'Encuentre las correcciones de los Jurados',
          to: '/propuesta-diplomado/retroalimentacion-viabilidad',
        },
      ],

      ////////////////////////////7
      /*Menu Lateral*/
      menu_lateral_proyecto: [
        {
          title: 'Adjuntar Proyecto',
          description: 'Envíe el archivo con su proyecto',
          to: '/proyectos/adjuntar-proyecto',
        },
        {
          title: 'Retroalimentación Asesor',
          description: 'Encuentre las correcciones del Asesor',
          to: '/proyectos/retroalimentacion',
        },
        {
          title: 'Enviar Proyecto',
          description: 'En esta sección enviará su proyecto al Ciecyt',
          to: '/proyectos/enviar-proyecto',
        },

        {
          title: 'Retroalimentación Jurados',
          description: 'Encuentre las correcciones de los Jurados',
          to: '/proyectos/retroalimentacion-jurados',
        },
      ],

      //////////////////////////////////

      menu_lateral_listado: [
        {
          title: 'Asesor Propuestas',
          description: 'Espacio para revisar las propuestas a cargo',
          to: '/viabilidad-propuesta/listado-asesor',
        },
        {
          title: 'Asesor Proyectos',
          description: 'Espacio para revisar los proyectos a cargo',
          to: '/viabilidad-proyecto/listado-asesor-proyecto',
        },

        {
          title: 'Jurado',
          description: 'Evaluar la viabilidad de las propuestas y los proyectos',
          to: '/viabilidad-propuesta/listado-jurado',
        },
        {
          title: 'Sustentaciones',
          description: 'Evaluar la Sustentación de los Proyectos',
          to: '/viabilidad-propuesta/listado-sustentacion',
        },
      ],
      menu_lateral_ciecyt: [
        {
          title: 'Ciecyt',
          description: 'Espacio para revisar las propuestas del Ciecyt',
          to: '/ciecyt/listado-ciecyt',
        },
        {
          title: 'Jurado(s)',
          description: 'Ingrese el jurado o jurados',
          to: '/ciecyt/asignar-jurado',
        },
        {
          title: 'Asesor(es)',
          description: 'Ingrese el asesor o asesores',
          to: '/ciecyt/asignar-asesor',
        },
      ],
      menu_lateral_nueva: [
        {
          title: 'Propuesta Nueva',
          description: 'Espacio para crear las propuestas del Ciecyt',
          to: '/propuesta-nueva/informacion-general-nueva',
        },
        {
          title: 'Integrantes',
          description: 'Integrantes de la Propuesta',
          to: '/propuesta-nueva/integrantes-nueva',
        },
        {
          title: 'Asesor',
          description: 'Asignar asesor al proyecto',
          to: '/propuesta-nueva/asesor-nueva',
        },
        {
          title: 'Jurado',
          description: 'Jurados de sustentación',
          to: '/propuesta-nueva/jurado-nueva',
        },
        {
          title: 'Inscripcion',
          description: 'Inscripción del trabajo de grado',
          to: '/propuesta-nueva/inscripcion-nueva',
        },
      ],
    },
    mutations: {
      initAlert(state) {
        state.dismissSecs = 0;
        state.dismissCountDown = 0;
        state.alertType = '';
        state.alertMessage = {};
      },
      setAlertType(state, alertType) {
        state.alertType = alertType;
      },
      setAlertMessage(state, alertMessage) {
        state.dismissSecs = 5;
        state.dismissCountDown = 5;
        state.alertMessage = alertMessage;
      },
      countDownChanged(state, newCountDown) {
        state.dismissCountDown = newCountDown;
      },
      currentLanguage(state, newLanguage) {
        state.currentLanguage = newLanguage;
        localStorage.setItem('currentLanguage', newLanguage);
      },
      authenticate(state) {
        state.logon = true;
      },
      authenticated(state, identity) {
        state.userIdentity = identity;
        state.authenticated = true;
        state.logon = false;
      },
      logout(state) {
        state.userIdentity = null;
        state.authenticated = false;
        state.logon = false;
      },
      setActiveProfiles(state, profile) {
        state.activeProfiles = profile;
      },
      setRibbonOnProfiles(state, ribbon) {
        state.ribbonOnProfiles = ribbon;
      },
    },
    getters: {
      dismissSecs: state => state.dismissSecs,
      dismissCountDown: state => state.dismissCountDown,
      alertType: state => state.alertType,
      alertMessage: state => state.alertMessage,
      currentLanguage: state => state.currentLanguage,
      languages: state => state.languages,
      logon: state => state.logon,
      account: state => state.userIdentity,
      authenticated: state => state.authenticated,
      activeProfiles: state => state.activeProfiles,
      ribbonOnProfiles: state => state.ribbonOnProfiles,
      menuLateral: state => state.menu_lateral,
      menuLateralProyecto: state => state.menu_lateral_proyecto,
      menuLateralPasantia: state => state.menu_lateral_pasantia,
      menuLateralDiplomado: state => state.menu_lateral_diplomado,
      menuLateralListado: state => state.menu_lateral_listado,
      menuLateralCiecyt: state => state.menu_lateral_ciecyt,
      menuLateralNueva: state => state.menu_lateral_nueva,
    },
  });
}
