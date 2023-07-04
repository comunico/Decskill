/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, MountingOptions } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { RouteLocation } from 'vue-router';

import PriceDetails from '../../../../../../main/webapp/app/entities/price/price-details.vue';
import PriceService from '../../../../../../main/webapp/app/entities/price/price.service';
import AlertService from '../../../../../../main/webapp/app/shared/alert/alert.service';

type PriceDetailsComponentType = InstanceType<typeof PriceDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const priceSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Price Management Detail Component', () => {
    let priceServiceStub: SinonStubbedInstance<PriceService>;
    let mountOptions: MountingOptions<PriceDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      priceServiceStub = sinon.createStubInstance<PriceService>(PriceService);

      alertService = new AlertService({
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'router-link': true,
        },
        provide: {
          alertService,
          priceService: () => priceServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        priceServiceStub.find.resolves(priceSample);
        route = {
          params: {
            priceId: '' + 123,
          },
        };
        const wrapper = shallowMount(PriceDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.price).toMatchObject(priceSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        priceServiceStub.find.resolves(priceSample);
        const wrapper = shallowMount(PriceDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
