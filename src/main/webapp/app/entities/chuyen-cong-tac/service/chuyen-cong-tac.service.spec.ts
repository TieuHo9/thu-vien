import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IChuyenCongTac, ChuyenCongTac } from '../chuyen-cong-tac.model';

import { ChuyenCongTacService } from './chuyen-cong-tac.service';

describe('Service Tests', () => {
  describe('ChuyenCongTac Service', () => {
    let service: ChuyenCongTacService;
    let httpMock: HttpTestingController;
    let elemDefault: IChuyenCongTac;
    let expectedResult: IChuyenCongTac | IChuyenCongTac[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(ChuyenCongTacService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        maChuyenDi: 'AAAAAAA',
        tenChuyenDi: 'AAAAAAA',
        thoiGianTu: currentDate,
        thoiGianDen: currentDate,
        maNhanVien: 'AAAAAAA',
        tenNhanVien: 'AAAAAAA',
        soDienThoai: 'AAAAAAA',
        diaDiem: 'AAAAAAA',
        soTien: 'AAAAAAA',
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            thoiGianTu: currentDate.format(DATE_FORMAT),
            thoiGianDen: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a ChuyenCongTac', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            thoiGianTu: currentDate.format(DATE_FORMAT),
            thoiGianDen: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            thoiGianTu: currentDate,
            thoiGianDen: currentDate,
          },
          returnedFromService
        );

        service.create(new ChuyenCongTac()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ChuyenCongTac', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            maChuyenDi: 'BBBBBB',
            tenChuyenDi: 'BBBBBB',
            thoiGianTu: currentDate.format(DATE_FORMAT),
            thoiGianDen: currentDate.format(DATE_FORMAT),
            maNhanVien: 'BBBBBB',
            tenNhanVien: 'BBBBBB',
            soDienThoai: 'BBBBBB',
            diaDiem: 'BBBBBB',
            soTien: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            thoiGianTu: currentDate,
            thoiGianDen: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a ChuyenCongTac', () => {
        const patchObject = Object.assign(
          {
            maChuyenDi: 'BBBBBB',
            tenChuyenDi: 'BBBBBB',
            thoiGianTu: currentDate.format(DATE_FORMAT),
            thoiGianDen: currentDate.format(DATE_FORMAT),
            maNhanVien: 'BBBBBB',
            tenNhanVien: 'BBBBBB',
            soDienThoai: 'BBBBBB',
            soTien: 'BBBBBB',
          },
          new ChuyenCongTac()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            thoiGianTu: currentDate,
            thoiGianDen: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ChuyenCongTac', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            maChuyenDi: 'BBBBBB',
            tenChuyenDi: 'BBBBBB',
            thoiGianTu: currentDate.format(DATE_FORMAT),
            thoiGianDen: currentDate.format(DATE_FORMAT),
            maNhanVien: 'BBBBBB',
            tenNhanVien: 'BBBBBB',
            soDienThoai: 'BBBBBB',
            diaDiem: 'BBBBBB',
            soTien: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            thoiGianTu: currentDate,
            thoiGianDen: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a ChuyenCongTac', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addChuyenCongTacToCollectionIfMissing', () => {
        it('should add a ChuyenCongTac to an empty array', () => {
          const chuyenCongTac: IChuyenCongTac = { id: 123 };
          expectedResult = service.addChuyenCongTacToCollectionIfMissing([], chuyenCongTac);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(chuyenCongTac);
        });

        it('should not add a ChuyenCongTac to an array that contains it', () => {
          const chuyenCongTac: IChuyenCongTac = { id: 123 };
          const chuyenCongTacCollection: IChuyenCongTac[] = [
            {
              ...chuyenCongTac,
            },
            { id: 456 },
          ];
          expectedResult = service.addChuyenCongTacToCollectionIfMissing(chuyenCongTacCollection, chuyenCongTac);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a ChuyenCongTac to an array that doesn't contain it", () => {
          const chuyenCongTac: IChuyenCongTac = { id: 123 };
          const chuyenCongTacCollection: IChuyenCongTac[] = [{ id: 456 }];
          expectedResult = service.addChuyenCongTacToCollectionIfMissing(chuyenCongTacCollection, chuyenCongTac);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(chuyenCongTac);
        });

        it('should add only unique ChuyenCongTac to an array', () => {
          const chuyenCongTacArray: IChuyenCongTac[] = [{ id: 123 }, { id: 456 }, { id: 62141 }];
          const chuyenCongTacCollection: IChuyenCongTac[] = [{ id: 123 }];
          expectedResult = service.addChuyenCongTacToCollectionIfMissing(chuyenCongTacCollection, ...chuyenCongTacArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const chuyenCongTac: IChuyenCongTac = { id: 123 };
          const chuyenCongTac2: IChuyenCongTac = { id: 456 };
          expectedResult = service.addChuyenCongTacToCollectionIfMissing([], chuyenCongTac, chuyenCongTac2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(chuyenCongTac);
          expect(expectedResult).toContain(chuyenCongTac2);
        });

        it('should accept null and undefined values', () => {
          const chuyenCongTac: IChuyenCongTac = { id: 123 };
          expectedResult = service.addChuyenCongTacToCollectionIfMissing([], null, chuyenCongTac, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(chuyenCongTac);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
