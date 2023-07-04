import { IBrand } from '@/shared/model/brand.model';
import { IProduct } from '@/shared/model/product.model';

import { Currecny } from '@/shared/model/enumerations/currecny.model';
export interface IPrice {
  id?: number;
  startDate?: Date | null;
  endDate?: Date | null;
  priority?: number | null;
  price?: number | null;
  currency?: keyof typeof Currecny | null;
  brand?: IBrand | null;
  product?: IProduct | null;
}

export class Price implements IPrice {
  constructor(
    public id?: number,
    public startDate?: Date | null,
    public endDate?: Date | null,
    public priority?: number | null,
    public price?: number | null,
    public currency?: keyof typeof Currecny | null,
    public brand?: IBrand | null,
    public product?: IProduct | null
  ) {}
}
