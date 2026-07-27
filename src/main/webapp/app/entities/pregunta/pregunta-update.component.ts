import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import TipoPreguntaService from '../tipo-pregunta/tipo-pregunta.service';
import { ITipoPregunta } from '@/shared/model/tipo-pregunta.model';

import ModalidadService from '../modalidad/modalidad.service';
import { IModalidad } from '@/shared/model/modalidad.model';

import ElementoModalidadService from '../elemento-modalidad/elemento-modalidad.service';
import { IElementoModalidad } from '@/shared/model/elemento-modalidad.model';

import FasesService from '../fases/fases.service';
import { IFases } from '@/shared/model/fases.model';

import ElementoService from '../elemento/elemento.service';
import { IElemento, Elemento } from '@/shared/model/elemento.model';

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
  @Inject('elementoModalidadService') private elementoModalidadService: () => ElementoModalidadService;

  public modalidads: IModalidad[] = [];

  //public elementoModalidads: IElementoModalidad[] = [];

  public authorities: any[] = [];
  public tmpAuthorities: any[] = [];

  public existeElemento: boolean = false;

  @Inject('fasesService') private fasesService: () => FasesService;

  public fass: IFases[] = [];

  @Inject('elementoService') private elementoService: () => ElementoService;

  @Inject('preguntaModalidadService') private preguntaModalidadService: () => PreguntaModalidadService;
  @Inject('preguntaAuthorityService') private preguntaAuthorityService: () => PreguntaAuthorityService;

  @Inject('userService') private userManagementService: () => UserManagementService;

  public elements: IElemento[] = [];

  public preguntasModalidsPreguntaId: IPreguntaModalidad[] = [];

  public preguntasAuthoritsPreguntaId: IPreguntaAuthority[] = [];

  public authoritiesPreguntaId: IPreguntaModalidad[] = [];

  public elemento: IElemento = new Elemento();

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

    //console.log(this.pregunta.elementoId); //si reporta
    //1
    if (this.pregunta.preguntaElementoId) {
      this.elements.forEach(item => {
        if (item.id == this.pregunta.preguntaElementoId) {
          //this.elemento = item;
          this.pregunta.preguntaElemento = item.elemento; //error al guardar
        }
      });
    }

    //2
    if (this.pregunta.preguntaFaseId) {
      this.fass.forEach(item => {
        if (item.id == this.pregunta.preguntaFaseId) {
          //this.elemento = item;
          this.pregunta.preguntaFase = item.fase; //error al guardar
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
    return this.elements.filter(elemento => {
      return elemento.elementoFasesId == this.pregunta.preguntaFaseId;
    });
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
    //console.log(event.target.value);
  }

  setModalidades(event) {
    var seleccionadaId = event;
    if (seleccionadaId) {
      this.existeElemento = true;
      let res = this.elementoModalidadService()
        .retrieveModalidadElemento(seleccionadaId)
        .then(res => {
          //this.elementoModalidads = res.data;
          this.modalidadesAsignadas = res.data;
        });
    } else {
      this.existeElemento = false;
    }
    console.log(this.existeElemento);
  }

  get Fases() {
    return this.fass;
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  async initRelationships(preguntaId) {
    console.log(preguntaId);
    if (preguntaId) {
      let res = await this.preguntaService()
        .find(preguntaId)
        .then(res => {
          this.pregunta = res;
          this.preguntaId = res.id;
          if (res.preguntaElementoId != null) {
            this.existeElemento = true;
          } else {
            this.existeElemento = false;
          }
          console.log(this.existeElemento);
        });
    }
    let res = await this.tipoPreguntaService()
      .retrieve()
      .then(res => {
        this.tipoPreguntas = res.data;
        /////////////////////77
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
        /////////////////////////////777
      });
    res = await this.modalidadService()
      .retrieve()
      .then(res => {
        this.modalidads = res.data;
      });

    if (this.preguntaId) {
      res = await this.modalidadService()
        .retrieveModalidadPregunta(parseInt(preguntaId))
        .then(res => {
          this.modalidadesAsignadas = res.data;
        });
    }

    res = await this.userManagementService()
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
      res = await this.preguntaAuthorityService()
        .retrievePreguntasAuthority(parseInt(preguntaId))
        .then(res => {
          this.authoritiesAsignadas = res.data;
        });
    }

    if (this.preguntaId) {
      res = await this.preguntaAuthorityService()
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

    this.elementoService()
      .retrieveNoPage()
      //.retrieve()
      .then(res => {
        this.elements = res.data;
      });
  }
}
