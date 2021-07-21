import { ICapBac } from 'app/entities/cap-bac/cap-bac.model';
import { IDeXuatThanhToan } from 'app/entities/de-xuat-thanh-toan/de-xuat-thanh-toan.model';

export interface IDinhMuc {
  id?: number;
  maMuc?: string | null;
  loaiPhi?: string | null;
  soTien?: string | null;
  capBac?: string | null;
  cbacs?: ICapBac[] | null;
  dxuattts?: IDeXuatThanhToan[] | null;
}

export class DinhMuc implements IDinhMuc {
  constructor(
    public id?: number,
    public maMuc?: string | null,
    public loaiPhi?: string | null,
    public soTien?: string | null,
    public capBac?: string | null,
    public cbacs?: ICapBac[] | null,
    public dxuattts?: IDeXuatThanhToan[] | null
  ) {}
}

export function getDinhMucIdentifier(dinhMuc: IDinhMuc): number | undefined {
  return dinhMuc.id;
}
