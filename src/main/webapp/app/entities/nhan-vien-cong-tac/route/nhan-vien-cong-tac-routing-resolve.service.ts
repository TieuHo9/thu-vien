import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { INhanVienCongTac, NhanVienCongTac } from '../nhan-vien-cong-tac.model';
import { NhanVienCongTacService } from '../service/nhan-vien-cong-tac.service';

@Injectable({ providedIn: 'root' })
export class NhanVienCongTacRoutingResolveService implements Resolve<INhanVienCongTac> {
  constructor(protected service: NhanVienCongTacService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INhanVienCongTac> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((nhanVienCongTac: HttpResponse<NhanVienCongTac>) => {
          if (nhanVienCongTac.body) {
            return of(nhanVienCongTac.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new NhanVienCongTac());
  }
}
