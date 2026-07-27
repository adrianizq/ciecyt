<template>
  <div class="row">
    <div class="col-sm-4">
      <menu-lateral-diplomado :proyectoId='$route.params.proyectoId'></menu-lateral-diplomado>
    </div>
    <div class="col-sm-8">
    <div class="form-group">
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
              <div class="form-group">
                <label
                  class="form-control-label"
                  for="proyecto-documento"
                >Descripción de la Activdad  </label>
                <input type="text" class="form-control" 
                name="actividad" id="actividad"  v-model="item.actividad"/>
              </div>
            </div>
            
            <!--<div class="col-3">
              <div class="form-group">
                <label class="form-control-label" for="proyecto-apellido">Duración</label>
                <input type="text" class="form-control" 
                name="duracion" id="duracion" v-model="item.duracion" />
              </div>
            </div>-->
            
            <div class="col-12">
              <div class="form-group">
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

      <button type="submit" id="save-entity" 
       class="btn btn-primary float-right"
         @click="save()" > 
        <font-awesome-icon :icon="['fas', 'save']"></font-awesome-icon>&nbsp;
        <span>Guardar</span>
      </button>
      <button
        type="submit"
        id="save-entity"
        class="btn btn-primary float-right"
        @click="nuevo_cronograma()"
      >
        <font-awesome-icon :icon="['fas', 'plus']"></font-awesome-icon>&nbsp;
        <span></span>
      </button>
    </div>
  </div>
</template>


<script lang="ts">

import { Component, Inject, Vue } from 'vue-property-decorator';
import MenuLateralDiplomado from '@/components/propuesta_diplomado/menu_lateral_diplomado.vue';
import CronogramaService from '@/entities/cronograma/cronograma.service';
import { ICronograma, Cronograma } from '@/shared/model/cronograma.model';
import { IProyecto, Proyecto } from '@/shared/model/proyecto.model';
import ProyectoService from '@/entities/proyecto/proyecto.service';
import AlertService from '@/shared/alert/alert.service';
//import { mixins } from 'vue-class-component';
//import Vue2Filters from 'vue2-filters';
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
   @Inject('alertService') private alertService: () => AlertService;
  @Inject('proyectoService') private proyectoService: () => ProyectoService;
  @Inject('cronogramaService') private cronogramaService: () => CronogramaService;
  
  
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

  beforeRouteEnter(to, from, next) {
            next(vm => {
              
                    vm.initRelationships();
                   
            });
    }

             public save(): void {//debo guardar un elemento proyecto
            try {
                this.isSaving = true;
                var i=this.cronograms.length;
                //var i=0;
                for (let e of this.cronograms) {
                    //Actualizando el cronograma
                     var resultado = new Cronograma();
                     e.cronogramaProyectoId = this.proyId;
                     e.ordenVista = i++;  

                       
            
                    if (e.id) {
                        this.cronogramaService().update(e); //envio un elemento
                        this.$router.push({ name: 'PropuestaAdjuntarPropuestaDiplomadoView',params:{ proyectoId: this.proyId}});
                    } else {
                        
                        this.cronogramaService().create(e)
                        .then(param => {
                            this.$router.push({ name: 'PropuestaAdjuntarPropuestaDiplomadoView',params:{ proyectoId: this.proyId}});
                            //const message = this.$t('ciecytApp.cronograma.created', { param: param.id });
                            const message = "Se ha creado un nuevo cronograma" + { param: param.id };
                            this.alertService().showAlert(message, 'success');
                        });
                    }
                }

            } catch (e) {
                //TODO: mostrar mensajes de error
            }
        }

        async initRelationships() {
           try {

             this.nuevo_cronograma() ; //crea una primera tarjeta
             this.proyId = parseInt(this.$route.params.proyectoId);
            

             //this.proyecto = await this.proyectoService().find(this.proyId);
             this.proyecto = await this.proyectoService().find(this.proyId);


              console.log(this.proyId);
            
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
              console.log("error al recuperar la informacion de cronograma ");
            }
 
        }

  

}
</script>

<style scoped>
</style>
