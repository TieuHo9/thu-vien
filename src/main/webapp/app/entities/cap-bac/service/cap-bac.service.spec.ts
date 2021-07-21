import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICapBac, CapBac } from '../cap-bac.model';

import { CapBacService } from './cap-bac.service';

describe('Service Tests', () => {
  describe('CapBac Service', () => {
    let service: CapBacService;
    let httpMock: HttpTestingController;
    let elemDefault: ICapBac;
    let expectedResult: ICapBac | ICapBac[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(CapBacService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        tenCap: 'AAAAAAA',
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

      it('should create a CapBac', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new CapBac()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CapBac', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            tenCap: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a CapBac', () => {
        const patchObject = Object.assign(
          {
            tenCap: 'BBBBBB',
          },
          new CapBac()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of CapBac', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            tenCap: 'BBBBBB',
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

      it('should delete a CapBac', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addCapBacToCollectionIfMissing', () => {
        it('should add a CapBac to an empty array', () => {
          const capBac: ICapBac = { id: 123 };
          expectedResult = service.addCapBacToCollectionIfMissing([], capBac);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(capBac);
        });

        it('should not add a CapBac to an array that contains it', () => {
          const capBac: ICapBac = { id: 123 };
          const capBacCollection: ICapBac[] = [
            {
              ...capBac,
            },
            { id: 456 },
          ];
          expectedResult = service.addCapBacToCollectionIfMissing(capBacCollection, capBac);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a CapBac to an array that doesn't contain it", () => {
          const capBac: ICapBac = { id: 123 };
          const capBacCollection: ICapBac[] = [{ id: 456 }];
          expectedResult = service.addCapBacToCollectionIfMissing(capBacCollection, capBac);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(capBac);
        });

        it('should add only unique CapBac to an array', () => {
          const capBacArray: ICapBac[] = [{ id: 123 }, { id: 456 }, { id: 83782 }];
          const capBacCollection: ICapBac[] = [{ id: 123 }];
          expectedResult = service.addCapBacToCollectionIfMissing(capBacCollection, ...capBacArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const capBac: ICapBac = { id: 123 };
          const capBac2: ICapBac = { id: 456 };
          expectedResult = service.addCapBacToCollectionIfMissing([], capBac, capBac2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(capBac);
          expect(expectedResult).toContain(capBac2);
        });

        it('should accept null and undefined values', () => {
          const capBac: ICapBac = { id: 123 };
          expectedResult = service.addCapBacToCollectionIfMissing([], null, capBac, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(capBac);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
