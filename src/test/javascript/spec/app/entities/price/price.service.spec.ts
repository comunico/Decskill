/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';
import dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from '../../../../../../main/webapp/app/shared/composables/date-format';
import PriceService from '../../../../../../main/webapp/app/entities/price/price.service';
import { Price } from '../../../../../../main/webapp/app/shared/model/price.model';
import { Currecny } from '@/shared/model/enumerations/currecny.model';

const error = {
  response: {
    status: null,
    data: {
      type: null,
    },
  },
};

const axiosStub = {
  get: sinon.stub(axios, 'get'),
  post: sinon.stub(axios, 'post'),
  put: sinon.stub(axios, 'put'),
  patch: sinon.stub(axios, 'patch'),
  delete: sinon.stub(axios, 'delete'),
};

describe('Service Tests', () => {
  describe('Price Service', () => {
    let service: PriceService;
    let elemDefault;
    let currentDate: Date;

    beforeEach(() => {
      service = new PriceService();
      currentDate = new Date();
      elemDefault = new Price(123, currentDate, currentDate, 0, 0, 'EUR');
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            startDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
            endDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          elemDefault
        );
        axiosStub.get.resolves({ data: returnedFromService });

        return service.find(123).then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });

      it('should not find an element', async () => {
        axiosStub.get.rejects(error);
        return service
          .find(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should create a Price', async () => {
        const returnedFromService = Object.assign(
          {
            id: 123,
            startDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
            endDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            startDate: currentDate,
            endDate: currentDate,
          },
          returnedFromService
        );

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a Price', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a Price', async () => {
        const returnedFromService = Object.assign(
          {
            startDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
            endDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
            priority: 1,
            price: 1,
            currency: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            startDate: currentDate,
            endDate: currentDate,
          },
          returnedFromService
        );
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a Price', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a Price', async () => {
        const patchObject = Object.assign(
          {
            currency: 'BBBBBB',
          },
          new Price()
        );
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            startDate: currentDate,
            endDate: currentDate,
          },
          returnedFromService
        );
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a Price', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of Price', async () => {
        const returnedFromService = Object.assign(
          {
            startDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
            endDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
            priority: 1,
            price: 1,
            currency: 'BBBBBB',
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            startDate: currentDate,
            endDate: currentDate,
          },
          returnedFromService
        );
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve().then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of Price', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a Price', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a Price', async () => {
        axiosStub.delete.rejects(error);

        return service
          .delete(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });
    });
  });
});
