import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IChiPhi, ChiPhi } from '../chi-phi.model';
import { ChiPhiService } from '../service/chi-phi.service';

@Injectable({ providedIn: 'root' })
export class ChiPhiRoutingResolveService implements Resolve<IChiPhi> {
  constructor(protected service: ChiPhiService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IChiPhi> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((chiPhi: HttpResponse<ChiPhi>) => {
          if (chiPhi.body) {
            return of(chiPhi.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ChiPhi());
  }
}
