import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { PhongBanService } from '../service/phong-ban.service';

import { PhongBanComponent } from './phong-ban.component';

describe('Component Tests', () => {
  describe('PhongBan Management Component', () => {
    let comp: PhongBanComponent;
    let fixture: ComponentFixture<PhongBanComponent>;
    let service: PhongBanService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [PhongBanComponent],
      })
        .overrideTemplate(PhongBanComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PhongBanComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(PhongBanService);

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
      expect(comp.phongBans?.[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
