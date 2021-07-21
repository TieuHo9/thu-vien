import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { NhanVienService } from '../service/nhan-vien.service';

import { NhanVienComponent } from './nhan-vien.component';

describe('Component Tests', () => {
  describe('NhanVien Management Component', () => {
    let comp: NhanVienComponent;
    let fixture: ComponentFixture<NhanVienComponent>;
    let service: NhanVienService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [NhanVienComponent],
      })
        .overrideTemplate(NhanVienComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NhanVienComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(NhanVienService);

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
      expect(comp.nhanViens?.[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
