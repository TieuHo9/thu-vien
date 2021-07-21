import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPhongBanNhanVien } from '../phong-ban-nhan-vien.model';
import { PhongBanNhanVienService } from '../service/phong-ban-nhan-vien.service';

@Component({
  templateUrl: './phong-ban-nhan-vien-delete-dialog.component.html',
})
export class PhongBanNhanVienDeleteDialogComponent {
  phongBanNhanVien?: IPhongBanNhanVien;

  constructor(protected phongBanNhanVienService: PhongBanNhanVienService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.phongBanNhanVienService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
