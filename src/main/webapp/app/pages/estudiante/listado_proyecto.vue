
<template>
  <div class="row">
    

    <div>
      <h2 id="page-heading">
        <span id="proyecto-heading">Mis Proyectos</span>
      </h2>
      <br />
      <jhi-loading v-if="isFetching"></jhi-loading>
      <jhi-empty v-if="!isFetching && proyects && proyects.length === 0" titulo="No se encontraron proyectos" mensaje=""></jhi-empty>
      <!--<div>{{ username }} con id {{ userid }} </div>-->
      <div class="table-responsive" v-if="proyects && proyects.length > 0">
        <table class="table table-striped">
          <thead>
            <tr>
              <th role="button" tabindex="0" :aria-sort="propOrder === 'id' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('id')" v-on:keydown.enter="changeOrder('id')" v-on:keydown.space.prevent="changeOrder('id')">
                <span v-text="$t('global.field.id')"></span> <font-awesome-icon icon="sort"></font-awesome-icon>
              </th>
              <th role="button" tabindex="0" :aria-sort="propOrder === 'titulo' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('titulo')" v-on:keydown.enter="changeOrder('titulo')" v-on:keydown.space.prevent="changeOrder('titulo')">
                <span v-text="$t('ciecytApp.proyecto.titulo')"></span> <font-awesome-icon icon="sort"></font-awesome-icon>
              </th>
              <th role="button" tabindex="0" :aria-sort="propOrder === 'modalidad' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('modalidad')" v-on:keydown.enter="changeOrder('modalidad')" v-on:keydown.space.prevent="changeOrder('modalidad')">
                <span v-text="$t('ciecytApp.proyecto.modalidad')"></span> <font-awesome-icon icon="sort"></font-awesome-icon>
              </th>
              <th scope="col" class="text-right"><span class="visually-hidden" v-text="$t('global.menu.actions')"></span></th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="proyecto in proyects" :key="proyecto.id">
              <td>
                {{ proyecto.id }}
              </td>

              <td>{{ proyecto.titulo }}</td>
              <td>{{ proyecto.proyectoModalidadModalidad }}</td>
              <!-- Tesis-->
              <td class="text-right">
                <!-- Radicación de la propuesta ante el CIECYT / aval del asesor -->
                <div class="btn-group" v-if="puedeEnviarPropuesta(proyecto)">
                  <router-link
                    :to="{ name: 'PropuestaEnviarPropuestaView', params: { proyectoId: proyecto.id } }"
                  >
                    <button type="button" id="enviar-entity" class="btn btn-primary">
                        <font-awesome-icon icon="paper-plane"></font-awesome-icon>&nbsp;<span>{{ etiquetaEnviarPropuesta(proyecto) }}</span>
                    </button>
                  </router-link>
                </div>
                <!-- Proyecto ya viable: continuar en la fase de proyecto -->
                <div class="btn-group" v-if="proyecto.viabilidad=='VIABLE'">
                  <router-link
                    :to="{ name: 'ProyectoElementosEditlView', params: { proyectoId: proyecto.id } }"
                  >
                    <button type="submit" id="save-entity"   class="btn btn-info" v-if="proyecto.viabilidad=='VIABLE'"> 
                    <!--<button type="submit" id="save-entity"   class="btn btn-info" > -->
                        <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"></span>
   
                    </button>
                  </router-link>
                </div>
                <!-- Propuesta en curso: retomar el asistente en el paso de informacion general -->
                <div class="btn-group" v-if="proyecto.viabilidad!='VIABLE'">
                  <router-link :to="rutaEdicion(proyecto)">
                    <button type="button" id="continuar-entity" class="btn btn-primary">
                        <font-awesome-icon icon="arrow-right"></font-awesome-icon>&nbsp;<span>Continuar</span>
                    </button>
                  </router-link>
                </div>
                
              </td>
            </tr>

            
          </tbody>
        </table>
      </div>

      <div v-show="proyects && proyects.length > 0">
        <div class="row justify-content-center">
          <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
        </div>
        <div class="row justify-content-center">
          <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" @update:model-value="loadPage"></b-pagination>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { useVuelidate } from '@vuelidate/core';
import { mixins } from 'vue-facing-decorator';
import AlertService from '@/shared/alert/alert.service';

import { Component, Inject, Vue } from 'vue-facing-decorator';
import MenuLateralListado from '@/components/propuesta_listado/menu_lateral_listado.vue';

import { IProyecto, Proyecto } from '@/shared/model/proyecto.model';
import ProyectoService from '@/entities/proyecto/proyecto.service';

const validations: any = {};

@Component({
  components: { MenuLateralListado },
  validations
})
export default class Listado extends Vue {
  @Inject  private proyectoService: () => ProyectoService;

  @Inject  private alertService: () => AlertService;

  //  public elementosProyecto: IElementoProyecto[] =[];
  public proyects: IProyecto[] = [];

  public proyId: any = null;

  private removeId: number = null;
  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;

  public isFetching = false;
  public dismissCountDown: number = this.$store.getters.dismissCountDown;
  public dismissSecs: number = this.$store.getters.dismissSecs;
  public alertType: string = this.$store.getters.alertType;
  public alertMessage: any = this.$store.getters.alertMessage;
  public autoridades: any = this.$store.getters.account.authorities;

  public getAlertFromStore() {
    this.dismissCountDown = this.$store.getters.dismissCountDown;
    this.dismissSecs = this.$store.getters.dismissSecs;
    this.alertType = this.$store.getters.alertType;
    this.alertMessage = this.$store.getters.alertMessage;
  }

  public countDownChanged(dismissCountDown: number) {
    this.alertService().countDownChanged(dismissCountDown);
    this.getAlertFromStore();
  }

  public mounted(): void {
    this.retrieveAllProyectos();
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAllProyectos();
  }

  public retrieveAllProyectos(): void {
    this.isFetching = true;
    let seConsulto = false;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort()
    };
    /*
    if (this.autoridades.includes('ROLE_JURADO')) {
      this.proyectoService()
        .retrieveProyectoIntegranteAuthority(this.userid, 'ROLE_JURADO', paginationQuery)
        .then(
          res => {
            this.proyects = res.data;
            this.totalItems = Number(res.headers['x-total-count']);
            this.queryCount = this.totalItems;
            this.isFetching = false;
          },
          err => {
            this.isFetching = false;
          }
        );
    } */ //del if ROLE_ESTUDIANTE
    if (this.autoridades.includes('ROLE_ESTUDIANTE')) {
      seConsulto = true;
      this.proyectoService()
        //.retrieveProyectoIntegrante(this.userid,paginationQuery) //todos los roles no borrar
        .retrieveProyectoIntegranteAuthority(this.userid, 'ROLE_ESTUDIANTE', paginationQuery)
        //.retrieveProyectoIntegranteRol(this.userid, 'ROLE_ESTUDIANTE', paginationQuery)

        .then(
          res => {
            this.proyects = res.data;
            this.totalItems = Number(res.headers['x-total-count']);
            this.queryCount = this.totalItems;
            this.isFetching = false;
          },
          err => {
            this.isFetching = false;
          }
        );
    } //del if ROLE_ESTUDIANTE
    if (!seConsulto) {
      this.isFetching = false;
    }
  }

  public prepareRemove(instance: IProyecto): void {
    this.removeId = instance.id;
  }

  public removeProyecto(): void {
    this.proyectoService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('ciecytApp.proyecto.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllProyectos();
        this.closeDialog();
      });
  }

  public sort(): Array<any> {
    const result = [this.propOrder + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.propOrder !== 'id') {
      result.push('id');
    }
    return result;
  }

  public loadPage(page: number): void {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  public transition(): void {
    this.retrieveAllProyectos();
  }

  public changeOrder(propOrder): void {
    this.propOrder = propOrder;
    this.reverse = !this.reverse;
    this.transition();
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }

  /**
   * Ruta del asistente para retomar una propuesta que aun no es viable. Se arma por
   * modalidad para reapuntar al mismo wizard con el que se inicio el borrador
   * (Acuerdo 25, art. 5): antes solo se ofrecia continuar cuando ya era viable, de
   * modo que un borrador en elaboracion no se podia retomar desde "Mis propuestas".
   */
  public rutaEdicion(proyecto: IProyecto): string {
    const modalidad = proyecto.proyectoModalidadModalidad || '';
    if (modalidad === 'Tesis') {
      return '/propuesta/informacion-general/' + proyecto.id;
    }
    if (modalidad.includes('Pasantia')) {
      return '/propuesta-pasantia/informacion-general-pasantia/' + proyecto.id;
    }
    if (modalidad === 'Diplomado') {
      return '/propuesta-diplomado/informacion-general-diplomado/' + proyecto.id;
    }
    // Publicacion de Articulo y Especializacion usan el wizard "nueva".
    return '/propuesta-nueva/informacion-general-nueva/' + proyecto.id;
  }

  /**
   * Estados en los que el estudiante debe entrar al asistente de radicación:
   * borradores, documentación observada (para corregir y volver a radicar),
   * habilitado por CIECYT (para solicitar el aval del asesor) y correcciones
   * del asesor (para reenviar). El Acuerdo 25 exige radicar ante el CIECYT.
   */
  public puedeEnviarPropuesta(proyecto: IProyecto): boolean {
    return (
      proyecto.estado === 'PREINSCRITA' ||
      proyecto.estado === 'EN_ELABORACION_PROPUESTA' ||
      proyecto.estado === 'OBSERVACIONES_DOCUMENTACION' ||
      proyecto.estado === 'HABILITADO' ||
      proyecto.estado === 'CORRECCIONES_ASESOR' ||
      proyecto.estado === 'CORRECCIONES_JURADO_PROPUESTA'
    );
  }

  public etiquetaEnviarPropuesta(proyecto: IProyecto): string {
    if (proyecto.estado === 'HABILITADO') {
      return 'Solicitar aval';
    }
    if (proyecto.estado === 'CORRECCIONES_ASESOR') {
      return 'Reenviar al asesor';
    }
    if (proyecto.estado === 'CORRECCIONES_JURADO_PROPUESTA') {
      return 'Reenviar al jurado';
    }
    return 'Radicar propuesta';
  }

  public isSaving = false;

  public get username(): string {
    return this.$store.getters.account ? this.$store.getters.account.login : '';
  }

  public get userid(): string {
    return this.$store.getters.account ? this.$store.getters.account.id : '';
  }

  public get authorities(): string {
    return this.$store.getters.account ? this.$store.getters.account.authorities : '';
  }
}
</script>

<style scoped>
</style>
