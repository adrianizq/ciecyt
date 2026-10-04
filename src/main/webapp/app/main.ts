import { createApp } from 'vue';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';

import App from './app.vue';
import router from './router';
import * as config from './shared/config/config';
import { setupAxiosInterceptors } from './shared/config/axios-interceptor';
import { initBootstrapVue } from './shared/config/config-bootstrap-vue';
import JhiItemCountComponent from './shared/jhi-item-count.vue';
import JhiLoadingComponent from './shared/jhi-loading.vue';
import JhiEmptyComponent from './shared/jhi-empty.vue';
import FileUpload from './shared/components/file-upload.vue';
import MenuLateralWizard from './components/shared/menu-lateral-wizard.vue';
import AuditsService from './admin/audits/audits.service';

import HealthService from './admin/health/health.service';
import MetricsService from './admin/metrics/metrics.service';
import LogsService from './admin/logs/logs.service';
import ActivateService from './account/activate/activate.service';
import RegisterService from './account/register/register.service';
import UserManagementService from '@/admin/user-management/user-management.service';

import LoginService from './account/login.service';
import AccountService from './account/account.service';

import '../content/scss/vendor.scss';
import 'bootstrap-vue-next/dist/bootstrap-vue-next.css';
import '../content/scss/global.scss';
import AlertService from '@/shared/alert/alert.service';
import TranslationService from '@/locale/translation.service';
import ConfigurationService from '@/admin/configuration/configuration.service';

import ProyectoService from '@/entities/proyecto/proyecto.service';
import PrediccionesService from '@/entities/predicciones/predicciones.service';
import LineaInvestigacionService from '@/entities/linea-investigacion/linea-investigacion.service';
import GrupoSemilleroService from '@/entities/grupo-semillero/grupo-semillero.service';
import FacultadService from '@/entities/facultad/facultad.service';
import ModalidadService from '@/entities/modalidad/modalidad.service';
import AcuerdoService from '@/entities/acuerdo/acuerdo.service';
import MunicipioService from '@/entities/municipio/municipio.service';
import DocenteHabilitadoService from '@/entities/docente-habilitado/docente-habilitado.service';
import AsesorExternoService from '@/entities/asesor-externo/asesor-externo.service';
import DecanoFacultadService from '@/entities/decano-facultad/decano-facultad.service';
import RemisionPadronService from '@/entities/remision-padron/remision-padron.service';
import DepartamentoService from '@/entities/departamento/departamento.service';
import CicloPropedeuticoService from '@/entities/ciclo-propedeutico/ciclo-propedeutico.service';
import ProductoService from '@/entities/producto/producto.service';
import ProductoProyectoService from '@/entities/producto-proyecto/producto-proyecto.service';
import ImpactosEsperadosService from '@/entities/impactos-esperados/impactos-esperados.service';
import CronogramaService from '@/entities/cronograma/cronograma.service';
import RubroService from '@/entities/rubro/rubro.service';
import EntidadService from '@/entities/entidad/entidad.service';
import ElementoProyectoService from '@/entities/elemento-proyecto/elemento-proyecto.service';
import FormatoService from '@/entities/formato/formato.service';
import TipoPreguntaService from '@/entities/tipo-pregunta/tipo-pregunta.service';
import PreguntaService from '@/entities/pregunta/pregunta.service';
import ProyectoRespuestasService from '@/entities/proyecto-respuestas/proyecto-respuestas.service';
import RolesModalidadService from '@/entities/roles-modalidad/roles-modalidad.service';
import FasesService from '@/entities/fases/fases.service';
import ProyectoFaseService from '@/entities/proyecto-fase/proyecto-fase.service';
import CronogramaCiecytService from '@/entities/cronograma-ciecyt/cronograma-ciecyt.service';
import CronogramaCiecytFasesService from '@/entities/cronograma-ciecyt-fases/cronograma-ciecyt-fases.service';
import IntegranteProyectoService from '@/entities/integrante-proyecto/integrante-proyecto.service';
import InformacionPasantiaService from '@/entities/informacion-pasantia/informacion-pasantia.service';
import SolicitudService from '@/entities/solicitud/solicitud.service';
import AdjuntoProyectoFaseService from '@/entities/adjunto-proyecto-fase/adjunto-proyecto-fase.service';
import RequisitoProyectoService from '@/entities/requisito-proyecto/requisito-proyecto.service';
import RetroalimentacionService from '@/entities/retroalimentacion/retroalimentacion.service';
import AdjuntoRetroalimentacionService from '@/entities/adjunto-retroalimentacion/adjunto-retroalimentacion.service';
import FichaTecnicaService from '@/entities/ficha-tecnica/ficha-tecnica.service';
import CategorizacionService from '@/entities/categorizacion/categorizacion.service';
import UsuarioService from '@/entities/usuario/usuario.service';
import UserInfoService from '@/entities/user-info/user-info.service';
import MenuService from '@/entities/menu/menu.service';
import RolMenuService from '@/entities/rol-menu/rol-menu.service';
import InvestigacionTipoService from '@/entities/investigacion-tipo/investigacion-tipo.service';
import ProgramaService from '@/entities/programa/programa.service';
import CicloService from '@/entities/ciclo/ciclo.service';
// jhipster-needle-add-entity-service-to-main-import - JHipster will import entities services here

//Complement model select
import PreguntaModalidadService from '@/entities/pregunta-modalidad/pregunta-modalidad.service';

import PreguntaAuthorityService from '@/entities/pregunta-authority/pregunta-authority.service';

const i18n = config.initI18N();
const store = config.initVueXStore();

const alertService = new AlertService(store);
const translationService = new TranslationService(store, i18n);
const loginService = new LoginService();
const accountService = new AccountService(store, translationService, router);

// Antes esto solo escribia un console.log en la consola del navegador: la sesion
// caducada seguia pareciendo activa y un 403 no mostraba nada al usuario.
setupAxiosInterceptors(status => {
  const token = localStorage.getItem('jhi-authenticationToken') || sessionStorage.getItem('jhi-authenticationToken');
  if (status === 401) {
    if (!token || !store.getters.authenticated) {
      return;
    }
    store.commit('logout');
    alertService.showAlert('Su sesión ha expirado. Vuelva a iniciar sesión para continuar.', 'warning');
    if (router.currentRoute.value.path !== '/') {
      router.push('/').catch(() => {});
    }
    return;
  }
  alertService.showAlert('No tiene permisos para realizar esta acción.', 'danger');
});

// Muchos .then() del frontend no tenian .catch(): el fallo solo aparecia en la
// consola y al usuario no le llegaba nada. Con esto cualquier peticion HTTP que
// quede sin manejar muestra el mismo aviso que showHttpError, sin repetir el de
// sesion caducada/permisos que ya emite el interceptor.
window.addEventListener('unhandledrejection', event => {
  const reason: any = event && event.reason;
  if (!reason) {
    return;
  }
  const response = typeof reason === 'object' ? reason.response : null;
  if (response && (response.status === 401 || response.status === 403)) {
    return;
  }
  // Pasa toda la razon (o solo la respuesta de Axios) a showHttpError para que
  // mapee 0/4xx/5xx a mensajes en lenguaje natural.
  alertService.showHttpError(null, response || reason);
});

const provide: Record<string, unknown> = {
  loginService: () => loginService,
  activateService: () => new ActivateService(),
  registerService: () => new RegisterService(),
  userService: () => new UserManagementService(),

  auditsService: () => new AuditsService(),

  healthService: () => new HealthService(),

  configurationService: () => new ConfigurationService(),
  logsService: () => new LogsService(),
  metricsService: () => new MetricsService(),
  alertService: () => alertService,
  translationService: () => translationService,
  proyectoService: () => new ProyectoService(),
  docenteHabilitadoService: () => new DocenteHabilitadoService(),
  asesorExternoService: () => new AsesorExternoService(),
  decanoFacultadService: () => new DecanoFacultadService(),
  remisionPadronService: () => new RemisionPadronService(),
  prediccionesService: () => new PrediccionesService(),
  lineaInvestigacionService: () => new LineaInvestigacionService(),

  grupoSemilleroService: () => new GrupoSemilleroService(),
  facultadService: () => new FacultadService(),
  modalidadService: () => new ModalidadService(),
  acuerdoService: () => new AcuerdoService(),
  municipioService: () => new MunicipioService(),
  departamentoService: () => new DepartamentoService(),
  cicloPropedeuticoService: () => new CicloPropedeuticoService(),
  productoService: () => new ProductoService(),
  productoProyectoService: () => new ProductoProyectoService(),
  impactosEsperadosService: () => new ImpactosEsperadosService(),
  cronogramaService: () => new CronogramaService(),
  rubroService: () => new RubroService(),
  entidadService: () => new EntidadService(),
  elementoProyectoService: () => new ElementoProyectoService(),
  formatoService: () => new FormatoService(),
  tipoPreguntaService: () => new TipoPreguntaService(),
  preguntaService: () => new PreguntaService(),
  proyectoRespuestasService: () => new ProyectoRespuestasService(),
  rolesModalidadService: () => new RolesModalidadService(),
  fasesService: () => new FasesService(),
  proyectoFaseService: () => new ProyectoFaseService(),
  cronogramaCiecytService: () => new CronogramaCiecytService(),
  cronogramaCiecytFasesService: () => new CronogramaCiecytFasesService(),
  integranteProyectoService: () => new IntegranteProyectoService(),
  informacionPasantiaService: () => new InformacionPasantiaService(),
  solicitudService: () => new SolicitudService(),
  adjuntoProyectoFaseService: () => new AdjuntoProyectoFaseService(),
  requisitoProyectoService: () => new RequisitoProyectoService(),
  retroalimentacionService: () => new RetroalimentacionService(),
  adjuntoRetroalimentacionService: () => new AdjuntoRetroalimentacionService(),
  fichaTecnicaService: () => new FichaTecnicaService(),
  categorizacionService: () => new CategorizacionService(),
  usuarioService: () => new UsuarioService(),
  userInfoService: () => new UserInfoService(),
  menuService: () => new MenuService(),
  rolMenuService: () => new RolMenuService(),
  investigacionTipoService: () => new InvestigacionTipoService(),
  preguntaModalidadService: () => new PreguntaModalidadService(),
  preguntaAuthorityService: () => new PreguntaAuthorityService(),

  programaService: () => new ProgramaService(),
  cicloService: () => new CicloService(),
  // jhipster-needle-add-entity-service-to-main - JHipster will import entities services here
  accountService: () => accountService,
};

const app = createApp(App);
for (const [token, value] of Object.entries(provide)) {
  app.provide(token, value);
}

config.initFortAwesome();
initBootstrapVue(app);
app.component('font-awesome-icon', FontAwesomeIcon);
app.component('jhi-item-count', JhiItemCountComponent);
app.component('jhi-loading', JhiLoadingComponent);
app.component('jhi-empty', JhiEmptyComponent);
app.component('file-upload', FileUpload);
app.component('menu-lateral-wizard', MenuLateralWizard);
app.use(store);
app.use(i18n);
app.use(router);

router.beforeEach((to, _from, next) => {
  if (!to.matched.length) {
    next('/not-found');
    return;
  }

  const authorities = to.meta && (to.meta.authorities as string[] | undefined);
  if (authorities && authorities.length > 0) {
    if (!accountService.hasAnyAuthority(authorities)) {
      sessionStorage.setItem('requested-url', to.fullPath);
      next('/forbidden');
      return;
    }
  }
  next();
});

app.mount('#app');
