<template>
  <div class="row">
    <div class="col-sm-4">
      <menu-lateral :proyectoId='$route.params.proyectoId'></menu-lateral>
    </div>
    <div class="col-sm-8">
    <div class="form-group">
              <label class="form-control-label" for="encabezado">
               <h2>Entidades Financiadoras</h2>
               </label>
              </div>
      <div :key="key" v-for="(item, key) in entidadesFinanciadors">
        <b-card
        :header="`Entidad numero ${key+1}`" 
         border-variant="primary"
        
        header-bg-variant="light"
        body-bg-variant="light"
        header-text-variant="info"
        >
          <div class="row">
            
             <div class="col-12">
                  <b-form-group label="Entidad Financiadora" label-for="entidad">
                    <b-form-select :options="entidads"
                      text-field="entidad"
                      value-field="id"
                      id="id"
                       v-model="item.entidadFinanciadoraEntidadId"
                    ></b-form-select>
                  </b-form-group>
                </div>
            <div class="col-3">
              <div class="form-group">
                <label class="form-control-label" for="proyecto-apellido">Valor</label>
                <input type="text" class="form-control" name="valor"
                 id="valor"  v-model="item.valor"/>
              </div>
            </div>
            <div class="col-12">
              <b-form-radio-group :id="`aprobada-${key}`" 
                  :name="`aprobada-${key}`"    v-model="item.aprobada">
                Estado &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <b-form-radio value="true">Aprobada</b-form-radio>
                <b-form-radio value="false">En Trámite</b-form-radio>
                <!--<b-form-radio value="third" disabled>This one is Disabled</b-form-radio>
                <b-form-radio :value="{ fourth: 4 }">This is the 4th radio</b-form-radio>-->
              </b-form-radio-group>
            </div>
            
          </div>
          <hr>
        </b-card>
        <hr>
      </div>
      <button type="submit" id="save-entity"
              class="btn btn-primary float-right"
               @click="save()" >
              <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;
              <span>Guardar</span>
            </button>
            <button type="submit" id="save-entity" class="btn btn-primary float-right" @click="nuevo_entidad()">
              <font-awesome-icon :icon="['fas', 'plus']"></font-awesome-icon>&nbsp;
              <span></span>
            </button>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Inject, Vue } from 'vue-property-decorator';
import MenuLateral from '@/components/propuesta/menu_lateral.vue';
import { IEntidadFinanciadora, EntidadFinanciadora } from '@/shared/model/entidad-financiadora.model';
import EntidadFinanciadoraService from '@/entities/entidad-financiadora/entidad-financiadora.service';
import EntidadService from '@/entities/entidad/entidad.service';
import { IEntidad, Entidad } from '@/shared/model/entidad.model';
import { IProyecto, Proyecto } from '@/shared/model/proyecto.model';
import ProyectoService from '@/entities/proyecto/proyecto.service';

const validations: any = {};


@Component({
  components: { MenuLateral }
})
export default class Entidades extends Vue {
 
  @Inject('proyectoService') private proyectoService: () => ProyectoService;
  @Inject('entidadFinanciadoraService') private entidadFinanciadoraService: () => EntidadFinanciadoraService;
  @Inject('entidadService') private entidadService: () => EntidadService;
  
  

  //entidades = [];
  public entidadesFinanciadors: IEntidadFinanciadora[] = [];
  nuevo_entidad() {
    this.entidadesFinanciadors.push({
      entidadFinanciadoraProyectoId: this.proyId      
     });

  }

   public proyecto: IProyecto = new Proyecto();
   public proyId: any = null;
   //public entidadesFinanciadors: IEntidadFinanciadora[] = [];
    public entidads: IEntidad[] = [];
   public isSaving = false;

  beforeRouteEnter(to, from, next) {
            next(vm => {
              
                    vm.initRelationships();
                   
            });
    }

             public save(): void {//debo guardar un elemento proyecto
            try {
                this.isSaving = true;
                
                for (let e of this.entidadesFinanciadors) {
                    //Actualizando el impacto
                     var resultado = new EntidadFinanciadora();
                     e.entidadFinanciadoraProyectoId = this.proyId;
                       

                       
            
                    if (e.id) {
                        this.entidadFinanciadoraService().update(e); //envio un elemento
                        this.$router.push({ name: 'PropuestaCronogramaView',params:{ proyectoId: this.proyId}});

                    } else {
                        
                        this.entidadFinanciadoraService().create(e)
                        .then(param => {
                            this.$router.push({ name: 'PropuestaCronogramaView',params:{ proyectoId: this.proyId}});
                        });
                    }
                }

            } catch (e) {
                //TODO: mostrar mensajes de error
            }
        }

        async initRelationships() {
           try {

               this.nuevo_entidad() ; //crea una primera tarjeta
             this.proyId = parseInt(this.$route.params.proyectoId);
            

             //this.proyecto = await this.proyectoService().find(this.proyId);
             this.proyecto = await this.proyectoService().find(this.proyId);


              console.log(this.proyId);
            
            //recuperar las entidadesFinanciadores enviando un idProyecto (api)
            //retrieveImpactsEsperadosProyecto
            
            this.entidadFinanciadoraService()
                .retrieveEntidadFinanciadora(this.proyId)
                .then(res=> {

                    this.entidadesFinanciadors = res.data;
                    if(res.data.length ==0){
                     this.nuevo_entidad();
                   }
                })
            
  
                 //Obteniendo las lineas de investigacion
            this.entidadService()
                .retrieve()
                .then(res => {
                    this.entidads = res.data;
                });

            }
            catch(e){ 
              console.log("error al recuperar la informacion de impactos esperados ");
            }
 
        }
}
</script>

<style scoped>
</style>
