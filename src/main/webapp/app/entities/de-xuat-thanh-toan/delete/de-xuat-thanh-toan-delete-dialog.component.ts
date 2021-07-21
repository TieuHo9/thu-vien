import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDeXuatThanhToan } from '../de-xuat-thanh-toan.model';
import { DeXuatThanhToanService } from '../service/de-xuat-thanh-toan.service';

@Component({
  templateUrl: './de-xuat-thanh-toan-delete-dialog.component.html',
})
export class DeXuatThanhToanDeleteDialogComponent {
  deXuatThanhToan?: IDeXuatThanhToan;

  constructor(protected deXuatThanhToanService: DeXuatThanhToanService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.deXuatThanhToanService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
