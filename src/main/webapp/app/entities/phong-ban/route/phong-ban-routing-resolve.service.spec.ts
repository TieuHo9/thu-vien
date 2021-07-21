jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IPhongBan, PhongBan } from '../phong-ban.model';
import { PhongBanService } from '../service/phong-ban.service';

import { PhongBanRoutingResolveService } from './phong-ban-routing-resolve.service';

describe('Service Tests', () => {
  describe('PhongBan routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: PhongBanRoutingResolveService;
    let service: PhongBanService;
    let resultPhongBan: IPhongBan | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(PhongBanRoutingResolveService);
      service = TestBed.inject(PhongBanService);
      resultPhongBan = undefined;
    });

    describe('resolve', () => {
      it('should return IPhongBan returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultPhongBan = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultPhongBan).toEqual({ id: 123 });
      });

      it('should return new IPhongBan if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultPhongBan = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultPhongBan).toEqual(new PhongBan());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultPhongBan = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultPhongBan).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
