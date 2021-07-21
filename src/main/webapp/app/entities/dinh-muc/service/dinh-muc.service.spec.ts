import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IDinhMuc, DinhMuc } from '../dinh-muc.model';

import { DinhMucService } from './dinh-muc.service';

describe('Service Tests', () => {
  describe('DinhMuc Service', () => {
    let service: DinhMucService;
    let httpMock: HttpTestingController;
    let elemDefault: IDinhMuc;
    let expectedResult: IDinhMuc | IDinhMuc[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(DinhMucService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        maMuc: 'AAAAAAA',
        loaiPhi: 'AAAAAAA',
        soTien: 'AAAAAAA',
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

      it('should create a DinhMuc', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new DinhMuc()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a DinhMuc', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            maMuc: 'BBBBBB',
            loaiPhi: 'BBBBBB',
            soTien: 'BBBBBB',
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

      it('should partial update a DinhMuc', () => {
        const patchObject = Object.assign(
          {
            maMuc: 'BBBBBB',
            loaiPhi: 'BBBBBB',
          },
          new DinhMuc()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of DinhMuc', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            maMuc: 'BBBBBB',
            loaiPhi: 'BBBBBB',
            soTien: 'BBBBBB',
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

      it('should delete a DinhMuc', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addDinhMucToCollectionIfMissing', () => {
        it('should add a DinhMuc to an empty array', () => {
          const dinhMuc: IDinhMuc = { id: 123 };
          expectedResult = service.addDinhMucToCollectionIfMissing([], dinhMuc);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(dinhMuc);
        });

        it('should not add a DinhMuc to an array that contains it', () => {
          const dinhMuc: IDinhMuc = { id: 123 };
          const dinhMucCollection: IDinhMuc[] = [
            {
              ...dinhMuc,
            },
            { id: 456 },
          ];
          expectedResult = service.addDinhMucToCollectionIfMissing(dinhMucCollection, dinhMuc);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a DinhMuc to an array that doesn't contain it", () => {
          const dinhMuc: IDinhMuc = { id: 123 };
          const dinhMucCollection: IDinhMuc[] = [{ id: 456 }];
          expectedResult = service.addDinhMucToCollectionIfMissing(dinhMucCollection, dinhMuc);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(dinhMuc);
        });

        it('should add only unique DinhMuc to an array', () => {
          const dinhMucArray: IDinhMuc[] = [{ id: 123 }, { id: 456 }, { id: 26823 }];
          const dinhMucCollection: IDinhMuc[] = [{ id: 123 }];
          expectedResult = service.addDinhMucToCollectionIfMissing(dinhMucCollection, ...dinhMucArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const dinhMuc: IDinhMuc = { id: 123 };
          const dinhMuc2: IDinhMuc = { id: 456 };
          expectedResult = service.addDinhMucToCollectionIfMissing([], dinhMuc, dinhMuc2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(dinhMuc);
          expect(expectedResult).toContain(dinhMuc2);
        });

        it('should accept null and undefined values', () => {
          const dinhMuc: IDinhMuc = { id: 123 };
          expectedResult = service.addDinhMucToCollectionIfMissing([], null, dinhMuc, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(dinhMuc);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
