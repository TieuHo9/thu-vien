import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDeXuatThanhToan } from '../de-xuat-thanh-toan.model';
import { DeXuatThanhToanService } from '../service/de-xuat-thanh-toan.service';
import { ActivatedRoute } from '@angular/router';
import { ExportService } from '../service/export.service';

@Component({
  selector: 'jhi-xet-duyet',
  templateUrl: './xet-duyet.html',
})
export class XetDuyetComponent implements OnInit {
  deXuatThanhToans?: any;
  isLoading = false;

  constructor(
    protected deXuatThanhToanService: DeXuatThanhToanService,
    protected modalService: NgbModal,
    private route: ActivatedRoute,
    private exportService: ExportService
  ) {}

  loadAll(): void {
    this.isLoading = true;

    this.deXuatThanhToanService.query().subscribe(
      (res: HttpResponse<IDeXuatThanhToan[]>) => {
        this.isLoading = false;
        this.deXuatThanhToans = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IDeXuatThanhToan): number {
    return item.id!;
  }

  searchbyname(tenDeXuat: string): void {
    console.warn('12', tenDeXuat);

    this.deXuatThanhToanService.getName(tenDeXuat).subscribe(dexuat => {
      this.deXuatThanhToans = dexuat;
      console.warn('123', dexuat);
    });
  }

  export(): void {
    this.exportService.exportExcel(this.deXuatThanhToans, 'Bao cao');
  }
}
