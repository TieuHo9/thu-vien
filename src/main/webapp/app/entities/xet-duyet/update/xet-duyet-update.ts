import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IDeXuatThanhToan, DeXuatThanhToan } from '../de-xuat-thanh-toan.model';
import { DeXuatThanhToanService } from '../service/de-xuat-thanh-toan.service';
import { IChiPhi } from 'app/entities/chi-phi/chi-phi.model';
import { ChiPhiService } from 'app/entities/chi-phi/service/chi-phi.service';
import { IDinhMuc } from 'app/entities/dinh-muc/dinh-muc.model';
import { DinhMucService } from 'app/entities/dinh-muc/service/dinh-muc.service';

@Component({
  selector: 'jhi-xet-duyet-update',
  templateUrl: './xet-duyet-update.html',
})
export class XetDuyetUpdateComponent implements OnInit {
  isSaving = false;

  chiPhisSharedCollection: IChiPhi[] = [];
  dinhMucsSharedCollection: IDinhMuc[] = [];

  editForm = this.fb.group({
    id: [],
    maDeXuat: [],
    tenDeXuat: [],
    tuNgay: [],
    denNgay: [],
    trangThaiTruongPhong: [],
    trangThaiPhongTaiVu: [],
    trangThaiBanLanhDao: [],
    chiphis: [],
    dmucs: [],
  });

  options: string[] = ['Xac nhan', 'Khong xac nhan'];
  checkOffice: any;
  checkDiretor: any;

  constructor(
    protected deXuatThanhToanService: DeXuatThanhToanService,
    protected chiPhiService: ChiPhiService,
    protected dinhMucService: DinhMucService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ deXuatThanhToan }) => {
      this.updateForm(deXuatThanhToan);
      console.warn('init data', deXuatThanhToan);
      console.warn('manager status', deXuatThanhToan.trangThaiTruongPhong);
      this.checkOffice = deXuatThanhToan.trangThaiTruongPhong;
      console.warn('check office ', this.checkOffice);
      this.checkDiretor = deXuatThanhToan.trangThaiPhongTaiVu;

      this.loadRelationshipsOptions();
    });
  }

  funcionManager(): boolean {
    if (this.checkOffice === 'Xac nhan') {
      return true;
    }
    return false;
  }

  funcionOffice(): boolean {
    if (this.checkDiretor === 'Xac nhan') {
      return true;
    }
    return false;
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const deXuatThanhToan = this.createFromForm();
    if (deXuatThanhToan.id !== undefined) {
      this.subscribeToSaveResponse(this.deXuatThanhToanService.update(deXuatThanhToan));
    } else {
      this.subscribeToSaveResponse(this.deXuatThanhToanService.create(deXuatThanhToan));
    }
  }

  trackChiPhiById(index: number, item: IChiPhi): number {
    return item.id!;
  }

  trackDinhMucById(index: number, item: IDinhMuc): number {
    return item.id!;
  }

  getSelectedChiPhi(option: IChiPhi, selectedVals?: IChiPhi[]): IChiPhi {
    if (selectedVals) {
      for (const selectedVal of selectedVals) {
        if (option.id === selectedVal.id) {
          return selectedVal;
        }
      }
    }
    return option;
  }

  getSelectedDinhMuc(option: IDinhMuc, selectedVals?: IDinhMuc[]): IDinhMuc {
    if (selectedVals) {
      for (const selectedVal of selectedVals) {
        if (option.id === selectedVal.id) {
          return selectedVal;
        }
      }
    }
    return option;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDeXuatThanhToan>>): void {
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

  protected updateForm(deXuatThanhToan: IDeXuatThanhToan): void {
    this.editForm.patchValue({
      id: deXuatThanhToan.id,
      maDeXuat: deXuatThanhToan.maDeXuat,
      tenDeXuat: deXuatThanhToan.tenDeXuat,
      tuNgay: deXuatThanhToan.tuNgay,
      denNgay: deXuatThanhToan.denNgay,
      trangThaiTruongPhong: deXuatThanhToan.trangThaiTruongPhong,
      trangThaiPhongTaiVu: deXuatThanhToan.trangThaiPhongTaiVu,
      trangThaiBanLanhDao: deXuatThanhToan.trangThaiBanLanhDao,
      chiphis: deXuatThanhToan.chiphis,
      dmucs: deXuatThanhToan.dmucs,
    });

    this.chiPhisSharedCollection = this.chiPhiService.addChiPhiToCollectionIfMissing(
      this.chiPhisSharedCollection,
      ...(deXuatThanhToan.chiphis ?? [])
    );
    this.dinhMucsSharedCollection = this.dinhMucService.addDinhMucToCollectionIfMissing(
      this.dinhMucsSharedCollection,
      ...(deXuatThanhToan.dmucs ?? [])
    );
  }

  protected loadRelationshipsOptions(): void {
    this.chiPhiService
      .query()
      .pipe(map((res: HttpResponse<IChiPhi[]>) => res.body ?? []))
      .pipe(
        map((chiPhis: IChiPhi[]) =>
          this.chiPhiService.addChiPhiToCollectionIfMissing(chiPhis, ...(this.editForm.get('chiphis')!.value ?? []))
        )
      )
      .subscribe((chiPhis: IChiPhi[]) => (this.chiPhisSharedCollection = chiPhis));

    this.dinhMucService
      .query()
      .pipe(map((res: HttpResponse<IDinhMuc[]>) => res.body ?? []))
      .pipe(
        map((dinhMucs: IDinhMuc[]) =>
          this.dinhMucService.addDinhMucToCollectionIfMissing(dinhMucs, ...(this.editForm.get('dmucs')!.value ?? []))
        )
      )
      .subscribe((dinhMucs: IDinhMuc[]) => (this.dinhMucsSharedCollection = dinhMucs));
  }

  protected createFromForm(): IDeXuatThanhToan {
    return {
      ...new DeXuatThanhToan(),
      id: this.editForm.get(['id'])!.value,
      maDeXuat: this.editForm.get(['maDeXuat'])!.value,
      tenDeXuat: this.editForm.get(['tenDeXuat'])!.value,
      tuNgay: this.editForm.get(['tuNgay'])!.value,
      denNgay: this.editForm.get(['denNgay'])!.value,
      trangThaiTruongPhong: this.editForm.get(['trangThaiTruongPhong'])!.value,
      trangThaiPhongTaiVu: this.editForm.get(['trangThaiPhongTaiVu'])!.value,
      trangThaiBanLanhDao: this.editForm.get(['trangThaiBanLanhDao'])!.value,
      chiphis: this.editForm.get(['chiphis'])!.value,
      dmucs: this.editForm.get(['dmucs'])!.value,
    };
  }
}
