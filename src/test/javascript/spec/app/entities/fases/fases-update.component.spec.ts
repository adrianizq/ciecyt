/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import FasesUpdateComponent from '@/entities/fases/fases-update.vue';
import FasesClass from '@/entities/fases/fases-update.component';
import FasesService from '@/entities/fases/fases.service';

import ModalidadService from '@/entities/modalidad/modalidad.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('Fases Management Update Component', () => {
    let wrapper: Wrapper<FasesClass>;
    let comp: FasesClass;
    let fasesServiceStub: SinonStubbedInstance<FasesService>;

    beforeEach(() => {
      fasesServiceStub = sinon.createStubInstance<FasesService>(FasesService);

      wrapper = shallowMount<FasesClass>(FasesUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          fasesService: () => fasesServiceStub,

          modalidadService: () => new ModalidadService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.fases = entity;
        fasesServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(fasesServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.fases = entity;
        fasesServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(fasesServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
