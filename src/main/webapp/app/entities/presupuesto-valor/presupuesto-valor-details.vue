<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="presupuestoValor">
                <h2 class="jh-entity-heading"><span v-text="$t('ciecytApp.presupuestoValor.detail.title')">PresupuestoValor</span> {{presupuestoValor.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ciecytApp.presupuestoValor.descripcion')">Descripcion</span>
                    </dt>
                    <dd>
                        <span>{{presupuestoValor.descripcion}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.presupuestoValor.justificacion')">Justificacion</span>
                    </dt>
                    <dd>
                        <span>{{presupuestoValor.justificacion}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.presupuestoValor.cantidad')">Cantidad</span>
                    </dt>
                    <dd>
                        <span>{{presupuestoValor.cantidad}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.presupuestoValor.valorUnitario')">Valor Unitario</span>
                    </dt>
                    <dd>
                        <span>{{presupuestoValor.valorUnitario}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.presupuestoValor.especie')">Especie</span>
                    </dt>
                    <dd>
                        <span>{{presupuestoValor.especie}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.presupuestoValor.dinero')">Dinero</span>
                    </dt>
                    <dd>
                        <span>{{presupuestoValor.dinero}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.presupuestoValor.ordenVista')">Orden Vista</span>
                    </dt>
                    <dd>
                        <span>{{presupuestoValor.ordenVista}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.presupuestoValor.presupuestoValorRubro')">Presupuesto Valor Rubro</span>
                    </dt>
                    <dd>
                        <div v-if="presupuestoValor.presupuestoValorRubroId">
                            <router-link :to="{name: 'RubroView', params: {presupuestoValorRubroId: presupuestoValor.presupuestoValorRubroId}}">{{presupuestoValor.presupuestoValorRubroRubro}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.presupuestoValor.presupuestoValorProyecto')">Presupuesto Valor Proyecto</span>
                    </dt>
                    <dd>
                        <div v-if="presupuestoValor.presupuestoValorProyectoId">
                            <router-link :to="{name: 'ProyectoView', params: {presupuestoValorProyectoId: presupuestoValor.presupuestoValorProyectoId}}">{{presupuestoValor.presupuestoValorProyectoTitulo}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.presupuestoValor.presupuestoValorEntidad')">Presupuesto Valor Entidad</span>
                    </dt>
                    <dd>
                        <div v-if="presupuestoValor.presupuestoValorEntidadId">
                            <router-link :to="{name: 'EntidadView', params: {presupuestoValorEntidadId: presupuestoValor.presupuestoValorEntidadId}}">{{presupuestoValor.presupuestoValorEntidadEntidad}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="presupuestoValor.id" :to="{name: 'PresupuestoValorEdit', params: {presupuestoValorId: presupuestoValor.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./presupuesto-valor-details.component.ts">
</script>
