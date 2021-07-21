jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { PhongBanNhanVienService } from '../service/phong-ban-nhan-vien.service';
import { IPhongBanNhanVien, PhongBanNhanVien } from '../phong-ban-nhan-vien.model';
import { INhanVien } from 'app/entities/nhan-vien/nhan-vien.model';
import { NhanVienService } from 'app/entities/nhan-vien/service/nhan-vien.service';

import { PhongBanNhanVienUpdateComponent } from './phong-ban-nhan-vien-update.component';

describe('Component Tests', () => {
  describe('PhongBanNhanVien Management Update Component', () => {
    let comp: PhongBanNhanVienUpdateComponent;
    let fixture: ComponentFixture<PhongBanNhanVienUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let phongBanNhanVienService: PhongBanNhanVienService;
    let nhanVienService: NhanVienService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [PhongBanNhanVienUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(PhongBanNhanVienUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PhongBanNhanVienUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      phongBanNhanVienService = TestBed.inject(PhongBanNhanVienService);
      nhanVienService = TestBed.inject(NhanVienService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call NhanVien query and add missing value', () => {
        const phongBanNhanVien: IPhongBanNhanVien = { id: 456 };
        const nviens: INhanVien[] = [{ id: 81965 }];
        phongBanNhanVien.nviens = nviens;

        const nhanVienCollection: INhanVien[] = [{ id: 1518 }];
        spyOn(nhanVienService, 'query').and.returnValue(of(new HttpResponse({ body: nhanVienCollection })));
        const additionalNhanViens = [...nviens];
        const expectedCollection: INhanVien[] = [...additionalNhanViens, ...nhanVienCollection];
        spyOn(nhanVienService, 'addNhanVienToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ phongBanNhanVien });
        comp.ngOnInit();

        expect(nhanVienService.query).toHaveBeenCalled();
        expect(nhanVienService.addNhanVienToCollectionIfMissing).toHaveBeenCalledWith(nhanVienCollection, ...additionalNhanViens);
        expect(comp.nhanViensSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const phongBanNhanVien: IPhongBanNhanVien = { id: 456 };
        const nviens: INhanVien = { id: 49902 };
        phongBanNhanVien.nviens = [nviens];

        activatedRoute.data = of({ phongBanNhanVien });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(phongBanNhanVien));
        expect(comp.nhanViensSharedCollection).toContain(nviens);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const phongBanNhanVien = { id: 123 };
        spyOn(phongBanNhanVienService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ phongBanNhanVien });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: phongBanNhanVien }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(phongBanNhanVienService.update).toHaveBeenCalledWith(phongBanNhanVien);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const phongBanNhanVien = new PhongBanNhanVien();
        spyOn(phongBanNhanVienService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ phongBanNhanVien });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: phongBanNhanVien }));
        saveSubject.complete();

        // THEN
        expect(phongBanNhanVienService.create).toHaveBeenCalledWith(phongBanNhanVien);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const phongBanNhanVien = { id: 123 };
        spyOn(phongBanNhanVienService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ phongBanNhanVien });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(phongBanNhanVienService.update).toHaveBeenCalledWith(phongBanNhanVien);
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
