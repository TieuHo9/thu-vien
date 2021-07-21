jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ChuyenCongTacService } from '../service/chuyen-cong-tac.service';
import { IChuyenCongTac, ChuyenCongTac } from '../chuyen-cong-tac.model';
import { INhanVien } from 'app/entities/nhan-vien/nhan-vien.model';
import { NhanVienService } from 'app/entities/nhan-vien/service/nhan-vien.service';

import { ChuyenCongTacUpdateComponent } from './chuyen-cong-tac-update.component';

describe('Component Tests', () => {
  describe('ChuyenCongTac Management Update Component', () => {
    let comp: ChuyenCongTacUpdateComponent;
    let fixture: ComponentFixture<ChuyenCongTacUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let chuyenCongTacService: ChuyenCongTacService;
    let nhanVienService: NhanVienService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ChuyenCongTacUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(ChuyenCongTacUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ChuyenCongTacUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      chuyenCongTacService = TestBed.inject(ChuyenCongTacService);
      nhanVienService = TestBed.inject(NhanVienService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call NhanVien query and add missing value', () => {
        const chuyenCongTac: IChuyenCongTac = { id: 456 };
        const nhanviens: INhanVien[] = [{ id: 91154 }];
        chuyenCongTac.nhanviens = nhanviens;

        const nhanVienCollection: INhanVien[] = [{ id: 13514 }];
        spyOn(nhanVienService, 'query').and.returnValue(of(new HttpResponse({ body: nhanVienCollection })));
        const additionalNhanViens = [...nhanviens];
        const expectedCollection: INhanVien[] = [...additionalNhanViens, ...nhanVienCollection];
        spyOn(nhanVienService, 'addNhanVienToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ chuyenCongTac });
        comp.ngOnInit();

        expect(nhanVienService.query).toHaveBeenCalled();
        expect(nhanVienService.addNhanVienToCollectionIfMissing).toHaveBeenCalledWith(nhanVienCollection, ...additionalNhanViens);
        expect(comp.nhanViensSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const chuyenCongTac: IChuyenCongTac = { id: 456 };
        const nhanviens: INhanVien = { id: 60205 };
        chuyenCongTac.nhanviens = [nhanviens];

        activatedRoute.data = of({ chuyenCongTac });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(chuyenCongTac));
        expect(comp.nhanViensSharedCollection).toContain(nhanviens);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const chuyenCongTac = { id: 123 };
        spyOn(chuyenCongTacService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ chuyenCongTac });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: chuyenCongTac }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(chuyenCongTacService.update).toHaveBeenCalledWith(chuyenCongTac);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const chuyenCongTac = new ChuyenCongTac();
        spyOn(chuyenCongTacService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ chuyenCongTac });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: chuyenCongTac }));
        saveSubject.complete();

        // THEN
        expect(chuyenCongTacService.create).toHaveBeenCalledWith(chuyenCongTac);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const chuyenCongTac = { id: 123 };
        spyOn(chuyenCongTacService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ chuyenCongTac });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(chuyenCongTacService.update).toHaveBeenCalledWith(chuyenCongTac);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackNhanVienById', () => {
        it('Should return tracked NhanVien primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackNhanVienById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });

    describe('Getting selected relationships', () => {
      describe('getSelectedNhanVien', () => {
        it('Should return option if no NhanVien is selected', () => {
          const option = { id: 123 };
          const result = comp.getSelectedNhanVien(option);
          expect(result === option).toEqual(true);
        });

        it('Should return selected NhanVien for according option', () => {
          const option = { id: 123 };
          const selected = { id: 123 };
          const selected2 = { id: 456 };
          const result = comp.getSelectedNhanVien(option, [selected2, selected]);
          expect(result === selected).toEqual(true);
          expect(result === selected2).toEqual(false);
          expect(result === option).toEqual(false);
        });

        it('Should return option if this NhanVien is not selected', () => {
          const option = { id: 123 };
          const selected = { id: 456 };
          const result = comp.getSelectedNhanVien(option, [selected]);
          expect(result === option).toEqual(true);
          expect(result === selected).toEqual(false);
        });
      });
    });
  });
});
