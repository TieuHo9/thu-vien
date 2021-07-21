import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CapBacComponent } from '../list/cap-bac.component';
import { CapBacDetailComponent } from '../detail/cap-bac-detail.component';
import { CapBacUpdateComponent } from '../update/cap-bac-update.component';
import { CapBacRoutingResolveService } from './cap-bac-routing-resolve.service';

const capBacRoute: Routes = [
  {
    path: '',
    component: CapBacComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CapBacDetailComponent,
    resolve: {
      capBac: CapBacRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CapBacUpdateComponent,
    resolve: {
      capBac: CapBacRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CapBacUpdateComponent,
    resolve: {
      capBac: CapBacRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(capBacRoute)],
  exports: [RouterModule],
})
export class CapBacRoutingModule {}
