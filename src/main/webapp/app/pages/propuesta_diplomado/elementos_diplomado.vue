<template>

    <div class="row">

        <div class="col-sm-4">
            <menu-lateral-diplomado :proyectoId='$route.params.proyectoId'></menu-lateral-diplomado>
        </div>
        <div class="col-sm-8">
           <form @submit.prevent="save('continuar')">
                <div class="row">

                <div class="mb-3 form-group">
              <label class="form-control-label" for="encabezado">
               <h2>Elementos</h2>
               </label>
              </div>
                     <div class="col-12" v-for="(ep, e) in elementosProyecto" :key="e">
                       

                         <div class="mb-3 form-group">

                           <!-- <b-form-textarea rows="5"  max-rows="10" class="form-control" 
                            
                                   v-model="ep.elementoProyectoProyectoDescripcion" />
                            -->

                        </div>

                       <b-form-group class="mb-3"
                            :label="'Elemento #' + (ep.elementoProyectoElementoId || e)"
                            :label-for="`ep-${i}`" 
                            :description="ep.elementoProyectoProyectoDescripcion"
                       >

                        
                       <div class="mb-3 form-group" >

                            <b-form-textarea rows="5"  max-rows="10" class="form-control" :name="`ep-${i}`"
                            :id="`ep-${i}`" 
                                   v-model="ep.dato"   />
                             


                        </div>

                        </b-form-group>
                    </div>
                </div>


                <div>

                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')"></span>
                    </button>

<!--
                    <router-link custom v-slot="{ navigate }" :to="{name: 'PropuestaIntegrantesView', query: {proyectoId: this.proyecto.id}}"><button class="btn btn-primary" @click="navigate">
                                <font-awesome-icon icon="save"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.save')"></span>
                            </button></router-link>
-->

                    <button type="button" id="save-borrador" class="btn btn-outline-secondary" v-on:click="save('borrador')" :disabled="isSaving">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Guardar borrador</span>
                    </button>

                    <button type="submit" id="save-entity" class="btn btn-primary" :disabled="isSaving">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Guardar y continuar</span>
                    </button>


                </div>

            </form>
        </div>
    </div>
</template>

<script lang="ts">
import { useVuelidate } from '@vuelidate/core';
import { Component, Inject, Vue, Hook } from 'vue-facing-decorator';
import MenuLateralDiplomado from '@/components/propuesta_diplomado/menu_lateral_diplomado.vue';
import AlertService from '@/shared/alert/alert.service';
import ElementoProyectoService from '@/entities/elemento-proyecto/elemento-proyecto.service';
import { IElementoProyecto } from '@/shared/model/elemento-proyecto.model';
import { IProyecto, Proyecto } from '@/shared/model/proyecto.model';
import ProyectoService from '@/entities/proyecto/proyecto.service';
import { IFases, Fases } from '@/shared/model/fases.model';
import FasesService from '@/entities/fases/fases.service';


    const validations: any = {};

   @Component({
        components: { MenuLateralDiplomado },
        validations
    })


export default class Elementos extends Vue {

  // El template llamaba previousState() pero el metodo no existia, asi que el boton
  // Cancel/atrás fallaba con un TypeError y no hacia nada.
  previousState() {
    window.history.back();
  }


   @Inject  private proyectoService: () => ProyectoService;
   @Inject  private elementoProyectoService: () => ElementoProyectoService;
   @Inject  private fasesService: () => FasesService;
   @Inject  private alertService: () => AlertService;


    public elementosProyecto: IElementoProyecto[] =[];
    //public elemProy: ElementoProyecto;
    public proyecto: IProyecto = new Proyecto();
    public proyId: any = null;
    public modalidadId: number = 0;
    public fase: IFases = new Fases();
   
    public isSaving = false;


        @Hook
        beforeRouteEnter(to, from, next) {
            next(vm => {

                    vm.initRelationships();
            });
        }

        public async save(accion: 'borrador' | 'continuar' = 'continuar'): Promise<void> {
            this.isSaving = true;
            try {
                for (const e of elementosProyecto) {
                    e.elementoFasesId = this.fase.id;
                    if (e.id) {
                        await this.elementoProyectoService().update(e);
                    } else {
                        const param = await this.elementoProyectoService().create(e);
                        e.id = param.id;
                    }
                }
                if (accion === 'borrador') {
                    this.alertService().showAlert('Borrador guardado. Aún puedes continuar más tarde.', 'info');
                } else {
                    this.$router.push({ name: 'PropuestaDiplomadoCronogramaView', params: { proyectoId: this.proyId } });
                }
            } catch (e) {
                this.alertService().showHttpError(this, e && e.response ? e.response : e);
            } finally {
                this.isSaving = false;
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
                await this.elementoProyectoService()
                .retrieveElementoProyecto(this.proyId, this.fase.id)
                .then(res=> {
                     this.elementosProyecto = res.data;
                });
            ////////////////////////////////////////////////////77

            }
            catch(e){
            }

        }

       

}
</script>

<style scoped>
</style>
