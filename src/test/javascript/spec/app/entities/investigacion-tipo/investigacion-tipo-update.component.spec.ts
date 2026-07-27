/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import InvestigacionTipoUpdateComponent from '@/entities/investigacion-tipo/investigacion-tipo-update.vue';
import InvestigacionTipoClass from '@/entities/investigacion-tipo/investigacion-tipo-update.component';
import InvestigacionTipoService from '@/entities/investigacion-tipo/investigacion-tipo.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('InvestigacionTipo Management Update Component', () => {
    let wrapper: Wrapper<InvestigacionTipoClass>;
    let comp: InvestigacionTipoClass;
    let investigacionTipoServiceStub: SinonStubbedInstance<InvestigacionTipoService>;

    beforeEach(() => {
      investigacionTipoServiceStub = sinon.createStubInstance<InvestigacionTipoService>(InvestigacionTipoService);

      wrapper = shallowMount<InvestigacionTipoClass>(InvestigacionTipoUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          investigacionTipoService: () => investigacionTipoServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.investigacionTipo = entity;
        investigacionTipoServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(investigacionTipoServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.investigacionTipo = entity;
        investigacionTipoServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(investigacionTipoServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
