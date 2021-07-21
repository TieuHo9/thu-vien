jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { NhanVienCongTacService } from '../service/nhan-vien-cong-tac.service';

import { NhanVienCongTacDeleteDialogComponent } from './nhan-vien-cong-tac-delete-dialog.component';

describe('Component Tests', () => {
  describe('NhanVienCongTac Management Delete Component', () => {
    let comp: NhanVienCongTacDeleteDialogComponent;
    let fixture: ComponentFixture<NhanVienCongTacDeleteDialogComponent>;
    let service: NhanVienCongTacService;
    let mockActiveModal: NgbActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [NhanVienCongTacDeleteDialogComponent],
        providers: [NgbActiveModal],
      })
        .overrideTemplate(NhanVienCongTacDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NhanVienCongTacDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(NhanVienCongTacService);
      mockActiveModal = TestBed.inject(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.close).not.toHaveBeenCalled();
        expect(mockActiveModal.dismiss).toHaveBeenCalled();
      });
    });
  });
});
