import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPhongBanNhanVien } from '../phong-ban-nhan-vien.model';
import { PhongBanNhanVienService } from '../service/phong-ban-nhan-vien.service';
import { PhongBanNhanVienDeleteDialogComponent } from '../delete/phong-ban-nhan-vien-delete-dialog.component';

@Component({
  selector: 'jhi-phong-ban-nhan-vien',
  templateUrl: './phong-ban-nhan-vien.component.html',
})
export class PhongBanNhanVienComponent implements OnInit {
  phongBanNhanViens?: IPhongBanNhanVien[];
  isLoading = false;

  constructor(protected phongBanNhanVienService: PhongBanNhanVienService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.phongBanNhanVienService.query().subscribe(
      (res: HttpResponse<IPhongBanNhanVien[]>) => {
        this.isLoading = false;
        this.phongBanNhanViens = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IPhongBanNhanVien): number {
    return item.id!;
  }

  delete(phongBanNhanVien: IPhongBanNhanVien): void {
    const modalRef = this.modalService.open(PhongBanNhanVienDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.phongBanNhanVien = phongBanNhanVien;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
