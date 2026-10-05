<template>
    <div>
        <div class="row justify-content-center">
            <div class="col-md-8">
                <h1 v-text="$t('reset.request.title')"></h1>

                <div class="alert alert-info" role="alert" v-html="$t('reset.request.messages.contactAdmin')"></div>

                <div class="card mt-4">
                    <div class="card-body">
                        <h5 class="card-title" v-text="$t('reset.request.steps.title')"></h5>
                        <ol class="mb-3">
                            <li v-text="$t('reset.request.steps.step1')"></li>
                            <li v-text="$t('reset.request.steps.step2')"></li>
                            <li v-text="$t('reset.request.steps.step3')"></li>
                        </ol>
                        <p class="text-muted small mb-3" v-html="$t('reset.request.messages.emailWarning')"></p>

                        <router-link v-if="authenticated" custom v-slot="{ navigate }" :to="{ name: 'JhiUserManagement' }">
                            <button class="btn btn-primary" id="goto-admin-reset" @click="navigate">
                                <font-awesome-icon icon="key"></font-awesome-icon>
                                <span v-text="$t('reset.request.actions.gotoAdmin')"></span>
                            </button>
                        </router-link>
                        <p v-else class="text-muted small mb-0" v-html="$t('reset.request.messages.requiresLogin')"></p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./reset-password-init.component.ts"></script>
