import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IChiPhi, ChiPhi } from '../chi-phi.model';

import { ChiPhiService } from './chi-phi.service';

describe('Service Tests', () => {
  describe('ChiPhi Service', () => {
    let service: ChiPhiService;
    let httpMock: HttpTestingController;
    let elemDefault: IChiPhi;
    let expectedResult: IChiPhi | IChiPhi[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(ChiPhiService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        loaiChiPhi: 'AAAAAAA',
        soTien: 'AAAAAAA',
        donViTienTe: 'AAAAAAA',
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

      it('should create a ChiPhi', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new ChiPhi()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ChiPhi', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            loaiChiPhi: 'BBBBBB',
            soTien: 'BBBBBB',
            donViTienTe: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a ChiPhi', () => {
        const patchObject = Object.assign(
          {
            soTien: 'BBBBBB',
            donViTienTe: 'BBBBBB',
          },
          new ChiPhi()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ChiPhi', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            loaiChiPhi: 'BBBBBB',
            soTien: 'BBBBBB',
            donViTienTe: 'BBBBBB',
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

      it('should delete a ChiPhi', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addChiPhiToCollectionIfMissing', () => {
        it('should add a ChiPhi to an empty array', () => {
          const chiPhi: IChiPhi = { id: 123 };
          expectedResult = service.addChiPhiToCollectionIfMissing([], chiPhi);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(chiPhi);
        });

        it('should not add a ChiPhi to an array that contains it', () => {
          const chiPhi: IChiPhi = { id: 123 };
          const chiPhiCollection: IChiPhi[] = [
            {
              ...chiPhi,
            },
            { id: 456 },
          ];
          expectedResult = service.addChiPhiToCollectionIfMissing(chiPhiCollection, chiPhi);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a ChiPhi to an array that doesn't contain it", () => {
          const chiPhi: IChiPhi = { id: 123 };
          const chiPhiCollection: IChiPhi[] = [{ id: 456 }];
          expectedResult = service.addChiPhiToCollectionIfMissing(chiPhiCollection, chiPhi);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(chiPhi);
        });

        it('should add only unique ChiPhi to an array', () => {
          const chiPhiArray: IChiPhi[] = [{ id: 123 }, { id: 456 }, { id: 67608 }];
          const chiPhiCollection: IChiPhi[] = [{ id: 123 }];
          expectedResult = service.addChiPhiToCollectionIfMissing(chiPhiCollection, ...chiPhiArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const chiPhi: IChiPhi = { id: 123 };
          const chiPhi2: IChiPhi = { id: 456 };
          expectedResult = service.addChiPhiToCollectionIfMissing([], chiPhi, chiPhi2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(chiPhi);
          expect(expectedResult).toContain(chiPhi2);
        });

        it('should accept null and undefined values', () => {
          const chiPhi: IChiPhi = { id: 123 };
          expectedResult = service.addChiPhiToCollectionIfMissing([], null, chiPhi, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(chiPhi);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
