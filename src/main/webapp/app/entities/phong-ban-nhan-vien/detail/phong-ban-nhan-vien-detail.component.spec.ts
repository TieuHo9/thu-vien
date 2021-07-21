import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PhongBanNhanVienDetailComponent } from './phong-ban-nhan-vien-detail.component';

describe('Component Tests', () => {
  describe('PhongBanNhanVien Management Detail Component', () => {
    let comp: PhongBanNhanVienDetailComponent;
    let fixture: ComponentFixture<PhongBanNhanVienDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [PhongBanNhanVienDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ phongBanNhanVien: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(PhongBanNhanVienDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PhongBanNhanVienDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load phongBanNhanVien on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.phongBanNhanVien).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
