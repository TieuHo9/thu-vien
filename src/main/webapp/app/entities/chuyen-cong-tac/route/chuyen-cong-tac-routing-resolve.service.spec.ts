jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IChuyenCongTac, ChuyenCongTac } from '../chuyen-cong-tac.model';
import { ChuyenCongTacService } from '../service/chuyen-cong-tac.service';

import { ChuyenCongTacRoutingResolveService } from './chuyen-cong-tac-routing-resolve.service';

describe('Service Tests', () => {
  describe('ChuyenCongTac routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: ChuyenCongTacRoutingResolveService;
    let service: ChuyenCongTacService;
    let resultChuyenCongTac: IChuyenCongTac | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(ChuyenCongTacRoutingResolveService);
      service = TestBed.inject(ChuyenCongTacService);
      resultChuyenCongTac = undefined;
    });

    describe('resolve', () => {
      it('should return IChuyenCongTac returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultChuyenCongTac = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultChuyenCongTac).toEqual({ id: 123 });
      });

      it('should return new IChuyenCongTac if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultChuyenCongTac = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultChuyenCongTac).toEqual(new ChuyenCongTac());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultChuyenCongTac = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultChuyenCongTac).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
