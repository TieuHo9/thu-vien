import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDeXuatThanhToan, DeXuatThanhToan } from '../de-xuat-thanh-toan.model';
import { DeXuatThanhToanService } from '../service/de-xuat-thanh-toan.service';

@Injectable({ providedIn: 'root' })
export class DeXuatThanhToanRoutingResolveService implements Resolve<IDeXuatThanhToan> {
  constructor(protected service: DeXuatThanhToanService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDeXuatThanhToan> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((deXuatThanhToan: HttpResponse<DeXuatThanhToan>) => {
          if (deXuatThanhToan.body) {
            return of(deXuatThanhToan.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DeXuatThanhToan());
  }
}
