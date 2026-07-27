<template>

    <div class="row">

        <div class="col-sm-8">
           <form @submit.prevent="save()">
                <div class="row">
                   
                 <div class="col-12">
                    <b-card  
                      border-variant="primary"
                      header-bg-variant="light"
                      body-bg-variant="light"
                     header-text-variant="info">  
                   
                        <table> 
               <tr><td><h2>Correcciones del Proyecto por el Asesor</h2></td></tr>
               <tr><td>Título: {{proyecto.titulo}} </td></tr>
               <tr><td>Programa: {{proyecto.programa}} </td></tr>
                <tr><td> &nbsp; </td></tr>
             </table>
                  </b-card>
                  <hr></hr>
                </div> 

                <div class="col-12">
                    <b-card  
                      border-variant="primary"
                      header-bg-variant="light"
                      body-bg-variant="light"
                     header-text-variant="info">  
                
                    <!-------------------------DESCARGAR ------->
                       <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.adjuntoProyectoFase.archivo')" for="adjunto-proyecto-fase-archivo">Archivo</label>
                        <div>
                            <div v-if="adjuntoProyectoFase.id"  class="form-text text-danger clearfix">
                            
                            
                                <!--<a class="pull-left" v-on:click="openFile(adjuntoProyectoFase.archivoContentType, adjuntoProyectoFase.file)" v-text="$t('entity.action.open')">open</a><br> -->
                                <a class="pull-left" v-on:click="this.descargar" v-text="$t('entity.action.open')">open </a>
                                <span class="pull-left">{{adjuntoProyectoFase.nombreArchivoOriginal }} <br /> {{adjuntoProyectoFase.archivoContentType}}, {{byteSize(adjuntoProyectoFase.file)}}</span>
                                
                            </div> 
                           
                           <!-- <input v-if="adjuntoProyectoFase.file==null" type="file" ref="file_archivo" id="file_archivo" v-on:change="asignarData($event, adjuntoProyectoFase, 'archivo', false)" v-text="$t('entity.action.addblob')"/>-->
                            
                        </div>
                        
                    </div> 
                   </b-card>
                  <hr></hr>
                </div> 
               
                   
           
                    <div class="col-12" v-for="(ep, i) in proyectoRespuests" :key="i">
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
                                   v-model="ep.dato"  v-if="ep.dato!=null" readonly="true" />
                            </div>
                       </b-form-group>

                        <!-- TIPOS Pregunta--------------------------------------------->
                        <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.proyectoRespuestas.respuesta')" for="proyecto-respuestas-respuesta">Respuesta</label>
                        <select class="form-control" c  v-model="ep.respuesta" 
                          id="proyecto-respuestas-respuesta"
                          v-if="ep.preguntaTipoPreguntaTipoPregunta==`Cumple NoCumple NoAplica`" >
                            <option value="CUMPLE" v-bind:label="$t('ciecytApp.EnumRespuestas.CUMPLE')">CUMPLE</option>
                            <option value="NO_CUMPLE" v-bind:label="$t('ciecytApp.EnumRespuestas.NO_CUMPLE')">NO_CUMPLE</option>
                            <option value="NO_APLICA" v-bind:label="$t('ciecytApp.EnumRespuestas.NO_APLICA')">NO_APLICA</option>
                        </select>
                        
                        <select class="form-control" name="respuesta"  v-model.bool="ep.siNo" 
                          id="proyecto-respuestas-respuesta"
                          v-if="ep.preguntaTipoPreguntaTipoPregunta==`Si o No`" >
                            <option value="true" v-bind:label="$t('ciecytApp.EnumRespuestas.SI')">SI</option>
                            <option value="false" v-bind:label="$t('ciecytApp.EnumRespuestas.NO')">NO</option>
                        </select>

                        <!--<b-form-input  type="range" min="0" v-bind:max="ep.puntajeMaximo" :step="0.1"
                         v-if="ep.preguntaTipoPreguntaTipoPregunta==`Nota (con puntaje)`" 
                         v-model="ep.respuestaNumero">
                         </b-form-input>
                          <div class="mt-2">Nota: {{ ep.respuestaNumero }}</div>
                        -->
                         <div class="mt-2">
                            <input type="number" min="0" v-bind:max="ep.puntajeMaximo" :step="0.1"
                            v-if="ep.preguntaTipoPreguntaTipoPregunta==`Nota (con puntaje)`" 
                            v-model="ep.respuestaNumero">
                         </div>     
                         

                        <b-form-textarea  
                         v-if="ep.preguntaTipoPreguntaTipoPregunta==`Libre (sin puntaje ni viabilidad)`" 
                         v-model="ep.respuestaTexto">
                        </b-form-textarea>
                  
                        </div>
                       <!-------------observaciones ------------->
                     <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.proyectoRespuestas.observaciones')" for="proyecto-respuestas-respuesta">observaciones</label>
                     <b-form-textarea  
                         
                         v-model="ep.observaciones">
                        </b-form-textarea>
                     </div>
                     <!---------------------------        ---->

                     </b-card>
                     <hr>
                          
    
                    </div> <!-- fin del for each -->
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
                                   v-model="proyecto.recomendacionesAsesorProyecto"  />
                        </div>
                       </b-form-group>
                       </b-card>
                       </div>
                    <!-- ------------------------------------------->
                    
                     
                </div>
              
   <hr></hr>

 <!-------------------------DESCARGAR ------->
                       <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.adjuntoRetroalimentacion.archivo')" for="adjunto-retroalimentacion-archivo">Archivo</label>
                        <div>
                            <div v-if="adjuntoRetroalimentacion.id"  class="form-text text-danger clearfix">
                            
                            
                                <!--<a class="pull-left" v-on:click="openFile(adjuntoProyectoFase.archivoContentType, adjuntoProyectoFase.file)" v-text="$t('entity.action.open')">open</a><br> -->
                                <a class="pull-left" v-on:click="this.descargarRetro" v-text="$t('entity.action.open')">open</a>
                                <span class="pull-left">{{adjuntoRetroalimentacion.nombreArchivoOriginal }} <br /> {{adjuntoRetroalimentacion.archivoContentType}}, {{byteSize(adjuntoRetroalimentacion.file)}}</span>
                                <button type="button" v-on:click="this.eliminarRetro" v-text="$t('entity.action.delete')">
                                        class="btn btn-secondary btn-xs pull-right">
                                    <font-awesome-icon icon="times"></font-awesome-icon>
                                </button> 
                            </div> 
                            <input v-if="adjuntoRetroalimentacion.file==null" type="file" ref="file_archivo" id="file_archivo" v-on:change="asignarDataRetro($event, adjuntoRetroalimentacion, 'archivo', false)" v-text="$t('entity.action.addblob')"/>
                            <span  v-if="adjuntoRetroalimentacion.file!=null">Si desea subir otro adjunto, deberá eliminar el archivo actual</span>
                        </div>
                        <input type="hidden" class="form-control" name="archivo" id="adjunto-retroalimentacion-archivo"
                            :class="{'valid': !$v.adjuntoRetroalimentacion.archivo.$invalid, 'invalid': $v.adjuntoRetroalimentacion.archivo.$invalid }" v-model="$v.adjuntoRetroalimentacion.archivo.$model" />
                        <input type="hidden" class="form-control" name="archivoContentType" id="adjunto-retroalimentacion-archivoContentType"
                            v-model="adjuntoRetroalimentacion.archivoContentType" />
                         <input type="hidden" class="form-control" name="fileName" id="adjunto-retroalimentacion-fileName"
                            v-model="adjuntoRetroalimentacion.nombreArchivoOriginal" />
                    </div> 


 
<hr></hr>
                <div>

                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>



                    <button type="submit" id="save-entity" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>

                    <button type="submit" id="save-entity" class="btn btn-primary"  v-on:click="saveAndPreviousState()">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.saveandback')">Save</span>
                    </button>


                </div>

            </form>
        </div>
    </div>
</template>

<script lang="ts">
import { Component, Inject, Vue } from 'vue-property-decorator';
import { mixins } from 'vue-class-component';
import MenuLateral from '@/components/propuesta/menu_lateral.vue';
import AlertService from '@/shared/alert/alert.service';
import ProyectoRespuestasService from '@/entities/proyecto-respuestas/proyecto-respuestas.service';
import { EnumRespuestas, IProyectoRespuestas, ProyectoRespuestas } from '@/shared/model/proyecto-respuestas.model';
import PreguntaService from '@/entities/pregunta/pregunta.service';
import { IPregunta, Pregunta } from '@/shared/model/pregunta.model';
import { IProyecto, Proyecto } from '@/shared/model/proyecto.model';
import ProyectoService from '@/entities/proyecto/proyecto.service';
import { IElementoProyecto, ElementoProyecto } from '@/shared/model/elemento-proyecto.model';
import ElementoProyectoService from '@/entities/elemento-proyecto/elemento-proyecto.service';
import FasesService from '@/entities/fases/fases.service';
import { IFases, Fases } from '@/shared/model/fases.model';
import { IAdjuntoProyectoFase, AdjuntoProyectoFase } from '@/shared/model/adjunto-proyecto-fase.model';
import AdjuntoProyectoFaseService from '@/entities/adjunto-proyecto-fase/adjunto-proyecto-fase.service';

import { IAdjuntoRetroalimentacion, AdjuntoRetroalimentacion } from '@/shared/model/adjunto-retroalimentacion.model';
import AdjuntoRetroalimentacionService from '@/entities/adjunto-retroalimentacion/adjunto-retroalimentacion.service';

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
   proyecto:{
     enviado: {},
   },
    };

   @Component({
        components: { MenuLateral },
        validations
    })


export default class PropuestaEvaluar extends mixins(JhiDataUtils){


   @Inject('proyectoService') private proyectoService: () => ProyectoService;
   @Inject('proyectoRespuestasService') private proyectoRespuestasService: () => ProyectoRespuestasService;
   @Inject('preguntaService') private preguntaService: () => PreguntaService;
   @Inject('fasesService') private fasesService: () => FasesService;
   @Inject('elementoProyectoService') private elementoProyectoService: () => ElementoProyectoService;
   @Inject('adjuntoProyectoFaseService') private adjuntoProyectoFaseService: () => AdjuntoProyectoFaseService;
   @Inject('adjuntoRetroalimentacionService') private adjuntoRetroalimentacionService: () => AdjuntoRetroalimentacionService;

   @Inject('alertService') private alertService: () => AlertService;

    public adjuntoProyectoFass:IAdjuntoProyectoFase[] =[];
    public adjuntoProyectoFase: IAdjuntoProyectoFase = new AdjuntoProyectoFase();

    public adjuntoRetroalimentacions:IAdjuntoRetroalimentacion[] =[];
    public adjuntoRetroalimentacion: IAdjuntoRetroalimentacion = new AdjuntoRetroalimentacion();

    public pregunts: IPregunta[] = [];
    public fase: IFases = new Fases();
    public proyectoRespuests: IProyectoRespuestas[] =[];
    public elementoProyects: IElementoProyecto[]=[];
    public elemProy: ElementoProyecto;
    public proyecto: IProyecto = new Proyecto();
    public proyId: any = null;
  
  
    public modalidadId: number = 0;
    public enumRespuestas: EnumRespuestas;

    public isSaving = false;
    public proyectoRespuestasDatos: boolean = false;
    public  authority: any="ROLE_ASESOR";
     public nombreFase: any = "Proyecto";
    public mounted(): void {
    }

        beforeRouteEnter(to, from, next) {
            next(vm => {
                    
                    vm.initRelationships();
            });
        }


     descargar() {
        //console.log('se hizo clic');
        this.adjuntoProyectoFaseService().downloadFile(this.adjuntoProyectoFase.id, this.adjuntoProyectoFase.nombreArchivoOriginal);
     }

     descargarRetro() {
        //console.log('se hizo clic');
        this.adjuntoRetroalimentacionService().downloadFile(this.adjuntoRetroalimentacion.id, this.adjuntoRetroalimentacion.nombreArchivoOriginal);
     }

     eliminarRetro(ob) {
    console.log('entro a eliminar');
    this.adjuntoRetroalimentacionService().delete(this.adjuntoRetroalimentacion.id);
    //this.adjuntoProyectoFass=null;
    this.adjuntoRetroalimentacions = null;
        //this.isSaving = false;
           (<any>this).$router.go(0);
  }


     asignarData(event, entity, field, isImage){
     var fileData =  event.target.files[0];
    this.adjuntoProyectoFase.nombreArchivoOriginal= fileData.name;
    console.log(this.adjuntoProyectoFase.nombreArchivoOriginal);

    this.setFileData(event, entity, field, isImage)
    
  }

  asignarDataRetro(event, entity, field, isImage){
     var fileData =  event.target.files[0];
    this.adjuntoRetroalimentacion.nombreArchivoOriginal= fileData.name;
    console.log(this.adjuntoRetroalimentacion.nombreArchivoOriginal);

    this.setFileData(event, entity, field, isImage)
    
  }
        public save(): void {//debo guardar un elemento proyecto
            try {
                //this.pregunts[0].preguntaTipoPreguntaTipoPregunta
                //this.enumRespuestas.
                this.isSaving = true;

                /////////////////////////////////////////////////
                this.adjuntoRetroalimentacion.adjuntoRetroalimentacionProyectoId = this.proyecto.id;
    this.adjuntoRetroalimentacion.adjuntoRetroalimentacionFaseId = this.fase.id;
    this.adjuntoRetroalimentacion.authority = this.authority;
     this.adjuntoRetroalimentacion.fechaCreacion = new Date();
     //this.adjuntoRetroalimentacion.proyectoFaseProyectoTitulo =  this.proyecto.titulo;

    
    if(this.adjuntoRetroalimentacion.id) {
     console.log("Existe el adjunto");
      this.adjuntoRetroalimentacionService()
        .update(this.adjuntoRetroalimentacion)
        .then(param => {
            this.isSaving = false;
            //(<any>this).$router.go(0);
          const message = this.$t('ciecytApp.adjuntoRetroalimentacion.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      console.log("NO Existe el adjunto");
      this.adjuntoRetroalimentacionService()
        .create(this.adjuntoRetroalimentacion)
        .then(param => {
          this.isSaving = false;
           //(<any>this).$router.go(0);
          const message = this.$t('ciecytApp.adjuntoRetroalimentacion.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
   /////////////////////////////////////////////////
                for (let e of this.proyectoRespuests) {
                       e.faseId=this.fase.id;
                        e.authority=this.authority;
                    if (e.id) {
                        
                        this.proyectoRespuestasService().update(e)
                        .then(param => {
                           // //this.$router.push({ name: 'PropuestaPresupuestoView',params:{ proyectoId: this.proyId}});
                            (<any>this).$router.go(0);
                        });
                      
                    } else {
                        
                        this.proyectoRespuestasService().create(e)
                        .then(param => {
                           // //this.$router.push({ name: 'PropuestaPresupuestoView',params:{ proyectoId: this.proyId}});
                            (<any>this).$router.go(0);
                        });
                        
                    }
                }
                

            } catch (e) {
                //TODO: mostrar mensajes de error
            }

            //actualizar el proyecto para que se guarde la viabilidad
             try {
                this.proyectoService().updateProyecto(this.proyecto);

                  } catch (e) {
                //TODO: mostrar mensajes de error
            }
        }

        async initRelationships() {
           try {
               this.proyId = parseInt(this.$route.params.proyectoId);
               this.proyecto = await this.proyectoService().find(this.proyId);
               this.modalidadId = this.proyecto.proyectoModalidadId;

                let res= await this.fasesService()
                .retrieveFase(this.nombreFase)   
                 this.fase = res.data;
               
                res= await this.elementoProyectoService()
                .retrieveElementoProyecto(this.proyId, this.fase.id)   //recup los ElementosProyecto con un idproy
                 this.elementoProyects = res.data;

               

                res= await this.proyectoRespuestasService()
                .retrieveProyectoRespuestas(this.proyId, this.fase.id, this.authority)   //recup los proyresp con un idproy
                this.proyectoRespuests = res.data;
                if (this.proyectoRespuests.length>0){
                        this.proyectoRespuestasDatos=true;
                    }
                else{
                        this.proyectoRespuestasDatos=false;
                    }
                    //console.log(this.proyectoRespuestasDatos);
               
                

              //Obtenienedo los elementos de acuerdo a la modalidad
                res = await  this.preguntaService()
                 .retrievePreguntasModalidadyFaseyAuthority(this.modalidadId, this.fase.id, this.authority)
                
                    this.pregunts = res.data;
                this.pregunts.forEach(e => {
                  var proyResp: IProyectoRespuestas = new ProyectoRespuestas();
                  proyResp.proyectoRespuestasPreguntaPregunta= e.pregunta;
                  proyResp.proyectoRespuestasPreguntaId = e.id;
                  proyResp.proyectoRespuestasProyectoId = this.proyId;
                  //ubicar un elemento, no esta en proyectoRespuestas
                  proyResp.elemento = e.preguntaElemento;
                  proyResp.preguntaTipoPreguntaId = e.preguntaTipoPreguntaId;
                  proyResp.preguntaTipoPreguntaTipoPregunta = e.preguntaTipoPreguntaTipoPregunta;
                  proyResp.encabezado = e.encabezado;
                  proyResp.puntajeMaximo = e.puntajeMaximo;
                  this.elementoProyects.forEach(x => {
                      console.log("Entra al ciclo elementoProyecto");
                    if (x.elementoProyectoElementoId == e.preguntaElementoId){
                         proyResp.dato = x.dato;    
                    }
                  });
                  if (!this.proyectoRespuestasDatos){
                   this.proyectoRespuests.push(proyResp);
                  }
                  
                }); //fin del foreach pregunts
    
          
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
        
      });

      res=  await this.adjuntoRetroalimentacionService()
      .findAdjuntoRetroalimentacionProyectoFaseAuthority(this.proyId,  this.fase.id, this.authority)
      .then(res => {
        this.adjuntoRetroalimentacions = res.data;
        if(this.adjuntoRetroalimentacions.length==0){
         this.adjuntoRetroalimentacion =  new AdjuntoRetroalimentacion();
        }
        else{
          this.adjuntoRetroalimentacion = this.adjuntoRetroalimentacions[0];
        }
         console.log(this.adjuntoRetroalimentacions);
         console.log(this.adjuntoRetroalimentacion);
      });
     
            }
            catch(e){
              console.log("error al recuperar la informacion de elemento ");
            }
        }
public previousState() {
    this.$router.go(-1);
  }
        

public saveAndPreviousState() {
    //this.save();
    this.$router.go(-1);
  }
        
}

</script>

<style scoped>
</style>
