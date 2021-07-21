import { INhanVien } from 'app/entities/nhan-vien/nhan-vien.model';
import { IDinhMuc } from 'app/entities/dinh-muc/dinh-muc.model';

export interface ICapBac {
  id?: number;
  tenCap?: string | null;
  nvs?: INhanVien[] | null;
  mucs?: IDinhMuc[] | null;
}

export class CapBac implements ICapBac {
  constructor(public id?: number, public tenCap?: string | null, public nvs?: INhanVien[] | null, public mucs?: IDinhMuc[] | null) {}
}

export function getCapBacIdentifier(capBac: ICapBac): number | undefined {
  return capBac.id;
}
