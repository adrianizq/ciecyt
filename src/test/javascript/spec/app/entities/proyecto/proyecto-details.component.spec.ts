/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import ProyectoDetailComponent from '@/entities/proyecto/proyecto-details.vue';
import ProyectoClass from '@/entities/proyecto/proyecto-details.component';
import ProyectoService from '@/entities/proyecto/proyecto.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Proyecto Management Detail Component', () => {
    let wrapper: Wrapper<ProyectoClass>;
    let comp: ProyectoClass;
    let proyectoServiceStub: SinonStubbedInstance<ProyectoService>;

    beforeEach(() => {
      proyectoServiceStub = sinon.createStubInstance<ProyectoService>(ProyectoService);

      wrapper = shallowMount<ProyectoClass>(ProyectoDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { proyectoService: () => proyectoServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundProyecto = { id: 123 };
        proyectoServiceStub.find.resolves(foundProyecto);

        // WHEN
        comp.retrieveProyecto(123);
        await comp.$nextTick();

        // THEN
        expect(comp.proyecto).toBe(foundProyecto);
      });
    });
  });
});
