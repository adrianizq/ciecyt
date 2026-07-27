/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import CronogramaCiecytDetailComponent from '@/entities/cronograma-ciecyt/cronograma-ciecyt-details.vue';
import CronogramaCiecytClass from '@/entities/cronograma-ciecyt/cronograma-ciecyt-details.component';
import CronogramaCiecytService from '@/entities/cronograma-ciecyt/cronograma-ciecyt.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('CronogramaCiecyt Management Detail Component', () => {
    let wrapper: Wrapper<CronogramaCiecytClass>;
    let comp: CronogramaCiecytClass;
    let cronogramaCiecytServiceStub: SinonStubbedInstance<CronogramaCiecytService>;

    beforeEach(() => {
      cronogramaCiecytServiceStub = sinon.createStubInstance<CronogramaCiecytService>(CronogramaCiecytService);

      wrapper = shallowMount<CronogramaCiecytClass>(CronogramaCiecytDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { cronogramaCiecytService: () => cronogramaCiecytServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundCronogramaCiecyt = { id: 123 };
        cronogramaCiecytServiceStub.find.resolves(foundCronogramaCiecyt);

        // WHEN
        comp.retrieveCronogramaCiecyt(123);
        await comp.$nextTick();

        // THEN
        expect(comp.cronogramaCiecyt).toBe(foundCronogramaCiecyt);
      });
    });
  });
});
