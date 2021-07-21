import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPhongBanNhanVien } from '../phong-ban-nhan-vien.model';

@Component({
  selector: 'jhi-phong-ban-nhan-vien-detail',
  templateUrl: './phong-ban-nhan-vien-detail.component.html',
})
export class PhongBanNhanVienDetailComponent implements OnInit {
  phongBanNhanVien: IPhongBanNhanVien | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ phongBanNhanVien }) => {
      this.phongBanNhanVien = phongBanNhanVien;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
