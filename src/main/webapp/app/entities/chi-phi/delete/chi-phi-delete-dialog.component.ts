import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IChiPhi } from '../chi-phi.model';
import { ChiPhiService } from '../service/chi-phi.service';

@Component({
  templateUrl: './chi-phi-delete-dialog.component.html',
})
export class ChiPhiDeleteDialogComponent {
  chiPhi?: IChiPhi;

  constructor(protected chiPhiService: ChiPhiService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.chiPhiService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
