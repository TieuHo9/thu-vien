import { INhanVien } from 'app/entities/nhan-vien/nhan-vien.model';

export interface IPhongBan {
  id?: number;
  maPhong?: string | null;
  tenPhong?: string | null;
  nhanvs?: INhanVien[] | null;
}

export class PhongBan implements IPhongBan {
  constructor(public id?: number, public maPhong?: string | null, public tenPhong?: string | null, public nhanvs?: INhanVien[] | null) {}
}

export function getPhongBanIdentifier(phongBan: IPhongBan): number | undefined {
  return phongBan.id;
}
