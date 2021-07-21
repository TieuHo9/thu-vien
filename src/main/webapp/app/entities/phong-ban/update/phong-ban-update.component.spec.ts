jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { PhongBanService } from '../service/phong-ban.service';
import { IPhongBan, PhongBan } from '../phong-ban.model';

import { PhongBanUpdateComponent } from './phong-ban-update.component';

describe('Component Tests', () => {
  describe('PhongBan Management Update Component', () => {
    let comp: PhongBanUpdateComponent;
    let fixture: ComponentFixture<PhongBanUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let phongBanService: PhongBanService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [PhongBanUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(PhongBanUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PhongBanUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      phongBanService = TestBed.inject(PhongBanService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const phongBan: IPhongBan = { id: 456 };

        activatedRoute.data = of({ phongBan });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(phongBan));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const phongBan = { id: 123 };
        spyOn(phongBanService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ phongBan });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: phongBan }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(phongBanService.update).toHaveBeenCalledWith(phongBan);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const phongBan = new PhongBan();
        spyOn(phongBanService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ phongBan });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: phongBan }));
        saveSubject.complete();

        // THEN
        expect(phongBanService.create).toHaveBeenCalledWith(phongBan);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const phongBan = { id: 123 };
        spyOn(phongBanService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ phongBan });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(phongBanService.update).toHaveBeenCalledWith(phongBan);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
