<template>

    <div class="row">

        <div class="col-sm-4">
            <menu-lateral-pasantia :proyectoId='$route.params.proyectoId'></menu-lateral-pasantia>
        </div>
        <div class="col-sm-8">
           <form @submit.prevent="save('continuar')">
                <div class="row">

                <div class="form-group">
              <label class="form-control-label" for="encabezado">
               <h2>Elementos</h2>
               </label>
              </div>
                     <div class="col-12" v-for="(ep, e) in elementosProyecto" :key="e">
                       

                         <div class="form-group">

                           <!-- <b-form-textarea rows="5"  max-rows="10" class="form-control" 
                            
                                   v-model="ep.elementoProyectoProyectoDescripcion" />
                            -->

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
                </div>


                <div>

                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>

<!--
                    <router-link :to="{name: 'PropuestaIntegrantesView', query: {proyectoId: this.proyecto.id}}"  tag="button" class="btn btn-primary">
                                <font-awesome-icon icon="save"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.save')">Save</span>
                            </router-link>
-->

                    <button type="button" id="save-borrador" class="btn btn-outline-secondary" v-on:click="save('borrador')">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Guardar borrador</span>
                    </button>

                    <button type="submit" id="save-entity" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Guardar y continuar</span>
                    </button>


                </div>

            </form>
        </div>
    </div>
</template>

<script lang="ts">
import { Component, Inject, Vue } from 'vue-property-decorator';
import MenuLateralPasantia from '@/components/propuesta_pasantia/menu_lateral_pasantia.vue';
import AlertService from '@/shared/alert/alert.service';
import ElementoProyectoService from '@/entities/elemento-proyecto/elemento-proyecto.service';
import { IElementoProyecto } from '@/shared/model/elemento-proyecto.model';
import { IProyecto, Proyecto } from '@/shared/model/proyecto.model';
import ProyectoService from '@/entities/proyecto/proyecto.service';
import { IFases, Fases } from '@/shared/model/fases.model';
import FasesService from '@/entities/fases/fases.service';



    const validations: any = {};

   @Component({
        components: { MenuLateralPasantia },
        validations
    })


export default class Elementos extends Vue {


   @Inject('proyectoService') private proyectoService: () => ProyectoService;
   @Inject('elementoProyectoService') private elementoProyectoService: () => ElementoProyectoService;
   @Inject('fasesService') private fasesService: () => FasesService;
   @Inject('alertService') private alertService: () => AlertService;


    public elementosProyecto: IElementoProyecto[] =[];
    //public elemProy: ElementoProyecto;
    public proyecto: IProyecto = new Proyecto();
    public proyId: any = null;
    public modalidadId: number = 0;
    public fase: IFases = new Fases();
    
    public isSaving = false;


        beforeRouteEnter(to, from, next) {
            next(vm => {

                    vm.initRelationships();
            });
        }

        public save(accion: 'borrador' | 'continuar' = 'continuar'): void {//debo guardar un elemento proyecto
            try {
                this.isSaving = true;

                for (let e of this.elementosProyecto) {
                    //Actualizando el integrante
                    e.elementoFasesId = this.fase.id;
                    if (e.id) {
                        this.elementoProyectoService().update(e); //envio un elemento
                        if (accion === 'continuar') {
                            this.$router.push({ name: 'PropuestaPasantiaCronogramaView',params:{ proyectoId: this.proyId}});
                        }
                    } else {
                        //Creando un nuevo integrante
                        this.elementoProyectoService().create(e)
                        .then(param => {
                            e.id = param.id;
                            if (accion === 'continuar') {
                                this.$router.push({ name: 'PropuestaPasantiaCronogramaView',params:{ proyectoId: this.proyId}});
                            }
                        });
                    }
                }

                if (accion === 'borrador') {
                    this.isSaving = false;
                    this.alertService().showAlert('Borrador guardado. Aún puedes continuar más tarde.', 'info');
                }

            } catch (e) {
                //TODO: mostrar mensajes de error
            }
        }

        async initRelationships() {
           try {


               this.proyId = parseInt(this.$route.params.proyectoId);


                //this.proyecto = await this.proyectoService().find(this.proyId);
               await this.proyectoService()
                    .find(this.proyId)
                    .then(res=> {
                        this.proyecto = res;
                    })

                this.modalidadId = this.proyecto.proyectoModalidadId;

 
                await this.fasesService()
                    .findByFase("Propuesta")
                    .then(res=> {
                        this.fase = res;
                    });

                     
///////////////////////////////////////////////////////7
                var  elementosProyectoTemp: IElementoProyecto[] =[];
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
