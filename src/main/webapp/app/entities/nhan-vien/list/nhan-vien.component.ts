import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { INhanVien } from '../nhan-vien.model';
import { NhanVienService } from '../service/nhan-vien.service';
import { NhanVienDeleteDialogComponent } from '../delete/nhan-vien-delete-dialog.component';

@Component({
  selector: 'jhi-nhan-vien',
  templateUrl: './nhan-vien.component.html',
})
export class NhanVienComponent implements OnInit {
  nhanViens?: INhanVien[];
  isLoading = false;

  constructor(protected nhanVienService: NhanVienService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.nhanVienService.query().subscribe(
      (res: HttpResponse<INhanVien[]>) => {
        this.isLoading = false;
        this.nhanViens = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: INhanVien): number {
    return item.id!;
  }

  delete(nhanVien: INhanVien): void {
    const modalRef = this.modalService.open(NhanVienDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.nhanVien = nhanVien;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
