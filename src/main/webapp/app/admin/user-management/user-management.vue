<template>
    <div>
        <h2 class="mb-4">
            <span id="user-management-page-heading" v-text="$t('userManagement.home.title')"></span>
            <router-link custom v-slot="{ navigate }" :to="{name: 'JhiUserCreate'}"><button class="btn btn-primary btn-md float-right jh-create-entity" @click="navigate">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span v-text="$t('userManagement.home.createLabel')"></span>
            </button></router-link>
        </h2>
        <b-alert :show="dismissCountDown"
                 dismissible
                 :variant="alertType"
                 @dismissed="dismissCountDown=0"
                 @dismiss-count-down="countDownChanged">
            {{alertMessage}}
        </b-alert>
        <div class="table-responsive" v-if="users">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'id' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('id')" v-on:keydown.enter="changeOrder('id')" v-on:keydown.space.prevent="changeOrder('id')"><span v-text="$t('global.field.id')"></span>
                        <font-awesome-icon icon="sort"></font-awesome-icon>
                    </th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'login' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('login')" v-on:keydown.enter="changeOrder('login')" v-on:keydown.space.prevent="changeOrder('login')"><span v-text="$t('userManagement.login')"></span>
                        <font-awesome-icon icon="sort"></font-awesome-icon>
                    </th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'email' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('email')" v-on:keydown.enter="changeOrder('email')" v-on:keydown.space.prevent="changeOrder('email')"><span v-text="$t('userManagement.email')"></span>
                        <font-awesome-icon icon="sort"></font-awesome-icon>
                    </th>
                    <th scope="col" class="text-right"><span class="visually-hidden" v-text="$t('global.menu.actions')"></span></th>
                    <!--<th role="button" tabindex="0" :aria-sort="propOrder === 'langKey' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('langKey')" v-on:keydown.enter="changeOrder('langKey')" v-on:keydown.space.prevent="changeOrder('langKey')"><span v-text="$t('userManagement.langKey')"></span>
                        <font-awesome-icon icon="sort"></font-awesome-icon>
                    </th>-->
                    <th><span v-text="$t('userManagement.profiles')"></span></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'createdDate' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('createdDate')" v-on:keydown.enter="changeOrder('createdDate')" v-on:keydown.space.prevent="changeOrder('createdDate')"><span v-text="$t('userManagement.createdDate')"></span>
                        <font-awesome-icon icon="sort"></font-awesome-icon>
                    </th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'lastModifiedBy' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('lastModifiedBy')" v-on:keydown.enter="changeOrder('lastModifiedBy')" v-on:keydown.space.prevent="changeOrder('lastModifiedBy')"><span v-text="$t('userManagement.lastModifiedBy')"></span>
                        <font-awesome-icon icon="sort"></font-awesome-icon>
                    </th>
                    <th id="modified-date-sort" v-on:click="changeOrder('lastModifiedDate')"><span v-text="$t('userManagement.lastModifiedDate')"></span>
                        <font-awesome-icon icon="sort"></font-awesome-icon>
                    </th>
                    <th scope="col" class="text-right"><span class="visually-hidden" v-text="$t('global.menu.actions')"></span></th>
                </tr>
                </thead>
                <tbody v-if="users">
                <tr v-for="user in users" :key="user.id" :id="user.login">
                    <td>
                        <router-link custom v-slot="{ navigate, href }" :to="{name: 'JhiUserView', params: {userId: user.login}}"><a :href="href" @click="navigate">{{user.id}}</a></router-link>
                    </td>
                    <td>{{user.login}}</td>
                    <td class="jhi-user-email">{{user.email}}</td>
                    <td>
                        <button class="btn btn-danger btn-sm deactivated"
                                v-on:click="setActive(user, true)" v-if="!user.activated"
                                v-text="$t('userManagement.deactivated')"></button>
                        <button class="btn btn-success btn-sm" v-on:click="setActive(user, false)" v-if="user.activated"
                                :disabled="username === user.login" v-text="$t('userManagement.activated')"></button>
                    </td>
                    <!--<td>{{user.langKey}}</td>-->
                    <td>
                        <div v-for="authority of user.authorities" :key="authority">
                            <span class="badge badge-info">{{ authority }}</span>
                        </div>
                    </td>
                    <td>{{ formatDate(user.createdDate) }}</td>
                    <td>{{user.lastModifiedBy}}</td>
                    <td>{{ formatDate(user.lastModifiedDate) }}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link custom v-slot="{ navigate }" :to="{name: 'JhiUserView', params: {userId: user.login}}"><button class="btn btn-info btn-sm details" :aria-label="$t('entity.action.view')" @click="navigate">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')"></span>
                            </button></router-link>
                            <router-link custom v-slot="{ navigate }" :to="{name: 'JhiUserEdit', params: {userId: user.login}}"><button class="btn btn-primary btn-sm edit" :aria-label="$t('entity.action.edit')" @click="navigate">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')"></span>
                            </button></router-link>
                            <b-button v-on:click="prepareRemove(user)"
                                      variant="danger"
                                      class="btn btn-sm delete"
                                      :disabled="username === user.login"
                                      v-b-modal.removeUser>
                                <font-awesome-icon icon="times"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.delete')"></span>
                            </b-button>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
            <b-modal ref="removeUser" id="removeUser" v-bind:title="$t('entity.delete.title')" @ok="deleteUser()">
                <div class="modal-body">
                    <p id="jhi-delete-user-heading" v-text="$t('userManagement.delete.question', { 'login': removeId})"></p>
                </div>
                <template #modal-footer>
<div >
                    <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()"></button>
                    <button type="button" class="btn btn-primary" id="confirm-delete-user" v-text="$t('entity.action.delete')" v-on:click="deleteUser()"></button>
                </div>
</template>
            </b-modal>
        </div>
        <div v-show="users && users.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" @update:model-value="loadPage"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./user-management.component.ts">
</script>
