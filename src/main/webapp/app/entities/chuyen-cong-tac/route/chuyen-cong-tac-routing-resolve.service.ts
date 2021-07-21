import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IChuyenCongTac, ChuyenCongTac } from '../chuyen-cong-tac.model';
import { ChuyenCongTacService } from '../service/chuyen-cong-tac.service';

@Injectable({ providedIn: 'root' })
export class ChuyenCongTacRoutingResolveService implements Resolve<IChuyenCongTac> {
  constructor(protected service: ChuyenCongTacService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IChuyenCongTac> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((chuyenCongTac: HttpResponse<ChuyenCongTac>) => {
          if (chuyenCongTac.body) {
            return of(chuyenCongTac.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ChuyenCongTac());
  }
}
