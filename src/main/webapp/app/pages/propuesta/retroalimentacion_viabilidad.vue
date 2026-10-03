<template>
  <div class="row">
    <div class="col-sm-4">
      <menu-lateral :proyectoId="$route.params.proyectoId"></menu-lateral>
    </div>
    
   <!-- <div class="col-sm-8"  v-if="!proyecto.preEnviado"> -->
    <div class="col-sm-8" v-if="retroalimentacionVisible">
      <form @submit.prevent>
        <div class="row">
          <div class="col-12">
            
            <div class="mb-3 form-group">
              <label class="form-control-label" for="proyecto-titulo"></label>
               <h2>Retroalimentación  de  Viabilidad</h2>
<!--------------------------------------------------------->

                    <div class="col-12" v-for="(ep, i) in proyectoRespuests" :key="i">
                    <b-card  
                      border-variant="primary"
                      header-bg-variant="light"
                      body-bg-variant="light"
                     header-text-variant="info">
                     <!--<div class="text-secondary"> Tipo de pregunta {{ep.preguntaTipoPreguntaTipoPregunta}} </div>-->
                     <label  class="p-3 mb-2 bg-info text-white container-fluid">{{ep.encabezado}} </label>
                     
                     <b-form-group class="mb-3"
                            :label="ep.elemento"
                            :label-for="`ep-${i}`" 
                            :description="ep.proyectoRespuestasPreguntaPregunta"
                                                   
                       >
                       <div class="mb-3 form-group" >
                           

                            
                            <b-form-textarea rows="2"  max-rows="10" class="form-control" :name="`ep-${i}`"
                            :id="`ep-${i}`" v-if="ep.elemento"
                                   v-model="ep.elemento"  disabled="true"  />
                       </div>
                       </b-form-group>

                        
                       <!--- dato  -->
                          <b-form-group class="mb-3">
                       <div class="mb-3 form-group" >

                            <b-form-textarea rows="2"  max-rows="10" class="form-control" :name="`ep-${i}`"
                            :id="`ep-${i}` " 
                                   v-model="ep.dato"  v-if="ep.dato!=null" readonly="true"  disabled="true" />
                            </div>
                       </b-form-group>

                        <!-- TIPOS Pregunta--------------------------------------------->
                        <div class="mb-3 form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.proyectoRespuestas.respuesta')" for="proyecto-respuestas-respuesta"></label>
                        <select class="form-control" c  v-model="ep.respuesta"   disabled="true"
                          id="proyecto-respuestas-respuesta"
                          v-if="ep.preguntaTipoPreguntaTipoPregunta==`Cumple NoCumple NoAplica`" >
                            <option value="CUMPLE" v-bind:label="$t('ciecytApp.EnumRespuestas.CUMPLE')">CUMPLE</option>
                            <option value="NO_CUMPLE" v-bind:label="$t('ciecytApp.EnumRespuestas.NO_CUMPLE')">NO_CUMPLE</option>
                            <option value="NO_APLICA" v-bind:label="$t('ciecytApp.EnumRespuestas.NO_APLICA')">NO_APLICA</option>
                        </select>
                        
                        <select class="form-control" name="respuesta"  v-model="ep.siNo"  disabled="true"
                          id="proyecto-respuestas-respuesta"
                          v-if="ep.preguntaTipoPreguntaTipoPregunta==`Si o No`" >
                            <option value="true" v-bind:label="$t('ciecytApp.EnumRespuestas.SI')">SI</option>
                            <option value="false" v-bind:label="$t('ciecytApp.EnumRespuestas.NO')">NO</option>
                        </select>

                        <b-form-input  type="range" min="0" v-bind:max="ep.puntajeMaximo" :step="0.1"
                         v-if="ep.preguntaTipoPreguntaTipoPregunta==`Nota (con puntaje)`" 
                         v-model="ep.respuestaNumero"  disabled="true">
                         </b-form-input>
                          <div class="mt-2">Nota: {{ ep.respuestaNumero }}</div>
                        
                         

                        <b-form-textarea  
                         v-if="ep.preguntaTipoPreguntaTipoPregunta==`Libre (sin puntaje ni viabilidad)`" 
                         v-model="ep.respuestaTexto"  disabled="true">
                        </b-form-textarea>
                  
                        </div>
                                <!-------------observaciones ------------->
                     <div class="mb-3 form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.proyectoRespuestas.observaciones')" for="proyecto-respuestas-respuesta"></label>
                     <b-form-textarea  
                         
                         v-model="ep.observaciones" readonly="true">
                        </b-form-textarea>
                     </div>
                     <!---------------------------        ---->




                     </b-card>
                     <hr>
                       </div>    
    
                     <!-- fin del for each -->
                    <!-- ------------------------------------------->
                    <div class="col-12" >
                    <b-card  
                      border-variant="primary"
                      header-bg-variant="light"
                      body-bg-variant="light"
                     header-text-variant="info">
                    <b-form-group class="mb-3" 
                    description="Si tiene comentarios o sugerencias adicionales sobre el proyecto, diligencie este apartado">
                    <label class="form-control-label" 
                    v-text="$t('ciecytApp.proyecto.recomendaciones')" for="proyecto-recomendaciones"></label>
                       
                     <div class="mb-3 form-group" >
                       <b-form-textarea  class="form-control" name="proyecto-recomendaciones"
                                   v-model="proyecto.recomendaciones"  disabled="true"  />
                        </div>
                       </b-form-group>
                       </b-card>
                       </div>
            
                    <!-- ------------------------------------------->
                    
                     
               
              
   <hr>

<!--------------------------------------------------------->
             
             </div>
          </div>
  <!---adjunto viabilidad -->
         <div class="col-12">   
                    <b-card  
                      border-variant="primary"
                      header-bg-variant="light"
                      body-bg-variant="light"
                     header-text-variant="info">  
                
                  
                       <div class="mb-3 form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.adjuntoRetroalimentacion.correcionesPropuesta')" for="adjunto-proyecto-fase-archivo"></label>
                        
                        <div>
                            <div v-if="adjuntoRetroalimentacion.id"  class="form-text text-danger clearfix">
                               <a class="pull-left" v-on:click="this.descargarRetro" v-text="$t('entity.action.open')"></a>
                                <span class="pull-left">{{adjuntoRetroalimentacion.nombreArchivoOriginal }} <br /> {{adjuntoRetroalimentacion.archivoContentType}}, {{byteSize(adjuntoRetroalimentacion.file)}}</span>
                                
                            </div> 
                 
                        </div>
                    </div> 
                   </b-card>
                  <hr>
                </div> 
         
        </div> 

         <div class="col-12">   
           <b-card  
                      border-variant="primary"
                      header-bg-variant="light"
                      body-bg-variant="light"
                     header-text-variant="info">  
            <br>La propuesta fue evaluada como: <br>
                <div class="mt-1">
                    <b-badge pill :variant="viabilidadVariant">{{ viabilidadTexto }}</b-badge>
                    <p class="text-muted mt-2 mb-1">{{ viabilidadDescripcion }}</p>
                </div>
                </b-card>
        </div>
      </form>
    </div> 

    <div class="col-sm-8" v-else>
      <div class="alert alert-warning mt-4">
        La evaluación del jurado aún no ha sido publicada. Consulte más tarde.
      </div>
    </div>
   
  </div>
</template>

<script lang="ts">
import { useVuelidate } from '@vuelidate/core';
import { Component, Inject, Vue, Hook } from 'vue-facing-decorator';
import { mixins } from 'vue-facing-decorator';

import MenuLateral from '@/components/propuesta/menu_lateral.vue';
import { IProyecto, Proyecto } from '@/shared/model/proyecto.model';
import { opcionViabilidad, textoViabilidad, descripcionViabilidad } from '@/shared/config/viabilidad';
import { IUser } from '@/shared/model/user.model';

import ProyectoService from '@/entities/proyecto/proyecto.service';
import AlertService from '@/shared/alert/alert.service';
import { IIntegranteProyecto } from '@/shared/model/integrante-proyecto.model';

import { IAdjuntoProyectoFase, AdjuntoProyectoFase } from '@/shared/model/adjunto-proyecto-fase.model';
import AdjuntoProyectoFaseService from '@/entities/adjunto-proyecto-fase/adjunto-proyecto-fase.service';

import FasesService from '@/entities/fases/fases.service';
import { IFases, Fases } from '@/shared/model/fases.model';

import { IAdjuntoRetroalimentacion, AdjuntoRetroalimentacion } from '@/shared/model/adjunto-retroalimentacion.model';
import AdjuntoRetroalimentacionService from '@/entities/adjunto-retroalimentacion/adjunto-retroalimentacion.service';

import ProyectoRespuestasService from '@/entities/proyecto-respuestas/proyecto-respuestas.service';
import { EnumRespuestas, IProyectoRespuestas, ProyectoRespuestas } from '@/shared/model/proyecto-respuestas.model';


import JhiDataUtils from '@/shared/data/data-utils.service';

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
   adjuntoRetroalimentacion: {
    nombreAdjunto: {},
    fechaCreacion: {},
    fechaModificacion: {},
    estadoAdjunto: {},
    adjuntoRetroalimentacion: {},
    nombreArchivoOriginal: {},
    archivo: {},
    fechaInicio: {},
    fechaFin: {},
    file: {},
  },
    };


@Component({
  components: { MenuLateral },
  
})
export default class Retroalimentacion extends mixins(JhiDataUtils){
  @Inject  private proyectoService: () => ProyectoService;
  @Inject  private adjuntoProyectoFaseService: () => AdjuntoProyectoFaseService;
  @Inject  private adjuntoRetroalimentacionService: () => AdjuntoRetroalimentacionService;
  @Inject  private fasesService: () => FasesService;
  @Inject  private proyectoRespuestasService: () => ProyectoRespuestasService;


  @Inject  private alertService: () => AlertService;

 

  public integrants:IIntegranteProyecto[]= [];
  public terms:Boolean=false;
  
  public proyecto: IProyecto = new Proyecto();
  public proyId: any = null;
  public isSaving = false;

    //public adjuntoProyectoFass:IAdjuntoProyectoFase[] =[];
    //public adjuntoProyectoFase: IAdjuntoProyectoFase = new AdjuntoProyectoFase();

    public adjuntoRetroalimentacions:IAdjuntoRetroalimentacion[] =[];
    public adjuntoRetroalimentacion: IAdjuntoRetroalimentacion = new AdjuntoRetroalimentacion();
    
    public fase: IFases = new Fases();
    public  authority: any="ROLE_JURADO";
    public nombreFase: any = "Propuesta";
    public proyectoRespuests: IProyectoRespuestas[] =[];
    
  //public fasePropuesta: IFases = new Fases();
  //public faseProyecto: IFases = new Fases();

    //public nombreFasePropuesta: any = "Propuesta";
    //public nombreFaseProyecto: any = "Proyecto";

  @Hook
  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.proyectoId) {
        vm.retrieveProyecto(to.params.proyectoId);
      }
      vm.initRelationships();
    });
  }

  
     descargarRetro() {
        //console.log('se hizo clic');
        this.adjuntoRetroalimentacionService().downloadFile(this.adjuntoRetroalimentacion.id, this.adjuntoRetroalimentacion.nombreArchivoOriginal);
     }

      

     eliminarRetro(ob) {
         this.adjuntoRetroalimentacionService()
         .delete(this.adjuntoRetroalimentacion.id)
           .then(() => {
             this.alertService().showAlert('El archivo se eliminó correctamente.', 'success');
             this.initRelationships();
           })
           .catch(() => {
             this.alertService().showAlert('No fue posible eliminar el archivo. Intente de nuevo.', 'danger');
           });
       }


  

  asignarDataRetro(event, entity, field, isImage){
     var fileData =  event.target.files[0];
    this.adjuntoRetroalimentacion.nombreArchivoOriginal= fileData.name;
    console.log(this.adjuntoRetroalimentacion.nombreArchivoOriginal);

    this.setFileData(event, entity, field, isImage)
    
  }

   

  getNow() {
    const today = new Date();
    const date = today.getFullYear() + '-' + (today.getMonth() + 1) + '-' + today.getDate();
    return date;
  }

  
  retrieveProyecto() {
    this.proyectoService()
    .findProyectoIntegrantes(parseInt(this.$route.params.proyectoId))
      //.find(parseInt(this.$route.params.proyectoId))
      .then(res => {
        this.proyecto = res.data;
        this.integrants = this.proyecto.listaIntegrantesProyecto;
        console.log( res.data.listaIntegrantesProyecto);
        
      });
  }

  async initRelationships() {
    this.proyId = this.$route.params.proyectoId;

     /////////////////////////////////////////////////////////7
     let res= await this.fasesService()
                .retrieveFase(this.nombreFase)   
                 this.fase = res.data;
           
          
     /////////////////// Respuestas Viabilidad
      res= await this.proyectoRespuestasService()
                .retrieveProyectoRespuestas(this.proyId, this.fase.id, this.authority)   //recup los proyresp con un idproy
                this.proyectoRespuests = res.data.filter(r => r.proyectoRespuestasPreguntaId != null);


      await this.adjuntoRetroalimentacionService()
      .findAdjuntoRetroalimentacionProyectoFaseAuthority(this.proyId,  this.fase.id, this.authority)
      .then(res => {
        this.adjuntoRetroalimentacions = res.data;
        if(this.adjuntoRetroalimentacions.length==0){
         this.adjuntoRetroalimentacion =  new AdjuntoRetroalimentacion();
        }
        else{
          this.adjuntoRetroalimentacion = this.adjuntoRetroalimentacions[0];
        }
       
      });
/////////////////////////
        
     
  }

  get retroalimentacionVisible(): boolean {
    return (
      this.proyecto.estado === 'VIABLE' ||
      this.proyecto.estado === 'APROBADA_POR_ASESOR' ||
      this.proyecto.estado === 'NO_VIABLE' ||
      this.proyecto.estado === 'CORRECCIONES_ASESOR' ||
      this.proyecto.estado === 'CORRECCIONES_JURADO_PROPUESTA'
    );
  }

  get isDisabled(){
    	return !this.terms;
    }

  get viabilidadTexto(): string {
    return textoViabilidad(this.proyecto.viabilidad);
  }

  get viabilidadDescripcion(): string {
    return descripcionViabilidad(this.proyecto.viabilidad);
  }

  get viabilidadVariant(): string {
    const opcion = opcionViabilidad(this.proyecto.viabilidad);
    return opcion ? opcion.variant : 'secondary';
  }
}
</script>

<style scoped>
</style>