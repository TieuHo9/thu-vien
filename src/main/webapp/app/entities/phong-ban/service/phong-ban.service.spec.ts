import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPhongBan, PhongBan } from '../phong-ban.model';

import { PhongBanService } from './phong-ban.service';

describe('Service Tests', () => {
  describe('PhongBan Service', () => {
    let service: PhongBanService;
    let httpMock: HttpTestingController;
    let elemDefault: IPhongBan;
    let expectedResult: IPhongBan | IPhongBan[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(PhongBanService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        maPhong: 'AAAAAAA',
        tenPhong: 'AAAAAAA',
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

      it('should create a PhongBan', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new PhongBan()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a PhongBan', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            maPhong: 'BBBBBB',
            tenPhong: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a PhongBan', () => {
        const patchObject = Object.assign(
          {
            maPhong: 'BBBBBB',
          },
          new PhongBan()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of PhongBan', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            maPhong: 'BBBBBB',
            tenPhong: 'BBBBBB',
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

      it('should delete a PhongBan', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addPhongBanToCollectionIfMissing', () => {
        it('should add a PhongBan to an empty array', () => {
          const phongBan: IPhongBan = { id: 123 };
          expectedResult = service.addPhongBanToCollectionIfMissing([], phongBan);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(phongBan);
        });

        it('should not add a PhongBan to an array that contains it', () => {
          const phongBan: IPhongBan = { id: 123 };
          const phongBanCollection: IPhongBan[] = [
            {
              ...phongBan,
            },
            { id: 456 },
          ];
          expectedResult = service.addPhongBanToCollectionIfMissing(phongBanCollection, phongBan);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a PhongBan to an array that doesn't contain it", () => {
          const phongBan: IPhongBan = { id: 123 };
          const phongBanCollection: IPhongBan[] = [{ id: 456 }];
          expectedResult = service.addPhongBanToCollectionIfMissing(phongBanCollection, phongBan);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(phongBan);
        });

        it('should add only unique PhongBan to an array', () => {
          const phongBanArray: IPhongBan[] = [{ id: 123 }, { id: 456 }, { id: 92356 }];
          const phongBanCollection: IPhongBan[] = [{ id: 123 }];
          expectedResult = service.addPhongBanToCollectionIfMissing(phongBanCollection, ...phongBanArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const phongBan: IPhongBan = { id: 123 };
          const phongBan2: IPhongBan = { id: 456 };
          expectedResult = service.addPhongBanToCollectionIfMissing([], phongBan, phongBan2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(phongBan);
          expect(expectedResult).toContain(phongBan2);
        });

        it('should accept null and undefined values', () => {
          const phongBan: IPhongBan = { id: 123 };
          expectedResult = service.addPhongBanToCollectionIfMissing([], null, phongBan, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(phongBan);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
