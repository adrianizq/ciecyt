/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import CategorizacionUpdateComponent from '@/entities/categorizacion/categorizacion-update.vue';
import CategorizacionClass from '@/entities/categorizacion/categorizacion-update.component';
import CategorizacionService from '@/entities/categorizacion/categorizacion.service';

import ProyectoService from '@/entities/proyecto/proyecto.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('Categorizacion Management Update Component', () => {
    let wrapper: Wrapper<CategorizacionClass>;
    let comp: CategorizacionClass;
    let categorizacionServiceStub: SinonStubbedInstance<CategorizacionService>;

    beforeEach(() => {
      categorizacionServiceStub = sinon.createStubInstance<CategorizacionService>(CategorizacionService);

      wrapper = shallowMount<CategorizacionClass>(CategorizacionUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          categorizacionService: () => categorizacionServiceStub,

          proyectoService: () => new ProyectoService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.categorizacion = entity;
        categorizacionServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(categorizacionServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.categorizacion = entity;
        categorizacionServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(categorizacionServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
