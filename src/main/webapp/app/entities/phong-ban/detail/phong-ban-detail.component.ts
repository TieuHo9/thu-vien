import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPhongBan } from '../phong-ban.model';

@Component({
  selector: 'jhi-phong-ban-detail',
  templateUrl: './phong-ban-detail.component.html',
})
export class PhongBanDetailComponent implements OnInit {
  phongBan: IPhongBan | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ phongBan }) => {
      this.phongBan = phongBan;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
