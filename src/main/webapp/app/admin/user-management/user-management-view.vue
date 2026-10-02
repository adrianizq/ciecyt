<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="user">
                <h2 class="jh-entity-heading">
                    <span v-text="$t('userManagement.detail.title')"></span> [<b>{{user.login}}</b>]
                </h2>
                <dl class="row jh-entity-details">
                    <dt><span v-text="$t('userManagement.login')"></span></dt>
                    <dd>
                        <span>{{user.login}}</span>
                        <b-badge variant="success" v-if="user.activated" v-text="$t('userManagement.activated')"></b-badge>
                        <b-badge variant="danger" v-if="!user.activated" v-text="$t('userManagement.deactivated')"></b-badge>
                    </dd>
                    <dt><span v-text="$t('userManagement.firstName')"></span></dt>
                    <dd>{{user.firstName}}</dd>
                    <dt><span v-text="$t('userManagement.lastName')"></span></dt>
                    <dd>{{user.lastName}}</dd>
                    <dt v-if="userInfo"><span>Identificacion</span></dt>
                    <dd v-if="userInfo">{{userInfo.nuip}}</dd>
                    <dt><span v-text="$t('userManagement.email')"></span></dt>
                    <dd>{{user.email}}</dd>
                    <!--<dt><span v-text="$t('userManagement.langKey')"></span></dt>
                    <dd>{{user.langKey}}</dd>-->
                    <dt><span v-text="$t('userManagement.createdBy')"></span></dt>
                    <dd>{{user.createdBy}}</dd>
                    <dt><span v-text="$t('userManagement.createdDate')"></span></dt>
                    <dd>{{ formatDate(user.createdDate) }}</dd>
                    <dt><span v-text="$t('userManagement.lastModifiedBy')"></span></dt>
                    <dd>{{user.lastModifiedBy}}</dd>
                    <dt><span v-text="$t('userManagement.lastModifiedDate')"></span></dt>
                    <dd>{{ formatDate(user.lastModifiedDate) }}</dd>
                    <dt><span v-text="$t('userManagement.profiles')"></span></dt>
                    <dd>
                        <ul class="list-unstyled">
                            <li v-for="authority of user.authorities" :key="authority">
                                <b-badge variant="info">{{authority}}</b-badge>
                            </li>
                        </ul>
                    </dd>
                </dl>
                <router-link custom v-slot="{ navigate }" :to="{name: 'JhiUser'}"><button type="submit" class="btn btn-info" @click="navigate">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"></span>
                </button></router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./user-management-view.component.ts">
</script>
