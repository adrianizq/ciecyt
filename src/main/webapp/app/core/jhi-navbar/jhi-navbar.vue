<template>
    <b-navbar toggleable="md" type="dark" class="ciecyt-navbar">
        <div class="jh-logo-container float-left">
            <b-navbar-toggle right class="jh-navbar-toggler d-lg-none float-right" href="javascript:void(0);" data-toggle="collapse" target="header-tabs" aria-expanded="false"
                             aria-label="Toggle navigation">
                <font-awesome-icon icon="bars"/>
            </b-navbar-toggle>
            <b-navbar-brand class="logo float-left" b-link to="/">
                <span class="logo-img"></span>
                <span class="navbar-brand-text">CIECYT-ITP</span>
            </b-navbar-brand>
        </div>
        <b-collapse is-nav id="header-tabs">
            <b-navbar-nav class="ml-auto">
                <template v-for="menu in menus">
                    <b-nav-item :to="menu.url" exact v-if="!menu.children.length">
                        <span>
                            <font-awesome-icon :icon="menu.icono || 'asterisk'"/>
                            <span>{{ menu.nombre }}</span>
                        </span>
                    </b-nav-item>

                    <b-nav-item-dropdown
                        right
                        href="javascript:void(0);"
                        :id="menu.id.toString()"
                        :class="{'router-link-active': subIsActive(menu.url)}"
                        active-class="active"
                        class="pointer"
                        v-else>
                    <span slot="button-content" class="navbar-dropdown-menu">
                        <font-awesome-icon :icon="menu.icono || 'asterisk'"/>
                        <span>{{ menu.nombre }}</span>
                    </span>
                        <template v-for="submenu in menu.children">
                            <b-dropdown-item :to="submenu.url" tag="b-dropdown-item" v-if="isUrl(submenu.url)">
                                <font-awesome-icon :icon="submenu.icono || 'asterisk'"/>
                                <span>{{ submenu.nombre }}</span>
                            </b-dropdown-item>

                            <b-dropdown-item v-if="!isUrl(submenu.url)" @click="actionMenu(submenu.url)">
                                <font-awesome-icon :icon="submenu.icono || 'asterisk'"/>
                                <span>{{ submenu.nombre }}</span>
                            </b-dropdown-item>
                        </template>
                    </b-nav-item-dropdown>
                </template>
            </b-navbar-nav>
        </b-collapse>
    </b-navbar>
</template>

<script lang="ts" src="./jhi-navbar.component.ts">
</script>

<style scoped>
    .ciecyt-navbar {
        background: linear-gradient(135deg, #003366 0%, #002244 100%) !important;
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
    }

    .navbar-brand-text {
        font-size: 1.1rem;
        font-weight: 700;
        color: #C4A94D !important;
        letter-spacing: 0.5px;
    }

    .navbar-version {
        font-size: 10px;
        color: rgba(255, 255, 255, 0.5);
    }

    @media screen and (min-width: 768px) {
        .jh-navbar-toggler {
            display: none;
        }
    }

    @media screen and (min-width: 768px) and (max-width: 1150px) {
        span span {
            display: none;
        }
    }

    @media screen and (max-width: 767px) {
        .jh-logo-container {
            width: 100%;
        }
    }

    .navbar-title {
        display: inline-block;
        vertical-align: middle;
    }

    nav li.router-link-active .navbar-dropdown-menu {
        cursor: pointer;
        color: #C4A94D;
    }

    .navbar-brand.logo {
        padding: 5px 15px;
    }

    .logo .logo-img {
        height: 40px;
        display: inline-block;
        vertical-align: middle;
        width: 40px;
        background: #C4A94D;
        border-radius: 50%;
    }

    /* Dropdown menu styles */
    ::v-deep .dropdown-menu {
        border: none;
        box-shadow: 0 4px 16px rgba(0, 0, 0, 0.15);
        border-radius: 0.5rem;
        padding: 0.5rem 0;
    }

    ::v-deep .dropdown-item {
        padding: 0.5rem 1rem;
        font-size: 0.9rem;
        color: #333;
        transition: all 0.15s ease;
    }

    ::v-deep .dropdown-item:hover {
        background-color: #F4F6F9;
        color: #003366;
    }

    ::v-deep .dropdown-item.active,
    ::v-deep .dropdown-item:active {
        background-color: #003366;
        color: #fff;
    }
</style>
