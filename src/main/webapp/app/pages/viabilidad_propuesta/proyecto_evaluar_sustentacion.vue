<template>

    <div class="asesoria-evaluar">
        <form @submit.prevent="save()">

            <!-- Cabecera del proyecto -->
            <div class="evaluacion-header mb-4">
                <div class="d-flex align-items-center justify-content-between flex-wrap">
                    <div class="d-flex align-items-center">
                        <div class="header-icon">
                            <font-awesome-icon icon="tasks" />
                        </div>
                        <div>
                            <h2 class="mb-1">Sustentación del Proyecto — Jurado</h2>
                            <div class="header-meta">
                                <span class="meta-item" v-if="proyecto.titulo">
                                    <font-awesome-icon icon="book" /> {{ proyecto.titulo }}
                                </span>
                                <span class="meta-item" v-if="proyecto.programa">
                                    <font-awesome-icon icon="user" /> {{ proyecto.programa }}
                                </span>
                                <span class="meta-item" v-if="proyecto.proyectoModalidadModalidad">
                                    <font-awesome-icon icon="th-list" /> {{ proyecto.proyectoModalidadModalidad }}
                                </span>
                            </div>
                        </div>
                    </div>
                    <b-badge pill :variant="estadoVariant(proyecto.estado)" class="estado-badge">
                        {{ estadoLabel(proyecto.estado) }}
                    </b-badge>
                </div>
            </div>

            <div class="row">
                <div class="col-12">

                    <!-- Documento adjunto -->
                    <div class="evaluacion-card mb-3" v-if="adjuntoProyectoFase.id">
                        <div class="card-head">
                            <div class="head-title">
                                <font-awesome-icon icon="paperclip" class="head-icon" />
                                Documento adjunto
                            </div>
                        </div>
                        <div class="card-body-custom d-flex align-items-center justify-content-between flex-wrap">
                            <div class="d-flex align-items-center">
                                <div class="file-icon">
                                    <font-awesome-icon icon="file-alt" />
                                </div>
                                <div>
                                    <div class="file-name">{{ adjuntoProyectoFase.nombreArchivoOriginal }}</div>
                                    <div class="file-meta">{{ adjuntoProyectoFase.archivoContentType }} · {{ byteSize(adjuntoProyectoFase.file) }}</div>
                                </div>
                            </div>
                            <button type="button" class="btn btn-outline-primary btn-sm" v-on:click="descargar()">
                                <font-awesome-icon icon="download" /> Descargar
                            </button>
                        </div>
                    </div>

                    <!-- Elementos / Preguntas -->
                    <div class="elemento-item mb-3" v-for="(ep, i) in proyectoRespuests" :key="i">
                        <div class="evaluacion-card">
                            <div class="card-head">
                                <div class="head-title">
                                    <span class="head-index">{{ i + 1 }}</span>
                                    <span v-if="ep.encabezado">{{ ep.encabezado }}</span>
                                    <span v-else>Elemento</span>
                                </div>
                                <span v-if="ep.preguntaTipoPreguntaTipoPregunta" class="tipo-badge">
                                    {{ ep.preguntaTipoPreguntaTipoPregunta }}
                                </span>
                            </div>
                            <div class="card-body-custom">
                                <div class="elemento-nombre">{{ ep.elemento }}</div>
                                <div class="elemento-descripcion" v-if="ep.proyectoRespuestasPreguntaPregunta">
                                    {{ ep.proyectoRespuestasPreguntaPregunta }}
                                </div>

                                <!-- Contenido diligenciado por el estudiante -->
                                <div class="contenido-estudiante" v-if="ep.dato">
                                    <div class="contenido-label">
                                        <font-awesome-icon icon="eye" /> Contenido del proyecto
                                    </div>
                                    <div class="contenido-texto">{{ ep.dato }}</div>
                                </div>

                                <!-- Evaluación del jurado -->
                                <div class="respuesta-section" v-if="ep.preguntaTipoPreguntaTipoPregunta">
                                    <label class="respuesta-label">Evaluación del jurado</label>

                                    <div v-if="ep.preguntaTipoPreguntaTipoPregunta === 'Cumple NoCumple NoAplica'">
                                        <b-form-radio-group
                                          v-model="ep.respuesta"
                                          buttons
                                          button-variant="outline-success"
                                          size="sm"
                                          :name="`respuesta-${i}`"
                                        >
                                            <b-form-radio value="CUMPLE">Cumple</b-form-radio>
                                            <b-form-radio value="NO_CUMPLE">No cumple</b-form-radio>
                                            <b-form-radio value="NO_APLICA">No aplica</b-form-radio>
                                        </b-form-radio-group>
                                    </div>

                                    <div v-else-if="ep.preguntaTipoPreguntaTipoPregunta === 'Si o No'">
                                        <b-form-radio-group
                                          v-model="ep.siNo"
                                          buttons
                                          button-variant="outline-primary"
                                          size="sm"
                                          :name="`sinorespuesta-${i}`"
                                        >
                                            <b-form-radio :value="true">Sí</b-form-radio>
                                            <b-form-radio :value="false">No</b-form-radio>
                                        </b-form-radio-group>
                                    </div>

                                    <div v-else-if="ep.preguntaTipoPreguntaTipoPregunta === 'Nota (con puntaje)'" class="d-flex align-items-center">
                                        <input
                                          type="number"
                                          class="form-control nota-input nota"
                                          min="0"
                                          :max="ep.puntajeMaximo"
                                          step="0.1"
                                          v-model="ep.respuestaNumero"
                                          @input="calcularNota"
                                        />
                                        <small class="text-muted ml-2" v-if="ep.puntajeMaximo">Máximo: {{ ep.puntajeMaximo }}</small>
                                    </div>

                                    <b-form-textarea
                                      v-else-if="ep.preguntaTipoPreguntaTipoPregunta === 'Libre (sin puntaje ni viabilidad)'"
                                      v-model="ep.respuestaTexto"
                                      rows="3"
                                      max-rows="6"
                                      placeholder="Escriba aquí su respuesta..."
                                    />
                                </div>

                                <!-- Observaciones -->
                                <div class="observaciones-section">
                                    <label class="respuesta-label">
                                        <font-awesome-icon icon="comment-dots" /> Observaciones
                                    </label>
                                    <b-form-textarea
                                      v-model="ep.observaciones"
                                      rows="2"
                                      max-rows="5"
                                      placeholder="Escriba aquí sus observaciones..."
                                    />
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Recomendaciones -->
                    <div class="evaluacion-card mb-3">
                        <div class="card-head">
                            <div class="head-title">
                                <font-awesome-icon icon="info-circle" class="head-icon" />
                                Recomendaciones
                            </div>
                        </div>
                        <div class="card-body-custom">
                            <small class="text-muted d-block mb-2">
                                Si tiene comentarios o sugerencias adicionales sobre la sustentación del proyecto, diligencie este apartado.
                            </small>
                            <b-form-textarea
                              class="form-control"
                              name="proyecto-recomendaciones"
                              v-model="proyecto.recomendacionesJuradoSustentacion"
                              rows="3"
                              max-rows="6"
                              placeholder="Escriba aquí sus recomendaciones..."
                            />
                        </div>
                    </div>

                    <!-- Adjunto de retroalimentación -->
                    <div class="evaluacion-card mb-3">
                        <div class="card-head">
                            <div class="head-title">
                                <font-awesome-icon icon="paperclip" class="head-icon" />
                                Documento de retroalimentación
                            </div>
                        </div>
                        <div class="card-body-custom">
                            <div v-if="adjuntoRetroalimentacion.id" class="d-flex align-items-center justify-content-between flex-wrap">
                                <div class="d-flex align-items-center">
                                    <div class="file-icon">
                                        <font-awesome-icon icon="file-alt" />
                                    </div>
                                    <div>
                                        <div class="file-name">{{ adjuntoRetroalimentacion.nombreArchivoOriginal }}</div>
                                        <div class="file-meta">{{ adjuntoRetroalimentacion.archivoContentType }} · {{ byteSize(adjuntoRetroalimentacion.file) }}</div>
                                    </div>
                                </div>
                                <div class="d-flex">
                                    <button type="button" class="btn btn-outline-primary btn-sm mr-2" v-on:click="descargarRetro()">
                                        <font-awesome-icon icon="download" /> Descargar
                                    </button>
                                    <button type="button" class="btn btn-outline-danger btn-sm" v-on:click="eliminarRetro()">
                                        <font-awesome-icon icon="times" /> Eliminar
                                    </button>
                                </div>
                            </div>
                            <div v-if="adjuntoRetroalimentacion.file == null" class="upload-zone">
                                <input type="file" ref="file_archivo" id="file_archivo" v-on:change="asignarDataRetro($event, adjuntoRetroalimentacion, 'archivo', false)" />
                                <div class="upload-zone-text">
                                    <font-awesome-icon icon="paperclip" class="mr-2" />
                                    Seleccione un archivo para adjuntar
                                </div>
                            </div>
                            <small v-else class="text-muted">Si desea subir otro adjunto, deberá eliminar el archivo actual</small>
                            <input type="hidden" class="form-control" name="archivo" id="adjunto-retroalimentacion-archivo"
                                :class="{ 'valid': !$v.adjuntoRetroalimentacion.archivo.$invalid, 'invalid': $v.adjuntoRetroalimentacion.archivo.$invalid }" v-model="$v.adjuntoRetroalimentacion.archivo.$model" />
                            <input type="hidden" class="form-control" name="archivoContentType" id="adjunto-retroalimentacion-archivoContentType"
                                v-model="adjuntoRetroalimentacion.archivoContentType" />
                            <input type="hidden" class="form-control" name="fileName" id="adjunto-retroalimentacion-fileName"
                                v-model="adjuntoRetroalimentacion.nombreArchivoOriginal" />
                        </div>
                    </div>

                    <!-- Nota final -->
                    <div class="evaluacion-card mb-3">
                        <div class="card-head">
                            <div class="head-title">
                                <font-awesome-icon icon="check-circle" class="head-icon" />
                                Evaluación — Nota final
                            </div>
                        </div>
                        <div class="card-body-custom">
                            <p class="text-muted mb-3">
                                La <strong>nota</strong> final de la sustentación del proyecto se calcula automáticamente sumando las notas de cada elemento.
                                Entre 90 y 100 puntos el proyecto se considera de muy bueno a excelente; entre 70 y menos de 90 de aceptable a bueno. Con menos de 70 puntos el proyecto es rechazado.
                            </p>
                            <div class="d-flex align-items-center">
                                <input
                                  id="definitiva"
                                  class="form-control nota-final"
                                  v-if="!nota"
                                  v-model="proyecto.nota"
                                  disabled
                                />
                                <input id="definitiva" class="form-control nota-final" v-else disabled />
                                <small class="text-muted ml-3">Nota final de la sustentación</small>
                            </div>
                        </div>
                    </div>

                    <!-- Acciones -->
                    <div class="acciones-footer">
                        <button type="button" id="cancel-save" class="btn btn-light" v-on:click="previousState()">
                            <font-awesome-icon icon="undo" />&nbsp;<span>Cancelar</span>
                        </button>
                        <button type="submit" id="save-entity" class="btn btn-primary">
                            <font-awesome-icon icon="save" />&nbsp;<span>Guardar evaluación</span>
                        </button>
                        <button type="submit" id="save-entity" class="btn btn-outline-primary" v-on:click="saveAndPreviousState()">
                            <font-awesome-icon icon="save" />&nbsp;<span>Guardar y volver</span>
                        </button>
                    </div>

                </div>
            </div>
        </form>
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
import { numeric, required, minLength, maxLength, between, url } from 'vuelidate/lib/validators';


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
  proyecto: {
    titulo: {},
    nota: { required }
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
    public  authority: any="ROLE_JURADO";
     public nombreFase: any = "Sustentacion";
     public nota: number;

    public mounted(): void {
    }

    public estadoLabel(estado: string | undefined): string {
        const estados: any = {
            EN_ELABORACION_PROPUESTA: 'En elaboración de propuesta',
            EN_ELABORACION_PROYECTO: 'En elaboración de proyecto',
            EN_REVISION_ASESOR: 'En revisión del asesor',
            EN_REVISION_JURADO_PROPUESTA: 'En revisión del jurado (propuesta)',
            CORRECCIONES_ASESOR: 'Correcciones del asesor',
            CORRECCIONES_JURADO_PROPUESTA: 'Correcciones del jurado (propuesta)',
            EN_REVISION_JURADO_PROYECTO: 'En revisión del jurado (proyecto)',
            CORRECCIONES_JURADO_PROYECTO: 'Correcciones del jurado (proyecto)',
            VIABLE: 'Propuesta viable',
            NO_VIABLE: 'Propuesta no viable',
            LISTO_PARA_SUSTENTAR: 'Listo para sustentar',
            EN_SUSTENTACION: 'En sustentación',
            NOTA_DEFINITIVA: 'Nota definitiva'
        };
        return estado ? (estados[estado] || estado) : '';
    }

    public estadoVariant(estado: string | undefined): string {
        const variants: any = {
            EN_ELABORACION_PROPUESTA: 'secondary',
            EN_ELABORACION_PROYECTO: 'secondary',
            EN_REVISION_ASESOR: 'info',
            EN_REVISION_JURADO_PROPUESTA: 'info',
            CORRECCIONES_ASESOR: 'warning',
            CORRECCIONES_JURADO_PROPUESTA: 'warning',
            EN_REVISION_JURADO_PROYECTO: 'info',
            CORRECCIONES_JURADO_PROYECTO: 'warning',
            VIABLE: 'success',
            NO_VIABLE: 'danger',
            LISTO_PARA_SUSTENTAR: 'success',
            EN_SUSTENTACION: 'primary',
            NOTA_DEFINITIVA: 'success'
        };
        return estado ? (variants[estado] || 'secondary') : 'secondary';
    }

     get getNota(){
    	return this.nota;
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
    //console.log('entro a eliminar');
    this.adjuntoRetroalimentacionService().delete(this.adjuntoRetroalimentacion.id);
    //this.adjuntoProyectoFass=null;
    this.adjuntoRetroalimentacions = null;
        //this.isSaving = false;
           (<any>this).$router.go(0);
  }


     asignarData(event, entity, field, isImage){
     var fileData =  event.target.files[0];
    this.adjuntoProyectoFase.nombreArchivoOriginal= fileData.name;
    //console.log(this.adjuntoProyectoFase.nombreArchivoOriginal);

    this.setFileData(event, entity, field, isImage)
    
  }

  asignarDataRetro(event, entity, field, isImage){
     var fileData =  event.target.files[0];
    this.adjuntoRetroalimentacion.nombreArchivoOriginal= fileData.name;
    //console.log(this.adjuntoRetroalimentacion.nombreArchivoOriginal);

    this.setFileData(event, entity, field, isImage)
    
  }
        public save(): void {//debo guardar un elemento proyecto
            try {
                //this.pregunts[0].preguntaTipoPreguntaTipoPregunta
                //this.enumRespuestas.
                this.isSaving = true;

                /////////////////////////////////////////////////
                this.proyecto.nota=this.nota;
                this.adjuntoRetroalimentacion.adjuntoRetroalimentacionProyectoId = this.proyecto.id;
    this.adjuntoRetroalimentacion.adjuntoRetroalimentacionFaseId = this.fase.id;
    this.adjuntoRetroalimentacion.authority = this.authority;
     this.adjuntoRetroalimentacion.fechaCreacion = new Date();
     //this.proyecto.fechaProyectoSustentacion =  new Date();
     //this.adjuntoRetroalimentacion.proyectoFaseProyectoTitulo =  this.proyecto.titulo;

    
    if(this.adjuntoRetroalimentacion.id) {
     //console.log("Existe el adjunto");
      this.adjuntoRetroalimentacionService()
        .update(this.adjuntoRetroalimentacion)
        .then(param => {
            this.isSaving = false;
            //(<any>this).$router.go(0);
          const message = this.$t('ciecytApp.adjuntoRetroalimentacion.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      //console.log("NO Existe el adjunto");
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
                this.proyectoService()
                    .updateProyecto(this.proyecto)
                    .then(() => {
                        this.cambiarEstadoNotaDefinitiva();
                    });
            } catch (e) {
                //TODO: mostrar mensajes de error
            }
        }

        public cambiarEstadoNotaDefinitiva(): void {
            const observacion = this.proyecto.nota
                ? 'Sustentación evaluada - nota definitiva registrada'
                : 'Sustentación evaluada sin nota registrada';
            this.proyectoService()
                .cambiarEstado(this.proyecto.id, 'NOTA_DEFINITIVA', observacion)
                .then(() => {
                    const message = this.$t('ciecytApp.proyecto.estadoActualizado', { estado: 'NOTA_DEFINITIVA' });
                    this.alertService().showAlert(message, 'success');
                })
                .catch(err => {
                    this.alertService().showAlert('Error al actualizar estado: ' + err.message, 'danger');
                });
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
                const elementosCubiertos: number[] = [];
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
                    if (x.elementoProyectoElementoId == e.preguntaElementoId){
                       proyResp.dato = x.dato;
                       elementosCubiertos.push(x.elementoProyectoElementoId);
                    }
                  });
                  if (!this.proyectoRespuestasDatos){
                   this.proyectoRespuests.push(proyResp);
                  }
                  
                }); //fin del foreach pregunts

                //Mostrar siempre los elementos que diligenció el estudiante
                this.elementoProyects.forEach(x => {
                  if (!elementosCubiertos.includes(x.elementoProyectoElementoId)) {
                    const proyRespElem: IProyectoRespuestas = new ProyectoRespuestas();
                    proyRespElem.elemento = x.elementoProyectoElementoElemento;
                    proyRespElem.proyectoRespuestasPreguntaPregunta = x.elementoProyectoProyectoDescripcion;
                    proyRespElem.proyectoRespuestasProyectoId = this.proyId;
                    proyRespElem.encabezado = x.elementoProyectoElementoElemento;
                    proyRespElem.dato = x.dato;
                    this.proyectoRespuests.push(proyRespElem);
                  }
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
         //console.log(this.adjuntoProyectoFass);
        
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
         //console.log(this.adjuntoRetroalimentacions);
         //console.log(this.adjuntoRetroalimentacion);
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

   calcularNota(){
     console.log("calculando nota");
     var d =document.getElementsByTagName("input");
     var n=0.0;
     for(var i=0;i<d.length;i++){
       if(d[i].type=="number" && d[i].className=="nota"){
         n+=parseFloat(d[i].value);
         this.nota=n;
       
       }
       var f = (<HTMLInputElement>document.getElementById("definitiva"));
      
          f.value=this.nota.toFixed(1).toString();
      
     }
   }
        
}

</script>

<style scoped>
.asesoria-evaluar {
  font-family: 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif;
}

/* Cabecera */
.evaluacion-header {
  background: linear-gradient(135deg, #003366 0%, #004488 100%);
  color: #fff;
  border-radius: 0.75rem;
  padding: 1.5rem 2rem;
  box-shadow: 0 8px 24px rgba(0, 51, 102, 0.25);
}
.evaluacion-header h2 {
  color: #fff;
  font-weight: 600;
  font-size: 1.35rem;
}
.header-icon {
  width: 52px;
  height: 52px;
  border-radius: 12px;
  background: rgba(255, 255, 255, 0.15);
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.3rem;
  margin-right: 1rem;
}
.header-meta {
  margin-top: 0.4rem;
  font-size: 0.9rem;
}
.meta-item {
  margin-right: 1.2rem;
  opacity: 0.92;
}
.meta-item svg {
  margin-right: 0.3rem;
}
.estado-badge {
  font-size: 0.78rem;
  padding: 0.55em 1.1em;
  font-weight: 600;
}

/* Tarjetas */
.evaluacion-card {
  background: #fff;
  border-radius: 0.75rem;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
  overflow: hidden;
}
.card-head {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0.85rem 1.25rem;
  border-bottom: 1px solid #eef1f5;
  background: #fafbfc;
}
.head-title {
  font-weight: 600;
  color: #1a2332;
  display: flex;
  align-items: center;
}
.head-icon {
  color: #003366;
  margin-right: 0.55rem;
}
.head-index {
  width: 26px;
  height: 26px;
  border-radius: 50%;
  background: #003366;
  color: #fff;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  font-size: 0.78rem;
  font-weight: 600;
  margin-right: 0.6rem;
  flex-shrink: 0;
}
.tipo-badge {
  font-size: 0.7rem;
  background: #eef4fb;
  color: #003366;
  border-radius: 2rem;
  padding: 0.3em 0.85em;
  font-weight: 600;
  white-space: nowrap;
}
.card-body-custom {
  padding: 1.25rem;
}

/* Archivos */
.file-icon {
  width: 46px;
  height: 46px;
  border-radius: 10px;
  background: #eef4fb;
  color: #003366;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.2rem;
  margin-right: 1rem;
  flex-shrink: 0;
}
.file-name {
  font-weight: 600;
  color: #1a2332;
}
.file-meta {
  font-size: 0.8rem;
  color: #6b7280;
}
.upload-zone {
  position: relative;
  border: 2px dashed #cbd5e1;
  border-radius: 0.6rem;
  padding: 1.25rem;
  text-align: center;
  color: #6b7280;
  background: #fafbfc;
  cursor: pointer;
}
.upload-zone input[type='file'] {
  position: absolute;
  inset: 0;
  opacity: 0;
  cursor: pointer;
  width: 100%;
  height: 100%;
}
.upload-zone-text {
  font-size: 0.9rem;
}

/* Elementos */
.elemento-nombre {
  font-size: 1.05rem;
  font-weight: 600;
  color: #1a2332;
}
.elemento-descripcion {
  color: #6b7280;
  font-size: 0.88rem;
  margin-top: 0.25rem;
  line-height: 1.5;
}
.contenido-estudiante {
  margin-top: 1rem;
  background: #f7f9fc;
  border: 1px solid #e5eaf0;
  border-left: 4px solid #17a2b8;
  border-radius: 0.5rem;
  padding: 1rem 1.1rem;
}
.contenido-label {
  font-size: 0.72rem;
  text-transform: uppercase;
  letter-spacing: 0.05em;
  color: #17a2b8;
  font-weight: 600;
  margin-bottom: 0.5rem;
}
.contenido-label svg {
  margin-right: 0.3rem;
}
.contenido-texto {
  white-space: pre-wrap;
  color: #374151;
  line-height: 1.6;
  font-size: 0.92rem;
}
.respuesta-section,
.observaciones-section {
  margin-top: 1.1rem;
}
.respuesta-label {
  font-size: 0.8rem;
  font-weight: 600;
  color: #003366;
  display: block;
  margin-bottom: 0.5rem;
}
.respuesta-label svg {
  margin-right: 0.3rem;
}
.nota-input {
  max-width: 160px;
}
.nota-final {
  max-width: 220px;
  font-size: 1.15rem;
  font-weight: 600;
  color: #003366;
}

/* Acciones */
.acciones-footer {
  display: flex;
  justify-content: flex-end;
  gap: 0.75rem;
  padding: 0.5rem 0 1rem;
}
.acciones-footer .btn {
  min-width: 140px;
  border-radius: 0.5rem;
  font-weight: 500;
}
</style>
