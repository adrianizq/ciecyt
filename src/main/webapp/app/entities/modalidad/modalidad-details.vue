<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="modalidad">
                <h2 class="jh-entity-heading"><span v-text="$t('ciecytApp.modalidad.detail.title')">Modalidad</span> {{modalidad.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ciecytApp.modalidad.modalidad')">Modalidad</span>
                    </dt>
                    <dd>
                        <span>{{modalidad.modalidad}}</span>
                    </dd>

                    <dt>
                        <span v-text="$t('ciecytApp.modalidad.contieneLinea')">Contiene Linea de Investigacion</span>
                    </dt>
                    <dd>
                        <span>{{modalidad.contieneLinea}}</span>
                    </dd>

                    <dt>
                        <span v-text="$t('ciecytApp.modalidad.modalidadAcuerdo')">Modalidad Acuerdo</span>
                    </dt>
                    <dd>
                        <div v-if="modalidad.modalidadAcuerdoId">
                            <router-link :to="{name: 'AcuerdoView', params: {modalidadAcuerdoId: modalidad.modalidadAcuerdoId}}">{{modalidad.modalidadAcuerdoAcuerdo}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="modalidad.id" :to="{name: 'ModalidadEdit', params: {modalidadId: modalidad.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./modalidad-details.component.ts">
</script>
