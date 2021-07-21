import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPhongBan, PhongBan } from '../phong-ban.model';
import { PhongBanService } from '../service/phong-ban.service';

@Injectable({ providedIn: 'root' })
export class PhongBanRoutingResolveService implements Resolve<IPhongBan> {
  constructor(protected service: PhongBanService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPhongBan> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((phongBan: HttpResponse<PhongBan>) => {
          if (phongBan.body) {
            return of(phongBan.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PhongBan());
  }
}
