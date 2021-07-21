import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IPhongBan, PhongBan } from '../phong-ban.model';
import { PhongBanService } from '../service/phong-ban.service';

@Component({
  selector: 'jhi-phong-ban-update',
  templateUrl: './phong-ban-update.component.html',
})
export class PhongBanUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    maPhong: [],
    tenPhong: [],
  });

  constructor(protected phongBanService: PhongBanService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ phongBan }) => {
      this.updateForm(phongBan);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const phongBan = this.createFromForm();
    if (phongBan.id !== undefined) {
      this.subscribeToSaveResponse(this.phongBanService.update(phongBan));
    } else {
      this.subscribeToSaveResponse(this.phongBanService.create(phongBan));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPhongBan>>): void {
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

  protected updateForm(phongBan: IPhongBan): void {
    this.editForm.patchValue({
      id: phongBan.id,
      maPhong: phongBan.maPhong,
      tenPhong: phongBan.tenPhong,
    });
  }

  protected createFromForm(): IPhongBan {
    return {
      ...new PhongBan(),
      id: this.editForm.get(['id'])!.value,
      maPhong: this.editForm.get(['maPhong'])!.value,
      tenPhong: this.editForm.get(['tenPhong'])!.value,
    };
  }
}
