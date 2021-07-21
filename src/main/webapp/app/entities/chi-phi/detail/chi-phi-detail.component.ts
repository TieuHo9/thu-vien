import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IChiPhi } from '../chi-phi.model';

@Component({
  selector: 'jhi-chi-phi-detail',
  templateUrl: './chi-phi-detail.component.html',
})
export class ChiPhiDetailComponent implements OnInit {
  chiPhi: IChiPhi | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ chiPhi }) => {
      this.chiPhi = chiPhi;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
