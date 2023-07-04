/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, MountingOptions } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import Price from '../../../../../../main/webapp/app/entities/price/price.vue';
import PriceService from '../../../../../../main/webapp/app/entities/price/price.service';
import AlertService from '../../../../../../main/webapp/app/shared/alert/alert.service';

type PriceComponentType = InstanceType<typeof Price>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('Price Management Component', () => {
    let priceServiceStub: SinonStubbedInstance<PriceService>;
    let mountOptions: MountingOptions<PriceComponentType>['global'];

    beforeEach(() => {
      priceServiceStub = sinon.createStubInstance<PriceService>(PriceService);
      priceServiceStub.retrieve.resolves({ headers: {} });

      alertService = new AlertService({
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          bModal: bModalStub as any,
          'font-awesome-icon': true,
          'b-badge': true,
          'b-button': true,
          'router-link': true,
        },
        directives: {
          'b-modal': {},
        },
        provide: {
          alertService,
          priceService: () => priceServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        priceServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(Price, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(priceServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.prices[0]).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
    describe('Handles', () => {
      let comp: PriceComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(Price, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        priceServiceStub.retrieve.reset();
        priceServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        priceServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removePrice();
        await comp.$nextTick(); // clear components

        // THEN
        expect(priceServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(priceServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
