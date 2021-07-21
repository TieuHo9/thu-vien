jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { NhanVienCongTacService } from '../service/nhan-vien-cong-tac.service';
import { INhanVienCongTac, NhanVienCongTac } from '../nhan-vien-cong-tac.model';
import { IChuyenCongTac } from 'app/entities/chuyen-cong-tac/chuyen-cong-tac.model';
import { ChuyenCongTacService } from 'app/entities/chuyen-cong-tac/service/chuyen-cong-tac.service';

import { NhanVienCongTacUpdateComponent } from './nhan-vien-cong-tac-update.component';

describe('Component Tests', () => {
  describe('NhanVienCongTac Management Update Component', () => {
    let comp: NhanVienCongTacUpdateComponent;
    let fixture: ComponentFixture<NhanVienCongTacUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let nhanVienCongTacService: NhanVienCongTacService;
    let chuyenCongTacService: ChuyenCongTacService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [NhanVienCongTacUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(NhanVienCongTacUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NhanVienCongTacUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      nhanVienCongTacService = TestBed.inject(NhanVienCongTacService);
      chuyenCongTacService = TestBed.inject(ChuyenCongTacService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call ChuyenCongTac query and add missing value', () => {
        const nhanVienCongTac: INhanVienCongTac = { id: 456 };
        const chuyencts: IChuyenCongTac[] = [{ id: 14251 }];
        nhanVienCongTac.chuyencts = chuyencts;

        const chuyenCongTacCollection: IChuyenCongTac[] = [{ id: 33028 }];
        spyOn(chuyenCongTacService, 'query').and.returnValue(of(new HttpResponse({ body: chuyenCongTacCollection })));
        const additionalChuyenCongTacs = [...chuyencts];
        const expectedCollection: IChuyenCongTac[] = [...additionalChuyenCongTacs, ...chuyenCongTacCollection];
        spyOn(chuyenCongTacService, 'addChuyenCongTacToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ nhanVienCongTac });
        comp.ngOnInit();

        expect(chuyenCongTacService.query).toHaveBeenCalled();
        expect(chuyenCongTacService.addChuyenCongTacToCollectionIfMissing).toHaveBeenCalledWith(
          chuyenCongTacCollection,
          ...additionalChuyenCongTacs
        );
        expect(comp.chuyenCongTacsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const nhanVienCongTac: INhanVienCongTac = { id: 456 };
        const chuyencts: IChuyenCongTac = { id: 2321 };
        nhanVienCongTac.chuyencts = [chuyencts];

        activatedRoute.data = of({ nhanVienCongTac });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(nhanVienCongTac));
        expect(comp.chuyenCongTacsSharedCollection).toContain(chuyencts);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const nhanVienCongTac = { id: 123 };
        spyOn(nhanVienCongTacService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ nhanVienCongTac });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: nhanVienCongTac }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(nhanVienCongTacService.update).toHaveBeenCalledWith(nhanVienCongTac);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const nhanVienCongTac = new NhanVienCongTac();
        spyOn(nhanVienCongTacService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ nhanVienCongTac });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: nhanVienCongTac }));
        saveSubject.complete();

        // THEN
        expect(nhanVienCongTacService.create).toHaveBeenCalledWith(nhanVienCongTac);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const nhanVienCongTac = { id: 123 };
        spyOn(nhanVienCongTacService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ nhanVienCongTac });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(nhanVienCongTacService.update).toHaveBeenCalledWith(nhanVienCongTac);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackChuyenCongTacById', () => {
        it('Should return tracked ChuyenCongTac primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackChuyenCongTacById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });

    describe('Getting selected relationships', () => {
      describe('getSelectedChuyenCongTac', () => {
        it('Should return option if no ChuyenCongTac is selected', () => {
          const option = { id: 123 };
          const result = comp.getSelectedChuyenCongTac(option);
          expect(result === option).toEqual(true);
        });

        it('Should return selected ChuyenCongTac for according option', () => {
          const option = { id: 123 };
          const selected = { id: 123 };
          const selected2 = { id: 456 };
          const result = comp.getSelectedChuyenCongTac(option, [selected2, selected]);
          expect(result === selected).toEqual(true);
          expect(result === selected2).toEqual(false);
          expect(result === option).toEqual(false);
        });

        it('Should return option if this ChuyenCongTac is not selected', () => {
          const option = { id: 123 };
          const selected = { id: 456 };
          const result = comp.getSelectedChuyenCongTac(option, [selected]);
          expect(result === option).toEqual(true);
          expect(result === selected).toEqual(false);
        });
      });
    });
  });
});
