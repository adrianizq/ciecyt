<template>
  <div class="row">
    <div class="col-sm-4">
      <menu-lateral :proyectoId="$route.params.proyectoId"></menu-lateral>
    </div>
    
   <!-- <div class="col-sm-8"  v-if="!proyecto.preEnviado"> -->
    <div class="col-sm-8">
      <form @submit.prevent="save()">
        <div class="row">
          <div class="col-12">
            
            <div class="form-group">
              <label class="form-control-label" for="proyecto-titulo"></label>
               <h2>Retroalimentación  del  Asesor</h2>
<!--------------------------------------------------------->

                    <div class="col-12" v-for="(ep, i) in proyectoRespuestsAsesor" :key="i">
                    <b-card  
                      border-variant="primary"
                      header-bg-variant="light"
                      body-bg-variant="light"
                     header-text-variant="info">
                     <!--<div class="text-secondary"> Tipo de pregunta {{ep.preguntaTipoPreguntaTipoPregunta}} </div>-->
                     <label  class="p-3 mb-2 bg-info text-white container-fluid">{{ep.encabezado}} </label>
                     
                     <b-form-group
                            :label="ep.elemento"
                            :label-for="`ep-${i}`" 
                            :description="ep.proyectoRespuestasPreguntaPregunta"
                                                   
                       >
                       <div class="form-group" >
                           

                            
                            <b-form-textarea rows="2"  max-rows="10" class="form-control" :name="`ep-${i}`"
                            :id="`ep-${i}`" v-if="ep.elemento"
                                   v-model="ep.elemento"  disabled="true"  />
                       </div>
                       </b-form-group>

                        
                       <!--- dato  -->
                          <b-form-group>
                       <div class="form-group" >

                            <b-form-textarea rows="2"  max-rows="10" class="form-control" :name="`ep-${i}`"
                            :id="`ep-${i}` " 
                                   v-model="ep.dato"  v-if="ep.dato!=null" readonly="true"  disabled="true" />
                            </div>
                       </b-form-group>

                        <!-- TIPOS Pregunta--------------------------------------------->
                        <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.proyectoRespuestas.respuesta')" for="proyecto-respuestas-respuesta">Respuesta</label>
                        <select class="form-control" c  v-model="ep.respuesta"   disabled="true"
                          id="proyecto-respuestas-respuesta"
                          v-if="ep.preguntaTipoPreguntaTipoPregunta==`Cumple NoCumple NoAplica`" >
                            <option value="CUMPLE" v-bind:label="$t('ciecytApp.EnumRespuestas.CUMPLE')">CUMPLE</option>
                            <option value="NO_CUMPLE" v-bind:label="$t('ciecytApp.EnumRespuestas.NO_CUMPLE')">NO_CUMPLE</option>
                            <option value="NO_APLICA" v-bind:label="$t('ciecytApp.EnumRespuestas.NO_APLICA')">NO_APLICA</option>
                        </select>
                        
                        <select class="form-control" name="respuesta"  v-model.bool="ep.siNo"  disabled="true"
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
                     <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.proyectoRespuestas.observaciones')" for="proyecto-respuestas-respuesta">observaciones</label>
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
                    <b-form-group 
                    description="Si tiene comentarios o sugerencias adicionales sobre el proyecto, diligencie este apartado">
                    <label class="form-control-label" 
                    v-text="$t('ciecytApp.proyecto.recomendaciones')" for="proyecto-recomendaciones">Recomendaciones</label>
                       
                     <div class="form-group" >
                       <b-form-textarea  class="form-control" name="proyecto-recomendaciones"
                                   v-model="proyecto.recomendacionesAsesorPropuesta"  disabled="true"  />
                        </div>
                       </b-form-group>
                       </b-card>
                       </div>
            
                    <!-- ------------------------------------------->
                    
                     
               
              
   <hr></hr>

<!--------------------------------------------------------->
             
             </div>
          </div>

         <div class="col-12">    <!-------------------------correcciones asesor ------->
                    <b-card  
                      border-variant="primary"
                      header-bg-variant="light"
                      body-bg-variant="light"
                     header-text-variant="info">  
                
                 
                       <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.adjuntoRetroalimentacion.correcionesAsesor')" for="adjunto-proyecto-fase-archivo">Correcciones del Asesor</label>
                        
                        <div>
                            <div v-if="adjuntoAsesorRetroalimentacion.id"  class="form-text text-danger clearfix">
                               <a class="pull-left" v-on:click="this.descargarAsesorRetro" v-text="$t('entity.action.open')">open </a>
                                <span class="pull-left">{{adjuntoAsesorRetroalimentacion.nombreArchivoOriginal }} <br /> {{adjuntoAsesorRetroalimentacion.archivoContentType}}, {{byteSize(adjuntoAsesorRetroalimentacion.file)}}</span>
                                
                            </div> 
                 
                        </div>
                        
                    </div> 
                   </b-card>
                  <hr></hr>
                </div> 

<!---Correciones propuesta
         <div class="col-12">   
                    <b-card  
                      border-variant="primary"
                      header-bg-variant="light"
                      body-bg-variant="light"
                     header-text-variant="info">  
                
                  
                       <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.adjuntoRetroalimentacion.correcionesPropuesta')" for="adjunto-proyecto-fase-archivo">Archivo</label>
                        
                        <div>
                            <div v-if="adjuntoRetroalimentacion.id"  class="form-text text-danger clearfix">
                               <a class="pull-left" v-on:click="this.descargarRetro" v-text="$t('entity.action.open')">open </a>
                                <span class="pull-left">{{adjuntoRetroalimentacion.nombreArchivoOriginal }} <br /> {{adjuntoRetroalimentacion.archivoContentType}}, {{byteSize(adjuntoRetroalimentacion.file)}}</span>
                                
                            </div> 
                 
                        </div>
                        
                    </div> 
                   </b-card>
                  <hr></hr>
                </div> -->
               

        </div> 

        <div>
        
        </div>
      </form>
    </div> 
   
  </div>
</template>

<script lang="ts">
import { Component, Inject, Vue } from 'vue-property-decorator';
import { mixins } from 'vue-class-component';

import MenuLateral from '@/components/propuesta/menu_lateral.vue';
import { IProyecto, Proyecto } from '@/shared/model/proyecto.model';
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
  @Inject('proyectoService') private proyectoService: () => ProyectoService;
  @Inject('adjuntoProyectoFaseService') private adjuntoProyectoFaseService: () => AdjuntoProyectoFaseService;
  @Inject('adjuntoRetroalimentacionService') private adjuntoRetroalimentacionService: () => AdjuntoRetroalimentacionService;
  @Inject('fasesService') private fasesService: () => FasesService;
  @Inject('proyectoRespuestasService') private proyectoRespuestasService: () => ProyectoRespuestasService;


  @Inject('alertService') private alertService: () => AlertService;

 

  public integrants:IIntegranteProyecto[]= [];
  public terms:Boolean=false;
  
  public proyecto: IProyecto = new Proyecto();
  public proyId: any = null;
  public isSaving = false;

   
    public adjuntoAsesorRetroalimentacions:IAdjuntoRetroalimentacion[] =[];
    public adjuntoAsesorRetroalimentacion: IAdjuntoRetroalimentacion = new AdjuntoRetroalimentacion();
    
    public fase: IFases = new Fases();
    
    public nombreFase: any = "Propuesta";
    public  authorityAsesor: any="ROLE_ASESOR";
    public proyectoRespuestsAsesor: IProyectoRespuestas[] =[];
    
  

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.proyectoId) {
        vm.retrieveProyecto(to.params.proyectoId);
      }
      vm.initRelationships();
    });
  }

  

       descargarAsesorRetro() {
        //console.log('se hizo clic');
        this.adjuntoRetroalimentacionService().downloadFile(this.adjuntoAsesorRetroalimentacion.id, this.adjuntoAsesorRetroalimentacion.nombreArchivoOriginal);
     }

    


   

  

   asignarDataAsesorRetro(event, entity, field, isImage){
     var fileData =  event.target.files[0];
    this.adjuntoAsesorRetroalimentacion.nombreArchivoOriginal= fileData.name;
    console.log(this.adjuntoAsesorRetroalimentacion.nombreArchivoOriginal);

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
           
          
     /////////////////// Respuestas Asesor
      res= await this.proyectoRespuestasService()
                .retrieveProyectoRespuestas(this.proyId, this.fase.id, this.authorityAsesor)   //recup los proyresp con un idproy
                this.proyectoRespuestsAsesor = res.data;


      
/////////////////////////
         res=  await this.adjuntoRetroalimentacionService()
      .findAdjuntoRetroalimentacionProyectoFaseAuthority(this.proyId,  this.fase.id, this.authorityAsesor)
      .then(res => {
        this.adjuntoAsesorRetroalimentacions = res.data;
        if(this.adjuntoAsesorRetroalimentacions.length==0){
         this.adjuntoAsesorRetroalimentacion =  new AdjuntoRetroalimentacion();
        }
        else{
          this.adjuntoAsesorRetroalimentacion = this.adjuntoAsesorRetroalimentacions[0];
        }
       
      });
     
 
     
  }

  get isDisabled(){
    	return !this.terms;
    }
}
</script>

<style scoped>
</style>