import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import TipoPreguntaService from '../tipo-pregunta/tipo-pregunta.service';
import { ITipoPregunta } from '@/shared/model/tipo-pregunta.model';

import ModalidadService from '../modalidad/modalidad.service';
import { IModalidad } from '@/shared/model/modalidad.model';

import FasesService from '../fases/fases.service';
import { IFases } from '@/shared/model/fases.model';

import CicloService from '../ciclo/ciclo.service';
import { ICiclo } from '@/shared/model/ciclo.model';

import AlertService from '@/shared/alert/alert.service';
import { IPregunta, Pregunta } from '@/shared/model/pregunta.model';
import PreguntaService from './pregunta.service';

import { IPreguntaModalidad, PreguntaModalidad } from '@/shared/model/pregunta-modalidad.model';
import PreguntaModalidadService from '@/entities/pregunta-modalidad/pregunta-modalidad.service';

import { IPreguntaAuthority, PreguntaAuthority } from '@/shared/model/pregunta-authority.model';
import PreguntaAuthorityService from '@/entities/pregunta-authority/pregunta-authority.service';

import UserManagementService from '../../admin/user-management/user-management.service';
import { IAuthority } from '@/shared/model/authority.model';

const validations: any = {
  pregunta: {
    encabezado: {},
    descripcion: {},
    orden: {},
    pregunta: {},
    puntajeMaximo: {},
    preguntaElementoId: {},
  },
};

@Component({
  validations,
})
export default class PreguntaUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('preguntaService') private preguntaService: () => PreguntaService;
  public pregunta: IPregunta;

  public modalidadesAsignadas: IModalidad[] = [];

  public authoritiesAsignadas: IAuthority[] = [];

  @Inject('tipoPreguntaService') private tipoPreguntaService: () => TipoPreguntaService;

  public tipoPreguntas: ITipoPregunta[] = [];

  @Inject('modalidadService') private modalidadService: () => ModalidadService;

  public modalidads: IModalidad[] = [];

  public authorities: any[] = [];
  public tmpAuthorities: any[] = [];

  public existeElemento: boolean = false;

  @Inject('fasesService') private fasesService: () => FasesService;

  public fass: IFases[] = [];

  @Inject('cicloService') private cicloService: () => CicloService;

  public ciclos: ICiclo[] = [];

  @Inject('preguntaModalidadService') private preguntaModalidadService: () => PreguntaModalidadService;
  @Inject('preguntaAuthorityService') private preguntaAuthorityService: () => PreguntaAuthorityService;

  @Inject('userService') private userManagementService: () => UserManagementService;

  public elements: any[] = [];

  public preguntasModalidsPreguntaId: IPreguntaModalidad[] = [];

  public preguntasAuthoritsPreguntaId: IPreguntaAuthority[] = [];

  public authoritiesPreguntaId: IPreguntaModalidad[] = [];

  public tipoNota: boolean = false;

  public preguntaId: any = null;

  public isSaving = false;

  public selected: any = [];

  public constructor() {
    super();
    this.pregunta = new Pregunta();
    this.pregunta.preguntaModalidads = [];
    this.pregunta.authorities = [];
  }

  beforeRouteEnter(to, from, next) {
    next(vm => {
      // if (to.params.preguntaId) {
      //vm.retrievePregunta(to.params.preguntaId);
      vm.initRelationships(to.params.preguntaId);
      // }
      // else{
      //   vm.initRelationships(null);
      // }
    });
  }

  public save(): void {
    this.isSaving = true;

    //1
    if (this.pregunta.preguntaFaseId) {
      this.fass.forEach(item => {
        if (item.id == this.pregunta.preguntaFaseId) {
          this.pregunta.preguntaFase = item.fase;
        }
      });
    }
    //3
    this.pregunta.preguntaModalidads = [];
    this.modalidadesAsignadas.forEach(element => {
      var pr: IPreguntaModalidad = new PreguntaModalidad();
      pr.preguntaId = this.preguntaId;
      pr.modalidad2Id = element.id;
      this.pregunta.preguntaModalidads.push(pr);
      // console.log(this.pregunta.preguntaModalidads);
    });
    //4
    this.pregunta.authorities = [];
    this.authoritiesAsignadas.forEach(element => {
      var pr: IPreguntaAuthority = new PreguntaAuthority();
      pr.pregunta3Id = this.preguntaId;
      pr.authorityName = element.toString();
      this.pregunta.authorities.push(pr);
      console.log(this.pregunta.authorities);
    });

    if (this.pregunta.id) {
      this.preguntaService()
        .update(this.pregunta)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          //(<any>this).$router.go(0);
          const message = this.$t('ciecytApp.pregunta.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
      /////////

      // console.log(this.selected);
    } else {
      this.preguntaService()
        .create(this.pregunta)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          // (<any>this).$router.go(0);
          const message = this.$t('ciecytApp.pregunta.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
    //console.log(this.modalidadesAsignadas);
  }

  get Elementos() {
    return this.elements;
  }

  setTipoPregunta(event) {
    var seleccionadaId = event.target.value;
    this.tipoNota = false;
    this.tipoPreguntas.forEach(tp => {
      if (tp.tipoDato == 'nota' && seleccionadaId == tp.id) {
        this.tipoNota = true;
        return;
      }
    });
  }

  setModalidades(event) {
    this.existeElemento = false;
  }

  setFase(event) {
  }

  get Fases() {
    return this.fass;
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  async initRelationships(preguntaId) {
    if (preguntaId) {
      await this.preguntaService()
        .find(preguntaId)
        .then(res => {
          this.pregunta = res;
          this.preguntaId = res.id;
          this.existeElemento = false;
        });
    }
    await this.tipoPreguntaService()
      .retrieve()
      .then(res => {
        this.tipoPreguntas = res.data;
        if (this.preguntaId) {
          var seleccionadaId = this.pregunta.preguntaTipoPreguntaId;
          this.tipoNota = false;
          this.tipoPreguntas.forEach(tp => {
            if (tp.tipoDato == 'nota' && seleccionadaId == tp.id) {
              this.tipoNota = true;
              return;
            }
          });
        }
      });
    await this.modalidadService()
      .retrieve()
      .then(res => {
        this.modalidads = res.data;
      });

    if (this.preguntaId) {
      await this.modalidadService()
        .retrieveModalidadPregunta(parseInt(preguntaId))
        .then(res => {
          this.modalidadesAsignadas = res.data;
        });
    }

    await this.userManagementService()
      .retrieveAuthorities()
      .then(_res => {
        this.tmpAuthorities = _res.data;
        this.tmpAuthorities.forEach(a => {
          if (
            a != 'ROLE_USER' &&
            a != 'ROLE_ADMIN' &&
            a != 'ROLE_ANONYMOUS' &&
            a != 'ROLE_ESTUDIANTE' &&
            a != 'ROLE_DOCENTE' &&
            a != 'ROLE_CIECYT'
          )
            this.authorities.push(a);
        });
      });

    if (this.preguntaId) {
      await this.preguntaAuthorityService()
        .retrievePreguntasAuthority(parseInt(preguntaId))
        .then(res => {
          this.authoritiesAsignadas = res.data;
        });
    }

    if (this.preguntaId) {
      await this.preguntaAuthorityService()
        .retrievePreguntaAuthorityIdPregunta(parseInt(preguntaId))
        .then(res => {
          this.preguntasAuthoritsPreguntaId = res.data;
        });
    }

    this.fasesService()
      .retrieve()
      .then(res => {
        this.fass = res.data;
      });

    this.cicloService()
      .retrieveAll()
      .then(res => {
        this.ciclos = res;
      });
  }
}
