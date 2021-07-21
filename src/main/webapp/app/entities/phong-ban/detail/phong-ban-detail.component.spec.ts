import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PhongBanDetailComponent } from './phong-ban-detail.component';

describe('Component Tests', () => {
  describe('PhongBan Management Detail Component', () => {
    let comp: PhongBanDetailComponent;
    let fixture: ComponentFixture<PhongBanDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [PhongBanDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ phongBan: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(PhongBanDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PhongBanDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load phongBan on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.phongBan).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
