import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { INhanVien, NhanVien } from '../nhan-vien.model';
import { NhanVienService } from '../service/nhan-vien.service';
import { IPhongBan } from 'app/entities/phong-ban/phong-ban.model';
import { PhongBanService } from 'app/entities/phong-ban/service/phong-ban.service';
import { ICapBac } from 'app/entities/cap-bac/cap-bac.model';
import { CapBacService } from 'app/entities/cap-bac/service/cap-bac.service';

@Component({
  selector: 'jhi-nhan-vien-update',
  templateUrl: './nhan-vien-update.component.html',
})
export class NhanVienUpdateComponent implements OnInit {
  isSaving = false;

  phongBansSharedCollection: IPhongBan[] = [];
  capBacsSharedCollection: ICapBac[] = [];

  editForm = this.fb.group({
    id: [],
    maNhanVien: [],
    tenNhanVien: [],
    phongBan: [],
    capBac: [],
    phongbans: [],
    capbacs: [],
  });

  constructor(
    protected nhanVienService: NhanVienService,
    protected phongBanService: PhongBanService,
    protected capBacService: CapBacService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ nhanVien }) => {
      this.updateForm(nhanVien);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const nhanVien = this.createFromForm();
    if (nhanVien.id !== undefined) {
      this.subscribeToSaveResponse(this.nhanVienService.update(nhanVien));
    } else {
      this.subscribeToSaveResponse(this.nhanVienService.create(nhanVien));
    }
  }

  trackPhongBanById(index: number, item: IPhongBan): number {
    return item.id!;
  }

  trackCapBacById(index: number, item: ICapBac): number {
    return item.id!;
  }

  getSelectedPhongBan(option: IPhongBan, selectedVals?: IPhongBan[]): IPhongBan {
    if (selectedVals) {
      for (const selectedVal of selectedVals) {
        if (option.id === selectedVal.id) {
          return selectedVal;
        }
      }
    }
    return option;
  }

  getSelectedCapBac(option: ICapBac, selectedVals?: ICapBac[]): ICapBac {
    if (selectedVals) {
      for (const selectedVal of selectedVals) {
        if (option.id === selectedVal.id) {
          return selectedVal;
        }
      }
    }
    return option;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INhanVien>>): void {
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

  protected updateForm(nhanVien: INhanVien): void {
    this.editForm.patchValue({
      id: nhanVien.id,
      maNhanVien: nhanVien.maNhanVien,
      tenNhanVien: nhanVien.tenNhanVien,
      phongBan: nhanVien.phongBan,
      capBac: nhanVien.capBac,
      phongbans: nhanVien.phongbans,
      capbacs: nhanVien.capbacs,
    });

    this.phongBansSharedCollection = this.phongBanService.addPhongBanToCollectionIfMissing(
      this.phongBansSharedCollection,
      ...(nhanVien.phongbans ?? [])
    );
    this.capBacsSharedCollection = this.capBacService.addCapBacToCollectionIfMissing(
      this.capBacsSharedCollection,
      ...(nhanVien.capbacs ?? [])
    );
  }

  protected loadRelationshipsOptions(): void {
    this.phongBanService
      .query()
      .pipe(map((res: HttpResponse<IPhongBan[]>) => res.body ?? []))
      .pipe(
        map((phongBans: IPhongBan[]) =>
          this.phongBanService.addPhongBanToCollectionIfMissing(phongBans, ...(this.editForm.get('phongbans')!.value ?? []))
        )
      )
      .subscribe((phongBans: IPhongBan[]) => (this.phongBansSharedCollection = phongBans));

    this.capBacService
      .query()
      .pipe(map((res: HttpResponse<ICapBac[]>) => res.body ?? []))
      .pipe(
        map((capBacs: ICapBac[]) =>
          this.capBacService.addCapBacToCollectionIfMissing(capBacs, ...(this.editForm.get('capbacs')!.value ?? []))
        )
      )
      .subscribe((capBacs: ICapBac[]) => (this.capBacsSharedCollection = capBacs));
  }

  protected createFromForm(): INhanVien {
    return {
      ...new NhanVien(),
      id: this.editForm.get(['id'])!.value,
      maNhanVien: this.editForm.get(['maNhanVien'])!.value,
      tenNhanVien: this.editForm.get(['tenNhanVien'])!.value,
      phongBan: this.editForm.get(['phongBan'])!.value,
      capBac: this.editForm.get(['capBac'])!.value,
      phongbans: this.editForm.get(['phongbans'])!.value,
      capbacs: this.editForm.get(['capbacs'])!.value,
    };
  }
}
