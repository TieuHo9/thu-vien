import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { INhanVien, NhanVien } from '../nhan-vien.model';

import { NhanVienService } from './nhan-vien.service';

describe('Service Tests', () => {
  describe('NhanVien Service', () => {
    let service: NhanVienService;
    let httpMock: HttpTestingController;
    let elemDefault: INhanVien;
    let expectedResult: INhanVien | INhanVien[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(NhanVienService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        maNhanVien: 'AAAAAAA',
        tenNhanVien: 'AAAAAAA',
        phongBan: 'AAAAAAA',
        capBac: 'AAAAAAA',
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

      it('should create a NhanVien', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new NhanVien()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a NhanVien', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            maNhanVien: 'BBBBBB',
            tenNhanVien: 'BBBBBB',
            phongBan: 'BBBBBB',
            capBac: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a NhanVien', () => {
        const patchObject = Object.assign(
          {
            tenNhanVien: 'BBBBBB',
            capBac: 'BBBBBB',
          },
          new NhanVien()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of NhanVien', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            maNhanVien: 'BBBBBB',
            tenNhanVien: 'BBBBBB',
            phongBan: 'BBBBBB',
            capBac: 'BBBBBB',
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

      it('should delete a NhanVien', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addNhanVienToCollectionIfMissing', () => {
        it('should add a NhanVien to an empty array', () => {
          const nhanVien: INhanVien = { id: 123 };
          expectedResult = service.addNhanVienToCollectionIfMissing([], nhanVien);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(nhanVien);
        });

        it('should not add a NhanVien to an array that contains it', () => {
          const nhanVien: INhanVien = { id: 123 };
          const nhanVienCollection: INhanVien[] = [
            {
              ...nhanVien,
            },
            { id: 456 },
          ];
          expectedResult = service.addNhanVienToCollectionIfMissing(nhanVienCollection, nhanVien);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a NhanVien to an array that doesn't contain it", () => {
          const nhanVien: INhanVien = { id: 123 };
          const nhanVienCollection: INhanVien[] = [{ id: 456 }];
          expectedResult = service.addNhanVienToCollectionIfMissing(nhanVienCollection, nhanVien);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(nhanVien);
        });

        it('should add only unique NhanVien to an array', () => {
          const nhanVienArray: INhanVien[] = [{ id: 123 }, { id: 456 }, { id: 82185 }];
          const nhanVienCollection: INhanVien[] = [{ id: 123 }];
          expectedResult = service.addNhanVienToCollectionIfMissing(nhanVienCollection, ...nhanVienArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const nhanVien: INhanVien = { id: 123 };
          const nhanVien2: INhanVien = { id: 456 };
          expectedResult = service.addNhanVienToCollectionIfMissing([], nhanVien, nhanVien2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(nhanVien);
          expect(expectedResult).toContain(nhanVien2);
        });

        it('should accept null and undefined values', () => {
          const nhanVien: INhanVien = { id: 123 };
          expectedResult = service.addNhanVienToCollectionIfMissing([], null, nhanVien, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(nhanVien);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
