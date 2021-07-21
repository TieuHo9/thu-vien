import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { NhanVienCongTacService } from '../service/nhan-vien-cong-tac.service';

import { NhanVienCongTacComponent } from './nhan-vien-cong-tac.component';

describe('Component Tests', () => {
  describe('NhanVienCongTac Management Component', () => {
    let comp: NhanVienCongTacComponent;
    let fixture: ComponentFixture<NhanVienCongTacComponent>;
    let service: NhanVienCongTacService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [NhanVienCongTacComponent],
      })
        .overrideTemplate(NhanVienCongTacComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NhanVienCongTacComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(NhanVienCongTacService);

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
      expect(comp.nhanVienCongTacs?.[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
