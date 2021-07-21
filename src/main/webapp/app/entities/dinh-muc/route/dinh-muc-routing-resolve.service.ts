import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDinhMuc, DinhMuc } from '../dinh-muc.model';
import { DinhMucService } from '../service/dinh-muc.service';

@Injectable({ providedIn: 'root' })
export class DinhMucRoutingResolveService implements Resolve<IDinhMuc> {
  constructor(protected service: DinhMucService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDinhMuc> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((dinhMuc: HttpResponse<DinhMuc>) => {
          if (dinhMuc.body) {
            return of(dinhMuc.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DinhMuc());
  }
}
