import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { ChuyenCongTacService } from '../service/chuyen-cong-tac.service';

import { ChuyenCongTacComponent } from './chuyen-cong-tac.component';

describe('Component Tests', () => {
  describe('ChuyenCongTac Management Component', () => {
    let comp: ChuyenCongTacComponent;
    let fixture: ComponentFixture<ChuyenCongTacComponent>;
    let service: ChuyenCongTacService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ChuyenCongTacComponent],
      })
        .overrideTemplate(ChuyenCongTacComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ChuyenCongTacComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(ChuyenCongTacService);

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
      expect(comp.chuyenCongTacs?.[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
