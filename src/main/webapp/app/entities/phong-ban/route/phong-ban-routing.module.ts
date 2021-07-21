import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PhongBanComponent } from '../list/phong-ban.component';
import { PhongBanDetailComponent } from '../detail/phong-ban-detail.component';
import { PhongBanUpdateComponent } from '../update/phong-ban-update.component';
import { PhongBanRoutingResolveService } from './phong-ban-routing-resolve.service';

const phongBanRoute: Routes = [
  {
    path: '',
    component: PhongBanComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PhongBanDetailComponent,
    resolve: {
      phongBan: PhongBanRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PhongBanUpdateComponent,
    resolve: {
      phongBan: PhongBanRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PhongBanUpdateComponent,
    resolve: {
      phongBan: PhongBanRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(phongBanRoute)],
  exports: [RouterModule],
})
export class PhongBanRoutingModule {}
