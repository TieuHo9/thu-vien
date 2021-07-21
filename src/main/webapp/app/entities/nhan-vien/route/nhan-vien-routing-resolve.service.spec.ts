jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { INhanVien, NhanVien } from '../nhan-vien.model';
import { NhanVienService } from '../service/nhan-vien.service';

import { NhanVienRoutingResolveService } from './nhan-vien-routing-resolve.service';

describe('Service Tests', () => {
  describe('NhanVien routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: NhanVienRoutingResolveService;
    let service: NhanVienService;
    let resultNhanVien: INhanVien | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(NhanVienRoutingResolveService);
      service = TestBed.inject(NhanVienService);
      resultNhanVien = undefined;
    });

    describe('resolve', () => {
      it('should return INhanVien returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultNhanVien = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultNhanVien).toEqual({ id: 123 });
      });

      it('should return new INhanVien if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultNhanVien = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultNhanVien).toEqual(new NhanVien());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultNhanVien = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultNhanVien).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
