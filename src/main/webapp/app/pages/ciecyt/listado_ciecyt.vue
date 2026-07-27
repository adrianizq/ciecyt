<template>
<div class="row">
 <div class="col-sm-4">
        
            <menu-lateral-ciecyt :proyectoId='$route.params.proyectoId'></menu-lateral-ciecyt>
 </div>     
    <div class="col-sm-8">
        <h2 id="page-heading">
            <span id="proyecto-heading">Proyectos y propuestas</span>
            
        </h2>
        <div class="col-md-3">         
        <label  >Buscar por titulo: </label>
              <font-awesome-icon icon="search" :spin="isFetching"></font-awesome-icon>
                     
              <input type="text" name="buscarTitulo"  @change="retrieveSearchTitulo" v-model="searchTitulo"
              placeholder="palabras del titulo"/>

              <label  >por Programa: </label>  
              <font-awesome-icon icon="search" :spin="isFetching"></font-awesome-icon>
               <select class="form-control" id="elemento-elementoFase" 
                  name="buscarFaseId" v-model="searchPrograma"
                  placeholder="programa"
                  @change="retrieveSearchPrograma">
                 <option v-bind:value="null"></option>
                 <option v-bind:value="programaOption.id" v-for="programaOption in programas" :key="programaOption.id">{{programaOption.programa}}</option>
                 Programa
               </select>          
              </div>

        <b-alert :show="dismissCountDown"
            dismissible
            :variant="alertType"
            @dismissed="dismissCountDown=0"
            @dismiss-count-down="countDownChanged">
            {{alertMessage}}
        </b-alert>
        <br/>
        <div class="alert alert-warning" v-if="!isFetching && proyects && proyects.length === 0">
            <span>No se encontraron proyectos</span>
        </div>
       
        <div >
            <table class="table table-responsive table-striped table-hover table-fixed"
            
            v-if="proyects && proyects.length > 0"
            >
                <thead>
                <tr>
                  
                    <th  v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                
                  
                    <th  v-on:click="changeOrder('titulo')"><span v-text="$t('ciecytApp.proyecto.titulo')">Titulo</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                     <th  v-on:click="changeOrder('modalidad')"><span >Modalidad</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                     <th ><span>Jurado Viabilidad</span></th>
                     <th ><span >Jurado</span></th>
                     <th ><span >Asesor</span></th>

                     <!--
                         <th class="col-md-1" v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th class="col-md-3" v-on:click="changeOrder('titulo')"><span v-text="$t('ciecytApp.proyecto.titulo')">Titulo</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                     <th class="col-md-2" v-on:click="changeOrder('modalidad')"><span >Modalidad</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                     <th class="col-md-2"><span>Jurado Viabilidad</span></th>
                     <th class="col-md-2"><span >Jurado</span></th>
                     <th class="col-md-2"><span >Asesor</span></th>
                     -->
                    <!-- <th v-on:click="changeOrder('tipo')"><span v-text="$t('ciecytApp.proyecto.tipo')">Tipo</span> <font-awesome-icon icon="sort"></font-awesome-icon></th> -->
                   
                </tr>
                </thead>
                <tbody>
                <!-- JURADO ---------------------------------->
                <tr v-for="proyecto in proyects"
                    :key="proyecto.id">
                    <router-link :to="{name: 'PropuestaInformacionGeneralNuevaEditView', params: {proyectoId: proyecto.id}}" >
                    <td>{{proyecto.id}}</td>
                  </router-link>
                    <td>{{proyecto.titulo}}</td>
                     <td>{{proyecto.proyectoModalidadModalidad}}</td> 
                   
                
                    <!------ JuradoViabilidad ---->
                     <td class="text-right">
                        <div class="btn-group" v-if="!proyecto.tieneJuradoViabilidad&&proyecto.enviado">
                            <router-link :to="{name: 'AsignarJuradoViabilidadView', params: {proyectoId: proyecto.id}}" tag="button" class="btn btn-info btn-sm details">
                               <b-icon-person-fill></b-icon-person-fill>&nbsp;
                                <span class="d-none d-md-inline" >Asignar</span>
                            </router-link>
                         </div>

                         <div class="btn-group" v-if="proyecto.tieneJuradoViabilidad&&proyecto.enviado&&proyecto.viabilidad!=`VIABLE`">
                            <router-link :to="{name: 'AsignarJuradoViabilidadView', params: {proyectoId: proyecto.id}}" tag="button" class="btn btn-secondary btn-sm">
                                <b-icon-person-fill></b-icon-person-fill>&nbsp;
                                <span class="d-none d-md-inline"  >Cambiar</span>
                            </router-link>
                         </div>
                    </td>

                    <!------ Jurado ---->
                     <td class="text-right">
                        <div class="btn-group" v-if="!proyecto.tieneJurado&&proyecto.viabilidad==`VIABLE`">
                            <router-link :to="{name: 'AsignarJuradoView', params: {proyectoId: proyecto.id}}" tag="button" class="btn btn-info btn-sm details">
                                <b-icon-people-fill></b-icon-people-fill>&nbsp;
                                <span class="d-none d-md-inline" >Asignar</span>
                            </router-link>
                         </div>

                         <div class="btn-group" v-if="proyecto.tieneJurado&&proyecto.viabilidad==`VIABLE`">
                            <router-link :to="{name: 'AsignarJuradoView', params: {proyectoId: proyecto.id}}" tag="button" class="btn btn-secondary btn-sm">
                               <b-icon-people-fill></b-icon-people-fill>&nbsp;
                                <span  class="d-none d-md-inline">Cambiar </span>
                            </router-link>
                         </div>
                    </td>
                    <!---ASESor -->
                    <td class="text-right">
                        <div class="btn-group" v-if="!proyecto.tieneAsesor">
                            <router-link :to="{name: 'AsignarAsesorView', params: {proyectoId: proyecto.id}}" tag="button" class="btn btn-info btn-sm details">
                               <b-icon-person></b-icon-person>&nbsp;
                                <span class="d-none d-md-inline" >Asignar</span>
                            </router-link>
                         </div>

                         <div class="btn-group" v-if="proyecto.tieneAsesor">
                            <router-link :to="{name: 'AsignarAsesorView', params: {proyectoId: proyecto.id}}" tag="button" class="btn btn-secondary btn-sm">
                                <b-icon-person></b-icon-person>&nbsp;
                                <span class="d-none d-md-inline" >Cambiar </span>
                            </router-link>
                         </div>
                    </td>

                    <!-------------------------------------------------
                     <td class="text-right">
                        <div class="btn-group" >
                            <router-link :to="{name: 'AsignarAsesorView', params: {proyectoId: proyecto.id}}" tag="button" class="btn btn-info btn-sm details">
                               <b-icon-pencil-square></b-icon-pencil-square>&nbsp;
                                <span class="d-none d-md-inline" >Asesor</span>
                            </router-link>
                         </div>
                    </td>
                    --> 


                </tr>
               


                </tbody>
            </table>

            <button  @click="downloadPdf" >Descargar Informe</button>
        </div>
        
        <div v-show="proyects && proyects.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
    </div>
</template>

<script lang="ts">


import { mixins } from 'vue-class-component';


import Vue2Filters from 'vue2-filters';

import AlertService from '@/shared/alert/alert.service';



import { Component, Inject, Vue } from 'vue-property-decorator';
import MenuLateralCiecyt from '@/components/ciecyt/menu_lateral_ciecyt.vue';

import ProgramaService from '../../entities/programa/programa.service';
import { IPrograma } from '@/shared/model/programa.model';

import UserManagementService from '@/admin/user-management/user-management.service';
import { IUser, User } from '@/shared/model/user.model';

import { IProyecto, Proyecto } from '@/shared/model/proyecto.model';
import ProyectoService from '@/entities/proyecto/proyecto.service';
import { IIntegranteProyecto, IntegranteProyecto } from '@/shared/model/integrante-proyecto.model';
import IntegranteProyectoService from '@/entities/integrante-proyecto/integrante-proyecto.service';
import { jsPDF } from "jspdf";


    const validations: any = {};

   @Component({
        components: { MenuLateralCiecyt },
        validations
    })

export default class ListadoCiecyt extends Vue {
   @Inject('proyectoService') private proyectoService: () => ProyectoService;

    @Inject('integranteProyectoService') private integranteProyectoService: () => IntegranteProyectoService;

    @Inject('userService') private userManagementService: () => UserManagementService;

    @Inject('programaService') private programaService: () => ProgramaService;
 
   @Inject('alertService') private alertService: () => AlertService;

   //doc = new jsPDF();
   



    
   //  public elementosProyecto: IElementoProyecto[] =[];
    public proyects: IProyecto[] = [];

    public users: IUser[] = [];
   
   
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
  public autoridades: any =  this.$store.getters.account.authorities;

  public searchTitulo: any;
  public searchPrograma: any;

  public programas: IPrograma[] = [];

  //////////recuperar datos desde la busqueda por titulo del proyecto
retrieveSearchTitulo(){
  //si la cadena es vacia recupera todas los proyectoss
  if(this.searchTitulo==null || this.searchTitulo.length==0){
    this.retrieveAllProyectos()
  }
  else{
  const paginationQuery = {
    page: this.page - 1,
    size: this.itemsPerPage,
    sort: this.sort(),
  };
  this.proyectoService()
      .retrieveSearchTitulo(this.searchTitulo, paginationQuery)
      .then(
        res => {
          this.proyects = res.data;
          //console.log(this.licenses);
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
          this.alertService().showHttpError(this, err.response);
        }
      );
  }
}



  //////////recuperar datos desde la busqueda por programa del proyecto
  retrieveSearchPrograma(){
  //si la cadena es vacia recupera todas los proyectoss
  if(this.searchPrograma==null || this.searchPrograma.length==0){
    this.retrieveAllProyectos()
  }
  else{
  const paginationQuery = {
    page: this.page - 1,
    size: this.itemsPerPage,
    sort: this.sort(),
  };
  this.proyectoService()
      .retrieveSearchPrograma(this.searchPrograma, paginationQuery)
      .then(
        res => {
          this.proyects = res.data;
          //console.log(this.licenses);
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
          this.alertService().showHttpError(this, err.response);
        }
      );
  }
}


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

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort()
    };
    //if (this.autoridades.includes("ROLE_CIECYT")||this.autoridades.includes("ROLE_ADMIN")){
    this.proyectoService()
  
      .retrieveAllProyectosIntegrantes()
      .then(
        res => {
          this.proyects = res.data;
        

          /*for (let i = 0; i < this.proyects.length; i++) {
            for (let j=0; j< this.proyects[i].listaIntegrantes.length; j++){
               this.proyects[i].tieneJurado=false;
              if (this.proyects[i].listaIntegrantes[j].integranteProyectoRolesModalidadRol=="Jurado")
                this.proyects[i].tieneJurado=true;
            }
          } */

/*for (let i = 0; i < this.proyects.length; i++) {
        this.integranteProyectoService().retrieveJuradosProyectoNoPage(this.proyects[i].id,"Jurado").
        then((response) => {
                      
         this.proyects[i].tieneJurado=true;
            //resolve()
       // }, (response) => {
          
       // });
        //resolve()
    }*/


        },
        err => {
          this.isFetching = false;
        }

        
        
      );

     this.userManagementService()
      .retrieveAll()
      .then(res => {
        this.users = res.data;
      });
      //this.integrantesProyectoJurados=null;
      //Promise.all(Promise).then((this.proyects)=>{
      //    this.proyects.forEach(p => {
      //      this.integrantesProyectoJurados.push(this.integranteProyectoService().retrieveJuradosProyecto(p.id,"Jurado", paginationQuery));
      //  });
      //  console.log( this.integrantesProyectoJurados)



      //}//del if ROLE_ASESOR

      
      this.programaService()
      .retrieve()
      .then(res => {
        this.programas = res.data;
      });


      
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



    public isSaving = false;


        beforeRouteEnter(to, from, next) {
            next(vm => {

                    vm.initRelationships();
            });
        }

public get username(): string {
    return this.$store.getters.account ? this.$store.getters.account.login : '';
  }

  public firstName(login): string {
    var res="";
       this.users.forEach(u => {
      if(u.login==login){
        res=u.firstName.toString();
        //console.log(res);
       }
    });
    return res;
  }

  public lastName(login): string {
    var res="";
       this.users.forEach(u => {
      if(u.login==login){
        res=u.lastName.toString();
     }
    });
    return res;
  }


  public get userid(): string {
    return this.$store.getters.account ? this.$store.getters.account.id : '';
  }

public get authorities(): string {
    console.log(this.$store.getters.account);
    return this.$store.getters.account ? this.$store.getters.account.authorities : '';
  }

  
public downloadPdf(){

  //http://raw.githack.com/MrRio/jsPDF/master/docs/index.html

  //const doc = new jsPDF('p','in','letter')

  var imgData="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAMcAAADICAIAAADTMWJEAAAAAXNSR0IB2cksfwAAAFR6VFh0UmF3IHByb2ZpbGUgdHlwZSBpcHRjAAAImQXByQ3AMAgEwD/VcCwsLgc5sZRfHulfmZHn/bZ4im31oqoBw5VTDYKRuKpqAtlY9EzcQRx0mPyufA3LJEvUeAAAIABJREFUeJzsvXdgHdWZN3zqtNvVJUuyZcnduGBscKWYmKUFCKEECJAX82UXSKi7MZssEEoIpIEJkMCCHRYIAWJi2gJugI0Lxb0h25LV25V029TT3j8GFEM275fQyfL7R1dzZ+ae8punnec8A5VS4Ct8hU8U6PNuwFf4B8RXrPoKnzy+YtVX+OTxFau+wiePr1j1FT55fMWqr/DJ4ytW/d34Khbz/wvyeTfgiwgp5dBnhNChxyGEEMLwc/iVUkopdehpIe3C0/53Av5vfvL+khDhwUMJMfTv0Aff93Vd/2s3HOLT/2Zu/e9i1d840/v371+7di0hZN68ecOHDx+6at++fc8880xvby8hZMGCBcceeyyEcOnSpb7vX3LJJYS8J/gdxxFCxGKxQ393aJyHpN0/MP63aMBD+TSkyHp6enbs2DF9+vREIjF0wvLly5988slp06bl8/nnnnvusssuO+aYYwAA+/bt+9GPfjRt2rQrr7xy06ZNt99+ezQanTFjxtq1a4MguOiiiwghmUzm8ccf379/PyGkqqrqO9/5TiKRCHXl/yj//lHxD8uqDymj8EMul9N1Xdf18NvVq1c/+OCDd91116RJk4QQhJDm5uYlS5acccYZF110EQDgxhtv/M1vfjN27NiKioonn3wSALBw4cKioqKamhpKaVlZWRAECKGKigpN0zzP++Uvf9ne3v6jH/0oFostWrTojjvuuO222xBCnZ2da9as6e7unjhx4nHHHUcp/byH59PFPyyrhoRBSKn29vZHH3304MGDSqljjz32zDPPpJRWVVUZhtHe3j5p0qSQZ5s2bQqC4PDDDw8FzLx58zZu3Lhr166SkpLGxsaqqqpYLGbbtud5J510EqV0YGDAdd2ysjKE0Pr16zds2HDVVVeNHDkSAHDZZZft2bMHQrh3796f/OQniUTi8MMPf+WVV15//fXrr78+Eol8qJFDj8E/gBj7h2JV6LuF1nc+n/d9v7i4GEKYTqdvuummCRMm/OAHP9iwYcMjjzxSKBQWLlxYW1tLKW1raxua4Hw+H85xeJPw8s7OTt/3Pc9DCBFC1q9f/9RTT2Wz2csuu2zy5MmMMcMwAAA7d+7Udb26ulopJaWcNm3atGnTgiB46KGHlFI333xzKpVKp9OXXnrpiy++ePbZZ4eKOGz5oWQa8i6/vPhyt/5DQAghhIQQSqmXXnrp7rvvDqdqzZo1/f39F154YV1d3XnnnTdz5sxnn332wIEDVVVVyWSyo6NjiFUlJSVCiP7+/pAZ4WQTQkzTjMfj2WzWtu25c+decMEFvT29AADXdR3HicfjISMxxpqmhVKHMSal3LNnz549e+bNm5dKpYQQlmWVlJTs2LFjiP3hk7Br167FixevXr26UCgghL7sLtSXm1Xh3IdgjL322mtr1qzBGEMIp0+f3tLScuDAAQBAa2srpdTzvHAKjz76aMbYO++8o+t6VVVVW1tbPp8PWTV58mTTNDdv3gwhRAjt2bMHADBmzBiE0JFHHtnS0vLqmlcRQphgJhhCyPd9KaVhGgCA0tJSz/Pa29sRQhhjSilCqLm5OQiC+vp6pRTG2PO8UOZ9qCMvv/zyK6+88sorr9x4441r166F8D3fPOzgl45kX2INGBpMQ4rD9/0nn3yyra1t3759559//ogRI4YNG7ZixYr6+vp4PF4oFDo6OqqqqpRStbW1uq6HIqqqqmrv3r22bYeBgJEjR5519ll/+MMfGOexaPT111474/QzpkyZopQ655xzurq6lyz93Wuvv5bJDIwbN7a+vr6rqyubzUIFAACzZs16+eWXn3nmmdra2pKSkp07d86cOTMSiWCMU6lU2NqDBw/29/fPnz9/yBVFCPX392/ZsmXy5Mm33XbbunXrfvGLX1BKjzrqqEOdxy+XWsQ33XTT592Gj4Jwkrq7u5ctW7Z+/XrLsqqqqgYHBxsbG999990333xz4sSJxcXFzz///CmnnKJp2sqVK3VdnzlzJoSQc758+fLJkydPnTq1q6tr/fr1AwMD69atW7du3ZFHHjl1ytTq6uoD+/e7rnv22Weffsbp4XTqun700fMmHDaxKJmaM3P2+Rd8O5lMBkGg6/r0GTOqqqrKysqqqqreeeedFStWrFq1yvf92bNnU0pffvnlqqqqww47DACwZMmSrq6u73znOyUlJUPm+RtvvLFy5cpvfOMbY8aMqa2tfeONNw4cODB//nwI4eDg4IYNG6qrq4eCYV8KfJnaOhQKDym1devW3/3udzU1Nfv27evs7Lzllltmz579/PPPz549u7Ozc9GiRRMmTCgUChs3bpwzZ86MGTNWrVo1duzYGTNmLFu2TNf1efPmhcJp6tSppmnW1NSMGDEinLy5c+fOnTv30J8OVRKEcOL48RPHjx9qT01NzWWXXTb079y5c6dOndrS0qLrekNDAwBgxIgR559//vLlyzs6OgYHB3ft2nXJJZeMHTt2qCNKqTfeeKO4uPioo45SSoUBjlwuxxjTdX3lypUPP/zwsmXLTjrppOOPP17TtEM9ki8svjSx9Q+pAMbY97///a997Wvf+MY3PM9zHCeVSkEIFy1aNGrUqIsuuuj+++9fuXIlIWT69OmLFi1Kp9P33Xff9u3bdV1PJpMXXHDBzJkz/1o0MtRNSikQKlkAFADwfVorAIACCMHwHCkVwu/rqb+y/rNz5861a9cqpWbNmhXqUwhh2KODBw9effXVc2bPuebaawAAHZ0d/3rdv44cOfLWW2/NZDLXXnutZVmnnHLKmjVrCCE//OEPLcv6TMb740F9eeD7/u9///vnn39eKbVr166TTz553bp1nPPwWyGElHLlypWXXHJJoVBQSq1evfqiiy4644wzGhsbw3P279+/a9cuz/OGrOChazljnDHJuWRMca4O+fbvgJSSMe4HgjEpRPgT8oO3Gvo39FWXLl36TyecsHnz5vDgAw88sGDBgrCPjz322IknnhjSMQiC3bt3K6VefPHFd955R3605n1W+KJrwFBsSCl37tz5+OOPAwDOO++88CDGeMeOHbNnz+acw/dx1FFHPfbYY2+9/fYxRx997LHHjhgxYvny5UNuVH19/dBtEYTg/eMIIfBBw4WHf1xXFWyWzTC3QJiEAvhQUUNXtksQhAhLQqCpk2QSxmPKMBCEmBB86I2UAlIKKQGEYVRqSJiF4q1QKECMNm3alMtlN2/ZsnLlyunTp8+fPz+dTq9evZpSunPnztGjR5eWlo4bN66pqemBBx6YOHHi5MmTP7QK9IXCF1cDHqqenn/++fvuu++kk0664oorwiOu6y5atKi3t/eGG24YN25ceHDVqlVz5s555JFH3t2z986f/ewv7Q8pJVAKhnfGH5j97ECa96bh/mbetD8YHOjbs4fk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vHGSzxIiwbkf+jtN8E7KKTrM2MZLJogziboyuX3QcT3JFWeljNTSTv9szaa3dndwxox5Vw5yzmP9Y9GiRQ8++PBDDz28Z8+eRYsW3nHH7b/85a8eeujhd955Z/78+d/85je7u7uPQlsMGRFxZAQUGdXe0HbzR246N7dgohx/2fhlQSAsYf167S33b35m7Z6NGzvf6S0MxBR5rDghGqGnTZs2fuITH/vkJ69dtmzpk08+JS1x5uIFt99+23/9+ua77vr92rVrliw5e/v2bXfffffq1auXLFkyomAhojHEOXt9295frtstEknXjQwvS2msRDi5rSgCT0lokWRrg0LX2/1huHNI75UySIBgbHrgj4NACJMIS8aqVrPAoz0mrGqNfu7S5WMVn3NSqCom9tyFF0RtrcaUSzvBrtmR5qUQtTObW6dDzYewyLxdtrWdpMe0qLciVzLUfp4PJOxMqE0+3TW9sWJEouwXsjktuCkZ+7u3PQuHcIjRuvl5553/xBNP/e53t7344kvz589/9tk1t912++23375u3bpTTz3tO9/5zsDAwJFo64BTCCUThuj8qQueuP6nz177Hw9c/YN5iXEh+aUG+uSj37/y7q+f9/svnf3bz37zgR8XqiVlDiNbR0NMT/EG2L59++c+97mVK1fOmnXKk08+PW7cuAsuOO8nP/7xP/3wJ48++vi27dvPOmvxQw89/Itf/GrduueXL18+usH4gVQi0lr/zd1P+4o7TGTTphb0RVJOb65k052+ks1CWHafT06WW2mnysgkQEuxNzKdpAKqcssdF6WmUzFP2g6NFW7zG8p+MLGt7vzzR9buA8LJiUhEBGPccW1i2YW6Gnn70NTGJZtOTes8lov+/0fem8fpUVXp4+dutb17v70m6c4OhJCVkIQEkMUkgBAUUDZRXBgUfwMSER3Fccgg6AA6suMMCqgogkHZUWAgTICQhOyELN1JJ93pvftda7nr948KbQYQURNEf88f/am+b9WtU7eeuuecu5yjOoNCm64UKcUUoxTzkDEM+bW2QQCgVb0NNsJSRIeOHqxLSr+qqFfIeMKmuYc3tN///FqCsXq7c7ePW0prfcopp65YseKuu360bNmyadOmrl279rHHHvvRj+5atuzXU6Yc/oMf3OT71Xext2KLXhttMas+nWeYHpmfKMLIAYIsw5Om6uh2p/zD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bNiQymR27Gzdtm3bpEmTAGDb9u1r1qzp7+tLJpNnnHFGuVxesWJFT09PPp9fuHBhe3v7ypUrMcZz586dNWuWMWbDhg2vvvpqEATTpk075phjhj/C+BavvfZaZ2dnc3Pz1q1b4yjcPT0969atq1QqQojTTz89kUgMt/CRRx75q1/9atOmTZ/4xCe6urp279594YUX9vf3v/LKK5zzefPmFQqFp59+enBwsL293bKs+MIVK1asW7cOAObOnXvkkUf+9QQ48KMmSqnW1tb29vbW1lbO+dvpP2fOnEQicdddd919991CiPnz5xNC7rjjjrvuumvWrFmtra3f/e53gyCwLCsIglWrVu3YseMnP/nJ/fffDwA333zzli1bJk6ceM8999x9992+799www1PPPHEvHnzVq1aNaxMEUJ79ux56aWXjjvuuNNPP33NmjXbtm1rbW1dvnz5SSeddMopp2zZsmX58uUA8Itf/GL8+PHHH3/8iBEjbr/99kmTJp188snpdDrO7/3b3/5Wa71o0aI5c+YsW7ast7d3uMeK/8YZKz914YWDAwOrV6+O9dRNN974+GOPHX300Zs3b7799tu/853vPPbYY5MnTx4cHLziiiseeOCBCRMm7N69+5prruno6Fi9evVVV13V0tKSTCavu+66+O3GygghtHLlyra2toULF06fPn3EiBHxTy+88MK8efPOOussjPGPf/zj4W4YAJqbm8eNG7djx44wDDdu3Og4ztSpU3fs2PHTn/70jTfeEEJcf/31jzzySFNTU2xauK774IMP3nDDDVOmTBkcHLz22ms7Ozv/ekP2ALMKIaS13rhx49q1a9esWVMsFuO8Z/GvcRLRSZMm/eu//uuYMWMefvjhJUuWPPXUUwAwc+bMa665Zu7cuYccckixWCyXy4QQQsjFF1+8dOnSlpaWtWvXPvPMM6tXr/7Yxz42f/78hoaGFStW9PX1IYRGjBgxa9as66+//kMf+tCwYnrllVcmTpzoeZ5t21/4wheam5uffvrpo48+2nEcx3EWLFjwzDPPGGMaGxvHjh0LACNHjhw1alRtbS1CaPz48UqpIAg2bdp0wgknIISampqam5ufeeaZ4RaP7/Lyyy/PnDnz5EWL6uvqV69eHStBwXlLS8usWbOuueaa8847LwiCQw89dOHChSeffHJ/f//06dNPPfXUY489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"
  const doc = new jsPDF();
  doc.addImage(imgData, 10,0, 20,20);

  var sizes = [12, 16, 20]
, fonts = [['Times','Roman'],['Helvetica',''], ['Times','Italic']]
, font, size, lines
, margin = 0.5 // inches on a 8.5 x 11 inch sheet.
, verticalOffset = margin
, loremipsum = 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus id eros turpis. Vivamus tempor urna vitae sapien mollis molestie. Vestibulum in lectus non enim bibendum laoreet at at libero. Etiam malesuada erat sed sem blandit in varius orci porttitor. Sed at sapien urna. Fusce augue ipsum, molestie et adipiscing at, varius quis enim. Morbi sed magna est, vel vestibulum urna. Sed tempor ipsum vel mi pretium at elementum urna tempor. Nulla faucibus consectetur felis, elementum venenatis mi mollis gravida. Aliquam mi ante, accumsan eu tempus vitae, viverra quis justo.\n\nProin feugiat augue in augue rhoncus eu cursus tellus laoreet. Pellentesque eu sapien at diam porttitor venenatis nec vitae velit. Donec ultrices volutpat lectus eget vehicula. Nam eu erat mi, in pulvinar eros. Mauris viverra porta orci, et vehicula lectus sagittis id. Nullam at magna vitae nunc fringilla posuere. Duis volutpat malesuada ornare. Nulla in eros metus. Vivamus a posuere libero.'

  doc.setDrawColor(0, 255, 0)
	.setLineWidth(1/72)
	.line(margin, margin, margin, 11 - margin)
  .line(8.5 - margin, margin, 8.5-margin, 11-margin)

  var pageHeight= doc.internal.pageSize.height;
  doc.text("Proyectos y propuestas CIECYT", 10, 22);
  //this.doc.text(pageHeight.toString(), 10, 10);
   var y = 4;
  doc.setFontSize(10);
  doc.line(10, 24, 200, 24);
 /* for(var i =0; i < 1000; i++) {  //se comprobo para varias paginas
    this.doc.text("hola" + " i=" + i + " y=" + y,10,15+(y*5));
    if (y >= pageHeight-245)
        {
        this.doc.addPage();
        y = 0 // Restart height position
        } 
    y++;
  }*/
  /*********************************************** */
 for(let i =0; i < this.proyects.length; i++) {
  ////verificar nueva pagina
   if (y >= pageHeight-245)
        {
        doc.addPage();
        y = 0 // Restart height position
        } 
    doc.text(
      "Identificador: " + this.proyects[i].id.toString() 
      , 10,15+(y*5));
    y++;

    doc.text(
      "Modalidad: " + this.proyects[i].proyectoModalidadModalidad.toString()
      , 10,15+(y*5));
    y++;

   // var longitudTitulo =this.proyects[i].titulo.length;
   // var lineasTitulo = parseInt(longitudTitulo/100) +1;

    //var arrayLinea = this.proyects[i].titulo.match(/.{1,110}/g);
    /* arrayLinea.forEach(l => {
      doc.text(
       l 
      
     // + " "  + this.proyects[i].programa.toString() 
    , 10,15+(y*5));
    y++;
    });*/
    var textLines = doc.setFont('Times','Italic')
    .setFontSize(12)
    .splitTextToSize(this.proyects[i].titulo,157.25);

    textLines.forEach(l => {
      doc.text(
       l 
      
     // + " "  + this.proyects[i].programa.toString() 
    , 10,15+(y*5));
    y++;
    });
   
    doc.setFont('Times','Normal')
    
    doc.text(
       "Programa: " + this.proyects[i].programa.toString() 
       //+ " "  + this.proyects[i].facultadId.toString()
    , 10,15+(y*5));
    y++;
    if(this.proyects[i].listaIntegrantesProyecto.length>0){
    for(let j =0; j < this.proyects[i].listaIntegrantesProyecto.length; j++) {
      ////verificar nueva pagina
       if (y >= pageHeight-245)
        {
        doc.addPage();
        y = 0 // Restart height position
        } 
      if (this.proyects[i].listaIntegrantesProyecto[j].integranteProyectoUserLogin!=null){
        let fn = this.firstName(this.proyects[i].listaIntegrantesProyecto[j].integranteProyectoUserLogin)
        let ln = this.lastName(this.proyects[i].listaIntegrantesProyecto[j].integranteProyectoUserLogin)
     
      doc.text(
      //this.proyects[i].listaIntegrantesProyecto[j].integranteProyectoUserLogin.toString()
      this.proyects[i].listaIntegrantesProyecto[j].integranteProyectoRolesModalidadRol.toString() 
      + " " + fn
      + " " + ln

      
      ,15,15+(y*5));
      }
      y++;
    }
 }

///////////////////////////////
 if(this.proyects[i].fechaEnvioPropuesta!=null){
    doc.text(
      "Fecha de envío de la propuesta: "  + this.proyects[i].fechaEnvioPropuesta
    , 10,15+(y*5));
    }
    else{
      doc.text(
      "Fecha de envío de la propuesta: "  + "NO ENVIADA"
    , 10,15+(y*5));
    }
    y++;

    ////////////////////////////////////////////////////////////

///////////////////////////////
if(this.proyects[i].viable==null){
    doc.text(
      "Viablilidad: "  + "Sin asignar Viabilidad"
    , 10,15+(y*5));
}
else if(this.proyects[i].viable==false){
      doc.text(
      "Viablilidad: "  + "NO ES VIABLE"
    , 10,15+(y*5));
    }
else{
  doc.text(
      "Viablilidad: "  + "VIABLE"
    , 10,15+(y*5));
}
    y++;

    ////////////////////////////////////////////////////////////


    //////////////////////////////
if(this.proyects[i].nota==0 || this.proyects[i].nota==null){
    doc.text(
      "Nota: "  + "Sin asignar Nota"
    , 10,15+(y*5));
}
else if(this.proyects[i].nota>0){
      doc.text(
      "Nota: "  + this.proyects[i].nota
    , 10,15+(y*5));
    }

    y++;

    ////////////////////////////////////////////////////////////

    doc.line(10, 15+(y*5) , 200, 15+(y*5));
    y++;
}




doc.save("Proyectos y propuestas.pdf");
}
</script>

<style scoped>
</style>
