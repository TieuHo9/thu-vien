import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDeXuatThanhToan } from '../de-xuat-thanh-toan.model';
import { DeXuatThanhToanService } from '../service/de-xuat-thanh-toan.service';
import { DeXuatThanhToanDeleteDialogComponent } from '../delete/de-xuat-thanh-toan-delete-dialog.component';
import { ActivatedRoute } from '@angular/router';
import { ExportService } from '../service/export.service';
import { analyzeAndValidateNgModules } from '@angular/compiler';

@Component({
  selector: 'jhi-de-xuat-thanh-toan',
  templateUrl: './de-xuat-thanh-toan.component.html',
})
export class DeXuatThanhToanComponent implements OnInit {
  deXuatThanhToans?: any;
  dexuat?: any;
  isLoading = false;
  deXuatThanhToanLoc?: any;
  isLoading1 = false;
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
    this.isLoading1 = true;
    this.deXuatThanhToanService.getbaocaodathanhtoan().subscribe(
      res => {
        this.isLoading1 = false;
        this.deXuatThanhToanLoc = res;
      },
      () => {
        this.isLoading1 = false;
      }
    );
  }

  trackId(index: number, item: IDeXuatThanhToan): number {
    return item.id!;
  }

  delete(deXuatThanhToan: IDeXuatThanhToan): void {
    const modalRef = this.modalService.open(DeXuatThanhToanDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.deXuatThanhToan = deXuatThanhToan;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }

  searchbyname(tenDeXuat: string): void {
    console.warn('12', tenDeXuat);

    this.deXuatThanhToanService.getName(tenDeXuat).subscribe(dexuat => {
      this.deXuatThanhToans = dexuat;
      console.warn('123', dexuat);
    });
  }

  export(): void {
    console.warn('a', this.deXuatThanhToans);
    this.exportService.exportExcel(this.deXuatThanhToans, 'Bao cao');
  }

  exportThanhToan(): void {
    console.warn('export', this.deXuatThanhToanLoc);
    console.warn('a', this.deXuatThanhToans);
    this.exportService.exportExcel(this.deXuatThanhToanLoc, 'Bao cao da thanh toan');
  }
}
