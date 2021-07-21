import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDinhMuc } from '../dinh-muc.model';
import { DinhMucService } from '../service/dinh-muc.service';
import { DinhMucDeleteDialogComponent } from '../delete/dinh-muc-delete-dialog.component';

@Component({
  selector: 'jhi-dinh-muc',
  templateUrl: './dinh-muc.component.html',
})
export class DinhMucComponent implements OnInit {
  dinhMucs?: IDinhMuc[];
  isLoading = false;

  constructor(protected dinhMucService: DinhMucService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.dinhMucService.query().subscribe(
      (res: HttpResponse<IDinhMuc[]>) => {
        this.isLoading = false;
        this.dinhMucs = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IDinhMuc): number {
    return item.id!;
  }

  delete(dinhMuc: IDinhMuc): void {
    const modalRef = this.modalService.open(DinhMucDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.dinhMuc = dinhMuc;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
