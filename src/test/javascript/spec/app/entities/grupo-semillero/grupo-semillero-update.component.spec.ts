/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import GrupoSemilleroUpdateComponent from '@/entities/grupo-semillero/grupo-semillero-update.vue';
import GrupoSemilleroClass from '@/entities/grupo-semillero/grupo-semillero-update.component';
import GrupoSemilleroService from '@/entities/grupo-semillero/grupo-semillero.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('GrupoSemillero Management Update Component', () => {
    let wrapper: Wrapper<GrupoSemilleroClass>;
    let comp: GrupoSemilleroClass;
    let grupoSemilleroServiceStub: SinonStubbedInstance<GrupoSemilleroService>;

    beforeEach(() => {
      grupoSemilleroServiceStub = sinon.createStubInstance<GrupoSemilleroService>(GrupoSemilleroService);

      wrapper = shallowMount<GrupoSemilleroClass>(GrupoSemilleroUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          grupoSemilleroService: () => grupoSemilleroServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.grupoSemillero = entity;
        grupoSemilleroServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(grupoSemilleroServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.grupoSemillero = entity;
        grupoSemilleroServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(grupoSemilleroServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
