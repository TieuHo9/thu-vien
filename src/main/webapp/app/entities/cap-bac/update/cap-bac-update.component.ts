import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ICapBac, CapBac } from '../cap-bac.model';
import { CapBacService } from '../service/cap-bac.service';

@Component({
  selector: 'jhi-cap-bac-update',
  templateUrl: './cap-bac-update.component.html',
})
export class CapBacUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    tenCap: [],
  });

  constructor(protected capBacService: CapBacService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ capBac }) => {
      this.updateForm(capBac);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const capBac = this.createFromForm();
    if (capBac.id !== undefined) {
      this.subscribeToSaveResponse(this.capBacService.update(capBac));
    } else {
      this.subscribeToSaveResponse(this.capBacService.create(capBac));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICapBac>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(capBac: ICapBac): void {
    this.editForm.patchValue({
      id: capBac.id,
      tenCap: capBac.tenCap,
    });
  }

  protected createFromForm(): ICapBac {
    return {
      ...new CapBac(),
      id: this.editForm.get(['id'])!.value,
      tenCap: this.editForm.get(['tenCap'])!.value,
    };
  }
}
