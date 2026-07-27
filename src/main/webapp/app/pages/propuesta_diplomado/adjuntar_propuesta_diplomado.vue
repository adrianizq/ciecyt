<template>
  <div class="row">
    <div class="col-sm-4">
      <menu-lateral-diplomado :proyectoId="$route.params.proyectoId"></menu-lateral-diplomado>
    </div>
    <!--<div class="col-sm-8"  v-if="!proyecto.enviado"> -->
    <div class="col-sm-8">
      <!--<form @submit.prevent="save()">-->
         <form name="enviarAdjunto" role="form" novalidate v-on:submit.prevent="save()" >
        <div class="row">
          <div class="col-12">
            
            <div class="form-group">
              <label class="form-control-label" for="proyecto-titulo">
               <h2>Adjuntar Archivo a la Propuesta</h2><br />
              </label>

             
            </div>
          </div>

                    <!-------------------------DESCARGAR ------->
                       <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.adjuntoProyectoFase.archivo')" for="adjunto-proyecto-fase-archivo">Archivo</label>
                        <div>
                            <div v-if="adjuntoProyectoFase.id"  class="form-text text-danger clearfix">
                            
                            
                                <!--<a class="pull-left" v-on:click="openFile(adjuntoProyectoFase.archivoContentType, adjuntoProyectoFase.file)" v-text="$t('entity.action.open')">open</a><br> -->
                                <a class="pull-left" v-on:click="this.descargar" v-text="$t('entity.action.open')">open</a>
                                <span class="pull-left">{{adjuntoProyectoFase.nombreArchivoOriginal }} <br /> {{adjuntoProyectoFase.archivoContentType}}, {{byteSize(adjuntoProyectoFase.file)}}</span>
                                <button type="button" v-on:click="this.eliminar" v-text="$t('entity.action.delete')">
                                        class="btn btn-secondary btn-xs pull-right">
                                    <font-awesome-icon icon="times"></font-awesome-icon>
                                </button> 
                            </div> 
                            <input v-if="adjuntoProyectoFase.file==null" type="file" ref="file_archivo" id="file_archivo" v-on:change="asignarData($event, adjuntoProyectoFase, 'archivo', false)" v-text="$t('entity.action.addblob')"/>
                            <span  v-if="adjuntoProyectoFase.file!=null">Si desea subir otro adjunto de la propuesta, deberá eliminar el archivo actual</span>
                        </div>
                        <input type="hidden" class="form-control" name="archivo" id="adjunto-proyecto-fase-archivo"
                            :class="{'valid': !$v.adjuntoProyectoFase.archivo.$invalid, 'invalid': $v.adjuntoProyectoFase.archivo.$invalid }" v-model="$v.adjuntoProyectoFase.archivo.$model" />
                        <input type="hidden" class="form-control" name="archivoContentType" id="adjunto-proyecto-fase-archivoContentType"
                            v-model="adjuntoProyectoFase.archivoContentType" />
                         <input type="hidden" class="form-control" name="fileName" id="adjunto-proyecto-fase-fileName"
                            v-model="adjuntoProyectoFase.nombreArchivoOriginal" />
                    </div> 
                  
                  
        </div>
                   
        <div>
          <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>

          <button type="submit" id="save-entity" class="btn btn-primary" :disabled='isDisabled'>
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Enviar</span>
          </button>
        </div>
      </form>
    </div>
   <!--<div class="col-sm-8"  v-if="proyecto.enviado">
     <h2>Enviar la Propuesta</h2><br />
    La Propuesta ya ha sido enviada para su revisión
    </div> -->
  </div> 
</template>

<script lang="ts">
import { Component, Inject, Vue } from 'vue-property-decorator';

import MenuLateralDiplomado from '@/components/propuesta_diplomado/menu_lateral_diplomado.vue';
import { IProyecto, Proyecto } from '@/shared/model/proyecto.model';
import { IUser } from '@/shared/model/user.model';
import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import ProyectoService from '@/entities/proyecto/proyecto.service';
import AlertService from '@/shared/alert/alert.service';
//import { IIntegranteProyecto } from '@/shared/model/integrante-proyecto.model';
import { IAdjuntoProyectoFase, AdjuntoProyectoFase } from '@/shared/model/adjunto-proyecto-fase.model';
import AdjuntoProyectoFaseService from '@/entities/adjunto-proyecto-fase/adjunto-proyecto-fase.service';

import { IFases, Fases } from '@/shared/model/fases.model';
import FasesService from '@/entities/fases/fases.service';
import { resolve4 } from 'dns';

const validations: any = {
  adjuntoProyectoFase: {
    nombreAdjunto: {},
    fechaCreacion: {},
    fechaModificacion: {},
    estadoAdjunto: {},
    adjuntoProyectoFase: {},
    nombreArchivoOriginal: {},
    archivo: {},
    fechaInicio: {},
    fechaFin: {},
    file: {},
  },
};

@Component({
  validations, components: { MenuLateralDiplomado },
})

//export default class EnviarPropuesta extends Vue {
  export default class EnviarPropuesta extends mixins(JhiDataUtils){
  @Inject('proyectoService') private proyectoService: () => ProyectoService;
  @Inject('adjuntoProyectoFaseService') private adjuntoProyectoFaseService: () => AdjuntoProyectoFaseService;
  @Inject('fasesService') private fasesService: () => FasesService;
  @Inject('alertService') private alertService: () => AlertService;
  
  //public adjuntoProyectoFase: IAdjuntoProyectoFase = new AdjuntoProyectoFase();
  public adjuntoProyectoFass:IAdjuntoProyectoFase[] =[];
  public adjuntoProyectoFase: IAdjuntoProyectoFase = new AdjuntoProyectoFase();

  //public integrants:IIntegranteProyecto[]= [];
   public fase:IFases= new Fases();
   // public fases: IFases = new Fases();
   public faseId:number;
   public faseFase:string="Propuesta";
  public terms:Boolean=false;


  public proyecto: IProyecto = new Proyecto();
  public proyId: string = null;
  public isSaving = false;
  public modalidadId: any;
  

  descargar() {
    //console.log('se hizo clic');
    this.adjuntoProyectoFaseService().downloadFile(this.adjuntoProyectoFase.id, this.adjuntoProyectoFase.nombreArchivoOriginal);
  }

  eliminar(ob) {
    console.log('entro a eliminar');
    this.adjuntoProyectoFaseService().delete(this.adjuntoProyectoFase.id);
    this.adjuntoProyectoFass=null;
        //this.isSaving = false;
           (<any>this).$router.go(0);
  }

  asignarData(event, entity, field, isImage){
     var fileData =  event.target.files[0];
    this.adjuntoProyectoFase.nombreArchivoOriginal= fileData.name;
    console.log(this.adjuntoProyectoFase.nombreArchivoOriginal);

    this.setFileData(event, entity, field, isImage)
    
  }


  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.proyectoId) {
        //vm.retrieveProyecto(to.params.proyectoId);
      }
      vm.initRelationships();
    });
  }

  getNow() {
    const today = new Date();
    const date = today.getFullYear() + '-' + (today.getMonth() + 1) + '-' + today.getDate();
    return date;
  }

  public save(): void {
    this.isSaving = true;
 
   
    this.adjuntoProyectoFase.proyectoFaseProyectoId = this.proyecto.id;
    this.adjuntoProyectoFase.adjuntoProyectoFaseFaseId = this.faseId;
     this.adjuntoProyectoFase.fechaCreacion = new Date();
     this.adjuntoProyectoFase.proyectoFaseProyectoTitulo =  this.proyecto.titulo;

    
    if(this.adjuntoProyectoFase.id) {
     console.log("Existe el adjunto");
      this.adjuntoProyectoFaseService()
        .update(this.adjuntoProyectoFase)
        .then(param => {
            this.isSaving = false;
            (<any>this).$router.go(0);
          const message = this.$t('ciecytApp.adjuntoProyectoFase.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      console.log("NO Existe el adjunto");
      this.adjuntoProyectoFaseService()
        .create(this.adjuntoProyectoFase)
        .then(param => {
          this.isSaving = false;
           (<any>this).$router.go(0);
          const message = this.$t('ciecytApp.adjuntoProyectoFase.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
   
  }

  
  
  async initRelationships() {
    this.proyId = this.$route.params.proyectoId;

     let res = await this.proyectoService()
    .findProyectoIntegrantes(parseInt(this.$route.params.proyectoId))
      //.find(parseInt(this.$route.params.proyectoId))
      .then(res => {
        this.proyecto = res.data;
        this.modalidadId = this.proyecto.proyectoModalidadId;
        //this.integrants = this.proyecto.listaIntegrantesProyecto;
        
        
      });

      /*res=  await this.fasesService().retrieveFaseModalidad("Propuesta", this.modalidadId)
      .then(res => {
        this.fase = res.data;  
        this.faseId = this.fase.id;

      });*/

      res=  await this.fasesService().findByFase(this.faseFase)
      .then(res => {
        this.fase = res;  
        this.faseId = this.fase.id;

      });



      res=  await this.adjuntoProyectoFaseService()
      .findAdjuntoProyectoFase(this.proyId,  this.fase.id)
      .then(res => {
        this.adjuntoProyectoFass = res.data;
        if(this.adjuntoProyectoFass.length==0){
         this.adjuntoProyectoFase =  new AdjuntoProyectoFase();
        }
        else{
          this.adjuntoProyectoFase = this.adjuntoProyectoFass[0];
        }
         console.log(this.adjuntoProyectoFass);
         console.log(this.adjuntoProyectoFase);
      });
      
  }

  get isDisabled(){
    	return this.adjuntoProyectoFase.file!=null;
    }
}
</script>

<style scoped>
</style>
