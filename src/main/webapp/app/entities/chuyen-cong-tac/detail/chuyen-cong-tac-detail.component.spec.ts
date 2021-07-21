import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ChuyenCongTacDetailComponent } from './chuyen-cong-tac-detail.component';

describe('Component Tests', () => {
  describe('ChuyenCongTac Management Detail Component', () => {
    let comp: ChuyenCongTacDetailComponent;
    let fixture: ComponentFixture<ChuyenCongTacDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [ChuyenCongTacDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ chuyenCongTac: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(ChuyenCongTacDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ChuyenCongTacDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load chuyenCongTac on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.chuyenCongTac).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
