jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IChiPhi, ChiPhi } from '../chi-phi.model';
import { ChiPhiService } from '../service/chi-phi.service';

import { ChiPhiRoutingResolveService } from './chi-phi-routing-resolve.service';

describe('Service Tests', () => {
  describe('ChiPhi routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: ChiPhiRoutingResolveService;
    let service: ChiPhiService;
    let resultChiPhi: IChiPhi | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(ChiPhiRoutingResolveService);
      service = TestBed.inject(ChiPhiService);
      resultChiPhi = undefined;
    });

    describe('resolve', () => {
      it('should return IChiPhi returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultChiPhi = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultChiPhi).toEqual({ id: 123 });
      });

      it('should return new IChiPhi if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultChiPhi = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultChiPhi).toEqual(new ChiPhi());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultChiPhi = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultChiPhi).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
