import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IDeXuatThanhToan, DeXuatThanhToan } from '../de-xuat-thanh-toan.model';

import { DeXuatThanhToanService } from './de-xuat-thanh-toan.service';

describe('Service Tests', () => {
  describe('DeXuatThanhToan Service', () => {
    let service: DeXuatThanhToanService;
    let httpMock: HttpTestingController;
    let elemDefault: IDeXuatThanhToan;
    let expectedResult: IDeXuatThanhToan | IDeXuatThanhToan[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(DeXuatThanhToanService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        maDeXuat: 'AAAAAAA',
        tenDeXuat: 'AAAAAAA',
        tuNgay: currentDate,
        denNgay: currentDate,
        trangThaiTruongPhong: 'AAAAAAA',
        trangThaiPhongTaiVu: 'AAAAAAA',
        trangThaiBanLanhDao: 'AAAAAAA',
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            tuNgay: currentDate.format(DATE_FORMAT),
            denNgay: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a DeXuatThanhToan', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            tuNgay: currentDate.format(DATE_FORMAT),
            denNgay: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            tuNgay: currentDate,
            denNgay: currentDate,
          },
          returnedFromService
        );

        service.create(new DeXuatThanhToan()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a DeXuatThanhToan', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            maDeXuat: 'BBBBBB',
            tenDeXuat: 'BBBBBB',
            tuNgay: currentDate.format(DATE_FORMAT),
            denNgay: currentDate.format(DATE_FORMAT),
            trangThaiTruongPhong: 'BBBBBB',
            trangThaiPhongTaiVu: 'BBBBBB',
            trangThaiBanLanhDao: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            tuNgay: currentDate,
            denNgay: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a DeXuatThanhToan', () => {
        const patchObject = Object.assign(
          {
            maDeXuat: 'BBBBBB',
            tenDeXuat: 'BBBBBB',
            tuNgay: currentDate.format(DATE_FORMAT),
            trangThaiPhongTaiVu: 'BBBBBB',
            trangThaiBanLanhDao: 'BBBBBB',
          },
          new DeXuatThanhToan()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            tuNgay: currentDate,
            denNgay: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of DeXuatThanhToan', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            maDeXuat: 'BBBBBB',
            tenDeXuat: 'BBBBBB',
            tuNgay: currentDate.format(DATE_FORMAT),
            denNgay: currentDate.format(DATE_FORMAT),
            trangThaiTruongPhong: 'BBBBBB',
            trangThaiPhongTaiVu: 'BBBBBB',
            trangThaiBanLanhDao: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            tuNgay: currentDate,
            denNgay: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a DeXuatThanhToan', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addDeXuatThanhToanToCollectionIfMissing', () => {
        it('should add a DeXuatThanhToan to an empty array', () => {
          const deXuatThanhToan: IDeXuatThanhToan = { id: 123 };
          expectedResult = service.addDeXuatThanhToanToCollectionIfMissing([], deXuatThanhToan);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(deXuatThanhToan);
        });

        it('should not add a DeXuatThanhToan to an array that contains it', () => {
          const deXuatThanhToan: IDeXuatThanhToan = { id: 123 };
          const deXuatThanhToanCollection: IDeXuatThanhToan[] = [
            {
              ...deXuatThanhToan,
            },
            { id: 456 },
          ];
          expectedResult = service.addDeXuatThanhToanToCollectionIfMissing(deXuatThanhToanCollection, deXuatThanhToan);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a DeXuatThanhToan to an array that doesn't contain it", () => {
          const deXuatThanhToan: IDeXuatThanhToan = { id: 123 };
          const deXuatThanhToanCollection: IDeXuatThanhToan[] = [{ id: 456 }];
          expectedResult = service.addDeXuatThanhToanToCollectionIfMissing(deXuatThanhToanCollection, deXuatThanhToan);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(deXuatThanhToan);
        });

        it('should add only unique DeXuatThanhToan to an array', () => {
          const deXuatThanhToanArray: IDeXuatThanhToan[] = [{ id: 123 }, { id: 456 }, { id: 84242 }];
          const deXuatThanhToanCollection: IDeXuatThanhToan[] = [{ id: 123 }];
          expectedResult = service.addDeXuatThanhToanToCollectionIfMissing(deXuatThanhToanCollection, ...deXuatThanhToanArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const deXuatThanhToan: IDeXuatThanhToan = { id: 123 };
          const deXuatThanhToan2: IDeXuatThanhToan = { id: 456 };
          expectedResult = service.addDeXuatThanhToanToCollectionIfMissing([], deXuatThanhToan, deXuatThanhToan2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(deXuatThanhToan);
          expect(expectedResult).toContain(deXuatThanhToan2);
        });

        it('should accept null and undefined values', () => {
          const deXuatThanhToan: IDeXuatThanhToan = { id: 123 };
          expectedResult = service.addDeXuatThanhToanToCollectionIfMissing([], null, deXuatThanhToan, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(deXuatThanhToan);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
