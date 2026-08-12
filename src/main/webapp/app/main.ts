// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.common with an alias.
import Vue from 'vue';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';

////////////////////////777
import { BootstrapVue, BootstrapVueIcons } from 'bootstrap-vue';

/////////////////////////////77777

import App from './app.vue';
import Vue2Filters from 'vue2-filters';
import router from './router';
import * as config from './shared/config/config';
import * as bootstrapVueConfig from './shared/config/config-bootstrap-vue';
import JhiItemCountComponent from './shared/jhi-item-count.vue';
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
import 'vue-search-select/dist/VueSearchSelect.css';
import PreguntaModalidadService from '@/entities/pregunta-modalidad/pregunta-modalidad.service';

import PreguntaAuthorityService from '@/entities/pregunta-authority/pregunta-authority.service';

Vue.config.productionTip = false;
config.initVueApp(Vue);
config.initFortAwesome(Vue);
bootstrapVueConfig.initBootstrapVue(Vue);
Vue.use(Vue2Filters);
Vue.component('font-awesome-icon', FontAwesomeIcon);
Vue.component('jhi-item-count', JhiItemCountComponent);

Vue.use(BootstrapVue);
Vue.use(BootstrapVueIcons);

const i18n = config.initI18N(Vue);
const store = config.initVueXStore(Vue);

const alertService = new AlertService(store);
const translationService = new TranslationService(store, i18n);
const loginService = new LoginService();
const accountService = new AccountService(store, translationService, router);

router.beforeEach((to, from, next) => {
  if (!to.matched.length) {
    next('/not-found');
  }

  if (to.meta && to.meta.authorities && to.meta.authorities.length > 0) {
    if (!accountService.hasAnyAuthority(to.meta.authorities)) {
      sessionStorage.setItem('requested-url', to.fullPath);
      next('/forbidden');
    } else {
      next();
    }
  } else {
    // no authorities, so just proceed
    next();
  }
});

/*tslint:disable */
new Vue({
  el: '#app',
  components: { App },
  template: '<App/>',
  router,
  provide: {
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
  },
  i18n,
  store,
});
