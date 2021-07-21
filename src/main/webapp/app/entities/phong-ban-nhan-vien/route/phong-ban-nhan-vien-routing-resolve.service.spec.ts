jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IPhongBanNhanVien, PhongBanNhanVien } from '../phong-ban-nhan-vien.model';
import { PhongBanNhanVienService } from '../service/phong-ban-nhan-vien.service';

import { PhongBanNhanVienRoutingResolveService } from './phong-ban-nhan-vien-routing-resolve.service';

describe('Service Tests', () => {
  describe('PhongBanNhanVien routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: PhongBanNhanVienRoutingResolveService;
    let service: PhongBanNhanVienService;
    let resultPhongBanNhanVien: IPhongBanNhanVien | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(PhongBanNhanVienRoutingResolveService);
      service = TestBed.inject(PhongBanNhanVienService);
      resultPhongBanNhanVien = undefined;
    });

    describe('resolve', () => {
      it('should return IPhongBanNhanVien returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultPhongBanNhanVien = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultPhongBanNhanVien).toEqual({ id: 123 });
      });

      it('should return new IPhongBanNhanVien if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultPhongBanNhanVien = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultPhongBanNhanVien).toEqual(new PhongBanNhanVien());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultPhongBanNhanVien = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultPhongBanNhanVien).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
