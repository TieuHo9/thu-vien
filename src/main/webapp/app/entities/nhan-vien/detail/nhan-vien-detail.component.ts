import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INhanVien } from '../nhan-vien.model';

@Component({
  selector: 'jhi-nhan-vien-detail',
  templateUrl: './nhan-vien-detail.component.html',
})
export class NhanVienDetailComponent implements OnInit {
  nhanVien: INhanVien | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ nhanVien }) => {
      this.nhanVien = nhanVien;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
