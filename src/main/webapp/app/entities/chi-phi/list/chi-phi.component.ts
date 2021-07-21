import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IChiPhi } from '../chi-phi.model';
import { ChiPhiService } from '../service/chi-phi.service';
import { ChiPhiDeleteDialogComponent } from '../delete/chi-phi-delete-dialog.component';

@Component({
  selector: 'jhi-chi-phi',
  templateUrl: './chi-phi.component.html',
})
export class ChiPhiComponent implements OnInit {
  chiPhis?: IChiPhi[];
  isLoading = false;

  constructor(protected chiPhiService: ChiPhiService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.chiPhiService.query().subscribe(
      (res: HttpResponse<IChiPhi[]>) => {
        this.isLoading = false;
        this.chiPhis = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IChiPhi): number {
    return item.id!;
  }

  delete(chiPhi: IChiPhi): void {
    const modalRef = this.modalService.open(ChiPhiDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.chiPhi = chiPhi;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
