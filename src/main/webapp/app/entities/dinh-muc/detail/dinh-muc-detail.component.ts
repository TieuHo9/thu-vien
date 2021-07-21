import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDinhMuc } from '../dinh-muc.model';

@Component({
  selector: 'jhi-dinh-muc-detail',
  templateUrl: './dinh-muc-detail.component.html',
})
export class DinhMucDetailComponent implements OnInit {
  dinhMuc: IDinhMuc | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dinhMuc }) => {
      this.dinhMuc = dinhMuc;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
