import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDeXuatThanhToan } from '../de-xuat-thanh-toan.model';

@Component({
  selector: 'jhi-de-xuat-thanh-toan-detail',
  templateUrl: './de-xuat-thanh-toan-detail.component.html',
})
export class DeXuatThanhToanDetailComponent implements OnInit {
  deXuatThanhToan: IDeXuatThanhToan | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ deXuatThanhToan }) => {
      this.deXuatThanhToan = deXuatThanhToan;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
