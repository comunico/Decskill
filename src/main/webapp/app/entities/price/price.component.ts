import { defineComponent, inject, onMounted, ref, Ref, watch, watchEffect } from 'vue';

import { IPrice } from '@/shared/model/price.model';
import { useDateFormat } from '@/shared/composables';
import PriceService from './price.service';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Price',
  setup() {
    const dateFormat = useDateFormat();
    const priceService = inject('priceService', () => new PriceService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const prices: Ref<IPrice[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrievePrices = async () => {
      isFetching.value = true;
      try {
        const res = await priceService().retrieve();
        prices.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrievePrices();
    };

    onMounted(async () => {
      await retrievePrices();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IPrice) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removePrice = async () => {
      try {
        await priceService().delete(removeId.value);
        const message = 'A Price is deleted with identifier ' + removeId.value;
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrievePrices();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      prices,
      handleSyncList,
      isFetching,
      retrievePrices,
      clear,
      ...dateFormat,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removePrice,
    };
  },
});
