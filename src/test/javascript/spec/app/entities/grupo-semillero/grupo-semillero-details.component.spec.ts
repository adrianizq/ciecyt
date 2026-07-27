/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import GrupoSemilleroDetailComponent from '@/entities/grupo-semillero/grupo-semillero-details.vue';
import GrupoSemilleroClass from '@/entities/grupo-semillero/grupo-semillero-details.component';
import GrupoSemilleroService from '@/entities/grupo-semillero/grupo-semillero.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('GrupoSemillero Management Detail Component', () => {
    let wrapper: Wrapper<GrupoSemilleroClass>;
    let comp: GrupoSemilleroClass;
    let grupoSemilleroServiceStub: SinonStubbedInstance<GrupoSemilleroService>;

    beforeEach(() => {
      grupoSemilleroServiceStub = sinon.createStubInstance<GrupoSemilleroService>(GrupoSemilleroService);

      wrapper = shallowMount<GrupoSemilleroClass>(GrupoSemilleroDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { grupoSemilleroService: () => grupoSemilleroServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundGrupoSemillero = { id: 123 };
        grupoSemilleroServiceStub.find.resolves(foundGrupoSemillero);

        // WHEN
        comp.retrieveGrupoSemillero(123);
        await comp.$nextTick();

        // THEN
        expect(comp.grupoSemillero).toBe(foundGrupoSemillero);
      });
    });
  });
});
