<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="modalidad">
                <h2 class="jh-entity-heading"><span v-text="$t('ciecytApp.modalidad.detail.title')"></span> {{modalidad.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ciecytApp.modalidad.modalidad')"></span>
                    </dt>
                    <dd>
                        <span>{{modalidad.modalidad}}</span>
                    </dd>

                    <dt>
                        <span v-text="$t('ciecytApp.modalidad.contieneLinea')"></span>
                    </dt>
                    <dd>
                        <span>{{modalidad.contieneLinea}}</span>
                    </dd>

                    <dt>
                        <span v-text="$t('ciecytApp.modalidad.modalidadAcuerdo')"></span>
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
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"></span>
                </button>
                <router-link custom v-slot="{ navigate }" v-if="modalidad.id" :to="{name: 'ModalidadEdit', params: {modalidadId: modalidad.id}}"><button class="btn btn-primary" @click="navigate">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"></span>
                </button></router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./modalidad-details.component.ts">
</script>
