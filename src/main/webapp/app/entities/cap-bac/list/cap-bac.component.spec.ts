import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { CapBacService } from '../service/cap-bac.service';

import { CapBacComponent } from './cap-bac.component';

describe('Component Tests', () => {
  describe('CapBac Management Component', () => {
    let comp: CapBacComponent;
    let fixture: ComponentFixture<CapBacComponent>;
    let service: CapBacService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [CapBacComponent],
      })
        .overrideTemplate(CapBacComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CapBacComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(CapBacService);

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
      expect(comp.capBacs?.[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
