import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICapBac, CapBac } from '../cap-bac.model';
import { CapBacService } from '../service/cap-bac.service';

@Injectable({ providedIn: 'root' })
export class CapBacRoutingResolveService implements Resolve<ICapBac> {
  constructor(protected service: CapBacService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICapBac> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((capBac: HttpResponse<CapBac>) => {
          if (capBac.body) {
            return of(capBac.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CapBac());
  }
}
