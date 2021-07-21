import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { INhanVienCongTac } from '../nhan-vien-cong-tac.model';
import { NhanVienCongTacService } from '../service/nhan-vien-cong-tac.service';
import { NhanVienCongTacDeleteDialogComponent } from '../delete/nhan-vien-cong-tac-delete-dialog.component';

@Component({
  selector: 'jhi-nhan-vien-cong-tac',
  templateUrl: './nhan-vien-cong-tac.component.html',
})
export class NhanVienCongTacComponent implements OnInit {
  nhanVienCongTacs?: INhanVienCongTac[];
  isLoading = false;

  constructor(protected nhanVienCongTacService: NhanVienCongTacService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.nhanVienCongTacService.query().subscribe(
      (res: HttpResponse<INhanVienCongTac[]>) => {
        this.isLoading = false;
        this.nhanVienCongTacs = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: INhanVienCongTac): number {
    return item.id!;
  }

  delete(nhanVienCongTac: INhanVienCongTac): void {
    const modalRef = this.modalService.open(NhanVienCongTacDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.nhanVienCongTac = nhanVienCongTac;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
