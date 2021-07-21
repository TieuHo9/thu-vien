jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { CapBacService } from '../service/cap-bac.service';
import { ICapBac, CapBac } from '../cap-bac.model';

import { CapBacUpdateComponent } from './cap-bac-update.component';

describe('Component Tests', () => {
  describe('CapBac Management Update Component', () => {
    let comp: CapBacUpdateComponent;
    let fixture: ComponentFixture<CapBacUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let capBacService: CapBacService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [CapBacUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(CapBacUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CapBacUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      capBacService = TestBed.inject(CapBacService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const capBac: ICapBac = { id: 456 };

        activatedRoute.data = of({ capBac });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(capBac));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const capBac = { id: 123 };
        spyOn(capBacService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ capBac });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: capBac }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(capBacService.update).toHaveBeenCalledWith(capBac);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const capBac = new CapBac();
        spyOn(capBacService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ capBac });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: capBac }));
        saveSubject.complete();

        // THEN
        expect(capBacService.create).toHaveBeenCalledWith(capBac);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const capBac = { id: 123 };
        spyOn(capBacService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ capBac });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(capBacService.update).toHaveBeenCalledWith(capBac);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
