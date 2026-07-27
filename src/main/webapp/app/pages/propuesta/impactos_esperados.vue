<template>
  <div class="row">
    <div class="col-sm-4">
      <menu-lateral :proyectoId='$route.params.proyectoId'></menu-lateral>
    </div>
    <div class="col-sm-8">
    <div class="form-group">
              <label class="form-control-label" for="encabezado">
               <h2>Impactos Esperados</h2>
               </label>
              </div>
      <div :key="key" v-for="(item, key) in impactosEsperads">
        <b-card :header="`Impacto ${key+1}`" 
       
         border-variant="primary"
        
        header-bg-variant="light"
        body-bg-variant="light"
        header-text-variant="info">
          <div class="row">
            
            <div class="col-12">
              <div class="form-group">
                <label class="form-control-label" for="proyecto-nombre">Describa el Impactos Esperado</label>
                <label class="float-right" id="contar">#{{key+1}}</label>

                <input type="text" class="form-control" name="impacto" 
                id="impacto"  v-model="item.impacto" />
              </div>
            </div>
            <div class="col-3">
              <div class="form-group">
                <label class="form-control-label" for="proyecto-apellido">Plazo en  Meses</label>
                <input type="text" class="form-control" name="plazo" id="plazo"
                 v-model="item.plazo" />
              </div>
            </div>
            <div class="col-12">
              <div class="form-group">
                <label class="form-control-label" for="proyecto-apellido">Indicador</label>
                <input type="text" class="form-control" name="indicador" id="indicador"
                 v-model="item.indicador" />
              </div>
            </div>
            <div class="col-12">
              <div class="form-group">
                <label class="form-control-label" for="proyecto-apellido">Supuestos</label>
                <input type="text" class="form-control" name="supuestos" id="supuestos"
                 v-model="item.supuestos" />
              </div>
            </div>
          </div>
        </b-card>
        <hr>
      </div>
      <button type="submit" id="save-entity" 
          class="btn btn-primary float-right"
          @click="save()">
        <font-awesome-icon :icon="['fas', 'save']"></font-awesome-icon>&nbsp;
        <span>Guardar</span>
      </button>
      <button
        type="submit"
        id="save-entity"
        class="btn btn-primary float-right"
        @click="nuevo_impacto()"
      >
        <font-awesome-icon :icon="['fas', 'plus']"></font-awesome-icon>&nbsp;
        <span></span>
      </button>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Inject, Vue } from 'vue-property-decorator';
import MenuLateral from '@/components/propuesta/menu_lateral.vue';
import { IImpactosEsperados, ImpactosEsperados } from '@/shared/model/impactos-esperados.model';
import ImpactosEsperadosService from '@/entities/impactos-esperados/impactos-esperados.service';
import { IProyecto, Proyecto } from '@/shared/model/proyecto.model';
import ProyectoService from '@/entities/proyecto/proyecto.service';

const validations: any = {};

var contar = 1;

@Component({
  components: { MenuLateral }
})
export default class Impactos_esperados extends Vue {

  @Inject('impactosEsperadosService') private impactosEsperadosService: () => ImpactosEsperadosService;
  @Inject('proyectoService') private proyectoService: () => ProyectoService;
  public impactosEsperads: IImpactosEsperados[] = [];
  nuevo_impacto() {
    this.impactosEsperads.push({
      impactosEsperadoProyectoId: this.proyId      
     });

  }

   public proyecto: IProyecto = new Proyecto();
   public proyId: any = null;
   //public impactosEsperads: IImpactosEsperados[] = [];
   public isSaving = false;

  beforeRouteEnter(to, from, next) {
            next(vm => {
              
                    vm.initRelationships();
                   
            });
    }

          public save(): void {//debo guardar un elemento proyecto
            try {
                this.isSaving = true;
                 var i=this.impactosEsperads.length;                
                for (let e of this.impactosEsperads) {
                    //Actualizando el impacto
                     var resultado = new ImpactosEsperados();

                     e.impactosEsperadoProyectoId = this.proyId;  
                    e.ordenVista = i++;
                       
            
                    if (e.id) {
                        this.impactosEsperadosService().update(e); //envio un elemento
                        this.$router.push({ name: 'PropuestaEntidadesView',params:{ proyectoId: this.proyId}});

                    } else {
                        
                        this.impactosEsperadosService().create(e)
                        .then(param => {
                            this.$router.push({ name: 'PropuestaEntidadesView',params:{ proyectoId: this.proyId}});
                        });
                    }
                }

            } catch (e) {
                //TODO: mostrar mensajes de error
            }
        }

          async initRelationships() {
           try {

               //this.nuevo_impacto() ; //crea una primera tarjeta
             this.proyId = parseInt(this.$route.params.proyectoId);

             //this.proyecto = await this.proyectoService().find(this.proyId);
             this.proyecto = await this.proyectoService().find(this.proyId);


              console.log(this.proyId);
            
            //recuperar los impactosEperados enviando un idProyecto (api)
            //retrieveImpactsEsperadosProyecto
            
            this.impactosEsperadosService()
                .retrieveImpactosEsperados(this.proyId)
                .then(res=> {

                    this.impactosEsperads = res.data;
                    if(res.data.length ==0){
                     this.nuevo_impacto();
                   }
                })
            
         


            }
            catch(e){ 
              console.log("error al recuperar la informacion de impactos esperados ");
            }

            
            
        }

}
</script>

<style scoped>
</style>
