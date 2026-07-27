<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="pregunta">
                <h2 class="jh-entity-heading"><span v-text="$t('ciecytApp.pregunta.detail.title')">Pregunta</span> {{pregunta.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ciecytApp.pregunta.encabezado')">Encabezado</span>
                    </dt>
                    <dd>
                        <span>{{pregunta.encabezado}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.pregunta.descripcion')">Descripcion</span>
                    </dt>
                    <dd>
                        <span>{{pregunta.descripcion}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.pregunta.pregunta')">Pregunta</span>
                    </dt>
                    <dd>
                        <span>{{pregunta.pregunta}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.pregunta.preguntaTipoPregunta')">Pregunta Tipo Pregunta</span>
                    </dt>
                    <dd>
                        <div v-if="pregunta.preguntaTipoPreguntaId">
                            <router-link :to="{name: 'TipoPreguntaView', params: {preguntaTipoPreguntaId: pregunta.preguntaTipoPreguntaId}}">{{pregunta.preguntaTipoPreguntaTipoPregunta}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.pregunta.preguntaModalidad')">Pregunta Modalidad</span>
                    </dt>
                    <dd>
                        <div v-if="pregunta.preguntaModalidadId">
                            <router-link :to="{name: 'ModalidadView', params: {preguntaModalidadId: pregunta.preguntaModalidadId}}">{{pregunta.preguntaModalidadModalidad}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.pregunta.preguntaRolesModalidad')">Pregunta Roles Modalidad</span>
                    </dt>
                    <dd>
                        <div v-if="pregunta.preguntaRolesModalidadId">
                            <router-link :to="{name: 'RolesModalidadView', params: {preguntaRolesModalidadId: pregunta.preguntaRolesModalidadId}}">{{pregunta.preguntaRolesModalidadRol}}</router-link>
                        </div>
                    </dd>
                    
                    <dt>
                        <span v-text="$t('ciecytApp.pregunta.elemento')">Elemento</span>
                    </dt>
                    <dd>
                        <div v-if="pregunta.elementoId">
                            <router-link :to="{name: 'ElementoView', params: {elementoId: pregunta.elementoId}}">{{pregunta.elementoId}}</router-link>
                        </div>
                    </dd>
                    
                   

                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="pregunta.id" :to="{name: 'PreguntaEdit', params: {preguntaId: pregunta.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./pregunta-details.component.ts">
</script>
