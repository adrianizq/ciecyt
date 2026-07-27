<template>
  <div class="row">
    <div class="col-sm-4">
      <menu-lateral :proyectoId='$route.params.proyectoId'></menu-lateral>
    </div>
    <div class="col-sm-8">

    <div class="form-group">
              <label class="form-control-label" for="encabezado">
               <h2>Presupuesto</h2>
               </label>
              </div>
      <div :key="key" v-for="(item, key) in presupuestoValors">
        <b-card :header="`Presupuesto Item ${key+1}`" 
         border-variant="primary"
        
        header-bg-variant="light"
        body-bg-variant="light"
        header-text-variant="info">
          <div class="row">
            <div class="col-12">
              <div class="form-group">
                <label class="form-control-label" for="proyecto-documento">Descripción</label>

                <label class="float-right" id="descripcion">#{{key+1}}</label>
                <b-form-textarea id="descripcion" placeholder="Descripción" rows="2" v-model="item.descripcion">
                </b-form-textarea>
              </div>

             <div class="form-group">
                  <b-form-group label="Rubro" label-for="rubro">
                    <b-form-select :options="rubros"
                      text-field="rubro"
                      value-field="id"
                      id="id"
                      v-model="item.presupuestoValorRubroId"
                    ></b-form-select>
                  </b-form-group>
                </div>

                
              <div class="form-group">
                <label class="form-control-label" for="justificacion">Justificación</label>
                <b-form-textarea id="justificacion" 
                placeholder="Justificación" rows="4"  v-model="item.justificacion">
                </b-form-textarea>
              </div>
              
              <div class="form-group">
                  <b-form-group label="Cantidad" label-for="cantidad">
                    <input type="text" class="form-control" 
                    id="cantidad" name="cantidad"  v-model="item.cantidad" />
                  </b-form-group>
                </div>

               <div class="form-group">
                  <b-form-group label="Valor Unitario" label-for="valor-unitario">
                    <input type="text" class="form-control" id="valor-unitario"
                     name="valor-unitario"  v-model="item.valorUnitario" />
                  </b-form-group>
                </div>

                  <div class="form-group">
                  <b-form-group label="Entidad Financiadora" label-for="entidad">
                    <b-form-select :options="entidads"
                      text-field="entidad"
                      value-field="id"
                      id="id"
                       v-model="item.presupuestoValorEntidadId"
                    ></b-form-select>
                  </b-form-group>
                </div>

                 <div class="form-group">
                  <b-form-group label="Contrapartida en Especie" label-for="especie">
                    <input type="text" class="form-control" id="especie"
                     name="especie"  v-model="item.especie" />
                  </b-form-group>
                 </div>
                 
               <div class="form-group">
                  <b-form-group label="Contrapartida en Dinero" label-for="dinero">
                    <input type="text" class="form-control" id="dinero"
                     name="dinero"  v-model="item.dinero" />
                  </b-form-group>
                </div>
            </div> <!-- no quitar contiene -->


                


          
            <div class="col-12 justify-content-center align-items-center">
              <div class="row">
                

               
                <!--
                <div class="col-sm-3 col-3">
                  <b-form-group label="Valor Total" label-for="valor-total">
                    <input type="text" class="form-control" 
                    id="valor-total" name="valor-total"  v-model="item.presupuestoValorRubroId" />
                  </b-form-group>
                </div>
                -->
               
              </div>
            </div>
            <div class="col-12 justify-content-center align-items-center">
              <div class="row">
                <!--<div class="col-sm-4 col-4">
              <b-form-radio
                aria-orientation="horizontal"
                name="some-radios"
                value="A"
              >Contrapartida En Especie</b-form-radio>
              <b-form-radio aria-orientation="horizontal" name="some-radios" value="B">En Efectivo</b-form-radio>
                </div>-->
                <!--
                <div>
                  <b-form-radio-group id="radio-group-2" name="radio-sub-component">
                    <b-form-radio value="first">Contrapartida En Especie</b-form-radio>
                    <b-form-radio value="second">En Efectivo</b-form-radio>
                    <b-form-radio value="third" disabled>This one is Disabled</b-form-radio>
                    <b-form-radio :value="{ fourth: 4 }">This is the 4th radio</b-form-radio>
                  </b-form-radio-group>
                </div>
                -->
                 
              </div>
            </div>
          </div>
        </b-card>
        <hr />
      </div>
      <div class="col-12 text-left">
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
          @click="nuevo_presupuesto()"
        >
          <font-awesome-icon :icon="['fas', 'plus']"></font-awesome-icon>&nbsp;
          <span></span>
        </button>
      </div>
      <br />
      <br />
      <label class="form-control-label float-right" for="proyecto-documento">
        Total.&nbsp;&nbsp;&nbsp;&nbsp;
        </label>
      <div class="col-12 text-left row float-right"></div>
    </div>
  </div>
</template>

<script lang="ts">

import { Component, Inject, Vue } from 'vue-property-decorator';
import MenuLateral from '@/components/propuesta/menu_lateral.vue';
import AlertService from '@/shared/alert/alert.service';

import { IPresupuestoValor, PresupuestoValor } from '@/shared/model/presupuesto-valor.model';
import PresupuestoValorService from '@/entities/presupuesto-valor/presupuesto-valor.service';
import { IEntidad, Entidad } from '@/shared/model/entidad.model';
import EntidadService from '@/entities/entidad/entidad.service';
import { IRubro, Rubro } from '@/shared/model/rubro.model';
import RubroService from '@/entities/rubro/rubro.service';
import { IProyecto, Proyecto } from '@/shared/model/proyecto.model';
import ProyectoService from '@/entities/proyecto/proyecto.service';

const validations: any = {};



@Component({
  components: { MenuLateral }
})
export default class Presupuesto extends Vue {

  @Inject('presupuestoValorService') private presupuestoValorService: () => PresupuestoValorService;
  @Inject('entidadService') private entidadService: () => EntidadService;
  @Inject('rubroService') private rubroService: () => RubroService;
  @Inject('proyectoService') private proyectoService: () => ProyectoService;
   
  
  @Inject('alertService') private alertService: () => AlertService;


  //presupuestos = [];
  public presupuestoValors: IPresupuestoValor[] =[];
  nuevo_presupuesto() {
    
  
    this.presupuestoValors.push({
      presupuestoValorProyectoId: this.proyId      
     });
  }


    public entidads: IEntidad[] = [];
    public rubros: IRubro[] = [];
   
    
    //public elemProy: ElementoProyecto;
    public proyecto: IProyecto = new Proyecto();
    public proyId: any = null;
    public modalidadId: number = 0;

    public entidad: number = null;
    public rubro: number = null;

    public isSaving = false;


        beforeRouteEnter(to, from, next) {
            next(vm => {
              
                    vm.initRelationships();
            });
        }


        public save(): void {//debo guardar un elemento proyecto
            try {
                this.isSaving = true;
                  var i=this.presupuestoValors.length;
                
                for (let e of this.presupuestoValors) {
                    //Actualizando el integrante
                     var presupuesto = new PresupuestoValor();
                     e.presupuestoValorProyectoId = this.proyId; 
                          e.ordenVista = i++; 
                       
            
                    if (e.id) {
                        this.presupuestoValorService().update(e); //envio un elemento
                        this.$router.push({ name: 'PropuestaResultadosEsperadosView',params:{ proyectoId: this.proyId}});

                    } else {
                        
                        this.presupuestoValorService().create(e)
                        .then(param => {
                            this.$router.push({ name: 'PropuestaResultadosEsperadosView',params:{ proyectoId: this.proyId}});
                        });
                    }
                }

            } catch (e) {
                //TODO: mostrar mensajes de error
            }
        }


        async initRelationships() {
           try {

             // this.nuevo_presupuesto() ; //crea una primera tarjeta
             this.proyId = parseInt(this.$route.params.proyectoId);

             //this.proyecto = await this.proyectoService().find(this.proyId);
             this.proyecto = await this.proyectoService().find(this.proyId);


              console.log(this.proyId);
            //Obtenienedo las entidades
            this.entidadService()
                .retrieve()
                .then(res => {
                    this.entidads = res.data;

            console.log("inicializando entidades");
            });

             //Obtenienedo las entidades
            this.rubroService()
                .retrieve()
                .then(res => {
                    this.rubros = res.data;

            console.log("inicializando rubros");
            });

            //recuperar los presupuestos enviando un idProyecto (api)
            //retrievePresupuetoProyecto
            this.presupuestoValorService()
                .retrievePresupuetoProyecto(this.proyId)
                .then(res=> {

                    this.presupuestoValors = res.data;
                    console.log(res.data);
                   if(res.data.length ==0){
                     this.nuevo_presupuesto() ;
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
              console.log("error al recuperar la informacion de presupuesto ");
            }

            
            
        }



}
</script>

<style scoped>
</style>
