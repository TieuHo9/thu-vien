import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ChiPhiComponent } from '../list/chi-phi.component';
import { ChiPhiDetailComponent } from '../detail/chi-phi-detail.component';
import { ChiPhiUpdateComponent } from '../update/chi-phi-update.component';
import { ChiPhiRoutingResolveService } from './chi-phi-routing-resolve.service';

const chiPhiRoute: Routes = [
  {
    path: '',
    component: ChiPhiComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ChiPhiDetailComponent,
    resolve: {
      chiPhi: ChiPhiRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ChiPhiUpdateComponent,
    resolve: {
      chiPhi: ChiPhiRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ChiPhiUpdateComponent,
    resolve: {
      chiPhi: ChiPhiRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(chiPhiRoute)],
  exports: [RouterModule],
})
export class ChiPhiRoutingModule {}
