/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@/shared/test/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { createMemoryHistory, createRouter } from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import LineaInvestigacionUpdateComponent from '@/entities/linea-investigacion/linea-investigacion-update.vue';
import LineaInvestigacionClass from '@/entities/linea-investigacion/linea-investigacion-update.component';
import LineaInvestigacionService from '@/entities/linea-investigacion/linea-investigacion.service';

import FacultadService from '@/entities/facultad/facultad.service';

const localVue = createLocalVue();

const i18n = config.initI18N();
const store = config.initVueXStore();
const router = createRouter({ history: createMemoryHistory(), routes: [] });
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('LineaInvestigacion Management Update Component', () => {
    let wrapper: Wrapper<LineaInvestigacionClass>;
    let comp: LineaInvestigacionClass;
    let lineaInvestigacionServiceStub: SinonStubbedInstance<LineaInvestigacionService>;

    beforeEach(() => {
      lineaInvestigacionServiceStub = sinon.createStubInstance<LineaInvestigacionService>(LineaInvestigacionService);

      wrapper = shallowMount<LineaInvestigacionClass>(LineaInvestigacionUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          lineaInvestigacionService: () => lineaInvestigacionServiceStub,

          facultadService: () => new FacultadService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.lineaInvestigacion = entity;
        lineaInvestigacionServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(lineaInvestigacionServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.lineaInvestigacion = entity;
        lineaInvestigacionServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(lineaInvestigacionServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
