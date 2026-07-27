/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import FormatoDetailComponent from '@/entities/formato/formato-details.vue';
import FormatoClass from '@/entities/formato/formato-details.component';
import FormatoService from '@/entities/formato/formato.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Formato Management Detail Component', () => {
    let wrapper: Wrapper<FormatoClass>;
    let comp: FormatoClass;
    let formatoServiceStub: SinonStubbedInstance<FormatoService>;

    beforeEach(() => {
      formatoServiceStub = sinon.createStubInstance<FormatoService>(FormatoService);

      wrapper = shallowMount<FormatoClass>(FormatoDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { formatoService: () => formatoServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundFormato = { id: 123 };
        formatoServiceStub.find.resolves(foundFormato);

        // WHEN
        comp.retrieveFormato(123);
        await comp.$nextTick();

        // THEN
        expect(comp.formato).toBe(foundFormato);
      });
    });
  });
});
