/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import UsuarioUpdateComponent from '@/entities/usuario/usuario-update.vue';
import UsuarioClass from '@/entities/usuario/usuario-update.component';
import UsuarioService from '@/entities/usuario/usuario.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('Usuario Management Update Component', () => {
    let wrapper: Wrapper<UsuarioClass>;
    let comp: UsuarioClass;
    let usuarioServiceStub: SinonStubbedInstance<UsuarioService>;

    beforeEach(() => {
      usuarioServiceStub = sinon.createStubInstance<UsuarioService>(UsuarioService);

      wrapper = shallowMount<UsuarioClass>(UsuarioUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          usuarioService: () => usuarioServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.usuario = entity;
        usuarioServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(usuarioServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.usuario = entity;
        usuarioServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(usuarioServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
