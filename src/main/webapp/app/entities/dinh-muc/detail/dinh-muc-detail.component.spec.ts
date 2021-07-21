import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DinhMucDetailComponent } from './dinh-muc-detail.component';

describe('Component Tests', () => {
  describe('DinhMuc Management Detail Component', () => {
    let comp: DinhMucDetailComponent;
    let fixture: ComponentFixture<DinhMucDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [DinhMucDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ dinhMuc: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(DinhMucDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DinhMucDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load dinhMuc on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.dinhMuc).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
