import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { XetDuyetComponent } from '../list/xet-duyet';
import { XetDuyetUpdateComponent } from '../update/xet-duyet-update';
import { DeXuatThanhToanRoutingResolveService } from './de-xuat-thanh-toan-routing-resolve.service';

const xetDuyetRoute: Routes = [
  {
    path: '',
    component: XetDuyetComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: XetDuyetUpdateComponent,
    resolve: {
      deXuatThanhToan: DeXuatThanhToanRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: XetDuyetUpdateComponent,
    resolve: {
      deXuatThanhToan: DeXuatThanhToanRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(xetDuyetRoute)],
  exports: [RouterModule],
})
export class DeXuatThanhToanRoutingModule {}
