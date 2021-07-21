import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPhongBan } from '../phong-ban.model';
import { PhongBanService } from '../service/phong-ban.service';
import { PhongBanDeleteDialogComponent } from '../delete/phong-ban-delete-dialog.component';

@Component({
  selector: 'jhi-phong-ban',
  templateUrl: './phong-ban.component.html',
})
export class PhongBanComponent implements OnInit {
  phongBans?: IPhongBan[];
  isLoading = false;

  constructor(protected phongBanService: PhongBanService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.phongBanService.query().subscribe(
      (res: HttpResponse<IPhongBan[]>) => {
        this.isLoading = false;
        this.phongBans = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IPhongBan): number {
    return item.id!;
  }

  delete(phongBan: IPhongBan): void {
    const modalRef = this.modalService.open(PhongBanDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.phongBan = phongBan;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
