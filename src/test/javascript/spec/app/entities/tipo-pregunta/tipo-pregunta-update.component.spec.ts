/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import TipoPreguntaUpdateComponent from '@/entities/tipo-pregunta/tipo-pregunta-update.vue';
import TipoPreguntaClass from '@/entities/tipo-pregunta/tipo-pregunta-update.component';
import TipoPreguntaService from '@/entities/tipo-pregunta/tipo-pregunta.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('TipoPregunta Management Update Component', () => {
    let wrapper: Wrapper<TipoPreguntaClass>;
    let comp: TipoPreguntaClass;
    let tipoPreguntaServiceStub: SinonStubbedInstance<TipoPreguntaService>;

    beforeEach(() => {
      tipoPreguntaServiceStub = sinon.createStubInstance<TipoPreguntaService>(TipoPreguntaService);

      wrapper = shallowMount<TipoPreguntaClass>(TipoPreguntaUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          tipoPreguntaService: () => tipoPreguntaServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.tipoPregunta = entity;
        tipoPreguntaServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(tipoPreguntaServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.tipoPregunta = entity;
        tipoPreguntaServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(tipoPreguntaServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
