jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { DeXuatThanhToanService } from '../service/de-xuat-thanh-toan.service';
import { IDeXuatThanhToan, DeXuatThanhToan } from '../de-xuat-thanh-toan.model';
import { IChiPhi } from 'app/entities/chi-phi/chi-phi.model';
import { ChiPhiService } from 'app/entities/chi-phi/service/chi-phi.service';
import { IDinhMuc } from 'app/entities/dinh-muc/dinh-muc.model';
import { DinhMucService } from 'app/entities/dinh-muc/service/dinh-muc.service';

import { DeXuatThanhToanUpdateComponent } from './de-xuat-thanh-toan-update.component';

describe('Component Tests', () => {
  describe('DeXuatThanhToan Management Update Component', () => {
    let comp: DeXuatThanhToanUpdateComponent;
    let fixture: ComponentFixture<DeXuatThanhToanUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let deXuatThanhToanService: DeXuatThanhToanService;
    let chiPhiService: ChiPhiService;
    let dinhMucService: DinhMucService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [DeXuatThanhToanUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(DeXuatThanhToanUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DeXuatThanhToanUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      deXuatThanhToanService = TestBed.inject(DeXuatThanhToanService);
      chiPhiService = TestBed.inject(ChiPhiService);
      dinhMucService = TestBed.inject(DinhMucService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call ChiPhi query and add missing value', () => {
        const deXuatThanhToan: IDeXuatThanhToan = { id: 456 };
        const chiphis: IChiPhi[] = [{ id: 62247 }];
        deXuatThanhToan.chiphis = chiphis;

        const chiPhiCollection: IChiPhi[] = [{ id: 18716 }];
        spyOn(chiPhiService, 'query').and.returnValue(of(new HttpResponse({ body: chiPhiCollection })));
        const additionalChiPhis = [...chiphis];
        const expectedCollection: IChiPhi[] = [...additionalChiPhis, ...chiPhiCollection];
        spyOn(chiPhiService, 'addChiPhiToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ deXuatThanhToan });
        comp.ngOnInit();

        expect(chiPhiService.query).toHaveBeenCalled();
        expect(chiPhiService.addChiPhiToCollectionIfMissing).toHaveBeenCalledWith(chiPhiCollection, ...additionalChiPhis);
        expect(comp.chiPhisSharedCollection).toEqual(expectedCollection);
      });

      it('Should call DinhMuc query and add missing value', () => {
        const deXuatThanhToan: IDeXuatThanhToan = { id: 456 };
        const dmucs: IDinhMuc[] = [{ id: 53288 }];
        deXuatThanhToan.dmucs = dmucs;

        const dinhMucCollection: IDinhMuc[] = [{ id: 52304 }];
        spyOn(dinhMucService, 'query').and.returnValue(of(new HttpResponse({ body: dinhMucCollection })));
        const additionalDinhMucs = [...dmucs];
        const expectedCollection: IDinhMuc[] = [...additionalDinhMucs, ...dinhMucCollection];
        spyOn(dinhMucService, 'addDinhMucToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ deXuatThanhToan });
        comp.ngOnInit();

        expect(dinhMucService.query).toHaveBeenCalled();
        expect(dinhMucService.addDinhMucToCollectionIfMissing).toHaveBeenCalledWith(dinhMucCollection, ...additionalDinhMucs);
        expect(comp.dinhMucsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const deXuatThanhToan: IDeXuatThanhToan = { id: 456 };
        const chiphis: IChiPhi = { id: 74843 };
        deXuatThanhToan.chiphis = [chiphis];
        const dmucs: IDinhMuc = { id: 98177 };
        deXuatThanhToan.dmucs = [dmucs];

        activatedRoute.data = of({ deXuatThanhToan });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(deXuatThanhToan));
        expect(comp.chiPhisSharedCollection).toContain(chiphis);
        expect(comp.dinhMucsSharedCollection).toContain(dmucs);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const deXuatThanhToan = { id: 123 };
        spyOn(deXuatThanhToanService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ deXuatThanhToan });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: deXuatThanhToan }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(deXuatThanhToanService.update).toHaveBeenCalledWith(deXuatThanhToan);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const deXuatThanhToan = new DeXuatThanhToan();
        spyOn(deXuatThanhToanService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ deXuatThanhToan });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: deXuatThanhToan }));
        saveSubject.complete();

        // THEN
        expect(deXuatThanhToanService.create).toHaveBeenCalledWith(deXuatThanhToan);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const deXuatThanhToan = { id: 123 };
        spyOn(deXuatThanhToanService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ deXuatThanhToan });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(deXuatThanhToanService.update).toHaveBeenCalledWith(deXuatThanhToan);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackChiPhiById', () => {
        it('Should return tracked ChiPhi primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackChiPhiById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackDinhMucById', () => {
        it('Should return tracked DinhMuc primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackDinhMucById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });

    describe('Getting selected relationships', () => {
      describe('getSelectedChiPhi', () => {
        it('Should return option if no ChiPhi is selected', () => {
          const option = { id: 123 };
          const result = comp.getSelectedChiPhi(option);
          expect(result === option).toEqual(true);
        });

        it('Should return selected ChiPhi for according option', () => {
          const option = { id: 123 };
          const selected = { id: 123 };
          const selected2 = { id: 456 };
          const result = comp.getSelectedChiPhi(option, [selected2, selected]);
          expect(result === selected).toEqual(true);
          expect(result === selected2).toEqual(false);
          expect(result === option).toEqual(false);
        });

        it('Should return option if this ChiPhi is not selected', () => {
          const option = { id: 123 };
          const selected = { id: 456 };
          const result = comp.getSelectedChiPhi(option, [selected]);
          expect(result === option).toEqual(true);
          expect(result === selected).toEqual(false);
        });
      });

      describe('getSelectedDinhMuc', () => {
        it('Should return option if no DinhMuc is selected', () => {
          const option = { id: 123 };
          const result = comp.getSelectedDinhMuc(option);
          expect(result === option).toEqual(true);
        });

        it('Should return selected DinhMuc for according option', () => {
          const option = { id: 123 };
          const selected = { id: 123 };
          const selected2 = { id: 456 };
          const result = comp.getSelectedDinhMuc(option, [selected2, selected]);
          expect(result === selected).toEqual(true);
          expect(result === selected2).toEqual(false);
          expect(result === option).toEqual(false);
        });

        it('Should return option if this DinhMuc is not selected', () => {
          const option = { id: 123 };
          const selected = { id: 456 };
          const result = comp.getSelectedDinhMuc(option, [selected]);
          expect(result === option).toEqual(true);
          expect(result === selected).toEqual(false);
        });
      });
    });
  });
});
