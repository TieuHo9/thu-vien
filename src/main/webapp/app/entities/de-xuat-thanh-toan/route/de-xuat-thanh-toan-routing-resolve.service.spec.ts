jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IDeXuatThanhToan, DeXuatThanhToan } from '../de-xuat-thanh-toan.model';
import { DeXuatThanhToanService } from '../service/de-xuat-thanh-toan.service';

import { DeXuatThanhToanRoutingResolveService } from './de-xuat-thanh-toan-routing-resolve.service';

describe('Service Tests', () => {
  describe('DeXuatThanhToan routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: DeXuatThanhToanRoutingResolveService;
    let service: DeXuatThanhToanService;
    let resultDeXuatThanhToan: IDeXuatThanhToan | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(DeXuatThanhToanRoutingResolveService);
      service = TestBed.inject(DeXuatThanhToanService);
      resultDeXuatThanhToan = undefined;
    });

    describe('resolve', () => {
      it('should return IDeXuatThanhToan returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDeXuatThanhToan = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultDeXuatThanhToan).toEqual({ id: 123 });
      });

      it('should return new IDeXuatThanhToan if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDeXuatThanhToan = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultDeXuatThanhToan).toEqual(new DeXuatThanhToan());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDeXuatThanhToan = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultDeXuatThanhToan).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
