import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IChuyenCongTac, ChuyenCongTac } from '../chuyen-cong-tac.model';
import { ChuyenCongTacService } from '../service/chuyen-cong-tac.service';
import { INhanVien } from 'app/entities/nhan-vien/nhan-vien.model';
import { NhanVienService } from 'app/entities/nhan-vien/service/nhan-vien.service';

@Component({
  selector: 'jhi-chuyen-cong-tac-update',
  templateUrl: './chuyen-cong-tac-update.component.html',
})
export class ChuyenCongTacUpdateComponent implements OnInit {
  isSaving = false;

  nhanViensSharedCollection: INhanVien[] = [];

  editForm = this.fb.group({
    id: [],
    maChuyenDi: [],
    tenChuyenDi: [],
    thoiGianTu: [],
    thoiGianDen: [],
    maNhanVien: [],
    tenNhanVien: [],
    soDienThoai: [],
    diaDiem: [],
    soTien: [],
    nhanviens: [],
  });

  constructor(
    protected chuyenCongTacService: ChuyenCongTacService,
    protected nhanVienService: NhanVienService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ chuyenCongTac }) => {
      this.updateForm(chuyenCongTac);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const chuyenCongTac = this.createFromForm();
    if (chuyenCongTac.id !== undefined) {
      this.subscribeToSaveResponse(this.chuyenCongTacService.update(chuyenCongTac));
    } else {
      this.subscribeToSaveResponse(this.chuyenCongTacService.create(chuyenCongTac));
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

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IChuyenCongTac>>): void {
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

  protected updateForm(chuyenCongTac: IChuyenCongTac): void {
    this.editForm.patchValue({
      id: chuyenCongTac.id,
      maChuyenDi: chuyenCongTac.maChuyenDi,
      tenChuyenDi: chuyenCongTac.tenChuyenDi,
      thoiGianTu: chuyenCongTac.thoiGianTu,
      thoiGianDen: chuyenCongTac.thoiGianDen,
      maNhanVien: chuyenCongTac.maNhanVien,
      tenNhanVien: chuyenCongTac.tenNhanVien,
      soDienThoai: chuyenCongTac.soDienThoai,
      diaDiem: chuyenCongTac.diaDiem,
      soTien: chuyenCongTac.soTien,
      nhanviens: chuyenCongTac.nhanviens,
    });

    this.nhanViensSharedCollection = this.nhanVienService.addNhanVienToCollectionIfMissing(
      this.nhanViensSharedCollection,
      ...(chuyenCongTac.nhanviens ?? [])
    );
  }

  protected loadRelationshipsOptions(): void {
    this.nhanVienService
      .query()
      .pipe(map((res: HttpResponse<INhanVien[]>) => res.body ?? []))
      .pipe(
        map((nhanViens: INhanVien[]) =>
          this.nhanVienService.addNhanVienToCollectionIfMissing(nhanViens, ...(this.editForm.get('nhanviens')!.value ?? []))
        )
      )
      .subscribe((nhanViens: INhanVien[]) => (this.nhanViensSharedCollection = nhanViens));
  }

  protected createFromForm(): IChuyenCongTac {
    return {
      ...new ChuyenCongTac(),
      id: this.editForm.get(['id'])!.value,
      maChuyenDi: this.editForm.get(['maChuyenDi'])!.value,
      tenChuyenDi: this.editForm.get(['tenChuyenDi'])!.value,
      thoiGianTu: this.editForm.get(['thoiGianTu'])!.value,
      thoiGianDen: this.editForm.get(['thoiGianDen'])!.value,
      maNhanVien: this.editForm.get(['maNhanVien'])!.value,
      tenNhanVien: this.editForm.get(['tenNhanVien'])!.value,
      soDienThoai: this.editForm.get(['soDienThoai'])!.value,
      diaDiem: this.editForm.get(['diaDiem'])!.value,
      soTien: this.editForm.get(['soTien'])!.value,
      nhanviens: this.editForm.get(['nhanviens'])!.value,
    };
  }
}
