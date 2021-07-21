import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IChuyenCongTac } from '../chuyen-cong-tac.model';
import { ChuyenCongTacService } from '../service/chuyen-cong-tac.service';
import { ChuyenCongTacDeleteDialogComponent } from '../delete/chuyen-cong-tac-delete-dialog.component';

@Component({
  selector: 'jhi-chuyen-cong-tac',
  templateUrl: './chuyen-cong-tac.component.html',
})
export class ChuyenCongTacComponent implements OnInit {
  chuyenCongTacs?: IChuyenCongTac[];
  isLoading = false;

  constructor(protected chuyenCongTacService: ChuyenCongTacService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.chuyenCongTacService.query().subscribe(
      (res: HttpResponse<IChuyenCongTac[]>) => {
        this.isLoading = false;
        this.chuyenCongTacs = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IChuyenCongTac): number {
    return item.id!;
  }

  delete(chuyenCongTac: IChuyenCongTac): void {
    const modalRef = this.modalService.open(ChuyenCongTacDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.chuyenCongTac = chuyenCongTac;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
