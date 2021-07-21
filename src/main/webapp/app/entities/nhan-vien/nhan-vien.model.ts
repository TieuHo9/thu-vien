import { IPhongBan } from 'app/entities/phong-ban/phong-ban.model';
import { ICapBac } from 'app/entities/cap-bac/cap-bac.model';
import { IChuyenCongTac } from 'app/entities/chuyen-cong-tac/chuyen-cong-tac.model';
import { IPhongBanNhanVien } from 'app/entities/phong-ban-nhan-vien/phong-ban-nhan-vien.model';

export interface INhanVien {
  id?: number;
  maNhanVien?: string | null;
  tenNhanVien?: string | null;
  phongBan?: string | null;
  capBac?: string | null;
  phongbans?: IPhongBan[] | null;
  capbacs?: ICapBac[] | null;
  chuyencongtacs?: IChuyenCongTac[] | null;
  pbans?: IPhongBanNhanVien[] | null;
}

export class NhanVien implements INhanVien {
  constructor(
    public id?: number,
    public maNhanVien?: string | null,
    public tenNhanVien?: string | null,
    public phongBan?: string | null,
    public capBac?: string | null,
    public phongbans?: IPhongBan[] | null,
    public capbacs?: ICapBac[] | null,
    public chuyencongtacs?: IChuyenCongTac[] | null,
    public pbans?: IPhongBanNhanVien[] | null
  ) {}
}

export function getNhanVienIdentifier(nhanVien: INhanVien): number | undefined {
  return nhanVien.id;
}
