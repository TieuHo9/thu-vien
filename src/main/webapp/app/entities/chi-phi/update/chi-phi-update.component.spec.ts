jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ChiPhiService } from '../service/chi-phi.service';
import { IChiPhi, ChiPhi } from '../chi-phi.model';

import { ChiPhiUpdateComponent } from './chi-phi-update.component';

describe('Component Tests', () => {
  describe('ChiPhi Management Update Component', () => {
    let comp: ChiPhiUpdateComponent;
    let fixture: ComponentFixture<ChiPhiUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let chiPhiService: ChiPhiService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ChiPhiUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(ChiPhiUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ChiPhiUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      chiPhiService = TestBed.inject(ChiPhiService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const chiPhi: IChiPhi = { id: 456 };

        activatedRoute.data = of({ chiPhi });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(chiPhi));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const chiPhi = { id: 123 };
        spyOn(chiPhiService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ chiPhi });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: chiPhi }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(chiPhiService.update).toHaveBeenCalledWith(chiPhi);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const chiPhi = new ChiPhi();
        spyOn(chiPhiService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ chiPhi });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: chiPhi }));
        saveSubject.complete();

        // THEN
        expect(chiPhiService.create).toHaveBeenCalledWith(chiPhi);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const chiPhi = { id: 123 };
        spyOn(chiPhiService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ chiPhi });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(chiPhiService.update).toHaveBeenCalledWith(chiPhi);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
