import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { INhanVienCongTac, NhanVienCongTac } from '../nhan-vien-cong-tac.model';

import { NhanVienCongTacService } from './nhan-vien-cong-tac.service';

describe('Service Tests', () => {
  describe('NhanVienCongTac Service', () => {
    let service: NhanVienCongTacService;
    let httpMock: HttpTestingController;
    let elemDefault: INhanVienCongTac;
    let expectedResult: INhanVienCongTac | INhanVienCongTac[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(NhanVienCongTacService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        maNhanVien: 'AAAAAAA',
        maChuyenDi: 'AAAAAAA',
        soTien: 'AAAAAAA',
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a NhanVienCongTac', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new NhanVienCongTac()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a NhanVienCongTac', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            maNhanVien: 'BBBBBB',
            maChuyenDi: 'BBBBBB',
            soTien: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a NhanVienCongTac', () => {
        const patchObject = Object.assign(
          {
            maChuyenDi: 'BBBBBB',
          },
          new NhanVienCongTac()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of NhanVienCongTac', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            maNhanVien: 'BBBBBB',
            maChuyenDi: 'BBBBBB',
            soTien: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a NhanVienCongTac', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addNhanVienCongTacToCollectionIfMissing', () => {
        it('should add a NhanVienCongTac to an empty array', () => {
          const nhanVienCongTac: INhanVienCongTac = { id: 123 };
          expectedResult = service.addNhanVienCongTacToCollectionIfMissing([], nhanVienCongTac);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(nhanVienCongTac);
        });

        it('should not add a NhanVienCongTac to an array that contains it', () => {
          const nhanVienCongTac: INhanVienCongTac = { id: 123 };
          const nhanVienCongTacCollection: INhanVienCongTac[] = [
            {
              ...nhanVienCongTac,
            },
            { id: 456 },
          ];
          expectedResult = service.addNhanVienCongTacToCollectionIfMissing(nhanVienCongTacCollection, nhanVienCongTac);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a NhanVienCongTac to an array that doesn't contain it", () => {
          const nhanVienCongTac: INhanVienCongTac = { id: 123 };
          const nhanVienCongTacCollection: INhanVienCongTac[] = [{ id: 456 }];
          expectedResult = service.addNhanVienCongTacToCollectionIfMissing(nhanVienCongTacCollection, nhanVienCongTac);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(nhanVienCongTac);
        });

        it('should add only unique NhanVienCongTac to an array', () => {
          const nhanVienCongTacArray: INhanVienCongTac[] = [{ id: 123 }, { id: 456 }, { id: 71308 }];
          const nhanVienCongTacCollection: INhanVienCongTac[] = [{ id: 123 }];
          expectedResult = service.addNhanVienCongTacToCollectionIfMissing(nhanVienCongTacCollection, ...nhanVienCongTacArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const nhanVienCongTac: INhanVienCongTac = { id: 123 };
          const nhanVienCongTac2: INhanVienCongTac = { id: 456 };
          expectedResult = service.addNhanVienCongTacToCollectionIfMissing([], nhanVienCongTac, nhanVienCongTac2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(nhanVienCongTac);
          expect(expectedResult).toContain(nhanVienCongTac2);
        });

        it('should accept null and undefined values', () => {
          const nhanVienCongTac: INhanVienCongTac = { id: 123 };
          expectedResult = service.addNhanVienCongTacToCollectionIfMissing([], null, nhanVienCongTac, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(nhanVienCongTac);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
