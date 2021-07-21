import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICapBac } from '../cap-bac.model';
import { CapBacService } from '../service/cap-bac.service';
import { CapBacDeleteDialogComponent } from '../delete/cap-bac-delete-dialog.component';

@Component({
  selector: 'jhi-cap-bac',
  templateUrl: './cap-bac.component.html',
})
export class CapBacComponent implements OnInit {
  capBacs?: ICapBac[];
  isLoading = false;

  constructor(protected capBacService: CapBacService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.capBacService.query().subscribe(
      (res: HttpResponse<ICapBac[]>) => {
        this.isLoading = false;
        this.capBacs = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: ICapBac): number {
    return item.id!;
  }

  delete(capBac: ICapBac): void {
    const modalRef = this.modalService.open(CapBacDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.capBac = capBac;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
