import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

//import FormatoService from '../formato/formato.service';
//import { IFormato } from '@/shared/model/formato.model';

//import ModalidadService from '../modalidad/modalidad.service';
//import { IModalidad } from '@/shared/model/modalidad.model';

import FasesService from '../fases/fases.service';
import { IFases } from '@/shared/model/fases.model';

import ModalidadService from '../modalidad/modalidad.service';
import { IElementoModalidad, ElementoModalidad } from '@/shared/model/elemento-modalidad.model';
import ElementoModalidadService from '@/entities/elemento-modalidad/elemento-modalidad.service';

import AlertService from '@/shared/alert/alert.service';
import { IElemento, Elemento } from '@/shared/model/elemento.model';
import ElementoService from './elemento.service';
import { IModalidad } from '@/shared/model/modalidad.model';

const validations: any = {
  elemento: {
    elemento: {},
    descripcion: {},
    orden: {},
  },
};

@Component({
  validations,
})
export default class ElementoUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('elementoService') private elementoService: () => ElementoService;
  @Inject('elementoModalidadService') private elementoModalidadService: () => ElementoModalidadService;
  @Inject('modalidadService') private modalidadService: () => ModalidadService;

  public modalidads: IModalidad[] = [];

  public elemento: IElemento = new Elemento();
  public elementosModalidsElementoId: IElementoModalidad[] = [];

  public elementoId: any = null;

  //@Inject('formatoService') private formatoService: () => FormatoService;

  //public formatoes: IFormato[] = [];

  @Inject('fasesService') private fasesService: () => FasesService;
  // @Inject('modalidadService') private modalidadService: () => ModalidadService;

  public fases: IFases[] = [];
  public isSaving = false;
  public modalidadesAsignadas: IModalidad[] = [];

  public constructor() {
    super();
    this.elemento = new Elemento();
    this.elemento.elementoModalidads = [];
  }

  //  public modalidads: IModalidad[] = [];

  beforeRouteEnter(to, from, next) {
    next(vm => {
      // if (to.params.elementoId) {
      //  this.elementoId=to.params.elementoId;
      vm.initRelationships(to.params.elementoId);
      // }
    });
  }

  public save(): void {
    this.isSaving = true;

    //1 No (basado en pregunta-update.component.ts)
    //2
    if (this.elemento.elementoFasesId) {
      this.fases.forEach(item => {
        if (item.id == this.elemento.elementoFasesId) {
          //this.elemento = item;
          this.elemento.elementoFasesFase = item.fase;
        }
      });
    }
    //3
    this.elemento.elementoModalidads = [];
    this.modalidadesAsignadas.forEach(element => {
      var pr: IElementoModalidad = new ElementoModalidad();
      pr.elementoId = this.elementoId;
      pr.modalidadId = element.id;
      this.elemento.elementoModalidads.push(pr);
      // console.log(this.pregunta.preguntaModalidads);
    });

    //4 No

    if (this.elemento.id) {
      this.elementoService()
        .update(this.elemento)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.elemento.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.elementoService()
        .create(this.elemento)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.elemento.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  async initRelationships(elementoId) {
    if (elementoId) {
      let res = await this.elementoService()
        .find(elementoId)
        .then(res => {
          this.elemento = res;
          this.elementoId = res.id;
        });
    }

    /*let res = await this.formatoService()
      .retrieve()
      .then(res => {
        this.formatoes = res.data;
      });*/
    this.fasesService()
      .retrieve()
      .then(res => {
        this.fases = res.data;
      });

    let res = await this.modalidadService()
      .retrieve()
      .then(res => {
        this.modalidads = res.data;
      });

    if (this.elementoId) {
      let res = await this.elementoModalidadService()
        .retrieveModalidadElemento(parseInt(this.elementoId))
        .then(res => {
          this.modalidadesAsignadas = res.data;
          console.log('modAsig!' + this.modalidadesAsignadas);
        });
    }
  }
}
