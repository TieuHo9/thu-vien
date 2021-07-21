import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DinhMucComponent } from '../list/dinh-muc.component';
import { DinhMucDetailComponent } from '../detail/dinh-muc-detail.component';
import { DinhMucUpdateComponent } from '../update/dinh-muc-update.component';
import { DinhMucRoutingResolveService } from './dinh-muc-routing-resolve.service';

const dinhMucRoute: Routes = [
  {
    path: '',
    component: DinhMucComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DinhMucDetailComponent,
    resolve: {
      dinhMuc: DinhMucRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DinhMucUpdateComponent,
    resolve: {
      dinhMuc: DinhMucRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DinhMucUpdateComponent,
    resolve: {
      dinhMuc: DinhMucRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(dinhMucRoute)],
  exports: [RouterModule],
})
export class DinhMucRoutingModule {}
