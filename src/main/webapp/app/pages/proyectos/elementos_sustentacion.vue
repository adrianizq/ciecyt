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
                            :label="ep.elementoProyectoElementoElemento"
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



                    <button type="submit" id="save-entity" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
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
import ElementoService from '@/entities/elemento/elemento.service';
import { IElemento, Elemento } from '@/shared/model/elemento.model';
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
   @Inject('elementoService') private elementoService: () => ElementoService;
   @Inject('elementoProyectoService') private elementoProyectoService: () => ElementoProyectoService;
   @Inject('fasesService') private fasesService: () => FasesService;
   @Inject('formatoService') private formatoService: () => FormatoService;
   @Inject('alertService') private alertService: () => AlertService;


    public elements: IElemento[] = [];
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

                for (let e of this.elementosProyecto) {
                    //Actualizando el integrante
                    e.elementoFasesId = this.fase.id;
                    if (e.id) {
                        this.elementoProyectoService().update(e); //envio un elemento
                        //this.$router.push({ name: 'PropuestaPresupuestoView',params:{ proyectoId: this.proyId}});
                    } else {
                        //Creando un nuevo integrante
                        this.elementoProyectoService().create(e)
                        //.then(param => {
                        //    this.$router.push({ name: 'PropuestaPresupuestoView',params:{ proyectoId: this.proyId}});
                        //});
                    }
                }

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
                var  elementosProyectoTemp: IElementoProyecto[] =[];
                await this.elementoProyectoService()
                .retrieveElementoProyecto(this.proyId, this.fase.id)
                .then(res=> {
                     //this.elementosProyecto = res.data;
                     elementosProyectoTemp = res.data;
                });
            ////////////////////////////////////////////////////77    

                await this.elementoService()
                //.retrieveElementosModalidad( this.modalidadId)
                //.retrieveElementosFase(this.fase.id)
                //.retrieveElementosFaseFormato(this.fase.id, this.formato.id)
                .retrieveElementosFaseModalidad(this.fase.id, this.modalidadId)
                .then(res => {
                    this.elements = res.data;
                  //copiar los datos de elementos a elemento-proyecto
                  this.elements.forEach(e => {
                       var existe= false;
                         elementosProyectoTemp.forEach(ep => {
                             if(e.id==ep.elementoProyectoElementoId){
                               
                                this.elementosProyecto.push(ep);
                    
                                 existe=true;
                             }
                         });
                  
                    if(existe==false){
                    var elemProy: IElementoProyecto = new ElementoProyecto();
                    elemProy.elementoProyectoElementoElemento= e.elemento;
                    elemProy.elementoProyectoProyectoDescripcion = e.descripcion;
                    elemProy.elementoProyectoElementoId = e.id;
                    elemProy.elementoProyectoProyectoId = this.proyId;
                    this.elementosProyecto.push(elemProy);
                    }
                   }); 
                 });
            }
            catch(e){
              console.log("error al recuperar la informacion de elemento ");
            }
        }
}
</script>

<style scoped>
</style>
