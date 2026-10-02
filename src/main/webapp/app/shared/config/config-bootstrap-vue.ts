import type { App } from 'vue';
import {
  BAlert,
  BBadge,
  BButton,
  BCard,
  BCardBody,
  BCardHeader,
  BCardText,
  BCollapse,
  BDropdown,
  BDropdownItem,
  BForm,
  BFormCheckbox,
  BFormGroup,
  BFormInput,
  BFormRadio,
  BFormRadioGroup,
  BFormSelect,
  BFormTextarea,
  BInputGroup,
  BLink,
  BModal,
  BNavItem,
  BNavItemDropdown,
  BNavbar,
  BNavbarBrand,
  BNavbarNav,
  BNavbarToggle,
  BPagination,
  BProgress,
  BProgressBar,
  BTable,
  BTab,
  BTabs,
} from 'bootstrap-vue-next';
import { vBModal } from 'bootstrap-vue-next';

import BFormDatepicker from '@/shared/components/form-datepicker.vue';

// bootstrap-vue-next no publica un plugin con todos los componentes: cada
// componente hay que registrarlo a mano sobre la instancia de la app.
const components: Record<string, any> = {
  'b-alert': BAlert,
  'b-badge': BBadge,
  'b-button': BButton,
  'b-card': BCard,
  'b-card-body': BCardBody,
  'b-card-header': BCardHeader,
  'b-card-text': BCardText,
  'b-checkbox': BFormCheckbox,
  'b-collapse': BCollapse,
  'b-dropdown': BDropdown,
  'b-dropdown-item': BDropdownItem,
  'b-form': BForm,
  'b-form-checkbox': BFormCheckbox,
  'b-form-datepicker': BFormDatepicker,
  'b-form-group': BFormGroup,
  'b-form-input': BFormInput,
  'b-form-radio': BFormRadio,
  'b-form-radio-group': BFormRadioGroup,
  'b-form-select': BFormSelect,
  'b-form-textarea': BFormTextarea,
  'b-input-group': BInputGroup,
  'b-link': BLink,
  'b-modal': BModal,
  'b-nav-item': BNavItem,
  'b-nav-item-dropdown': BNavItemDropdown,
  'b-navbar': BNavbar,
  'b-navbar-brand': BNavbarBrand,
  'b-navbar-nav': BNavbarNav,
  'b-navbar-toggle': BNavbarToggle,
  'b-pagination': BPagination,
  'b-progress': BProgress,
  'b-progress-bar': BProgressBar,
  'b-select': BFormSelect,
  'b-table': BTable,
  'b-tab': BTab,
  'b-tabs': BTabs,
};

export function initBootstrapVue(app: App): void {
  for (const [name, component] of Object.entries(components)) {
    app.component(name, component);
  }
  app.directive('b-modal', vBModal);
}
