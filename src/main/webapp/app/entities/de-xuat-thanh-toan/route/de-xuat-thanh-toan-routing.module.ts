import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DeXuatThanhToanComponent } from '../list/de-xuat-thanh-toan.component';
import { DeXuatThanhToanDetailComponent } from '../detail/de-xuat-thanh-toan-detail.component';
import { DeXuatThanhToanUpdateComponent } from '../update/de-xuat-thanh-toan-update.component';
import { DeXuatThanhToanRoutingResolveService } from './de-xuat-thanh-toan-routing-resolve.service';

const deXuatThanhToanRoute: Routes = [
  {
    path: '',
    component: DeXuatThanhToanComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DeXuatThanhToanDetailComponent,
    resolve: {
      deXuatThanhToan: DeXuatThanhToanRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DeXuatThanhToanUpdateComponent,
    resolve: {
      deXuatThanhToan: DeXuatThanhToanRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DeXuatThanhToanUpdateComponent,
    resolve: {
      deXuatThanhToan: DeXuatThanhToanRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(deXuatThanhToanRoute)],
  exports: [RouterModule],
})
export class DeXuatThanhToanRoutingModule {}
