/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@/shared/test/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { createMemoryHistory, createRouter } from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import AdjuntoRetroalimentacionUpdateComponent from '@/entities/adjunto-retroalimentacion/adjunto-retroalimentacion-update.vue';
import AdjuntoRetroalimentacionClass from '@/entities/adjunto-retroalimentacion/adjunto-retroalimentacion-update.component';
import AdjuntoRetroalimentacionService from '@/entities/adjunto-retroalimentacion/adjunto-retroalimentacion.service';

import RetroalimentacionService from '@/entities/retroalimentacion/retroalimentacion.service';

const localVue = createLocalVue();

const i18n = config.initI18N();
const store = config.initVueXStore();
const router = createRouter({ history: createMemoryHistory(), routes: [] });
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('AdjuntoRetroalimentacion Management Update Component', () => {
    let wrapper: Wrapper<AdjuntoRetroalimentacionClass>;
    let comp: AdjuntoRetroalimentacionClass;
    let adjuntoRetroalimentacionServiceStub: SinonStubbedInstance<AdjuntoRetroalimentacionService>;

    beforeEach(() => {
      adjuntoRetroalimentacionServiceStub = sinon.createStubInstance<AdjuntoRetroalimentacionService>(AdjuntoRetroalimentacionService);

      wrapper = shallowMount<AdjuntoRetroalimentacionClass>(AdjuntoRetroalimentacionUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          adjuntoRetroalimentacionService: () => adjuntoRetroalimentacionServiceStub,

          retroalimentacionService: () => new RetroalimentacionService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.adjuntoRetroalimentacion = entity;
        adjuntoRetroalimentacionServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(adjuntoRetroalimentacionServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.adjuntoRetroalimentacion = entity;
        adjuntoRetroalimentacionServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(adjuntoRetroalimentacionServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
