import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICapBac } from '../cap-bac.model';

@Component({
  selector: 'jhi-cap-bac-detail',
  templateUrl: './cap-bac-detail.component.html',
})
export class CapBacDetailComponent implements OnInit {
  capBac: ICapBac | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ capBac }) => {
      this.capBac = capBac;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
