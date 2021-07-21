import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ChiPhiDetailComponent } from './chi-phi-detail.component';

describe('Component Tests', () => {
  describe('ChiPhi Management Detail Component', () => {
    let comp: ChiPhiDetailComponent;
    let fixture: ComponentFixture<ChiPhiDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [ChiPhiDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ chiPhi: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(ChiPhiDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ChiPhiDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load chiPhi on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.chiPhi).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
