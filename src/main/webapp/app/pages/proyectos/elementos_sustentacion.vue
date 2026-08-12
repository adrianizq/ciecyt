<template>
    <div class="row">
       <div class="col-sm-4">
            <menu-lateral-proyecto :proyectoId='$route.params.proyectoId'></menu-lateral-proyecto>
        </div>
        <div class="col-sm-8">
           <form @submit.prevent="save()">
                <div class="row">
                <div class="form-group">
               <label class="form-control-label" for="encabezado">
               <h2>Elementos</h2>
               </label>
              </div>
                     <div class="col-12" v-for="(ep, e) in elementosProyecto" :key="e">
                         <div class="form-group">
                        </div>
                       <b-form-group
                            :label="'Elemento #' + (ep.elementoProyectoElementoId || e)"
                            :label-for="`ep-${i}`" 
                            :description="ep.elementoProyectoProyectoDescripcion"
                       >
                       <div class="form-group" >
                            <b-form-textarea rows="5"  max-rows="10" class="form-control" :name="`ep-${i}`"
                            :id="`ep-${i}`" 
                                   v-model="ep.dato"   />
                        </div>
                        </b-form-group>
                    </div>

    <!-- ------------------------------------------->
                    <div class="col-12" >
                    <b-card  
                      border-variant="primary"
                      header-bg-variant="light"
                      body-bg-variant="light"
                     header-text-variant="info">
                    <b-form-group 
                    description="Comentarios o sugerencias adicionales sobre el proyecto">
                    <label class="form-control-label" 
                    v-text="$t('ciecytApp.proyecto.nota')" for="proyecto-recomendaciones">nota</label>
                       
                     <div class="form-group" >
                       <b-form-textarea  class="form-control" name="proyecto-recomendaciones"
                                   v-model="proyecto.nota"  disabled="true"  />
                        </div>
                       </b-form-group>
                       </b-card>
                       </div>
            
                    <!-- ------------------------------------------->
                

                </div>
                <div>

                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>



                    <button type="submit" id="save-entity" class="btn btn-outline-secondary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Guardar borrador</span>
                    </button>


                </div>

            </form>
        </div>
    </div>
</template>

<script lang="ts">
import { Component, Inject, Vue } from 'vue-property-decorator';
import MenuLateralProyecto from '@/components/proyecto/menu_lateral_proyecto.vue';
import AlertService from '@/shared/alert/alert.service';
import ElementoProyectoService from '@/entities/elemento-proyecto/elemento-proyecto.service';
import { IElementoProyecto, ElementoProyecto } from '@/shared/model/elemento-proyecto.model';
import { IProyecto, Proyecto } from '@/shared/model/proyecto.model';
import ProyectoService from '@/entities/proyecto/proyecto.service';
import { IFases, Fases } from '@/shared/model/fases.model';
import FasesService from '@/entities/fases/fases.service';
import { IFormato, Formato } from '@/shared/model/formato.model';
import FormatoService from '@/entities/formato/formato.service';


    const validations: any = {};

   @Component({
        components: { MenuLateralProyecto },
        validations
    })


export default class Elementos extends Vue {


   @Inject('proyectoService') private proyectoService: () => ProyectoService;
   @Inject('elementoProyectoService') private elementoProyectoService: () => ElementoProyectoService;
   @Inject('fasesService') private fasesService: () => FasesService;
   @Inject('formatoService') private formatoService: () => FormatoService;
   @Inject('alertService') private alertService: () => AlertService;


    public elementosProyecto: IElementoProyecto[] =[];
    //public elemProy: ElementoProyecto;
    public proyecto: IProyecto = new Proyecto();
    public proyId: any = null;
    public modalidadId: number = 0;
    public fase: IFases = new Fases();
    p
    public isSaving = false;


        beforeRouteEnter(to, from, next) {
            next(vm => {

                    vm.initRelationships();
            });
        }

        public save(): void {//debo guardar un elemento proyecto
            try {
                this.isSaving = true;

                const operaciones: Promise<any>[] = [];
                for (let e of this.elementosProyecto) {
                    //Actualizando el integrante
                    e.elementoFasesId = this.fase.id;
                    if (e.id) {
                        operaciones.push(this.elementoProyectoService().update(e));
                    } else {
                        //Creando un nuevo integrante
                        operaciones.push(this.elementoProyectoService().create(e).then(param => { e.id = param.id; }));
                    }
                }

                Promise.all(operaciones).then(() => {
                    this.isSaving = false;
                    this.alertService().showAlert('Borrador guardado. Aún puedes continuar más tarde.', 'info');
                }).catch(() => {
                    this.isSaving = false;
                });

            } catch (e) {
                //TODO: mostrar mensajes de error
            }
        }

        async initRelationships() {
           try {
               
               this.proyId = parseInt(this.$route.params.proyectoId);
               await this.proyectoService()
                    .find(this.proyId)
                    .then(res=> {
                        this.proyecto = res;
                    })

                this.modalidadId = this.proyecto.proyectoModalidadId;

                await this.fasesService()
                    .findByFase("Sustentacion")
                    .then(res=> {
                        this.fase = res;
                    });

                

///////////////////////////////////////////////////////7
                await this.elementoProyectoService()
                .retrieveElementoProyecto(this.proyId, this.fase.id)
                .then(res=> {
                     this.elementosProyecto = res.data;
                });
            ////////////////////////////////////////////////////77
            }
            catch(e){
              console.log("error al recuperar la informacion de elemento ");
            }
        }
}
</script>

<style scoped>
</style>
