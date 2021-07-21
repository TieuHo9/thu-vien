import { INhanVien } from 'app/entities/nhan-vien/nhan-vien.model';

export interface IPhongBanNhanVien {
  id?: number;
  maPhong?: string | null;
  maNhanVien?: string | null;
  nviens?: INhanVien[] | null;
}

export class PhongBanNhanVien implements IPhongBanNhanVien {
  constructor(public id?: number, public maPhong?: string | null, public maNhanVien?: string | null, public nviens?: INhanVien[] | null) {}
}

export function getPhongBanNhanVienIdentifier(phongBanNhanVien: IPhongBanNhanVien): number | undefined {
  return phongBanNhanVien.id;
}
