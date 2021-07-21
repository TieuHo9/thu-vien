import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IPhongBanNhanVien, PhongBanNhanVien } from '../phong-ban-nhan-vien.model';
import { PhongBanNhanVienService } from '../service/phong-ban-nhan-vien.service';
import { INhanVien } from 'app/entities/nhan-vien/nhan-vien.model';
import { NhanVienService } from 'app/entities/nhan-vien/service/nhan-vien.service';

@Component({
  selector: 'jhi-phong-ban-nhan-vien-update',
  templateUrl: './phong-ban-nhan-vien-update.component.html',
})
export class PhongBanNhanVienUpdateComponent implements OnInit {
  isSaving = false;

  nhanViensSharedCollection: INhanVien[] = [];

  editForm = this.fb.group({
    id: [],
    maPhong: [],
    maNhanVien: [],
    nviens: [],
  });

  constructor(
    protected phongBanNhanVienService: PhongBanNhanVienService,
    protected nhanVienService: NhanVienService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ phongBanNhanVien }) => {
      this.updateForm(phongBanNhanVien);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const phongBanNhanVien = this.createFromForm();
    if (phongBanNhanVien.id !== undefined) {
      this.subscribeToSaveResponse(this.phongBanNhanVienService.update(phongBanNhanVien));
    } else {
      this.subscribeToSaveResponse(this.phongBanNhanVienService.create(phongBanNhanVien));
    }
  }

  trackNhanVienById(index: number, item: INhanVien): number {
    return item.id!;
  }

  getSelectedNhanVien(option: INhanVien, selectedVals?: INhanVien[]): INhanVien {
    if (selectedVals) {
      for (const selectedVal of selectedVals) {
        if (option.id === selectedVal.id) {
          return selectedVal;
        }
      }
    }
    return option;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPhongBanNhanVien>>): void {
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

  protected updateForm(phongBanNhanVien: IPhongBanNhanVien): void {
    this.editForm.patchValue({
      id: phongBanNhanVien.id,
      maPhong: phongBanNhanVien.maPhong,
      maNhanVien: phongBanNhanVien.maNhanVien,
      nviens: phongBanNhanVien.nviens,
    });

    this.nhanViensSharedCollection = this.nhanVienService.addNhanVienToCollectionIfMissing(
      this.nhanViensSharedCollection,
      ...(phongBanNhanVien.nviens ?? [])
    );
  }

  protected loadRelationshipsOptions(): void {
    this.nhanVienService
      .query()
      .pipe(map((res: HttpResponse<INhanVien[]>) => res.body ?? []))
      .pipe(
        map((nhanViens: INhanVien[]) =>
          this.nhanVienService.addNhanVienToCollectionIfMissing(nhanViens, ...(this.editForm.get('nviens')!.value ?? []))
        )
      )
      .subscribe((nhanViens: INhanVien[]) => (this.nhanViensSharedCollection = nhanViens));
  }

  protected createFromForm(): IPhongBanNhanVien {
    return {
      ...new PhongBanNhanVien(),
      id: this.editForm.get(['id'])!.value,
      maPhong: this.editForm.get(['maPhong'])!.value,
      maNhanVien: this.editForm.get(['maNhanVien'])!.value,
      nviens: this.editForm.get(['nviens'])!.value,
    };
  }
}
