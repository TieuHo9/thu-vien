import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IChiPhi, ChiPhi } from '../chi-phi.model';
import { ChiPhiService } from '../service/chi-phi.service';

@Component({
  selector: 'jhi-chi-phi-update',
  templateUrl: './chi-phi-update.component.html',
})
export class ChiPhiUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    loaiChiPhi: [],
    soTien: [],
    donViTienTe: [],
  });

  constructor(protected chiPhiService: ChiPhiService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ chiPhi }) => {
      this.updateForm(chiPhi);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const chiPhi = this.createFromForm();
    if (chiPhi.id !== undefined) {
      this.subscribeToSaveResponse(this.chiPhiService.update(chiPhi));
    } else {
      this.subscribeToSaveResponse(this.chiPhiService.create(chiPhi));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IChiPhi>>): void {
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

  protected updateForm(chiPhi: IChiPhi): void {
    this.editForm.patchValue({
      id: chiPhi.id,
      loaiChiPhi: chiPhi.loaiChiPhi,
      soTien: chiPhi.soTien,
      donViTienTe: chiPhi.donViTienTe,
    });
  }

  protected createFromForm(): IChiPhi {
    return {
      ...new ChiPhi(),
      id: this.editForm.get(['id'])!.value,
      loaiChiPhi: this.editForm.get(['loaiChiPhi'])!.value,
      soTien: this.editForm.get(['soTien'])!.value,
      donViTienTe: this.editForm.get(['donViTienTe'])!.value,
    };
  }
}
