<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="pregunta">
                <h2 class="jh-entity-heading"><span v-text="$t('ciecytApp.pregunta.detail.title')"></span> {{pregunta.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ciecytApp.pregunta.encabezado')"></span>
                    </dt>
                    <dd>
                        <span>{{pregunta.encabezado}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.pregunta.descripcion')"></span>
                    </dt>
                    <dd>
                        <span>{{pregunta.descripcion}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.pregunta.pregunta')"></span>
                    </dt>
                    <dd>
                        <span>{{pregunta.pregunta}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.pregunta.preguntaTipoPregunta')"></span>
                    </dt>
                    <dd>
                        <div v-if="pregunta.preguntaTipoPreguntaId">
                            <router-link :to="{name: 'TipoPreguntaView', params: {preguntaTipoPreguntaId: pregunta.preguntaTipoPreguntaId}}">{{pregunta.preguntaTipoPreguntaTipoPregunta}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.pregunta.preguntaModalidad')"></span>
                    </dt>
                    <dd>
                        <div v-if="pregunta.preguntaModalidadId">
                            <router-link :to="{name: 'ModalidadView', params: {preguntaModalidadId: pregunta.preguntaModalidadId}}">{{pregunta.preguntaModalidadModalidad}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.pregunta.preguntaRolesModalidad')"></span>
                    </dt>
                    <dd>
                        <div v-if="pregunta.preguntaRolesModalidadId">
                            <router-link :to="{name: 'RolesModalidadView', params: {preguntaRolesModalidadId: pregunta.preguntaRolesModalidadId}}">{{pregunta.preguntaRolesModalidadRol}}</router-link>
                        </div>
                    </dd>
                    
                    <dt>
                        <span v-text="$t('ciecytApp.pregunta.elemento')"></span>
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
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"></span>
                </button>
                <router-link custom v-slot="{ navigate }" v-if="pregunta.id" :to="{name: 'PreguntaEdit', params: {preguntaId: pregunta.id}}"><button class="btn btn-primary" @click="navigate">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"></span>
                </button></router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./pregunta-details.component.ts">
</script>
