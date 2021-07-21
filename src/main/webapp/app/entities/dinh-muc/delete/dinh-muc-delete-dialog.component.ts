import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDinhMuc } from '../dinh-muc.model';
import { DinhMucService } from '../service/dinh-muc.service';

@Component({
  templateUrl: './dinh-muc-delete-dialog.component.html',
})
export class DinhMucDeleteDialogComponent {
  dinhMuc?: IDinhMuc;

  constructor(protected dinhMucService: DinhMucService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dinhMucService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
