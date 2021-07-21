jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { DinhMucService } from '../service/dinh-muc.service';
import { IDinhMuc, DinhMuc } from '../dinh-muc.model';
import { ICapBac } from 'app/entities/cap-bac/cap-bac.model';
import { CapBacService } from 'app/entities/cap-bac/service/cap-bac.service';

import { DinhMucUpdateComponent } from './dinh-muc-update.component';

describe('Component Tests', () => {
  describe('DinhMuc Management Update Component', () => {
    let comp: DinhMucUpdateComponent;
    let fixture: ComponentFixture<DinhMucUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let dinhMucService: DinhMucService;
    let capBacService: CapBacService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [DinhMucUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(DinhMucUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DinhMucUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      dinhMucService = TestBed.inject(DinhMucService);
      capBacService = TestBed.inject(CapBacService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call CapBac query and add missing value', () => {
        const dinhMuc: IDinhMuc = { id: 456 };
        const cbacs: ICapBac[] = [{ id: 18511 }];
        dinhMuc.cbacs = cbacs;

        const capBacCollection: ICapBac[] = [{ id: 52619 }];
        spyOn(capBacService, 'query').and.returnValue(of(new HttpResponse({ body: capBacCollection })));
        const additionalCapBacs = [...cbacs];
        const expectedCollection: ICapBac[] = [...additionalCapBacs, ...capBacCollection];
        spyOn(capBacService, 'addCapBacToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ dinhMuc });
        comp.ngOnInit();

        expect(capBacService.query).toHaveBeenCalled();
        expect(capBacService.addCapBacToCollectionIfMissing).toHaveBeenCalledWith(capBacCollection, ...additionalCapBacs);
        expect(comp.capBacsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const dinhMuc: IDinhMuc = { id: 456 };
        const cbacs: ICapBac = { id: 56645 };
        dinhMuc.cbacs = [cbacs];

        activatedRoute.data = of({ dinhMuc });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(dinhMuc));
        expect(comp.capBacsSharedCollection).toContain(cbacs);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const dinhMuc = { id: 123 };
        spyOn(dinhMucService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ dinhMuc });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: dinhMuc }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(dinhMucService.update).toHaveBeenCalledWith(dinhMuc);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const dinhMuc = new DinhMuc();
        spyOn(dinhMucService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ dinhMuc });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: dinhMuc }));
        saveSubject.complete();

        // THEN
        expect(dinhMucService.create).toHaveBeenCalledWith(dinhMuc);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const dinhMuc = { id: 123 };
        spyOn(dinhMucService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ dinhMuc });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(dinhMucService.update).toHaveBeenCalledWith(dinhMuc);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackCapBacById', () => {
        it('Should return tracked CapBac primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackCapBacById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });

    describe('Getting selected relationships', () => {
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
