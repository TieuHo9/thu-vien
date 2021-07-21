import { IDeXuatThanhToan } from 'app/entities/de-xuat-thanh-toan/de-xuat-thanh-toan.model';

export interface IChiPhi {
  id?: number;
  loaiChiPhi?: string | null;
  soTien?: string | null;
  donViTienTe?: string | null;
  dexuats?: IDeXuatThanhToan[] | null;
}

export class ChiPhi implements IChiPhi {
  constructor(
    public id?: number,
    public loaiChiPhi?: string | null,
    public soTien?: string | null,
    public donViTienTe?: string | null,
    public dexuats?: IDeXuatThanhToan[] | null
  ) {}
}

export function getChiPhiIdentifier(chiPhi: IChiPhi): number | undefined {
  return chiPhi.id;
}
