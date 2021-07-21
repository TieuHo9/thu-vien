import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NhanVienDetailComponent } from './nhan-vien-detail.component';

describe('Component Tests', () => {
  describe('NhanVien Management Detail Component', () => {
    let comp: NhanVienDetailComponent;
    let fixture: ComponentFixture<NhanVienDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [NhanVienDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ nhanVien: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(NhanVienDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NhanVienDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load nhanVien on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.nhanVien).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
