<template>
    <div class="container-toggle">

        <!--<b-form-checkbox v-model="dataModel" switch @change="change"></b-form-checkbox>-->
        <div class="custom-control custom-switch">
            <!--<p>{{ options[0] }}</p>-->

            <input type="checkbox" class="custom-control-input" id="customSwitch1">
            <label class="toggle" for="customSwitch1">
                <div>
                    Si
                </div>

                <div>
                    No
                </div>

            </label>
            <!--<p>{{ options[1] }}</p>-->
        </div>


    </div>
</template>

<script lang="ts" src="./toggle.component.ts">
</script>

<style scoped>
    .toggle{
        display: flex;
    }

    .custom-switch {
        padding-left: 3.9375rem;
    }
</style>
