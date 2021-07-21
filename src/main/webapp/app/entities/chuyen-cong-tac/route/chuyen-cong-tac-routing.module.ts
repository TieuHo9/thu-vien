import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ChuyenCongTacComponent } from '../list/chuyen-cong-tac.component';
import { ChuyenCongTacDetailComponent } from '../detail/chuyen-cong-tac-detail.component';
import { ChuyenCongTacUpdateComponent } from '../update/chuyen-cong-tac-update.component';
import { ChuyenCongTacRoutingResolveService } from './chuyen-cong-tac-routing-resolve.service';

const chuyenCongTacRoute: Routes = [
  {
    path: '',
    component: ChuyenCongTacComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ChuyenCongTacDetailComponent,
    resolve: {
      chuyenCongTac: ChuyenCongTacRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ChuyenCongTacUpdateComponent,
    resolve: {
      chuyenCongTac: ChuyenCongTacRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ChuyenCongTacUpdateComponent,
    resolve: {
      chuyenCongTac: ChuyenCongTacRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(chuyenCongTacRoute)],
  exports: [RouterModule],
})
export class ChuyenCongTacRoutingModule {}
