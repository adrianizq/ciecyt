<template>
  <div class="row">
    <div class="col-sm-4">
      <menu-lateral-proyecto :proyectoId="$route.params.proyectoId"></menu-lateral-proyecto>
    </div>
    <div class="col-sm-8"  v-if="!proyecto.proyectoEnviado">
      <form @submit.prevent="save()">
        <div class="row">
          <div class="col-12">
            
            <div class="form-group">
              <label class="form-control-label" for="proyecto-titulo">
               <h2>Enviar el Proyecto</h2><br />

               <ul>
               <li>Título {{proyecto.titulo}} </li>
            <li v-for="l in integrants" v-bind:key="l">{{l.integranteProyectoRolesModalidadRol}}: {{l.integranteProyectoUserLogin}}</li>
             <ol></ol>
            
        </ul>
                Se va a enviar el Proyecto al Ciecyt, para ser revisada por el Jurado. <br />
                Para realizar esta operación, debe haber diligenciado correctamente los datos de su proyecto
                <br /> 

                <br />Si está de acuerdo marque la opcion
                <strong>Enviar al Jurado </strong>
                y de click en el boton <strong>Enviar </strong></label
              >

              <b-form-checkbox type="checkbox" 
              class="form-control" 
              name="terms"
              value="terms"
              id='terms'
              v-model='terms'
        
              >
                Enviar al Jurado
              </b-form-checkbox>
            </div>
          </div>
        </div>

        <div>
          <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>

          <button type="submit" id="save-entity" class="btn btn-primary" :disabled='isDisabled'>
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Enviar</span>
          </button>
        </div>
      </form>
    </div>
    <div class="col-sm-8"  v-if="proyecto.proyectoEnviado">
     <h2>Enviar el Proyecto</h2><br />
    La Proyecto ya ha sido enviada al Jurado para su revisión
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Inject, Vue } from 'vue-property-decorator';

import MenuLateralProyecto from '@/components/proyecto/menu_lateral_proyecto.vue';
import { IProyecto, Proyecto } from '@/shared/model/proyecto.model';
import { IUser } from '@/shared/model/user.model';

import ProyectoService from '@/entities/proyecto/proyecto.service';
import AlertService from '@/shared/alert/alert.service';
import { IIntegranteProyecto } from '@/shared/model/integrante-proyecto.model';

@Component({
  components: { MenuLateralProyecto },
})
export default class EnviarProyecto extends Vue {
  @Inject('proyectoService') private proyectoService: () => ProyectoService;
  @Inject('alertService') private alertService: () => AlertService;

  public integrants:IIntegranteProyecto[]= [];
  public terms:Boolean=false;


  public proyecto: IProyecto = new Proyecto();
  public proyId: string = null;
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.proyectoId) {
        vm.retrieveProyecto(to.params.proyectoId);
      }
      vm.initRelationships();
    });
  }

  getNow() {
    const today = new Date();
    const date = today.getFullYear() + '-' + (today.getMonth() + 1) + '-' + today.getDate();
    return date;
  }

  public save(): void {
    this.isSaving = true;
    //calcular la fecha actual para guardarla en
    //proyecto.fechaEnvioPropuesta

    this.proyecto.fechaEnvioProyecto = new Date();
    this.proyecto.proyectoEnviado = true;

    if (this.proyecto.id) {
      this.proyectoService()
        .updateProyecto(this.proyecto)
        .then(param => {
          this.isSaving = false;
          //this.$router.push({ name: 'PropuestaIntegrantesView', params: { proyectoId: this.proyecto.id.toString() } });
          (<any>this).$router.go(0);
          const message = this.$t('ciecytApp.proyecto.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.proyectoService()
        .createProyecto(this.proyecto)
        .then(param => {
          this.isSaving = false;

          this.proyId = String(param.id);

          // this.$router.push({ name: 'PropuestaIntegrantesView', params: { proyectoId: this.proyId } });
          (<any>this).$router.go(0);

          const message = 'Se ha creado un nuevo proyecto';
          this.alertService().showAlert(message, 'success');
        });
    }
    //this.submitStatus = 'PENDING';
    //setTimeout(() => {
    //  this.submitStatus = 'OK';
    //}, 500);
    //}
    // console.log(this.submitStatus);
  }

  retrieveProyecto() {
    this.proyectoService()
    .findProyectoIntegrantes(parseInt(this.$route.params.proyectoId))
      //.find(parseInt(this.$route.params.proyectoId))
      .then(res => {
        this.proyecto = res.data;
        this.integrants = this.proyecto.listaIntegrantesProyecto;
        console.log( res.data.listaIntegrantesProyecto);
        
      });
  }

  initRelationships() {
    this.proyId = this.$route.params.proyectoId;
  }

  get isDisabled(){
    	return !this.terms;
    }
}
</script>

<style scoped>
</style>