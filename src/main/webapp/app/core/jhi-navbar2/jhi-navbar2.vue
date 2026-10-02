<template>
  <b-navbar toggleable="md" type="info" class="bg-light"> 
 
        <b-collapse is-nav id="header-tabs">

           <!-- <b-navbar-nav class="ml-auto"> --> 
           <b-navbar-nav  class="navbar navbar-fixed-top navbar-custom ">
                <template v-for="menu in menus2">
                    <b-nav-item :key="menu.id" :to="menu.url" exact v-if="!menu.children.length">
                        <span>
                            <font-awesome-icon :icon="menu.icono || 'asterisk'"/>
                            <span>
                                {{menu.nombre}}
                            </span>
                        </span>
                    </b-nav-item>

                    <b-nav-item-dropdown :key="menu.id"
                        right
                        href="javascript:void(0);"
                        :id="menu.id.toString()"
                        :class="{'router-link-active': subIsActive(menu.url)}"
                        active-class="active"
                        class="pointer"
                        v-else>
                    <template #button-content>
<span  class="navbar-dropdown-menu">
                        <font-awesome-icon :icon="menu.icono || 'asterisk'"/>
                        <span>
                            {{ menu.nombre }}
                        </span>
                    </span>
</template>
                        <template v-for="submenu in menu.children">
                            <b-dropdown-item :key="submenu.id" :to="submenu.url" tag="b-dropdown-item" v-if="isUrl(submenu.url)">
                                <font-awesome-icon :icon="submenu.icono || 'asterisk'"/>
                                <span>{{ submenu.nombre }}</span>
                            </b-dropdown-item>

                            <b-dropdown-item :key="submenu.id" v-if="!isUrl(submenu.url)" @click="actionMenu(submenu.url)">
                                <font-awesome-icon :icon="submenu.icono || 'asterisk'"/>
                                <span>{{ submenu.nombre }}</span>
                            </b-dropdown-item>
                        </template>


                    </b-nav-item-dropdown>
                </template>

                <!--b-nav-item to="/" exact>
                    <span>
                        <font-awesome-icon icon="home"/>
                        <span v-text="$t('global.menu.home')"></span>
                    </span>
                </b-nav-item-->
                <!--<b-nav-item-dropdown
                    id="entity-menu"
                    v-if="authenticated"
                    :class="{'router-link-active': subIsActive('/entity')}"
                    active-class="active" class="pointer">
                    <span slot="button-content" class="navbar-dropdown-menu">
                        <font-awesome-icon icon="th-list" />
                        <span v-text="$t('global.menu.entities.main')"></span>
                    </span>
                    <b-dropdown-item to="/entity/proyecto">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.proyecto')"></span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/entity/linea-investigacion">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.lineaInvestigacion')"></span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/entity/grupo-semillero">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.grupoSemillero')"></span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/entity/facultad">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.facultad')"></span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/entity/modalidad">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.modalidad')"></span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/entity/acuerdo">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.acuerdo')"></span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/entity/ciclo-propedeutico">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.cicloPropedeutico')"></span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/entity/resultados-esperados">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.resultadosEsperados')"></span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/entity/producto">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.producto')"></span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/entity/producto-proyecto">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.productoProyecto')"></span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/entity/impactos-esperados">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.impactosEsperados')"></span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/entity/cronograma">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.cronograma')"></span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/entity/rubro">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.rubro')"></span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/entity/presupuesto-valor">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.presupuestoValor')"></span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/entity/entidad">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.entidad')"></span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/entity/entidad-financiadora">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.entidadFinanciadora')"></span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/entity/elemento">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.elemento')"></span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/entity/elemento-proyecto">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.elementoProyecto')"></span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/entity/formato">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.formato')"></span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/entity/tipo-pregunta">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.tipoPregunta')"></span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/entity/pregunta">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.pregunta')"></span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/entity/proyecto-respuestas">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.proyectoRespuestas')"></span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/entity/roles-modalidad">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.rolesModalidad')"></span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/entity/fases">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.fases')"></span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/entity/proyecto-fase">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.proyectoFase')"></span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/entity/cronograma-ciecyt">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.cronogramaCiecyt')"></span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/entity/cronograma-ciecyt-fases">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.cronogramaCiecytFases')"></span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/entity/integrante-proyecto">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.integranteProyecto')"></span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/entity/solicitud">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.solicitud')"></span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/entity/adjunto-proyecto-fase">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.adjuntoProyectoFase')"></span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/entity/retroalimentacion">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.retroalimentacion')"></span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/entity/adjunto-retroalimentacion">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.adjuntoRetroalimentacion')"></span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/entity/ficha-tecnica">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.fichaTecnica')"></span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/entity/categorizacion">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.categorizacion')"></span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/entity/usuario">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.usuario')"></span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/entity/rol-menu">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.rolMenu')"></span>
                    </b-dropdown-item>
                </b-nav-item-dropdown>-->

                <!--<b-nav-item-dropdown
                    id="admin-menu"
                    v-if="hasAnyAuthority('ROLE_ADMIN')"
                    :class="{'router-link-active': subIsActive('/admin')}"
                    active-class="active"
                    class="pointer">
                    <span slot="button-content" class="navbar-dropdown-menu">
                        <font-awesome-icon icon="user-plus"/>
                        <span v-text="$t('global.menu.admin.main')"></span>
                    </span>
                    <b-dropdown-item  to="/admin/jhi-metrics">
                        <font-awesome-icon icon="tachometer-alt" />
                        <span v-text="$t('global.menu.admin.metrics')"></span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/admin/jhi-health">
                        <font-awesome-icon icon="heart" />
                        <span v-text="$t('global.menu.admin.health')"></span>
                    </b-dropdown-item>
                    <b-dropdown-item  to="/admin/jhi-configuration">
                        <font-awesome-icon icon="list" />
                        <span v-text="$t('global.menu.admin.configuration')"></span>
                    </b-dropdown-item>
                    <b-dropdown-item  to="/admin/audits">
                        <font-awesome-icon icon="bell" />
                        <span v-text="$t('global.menu.admin.audits')"></span>
                    </b-dropdown-item>
                    <b-dropdown-item  to="/admin/logs">
                        <font-awesome-icon icon="tasks" />
                        <span v-text="$t('global.menu.admin.logs')"></span>
                    </b-dropdown-item>
                    <b-dropdown-item v-if="swaggerEnabled"  to="/admin/docs">
                        <font-awesome-icon icon="book" />
                        <span v-text="$t('global.menu.admin.apidocs')"></span>
                    </b-dropdown-item>
                </b-nav-item-dropdown-->

                <!--b-nav-item-dropdown--
                    id="admin-menu"
                    v-if="hasAnyAuthority('ROLE_ADMIN')"
                    :class="{'router-link-active': subIsActive('/pendiente')}"
                    active-class="active"
                    class="pointer">
                    <span slot="button-content" class="navbar-dropdown-menu">
                        <font-awesome-icon icon="user-plus"/>
                        <span>Pendientes por ubicar</span>
                    </span>
                    <b-dropdown-item to="/entity/menu">
                        <font-awesome-icon icon="bars"/>
                        <span>Aplicaciones</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/admin/user-management">
                        <font-awesome-icon icon="user"/>
                        <span v-text="$t('global.menu.admin.userManagement')"></span>
                    </b-dropdown-item>

                </b-nav-item-dropdown-->
                <!--<b-nav-item-dropdown id="languagesnavBarDropdown" right v-if="languages && Object.keys(languages).length > 1">
                    <span slot="button-content">
                        <font-awesome-icon icon="flag" />
                        <span v-text="$t('global.menu.language')"></span>
                    </span>
                    <b-dropdown-item v-for="(value, key) in languages" :key="`lang-${key}`" v-on:click="changeLanguage(key);"
                        :class="{ active: isActiveLanguage(key)}">
                        {{value.name}}
                    </b-dropdown-item>
                ></b-nav-item-dropdown-->


            </b-navbar-nav>
        </b-collapse>
    </b-navbar>
</template>

<script lang="ts" src="./jhi-navbar2.component.ts">
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
    /* ==========================================================================
        Navbar2
        ========================================================================== */
    .navbar-version {
        font-size: 10px;
    }


    @media screen and (min-width: 768px) {
        .jh-navbar-toggler {
            display: none;
        }
    }

    @media screen and (min-width: 768px) and (max-width: 1150px) {
        span span {
            display: none;
        }
    }

    @media screen and (max-width: 767px) {
        .jh-logo-container {
            width: 100%;
        }
    }

    .navbar-title {
        display: inline-block;
        vertical-align: middle;
    }

    /* waiting for bootstrap fix bug on nav-item-dropdown a:active
    https://github.com/bootstrap-vue/bootstrap-vue/issues/2219
    */
    nav li.router-link-active .navbar-dropdown-menu {
        cursor: pointer;
    }

    /* ==========================================================================
        Logo styles
        ========================================================================== */
    .navbar-brand.logo {
        padding: 5px 15px;
    }

    .logo .logo-img {
        height: 45px;
        display: inline-block;
        vertical-align: middle;
        width: 70px;
    }

    .logo-img {
        height: 100%;
        background: url("../../../content/images/logo-jhipster.png") no-repeat center center;
        background-size: contain;
        width: 100%;
    }
</style>
