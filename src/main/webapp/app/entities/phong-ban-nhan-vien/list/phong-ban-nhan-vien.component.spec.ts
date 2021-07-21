import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { PhongBanNhanVienService } from '../service/phong-ban-nhan-vien.service';

import { PhongBanNhanVienComponent } from './phong-ban-nhan-vien.component';

describe('Component Tests', () => {
  describe('PhongBanNhanVien Management Component', () => {
    let comp: PhongBanNhanVienComponent;
    let fixture: ComponentFixture<PhongBanNhanVienComponent>;
    let service: PhongBanNhanVienService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [PhongBanNhanVienComponent],
      })
        .overrideTemplate(PhongBanNhanVienComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PhongBanNhanVienComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(PhongBanNhanVienService);

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
      expect(comp.phongBanNhanViens?.[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
