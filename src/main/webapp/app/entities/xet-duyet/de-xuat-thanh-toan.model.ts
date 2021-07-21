import * as dayjs from 'dayjs';
import { IChiPhi } from 'app/entities/chi-phi/chi-phi.model';
import { IDinhMuc } from 'app/entities/dinh-muc/dinh-muc.model';

export interface IDeXuatThanhToan {
  id?: number;
  maDeXuat?: string | null;
  tenDeXuat?: string | null;
  tuNgay?: dayjs.Dayjs | null;
  denNgay?: dayjs.Dayjs | null;
  trangThaiTruongPhong?: string | null;
  trangThaiPhongTaiVu?: string | null;
  trangThaiBanLanhDao?: string | null;
  chiphis?: IChiPhi[] | null;
  dmucs?: IDinhMuc[] | null;
}

export class DeXuatThanhToan implements IDeXuatThanhToan {
  constructor(
    public id?: number,
    public maDeXuat?: string | null,
    public tenDeXuat?: string | null,
    public tuNgay?: dayjs.Dayjs | null,
    public denNgay?: dayjs.Dayjs | null,
    public trangThaiTruongPhong?: string | null,
    public trangThaiPhongTaiVu?: string | null,
    public trangThaiBanLanhDao?: string | null,
    public chiphis?: IChiPhi[] | null,
    public dmucs?: IDinhMuc[] | null
  ) {}
}

export function getDeXuatThanhToanIdentifier(deXuatThanhToan: IDeXuatThanhToan): number | undefined {
  return deXuatThanhToan.id;
}
