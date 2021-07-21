import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { NhanVienCongTacComponent } from '../list/nhan-vien-cong-tac.component';
import { NhanVienCongTacDetailComponent } from '../detail/nhan-vien-cong-tac-detail.component';
import { NhanVienCongTacUpdateComponent } from '../update/nhan-vien-cong-tac-update.component';
import { NhanVienCongTacRoutingResolveService } from './nhan-vien-cong-tac-routing-resolve.service';

const nhanVienCongTacRoute: Routes = [
  {
    path: '',
    component: NhanVienCongTacComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NhanVienCongTacDetailComponent,
    resolve: {
      nhanVienCongTac: NhanVienCongTacRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: NhanVienCongTacUpdateComponent,
    resolve: {
      nhanVienCongTac: NhanVienCongTacRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: NhanVienCongTacUpdateComponent,
    resolve: {
      nhanVienCongTac: NhanVienCongTacRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(nhanVienCongTacRoute)],
  exports: [RouterModule],
})
export class NhanVienCongTacRoutingModule {}
