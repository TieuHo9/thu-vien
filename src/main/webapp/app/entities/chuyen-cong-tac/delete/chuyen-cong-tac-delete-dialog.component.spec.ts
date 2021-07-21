jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ChuyenCongTacService } from '../service/chuyen-cong-tac.service';

import { ChuyenCongTacDeleteDialogComponent } from './chuyen-cong-tac-delete-dialog.component';

describe('Component Tests', () => {
  describe('ChuyenCongTac Management Delete Component', () => {
    let comp: ChuyenCongTacDeleteDialogComponent;
    let fixture: ComponentFixture<ChuyenCongTacDeleteDialogComponent>;
    let service: ChuyenCongTacService;
    let mockActiveModal: NgbActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ChuyenCongTacDeleteDialogComponent],
        providers: [NgbActiveModal],
      })
        .overrideTemplate(ChuyenCongTacDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ChuyenCongTacDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(ChuyenCongTacService);
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
