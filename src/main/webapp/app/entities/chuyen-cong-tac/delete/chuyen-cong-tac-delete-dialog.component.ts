import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IChuyenCongTac } from '../chuyen-cong-tac.model';
import { ChuyenCongTacService } from '../service/chuyen-cong-tac.service';

@Component({
  templateUrl: './chuyen-cong-tac-delete-dialog.component.html',
})
export class ChuyenCongTacDeleteDialogComponent {
  chuyenCongTac?: IChuyenCongTac;

  constructor(protected chuyenCongTacService: ChuyenCongTacService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.chuyenCongTacService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
