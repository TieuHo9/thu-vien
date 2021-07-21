import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CapBacDetailComponent } from './cap-bac-detail.component';

describe('Component Tests', () => {
  describe('CapBac Management Detail Component', () => {
    let comp: CapBacDetailComponent;
    let fixture: ComponentFixture<CapBacDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [CapBacDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ capBac: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(CapBacDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CapBacDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load capBac on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.capBac).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
