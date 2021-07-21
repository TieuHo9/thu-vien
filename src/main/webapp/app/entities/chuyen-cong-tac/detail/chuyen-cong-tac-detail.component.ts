import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IChuyenCongTac } from '../chuyen-cong-tac.model';

@Component({
  selector: 'jhi-chuyen-cong-tac-detail',
  templateUrl: './chuyen-cong-tac-detail.component.html',
})
export class ChuyenCongTacDetailComponent implements OnInit {
  chuyenCongTac: IChuyenCongTac | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ chuyenCongTac }) => {
      this.chuyenCongTac = chuyenCongTac;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
