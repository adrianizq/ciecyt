/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import ProyectoRespuestasDetailComponent from '@/entities/proyecto-respuestas/proyecto-respuestas-details.vue';
import ProyectoRespuestasClass from '@/entities/proyecto-respuestas/proyecto-respuestas-details.component';
import ProyectoRespuestasService from '@/entities/proyecto-respuestas/proyecto-respuestas.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('ProyectoRespuestas Management Detail Component', () => {
    let wrapper: Wrapper<ProyectoRespuestasClass>;
    let comp: ProyectoRespuestasClass;
    let proyectoRespuestasServiceStub: SinonStubbedInstance<ProyectoRespuestasService>;

    beforeEach(() => {
      proyectoRespuestasServiceStub = sinon.createStubInstance<ProyectoRespuestasService>(ProyectoRespuestasService);

      wrapper = shallowMount<ProyectoRespuestasClass>(ProyectoRespuestasDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { proyectoRespuestasService: () => proyectoRespuestasServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundProyectoRespuestas = { id: 123 };
        proyectoRespuestasServiceStub.find.resolves(foundProyectoRespuestas);

        // WHEN
        comp.retrieveProyectoRespuestas(123);
        await comp.$nextTick();

        // THEN
        expect(comp.proyectoRespuestas).toBe(foundProyectoRespuestas);
      });
    });
  });
});
