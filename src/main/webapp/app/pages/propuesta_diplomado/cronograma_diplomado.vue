<template>
  <div class="row">
    <div class="col-sm-4">
      <menu-lateral-diplomado :proyectoId='$route.params.proyectoId'></menu-lateral-diplomado>
    </div>
    <div class="col-sm-8">
    <div class="mb-3 form-group">
              <label class="form-control-label" for="encabezado">
               <h2>Cronograma</h2>
               </label>
              </div>
      <div :key="key" v-for="(item, key) in cronograms">
        <b-card  :header="`Actividad Número ${key+1}`" 
       
         border-variant="primary"
        
        header-bg-variant="light"
        body-bg-variant="light"
        header-text-variant="info"
       >
          <div class="row">
            <div class="col-12">
              <div class="mb-3 form-group">
                <label
                  class="form-control-label"
                  for="proyecto-documento"
                >Descripción de la Activdad  </label>
                <input type="text" class="form-control" 
                name="actividad" id="actividad"  v-model="item.actividad"/>
              </div>
            </div>
            
            <!--<div class="col-3">
              <div class="mb-3 form-group">
                <label class="form-control-label" for="proyecto-apellido">Duración</label>
                <input type="text" class="form-control" 
                name="duracion" id="duracion" v-model="item.duracion" />
              </div>
            </div>-->
            
            <div class="col-12">
              <div class="mb-3 form-group">
                <label class="form-control-label" for="proyecto-apellido">Fecha</label>
                <label for="datepicker-sm">Fecha de Inicio</label>
                 <b-form-datepicker size="sm-6" local="ESP" 
                    :id="`fecha-inicio-${key}`"
                    :name="`fecha-inicio-${key}`"
                    value="value"
                    v-model="item.fechaInicio">
                </b-form-datepicker>
                
          
              <label for="datepicker-lg">Fecha de Finalización</label>
              <b-form-datepicker size="sm-6" local="ESP"
                :id="`fecha-fin-${key}`"
                :name="`fecha-fin-${key}`" 
                value="value"
              v-model="item.fechaFin">
              </b-form-datepicker>
              
            </div>
          </div>
          </div>
        </b-card>
        <hr />
      </div>

      <button type="button" id="save-borrador"
       class="btn btn-outline-secondary float-right"
         @click="save('borrador')" :disabled="isSaving"> 
        <font-awesome-icon :icon="['fas', 'save']"></font-awesome-icon>&nbsp;
        <span>Guardar borrador</span>
      </button>
      <button type="submit" id="save-entity" 
       class="btn btn-primary float-right"
         @click="save('continuar')" :disabled="isSaving"> 
        <font-awesome-icon :icon="['fas', 'save']"></font-awesome-icon>&nbsp;
        <span>Guardar y continuar</span>
      </button>
      <button
        type="submit"
        id="save-entity"
        class="btn btn-primary float-right"
        @click="nuevo_cronograma()" :disabled="isSaving">
        <font-awesome-icon :icon="['fas', 'plus']"></font-awesome-icon>&nbsp;
        <span></span>
      </button>
    </div>
  </div>
</template>


<script lang="ts">

import { useVuelidate } from '@vuelidate/core';
import { Component, Inject, Vue, Hook } from 'vue-facing-decorator';
import MenuLateralDiplomado from '@/components/propuesta_diplomado/menu_lateral_diplomado.vue';
import CronogramaService from '@/entities/cronograma/cronograma.service';
import { ICronograma, Cronograma } from '@/shared/model/cronograma.model';
import { IProyecto, Proyecto } from '@/shared/model/proyecto.model';
import ProyectoService from '@/entities/proyecto/proyecto.service';
import AlertService from '@/shared/alert/alert.service';
//import { CalendarPlugin } from 'bootstrap-vue';

const validations: any = {
  cronograma: {
    actividad: {},
    duracion: {},
    fechaInicio: {},
    fechaFin: {}
  }
};

@Component({
  components: { MenuLateralDiplomado },
  validations
})
export default class CronogramaDiplomado extends Vue {
   @Inject  private alertService: () => AlertService;
  @Inject  private proyectoService: () => ProyectoService;
  @Inject  private cronogramaService: () => CronogramaService;
  
  
//cronogramas = [];
public cronograms: ICronograma[] = [];
   nuevo_cronograma() {
    this.cronograms.push({
      cronogramaProyectoId: this.proyId      
     });

  }


   public proyecto: IProyecto = new Proyecto();
   public proyId: any = null;
   //public cronograms: ICronograma[] = [];
   public isSaving = false;
  public  value: any= '';
  public context: any= null;

  @Hook
  beforeRouteEnter(to, from, next) {
            next(vm => {
              
                    vm.initRelationships();
                   
            });
    }

             public async save(accion: 'borrador' | 'continuar' = 'continuar'): Promise<void> {
            this.isSaving = true;
            try {
                let i = this.cronograms.length;
                for (const e of this.cronograms) {
                    e.cronogramaProyectoId = this.proyId;
                    e.ordenVista = i++;
                    if (e.id) {
                        await this.cronogramaService().update(e);
                    } else {
                        const param = await this.cronogramaService().create(e);
                        e.id = param.id;
                    }
                }
                if (accion === 'borrador') {
                    this.alertService().showAlert('Borrador guardado. Aún puedes continuar más tarde.', 'info');
                } else {
                    this.$router.push({ name: 'PropuestaAdjuntarPropuestaDiplomadoView', params: { proyectoId: this.proyId } });
                }
            } catch (e) {
                this.alertService().showHttpError(this, e && e.response ? e.response : e);
            } finally {
                this.isSaving = false;
            }
        }

        async initRelationships() {
           try {

             this.nuevo_cronograma() ; //crea una primera tarjeta
             this.proyId = parseInt(this.$route.params.proyectoId);
            

             //this.proyecto = await this.proyectoService().find(this.proyId);
             this.proyecto = await this.proyectoService().find(this.proyId);


            
            //recuperar los cronogramas enviando un idProyecto (api)
            //retrieveCronogramaProyecto
            
            this.cronogramaService()
                .retrieveCronograma(this.proyId)
                .then(res=> {

                    //this.cronograms = res.data;
                    this.cronograms = res.data;
                   if(res.data.length ==0){
                     this.nuevo_cronograma();
                   }
                })
            
  
            
            }
            catch(e){ 
            }
 
        }

  

}
</script>

<style scoped>
</style>
