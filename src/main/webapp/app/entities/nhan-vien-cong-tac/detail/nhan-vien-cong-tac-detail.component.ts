import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INhanVienCongTac } from '../nhan-vien-cong-tac.model';

@Component({
  selector: 'jhi-nhan-vien-cong-tac-detail',
  templateUrl: './nhan-vien-cong-tac-detail.component.html',
})
export class NhanVienCongTacDetailComponent implements OnInit {
  nhanVienCongTac: INhanVienCongTac | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ nhanVienCongTac }) => {
      this.nhanVienCongTac = nhanVienCongTac;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
