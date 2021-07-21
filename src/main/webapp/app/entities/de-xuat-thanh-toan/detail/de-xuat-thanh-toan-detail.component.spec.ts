import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DeXuatThanhToanDetailComponent } from './de-xuat-thanh-toan-detail.component';

describe('Component Tests', () => {
  describe('DeXuatThanhToan Management Detail Component', () => {
    let comp: DeXuatThanhToanDetailComponent;
    let fixture: ComponentFixture<DeXuatThanhToanDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [DeXuatThanhToanDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ deXuatThanhToan: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(DeXuatThanhToanDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DeXuatThanhToanDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load deXuatThanhToan on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.deXuatThanhToan).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
