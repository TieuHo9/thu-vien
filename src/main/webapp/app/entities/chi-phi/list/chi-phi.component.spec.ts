import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { ChiPhiService } from '../service/chi-phi.service';

import { ChiPhiComponent } from './chi-phi.component';

describe('Component Tests', () => {
  describe('ChiPhi Management Component', () => {
    let comp: ChiPhiComponent;
    let fixture: ComponentFixture<ChiPhiComponent>;
    let service: ChiPhiService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ChiPhiComponent],
      })
        .overrideTemplate(ChiPhiComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ChiPhiComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(ChiPhiService);

      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [{ id: 123 }],
            headers,
          })
        )
      );
    });

    it('Should call load all on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.chiPhis?.[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
