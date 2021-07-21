jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { NhanVienService } from '../service/nhan-vien.service';
import { INhanVien, NhanVien } from '../nhan-vien.model';
import { IPhongBan } from 'app/entities/phong-ban/phong-ban.model';
import { PhongBanService } from 'app/entities/phong-ban/service/phong-ban.service';
import { ICapBac } from 'app/entities/cap-bac/cap-bac.model';
import { CapBacService } from 'app/entities/cap-bac/service/cap-bac.service';

import { NhanVienUpdateComponent } from './nhan-vien-update.component';

describe('Component Tests', () => {
  describe('NhanVien Management Update Component', () => {
    let comp: NhanVienUpdateComponent;
    let fixture: ComponentFixture<NhanVienUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let nhanVienService: NhanVienService;
    let phongBanService: PhongBanService;
    let capBacService: CapBacService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [NhanVienUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(NhanVienUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NhanVienUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      nhanVienService = TestBed.inject(NhanVienService);
      phongBanService = TestBed.inject(PhongBanService);
      capBacService = TestBed.inject(CapBacService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call PhongBan query and add missing value', () => {
        const nhanVien: INhanVien = { id: 456 };
        const phongbans: IPhongBan[] = [{ id: 89594 }];
        nhanVien.phongbans = phongbans;

        const phongBanCollection: IPhongBan[] = [{ id: 97017 }];
        spyOn(phongBanService, 'query').and.returnValue(of(new HttpResponse({ body: phongBanCollection })));
        const additionalPhongBans = [...phongbans];
        const expectedCollection: IPhongBan[] = [...additionalPhongBans, ...phongBanCollection];
        spyOn(phongBanService, 'addPhongBanToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ nhanVien });
        comp.ngOnInit();

        expect(phongBanService.query).toHaveBeenCalled();
        expect(phongBanService.addPhongBanToCollectionIfMissing).toHaveBeenCalledWith(phongBanCollection, ...additionalPhongBans);
        expect(comp.phongBansSharedCollection).toEqual(expectedCollection);
      });

      it('Should call CapBac query and add missing value', () => {
        const nhanVien: INhanVien = { id: 456 };
        const capbacs: ICapBac[] = [{ id: 17 }];
        nhanVien.capbacs = capbacs;

        const capBacCollection: ICapBac[] = [{ id: 51795 }];
        spyOn(capBacService, 'query').and.returnValue(of(new HttpResponse({ body: capBacCollection })));
        const additionalCapBacs = [...capbacs];
        const expectedCollection: ICapBac[] = [...additionalCapBacs, ...capBacCollection];
        spyOn(capBacService, 'addCapBacToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ nhanVien });
        comp.ngOnInit();

        expect(capBacService.query).toHaveBeenCalled();
        expect(capBacService.addCapBacToCollectionIfMissing).toHaveBeenCalledWith(capBacCollection, ...additionalCapBacs);
        expect(comp.capBacsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const nhanVien: INhanVien = { id: 456 };
        const phongbans: IPhongBan = { id: 63025 };
        nhanVien.phongbans = [phongbans];
        const capbacs: ICapBac = { id: 26322 };
        nhanVien.capbacs = [capbacs];

        activatedRoute.data = of({ nhanVien });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(nhanVien));
        expect(comp.phongBansSharedCollection).toContain(phongbans);
        expect(comp.capBacsSharedCollection).toContain(capbacs);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const nhanVien = { id: 123 };
        spyOn(nhanVienService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ nhanVien });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: nhanVien }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(nhanVienService.update).toHaveBeenCalledWith(nhanVien);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const nhanVien = new NhanVien();
        spyOn(nhanVienService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ nhanVien });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: nhanVien }));
        saveSubject.complete();

        // THEN
        expect(nhanVienService.create).toHaveBeenCalledWith(nhanVien);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const nhanVien = { id: 123 };
        spyOn(nhanVienService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ nhanVien });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(nhanVienService.update).toHaveBeenCalledWith(nhanVien);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackPhongBanById', () => {
        it('Should return tracked PhongBan primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackPhongBanById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackCapBacById', () => {
        it('Should return tracked CapBac primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackCapBacById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });

    describe('Getting selected relationships', () => {
      describe('getSelectedPhongBan', () => {
        it('Should return option if no PhongBan is selected', () => {
          const option = { id: 123 };
          const result = comp.getSelectedPhongBan(option);
          expect(result === option).toEqual(true);
        });

        it('Should return selected PhongBan for according option', () => {
          const option = { id: 123 };
          const selected = { id: 123 };
          const selected2 = { id: 456 };
          const result = comp.getSelectedPhongBan(option, [selected2, selected]);
          expect(result === selected).toEqual(true);
          expect(result === selected2).toEqual(false);
          expect(result === option).toEqual(false);
        });

        it('Should return option if this PhongBan is not selected', () => {
          const option = { id: 123 };
          const selected = { id: 456 };
          const result = comp.getSelectedPhongBan(option, [selected]);
          expect(result === option).toEqual(true);
          expect(result === selected).toEqual(false);
        });
      });

      describe('getSelectedCapBac', () => {
        it('Should return option if no CapBac is selected', () => {
          const option = { id: 123 };
          const result = comp.getSelectedCapBac(option);
          expect(result === option).toEqual(true);
        });

        it('Should return selected CapBac for according option', () => {
          const option = { id: 123 };
          const selected = { id: 123 };
          const selected2 = { id: 456 };
          const result = comp.getSelectedCapBac(option, [selected2, selected]);
          expect(result === selected).toEqual(true);
          expect(result === selected2).toEqual(false);
          expect(result === option).toEqual(false);
        });

        it('Should return option if this CapBac is not selected', () => {
          const option = { id: 123 };
          const selected = { id: 456 };
          const result = comp.getSelectedCapBac(option, [selected]);
          expect(result === option).toEqual(true);
          expect(result === selected).toEqual(false);
        });
      });
    });
  });
});
