import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { INhanVienCongTac } from '../nhan-vien-cong-tac.model';
import { NhanVienCongTacService } from '../service/nhan-vien-cong-tac.service';

@Component({
  templateUrl: './nhan-vien-cong-tac-delete-dialog.component.html',
})
export class NhanVienCongTacDeleteDialogComponent {
  nhanVienCongTac?: INhanVienCongTac;

  constructor(protected nhanVienCongTacService: NhanVienCongTacService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.nhanVienCongTacService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
