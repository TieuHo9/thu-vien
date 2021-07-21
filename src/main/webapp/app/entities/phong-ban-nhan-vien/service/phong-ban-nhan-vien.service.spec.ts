import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPhongBanNhanVien, PhongBanNhanVien } from '../phong-ban-nhan-vien.model';

import { PhongBanNhanVienService } from './phong-ban-nhan-vien.service';

describe('Service Tests', () => {
  describe('PhongBanNhanVien Service', () => {
    let service: PhongBanNhanVienService;
    let httpMock: HttpTestingController;
    let elemDefault: IPhongBanNhanVien;
    let expectedResult: IPhongBanNhanVien | IPhongBanNhanVien[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(PhongBanNhanVienService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        maPhong: 'AAAAAAA',
        maNhanVien: 'AAAAAAA',
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

      it('should create a PhongBanNhanVien', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new PhongBanNhanVien()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a PhongBanNhanVien', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            maPhong: 'BBBBBB',
            maNhanVien: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a PhongBanNhanVien', () => {
        const patchObject = Object.assign({}, new PhongBanNhanVien());

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of PhongBanNhanVien', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            maPhong: 'BBBBBB',
            maNhanVien: 'BBBBBB',
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

      it('should delete a PhongBanNhanVien', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addPhongBanNhanVienToCollectionIfMissing', () => {
        it('should add a PhongBanNhanVien to an empty array', () => {
          const phongBanNhanVien: IPhongBanNhanVien = { id: 123 };
          expectedResult = service.addPhongBanNhanVienToCollectionIfMissing([], phongBanNhanVien);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(phongBanNhanVien);
        });

        it('should not add a PhongBanNhanVien to an array that contains it', () => {
          const phongBanNhanVien: IPhongBanNhanVien = { id: 123 };
          const phongBanNhanVienCollection: IPhongBanNhanVien[] = [
            {
              ...phongBanNhanVien,
            },
            { id: 456 },
          ];
          expectedResult = service.addPhongBanNhanVienToCollectionIfMissing(phongBanNhanVienCollection, phongBanNhanVien);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a PhongBanNhanVien to an array that doesn't contain it", () => {
          const phongBanNhanVien: IPhongBanNhanVien = { id: 123 };
          const phongBanNhanVienCollection: IPhongBanNhanVien[] = [{ id: 456 }];
          expectedResult = service.addPhongBanNhanVienToCollectionIfMissing(phongBanNhanVienCollection, phongBanNhanVien);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(phongBanNhanVien);
        });

        it('should add only unique PhongBanNhanVien to an array', () => {
          const phongBanNhanVienArray: IPhongBanNhanVien[] = [{ id: 123 }, { id: 456 }, { id: 37074 }];
          const phongBanNhanVienCollection: IPhongBanNhanVien[] = [{ id: 123 }];
          expectedResult = service.addPhongBanNhanVienToCollectionIfMissing(phongBanNhanVienCollection, ...phongBanNhanVienArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const phongBanNhanVien: IPhongBanNhanVien = { id: 123 };
          const phongBanNhanVien2: IPhongBanNhanVien = { id: 456 };
          expectedResult = service.addPhongBanNhanVienToCollectionIfMissing([], phongBanNhanVien, phongBanNhanVien2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(phongBanNhanVien);
          expect(expectedResult).toContain(phongBanNhanVien2);
        });

        it('should accept null and undefined values', () => {
          const phongBanNhanVien: IPhongBanNhanVien = { id: 123 };
          expectedResult = service.addPhongBanNhanVienToCollectionIfMissing([], null, phongBanNhanVien, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(phongBanNhanVien);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
