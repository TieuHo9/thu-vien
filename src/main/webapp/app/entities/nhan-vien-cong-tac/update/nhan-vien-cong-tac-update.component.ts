import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { INhanVienCongTac, NhanVienCongTac } from '../nhan-vien-cong-tac.model';
import { NhanVienCongTacService } from '../service/nhan-vien-cong-tac.service';
import { IChuyenCongTac } from 'app/entities/chuyen-cong-tac/chuyen-cong-tac.model';
import { ChuyenCongTacService } from 'app/entities/chuyen-cong-tac/service/chuyen-cong-tac.service';

@Component({
  selector: 'jhi-nhan-vien-cong-tac-update',
  templateUrl: './nhan-vien-cong-tac-update.component.html',
})
export class NhanVienCongTacUpdateComponent implements OnInit {
  isSaving = false;

  chuyenCongTacsSharedCollection: IChuyenCongTac[] = [];

  editForm = this.fb.group({
    id: [],
    maNhanVien: [],
    maChuyenDi: [],
    soTien: [],
    chuyencts: [],
  });

  constructor(
    protected nhanVienCongTacService: NhanVienCongTacService,
    protected chuyenCongTacService: ChuyenCongTacService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ nhanVienCongTac }) => {
      this.updateForm(nhanVienCongTac);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const nhanVienCongTac = this.createFromForm();
    if (nhanVienCongTac.id !== undefined) {
      this.subscribeToSaveResponse(this.nhanVienCongTacService.update(nhanVienCongTac));
    } else {
      this.subscribeToSaveResponse(this.nhanVienCongTacService.create(nhanVienCongTac));
    }
  }

  trackChuyenCongTacById(index: number, item: IChuyenCongTac): number {
    return item.id!;
  }

  getSelectedChuyenCongTac(option: IChuyenCongTac, selectedVals?: IChuyenCongTac[]): IChuyenCongTac {
    if (selectedVals) {
      for (const selectedVal of selectedVals) {
        if (option.id === selectedVal.id) {
          return selectedVal;
        }
      }
    }
    return option;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INhanVienCongTac>>): void {
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

  protected updateForm(nhanVienCongTac: INhanVienCongTac): void {
    this.editForm.patchValue({
      id: nhanVienCongTac.id,
      maNhanVien: nhanVienCongTac.maNhanVien,
      maChuyenDi: nhanVienCongTac.maChuyenDi,
      soTien: nhanVienCongTac.soTien,
      chuyencts: nhanVienCongTac.chuyencts,
    });

    this.chuyenCongTacsSharedCollection = this.chuyenCongTacService.addChuyenCongTacToCollectionIfMissing(
      this.chuyenCongTacsSharedCollection,
      ...(nhanVienCongTac.chuyencts ?? [])
    );
  }

  protected loadRelationshipsOptions(): void {
    this.chuyenCongTacService
      .query()
      .pipe(map((res: HttpResponse<IChuyenCongTac[]>) => res.body ?? []))
      .pipe(
        map((chuyenCongTacs: IChuyenCongTac[]) =>
          this.chuyenCongTacService.addChuyenCongTacToCollectionIfMissing(chuyenCongTacs, ...(this.editForm.get('chuyencts')!.value ?? []))
        )
      )
      .subscribe((chuyenCongTacs: IChuyenCongTac[]) => (this.chuyenCongTacsSharedCollection = chuyenCongTacs));
  }

  protected createFromForm(): INhanVienCongTac {
    return {
      ...new NhanVienCongTac(),
      id: this.editForm.get(['id'])!.value,
      maNhanVien: this.editForm.get(['maNhanVien'])!.value,
      maChuyenDi: this.editForm.get(['maChuyenDi'])!.value,
      soTien: this.editForm.get(['soTien'])!.value,
      chuyencts: this.editForm.get(['chuyencts'])!.value,
    };
  }
}
