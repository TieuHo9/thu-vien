import * as dayjs from 'dayjs';
import { INhanVien } from 'app/entities/nhan-vien/nhan-vien.model';
import { INhanVienCongTac } from 'app/entities/nhan-vien-cong-tac/nhan-vien-cong-tac.model';

export interface IChuyenCongTac {
  id?: number;
  maChuyenDi?: string | null;
  tenChuyenDi?: string | null;
  thoiGianTu?: dayjs.Dayjs | null;
  thoiGianDen?: dayjs.Dayjs | null;
  maNhanVien?: string | null;
  tenNhanVien?: string | null;
  soDienThoai?: string | null;
  diaDiem?: string | null;
  soTien?: string | null;
  thanhToan?: string | null;
  nhanviens?: INhanVien[] | null;
  nhanviencts?: INhanVienCongTac[] | null;
}

export class ChuyenCongTac implements IChuyenCongTac {
  constructor(
    public id?: number,
    public maChuyenDi?: string | null,
    public tenChuyenDi?: string | null,
    public thoiGianTu?: dayjs.Dayjs | null,
    public thoiGianDen?: dayjs.Dayjs | null,
    public maNhanVien?: string | null,
    public tenNhanVien?: string | null,
    public soDienThoai?: string | null,
    public diaDiem?: string | null,
    public soTien?: string | null,
    public nhanviens?: INhanVien[] | null,
    public nhanviencts?: INhanVienCongTac[] | null,
    public thanhToan?: string | null
  ) {}
}

export function getChuyenCongTacIdentifier(chuyenCongTac: IChuyenCongTac): number | undefined {
  return chuyenCongTac.id;
}
