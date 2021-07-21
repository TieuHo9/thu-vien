import { IChuyenCongTac } from 'app/entities/chuyen-cong-tac/chuyen-cong-tac.model';

export interface INhanVienCongTac {
  id?: number;
  maNhanVien?: string | null;
  maChuyenDi?: string | null;
  soTien?: string | null;
  chuyencts?: IChuyenCongTac[] | null;
}

export class NhanVienCongTac implements INhanVienCongTac {
  constructor(
    public id?: number,
    public maNhanVien?: string | null,
    public maChuyenDi?: string | null,
    public soTien?: string | null,
    public chuyencts?: IChuyenCongTac[] | null
  ) {}
}

export function getNhanVienCongTacIdentifier(nhanVienCongTac: INhanVienCongTac): number | undefined {
  return nhanVienCongTac.id;
}
