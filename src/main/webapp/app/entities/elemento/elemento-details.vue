<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="elemento">
                <h2 class="jh-entity-heading"><span v-text="$t('ciecytApp.elemento.detail.title')">Elemento</span> {{elemento.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ciecytApp.elemento.elemento')">Elemento</span>
                    </dt>
                    <dd>
                        <span>{{elemento.elemento}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.elemento.descripcion')">Descripcion</span>
                    </dt>
                    <dd>
                        <span>{{elemento.descripcion}}</span>
                    </dd>
                    <!--
                    <dt>
                        <span v-text="$t('ciecytApp.elemento.elementoFormato')">Elemento Formato</span>
                    </dt>
                    <dd>
                        <div v-if="elemento.elementoFormatoId">
                            <router-link :to="{name: 'FormatoView', params: {elementoFormatoId: elemento.elementoFormatoId}}">{{elemento.elementoFormatoFormato}}</router-link>
                        </div>
                    </dd>
                    -->
                    <!--<dt>
                        <span v-text="$t('ciecytApp.elemento.modalidad')">Elemento Modalidad</span>
                    </dt>
                    <dd>
                        <div v-if="elemento.modalidadId">
                            <router-link :to="{name: 'ModalidadView', params: {modalidadId: elemento.modalidadId}}">{{elemento.modalidadId}}</router-link>
                        </div>
                    </dd> -->

                    <dt>
                        <span v-text="$t('ciecytApp.elemento.elementoFase')">Fase</span>
                    </dt>
                    <dd>
                      <div  v-if="elemento.elementoFasesId">
                         <router-link :to="{name: 'FasesView', params: {fasesId: elemento.elementoFasesId}}">{{elemento.elementoFasesFase}}</router-link>
                       </div>
                    </dd>

                    
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="elemento.id" :to="{name: 'ElementoEdit', params: {elementoId: elemento.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./elemento-details.component.ts">
</script>
