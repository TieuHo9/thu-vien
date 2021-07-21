import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICapBac } from '../cap-bac.model';
import { CapBacService } from '../service/cap-bac.service';

@Component({
  templateUrl: './cap-bac-delete-dialog.component.html',
})
export class CapBacDeleteDialogComponent {
  capBac?: ICapBac;

  constructor(protected capBacService: CapBacService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.capBacService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
