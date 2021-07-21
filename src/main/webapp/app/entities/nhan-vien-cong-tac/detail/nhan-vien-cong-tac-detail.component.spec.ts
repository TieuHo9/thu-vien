import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NhanVienCongTacDetailComponent } from './nhan-vien-cong-tac-detail.component';

describe('Component Tests', () => {
  describe('NhanVienCongTac Management Detail Component', () => {
    let comp: NhanVienCongTacDetailComponent;
    let fixture: ComponentFixture<NhanVienCongTacDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [NhanVienCongTacDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ nhanVienCongTac: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(NhanVienCongTacDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NhanVienCongTacDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load nhanVienCongTac on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.nhanVienCongTac).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
