import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IDinhMuc, DinhMuc } from '../dinh-muc.model';
import { DinhMucService } from '../service/dinh-muc.service';
import { ICapBac } from 'app/entities/cap-bac/cap-bac.model';
import { CapBacService } from 'app/entities/cap-bac/service/cap-bac.service';

@Component({
  selector: 'jhi-dinh-muc-update',
  templateUrl: './dinh-muc-update.component.html',
})
export class DinhMucUpdateComponent implements OnInit {
  isSaving = false;

  capBacsSharedCollection: ICapBac[] = [];

  editForm = this.fb.group({
    id: [],
    maMuc: [],
    loaiPhi: [],
    soTien: [],
    capBac: [],
    cbacs: [],
  });

  constructor(
    protected dinhMucService: DinhMucService,
    protected capBacService: CapBacService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dinhMuc }) => {
      this.updateForm(dinhMuc);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dinhMuc = this.createFromForm();
    if (dinhMuc.id !== undefined) {
      this.subscribeToSaveResponse(this.dinhMucService.update(dinhMuc));
    } else {
      this.subscribeToSaveResponse(this.dinhMucService.create(dinhMuc));
    }
  }

  trackCapBacById(index: number, item: ICapBac): number {
    return item.id!;
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

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDinhMuc>>): void {
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

  protected updateForm(dinhMuc: IDinhMuc): void {
    this.editForm.patchValue({
      id: dinhMuc.id,
      maMuc: dinhMuc.maMuc,
      loaiPhi: dinhMuc.loaiPhi,
      soTien: dinhMuc.soTien,
      capBac: dinhMuc.capBac,
      cbacs: dinhMuc.cbacs,
    });

    this.capBacsSharedCollection = this.capBacService.addCapBacToCollectionIfMissing(
      this.capBacsSharedCollection,
      ...(dinhMuc.cbacs ?? [])
    );
  }

  protected loadRelationshipsOptions(): void {
    this.capBacService
      .query()
      .pipe(map((res: HttpResponse<ICapBac[]>) => res.body ?? []))
      .pipe(
        map((capBacs: ICapBac[]) =>
          this.capBacService.addCapBacToCollectionIfMissing(capBacs, ...(this.editForm.get('cbacs')!.value ?? []))
        )
      )
      .subscribe((capBacs: ICapBac[]) => (this.capBacsSharedCollection = capBacs));
  }

  protected createFromForm(): IDinhMuc {
    return {
      ...new DinhMuc(),
      id: this.editForm.get(['id'])!.value,
      maMuc: this.editForm.get(['maMuc'])!.value,
      loaiPhi: this.editForm.get(['loaiPhi'])!.value,
      soTien: this.editForm.get(['soTien'])!.value,
      capBac: this.editForm.get(['capBac'])!.value,
      cbacs: this.editForm.get(['cbacs'])!.value,
    };
  }
}
